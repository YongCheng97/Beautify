package ejb.session.stateless;

import entity.Booking;
import entity.Category;
import entity.Service;
import entity.ServiceProvider;
import entity.Tag;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewServiceException;
import util.exception.DeleteServiceException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.ServiceExistException;
import util.exception.ServiceNotFoundException;
import util.exception.ServiceProviderNotFoundException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProductException;
import util.exception.UpdateServiceException;

@Stateless
@Local(ServiceSessionBeanLocal.class)

public class ServiceSessionBean implements ServiceSessionBeanLocal {

    @EJB
    private BookingSessionBeanLocal bookingSessionBean;

    @EJB(name = "TagsSessionBeanLocal")
    private TagsSessionBeanLocal tagsSessionBeanLocal;

    @EJB
    private ServiceProviderSessionBeanLocal serviceProviderSessionBeanLocal;

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;

    @EJB
    private CategorySessionBeanLocal categorySessionBeanLocal;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ServiceSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Service createNewService(Service newService, Long serviceProviderId, Long categoryId, List<Long> tagIds) throws ServiceExistException, UnknownPersistenceException, InputDataValidationException, CreateNewServiceException {
        Set<ConstraintViolation<Service>> constraintViolations = validator.validate(newService);

        if (constraintViolations.isEmpty()) {
            try {

                if (categoryId == null) {
                    throw new CreateNewServiceException("The new service must be associated a leaf category");
                }

                Category category = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

                if (!category.getSubCategoryEntities().isEmpty()) {
                    throw new CreateNewServiceException("Selected category for the new service is not a leaf category");
                }

                newService.setCategory(category);
                category.getServices().add(newService);

                if (serviceProviderId == null) {
                    throw new CreateNewServiceException("The new service must be assoicated a service provider");
                }

                ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.retrieveServiceProviderById(serviceProviderId);

                newService.setServiceProvider(serviceProvider);
                serviceProvider.getServices().add(newService);

                em.persist(newService);

                if (tagIds != null && (!tagIds.isEmpty())) {
                    for (Long tagId : tagIds) {
                        Tag tag = tagsSessionBeanLocal.retrieveTagByTagId(tagId);
                        newService.addTag(tag);
                    }
                }

                em.flush();

                return newService;

            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new ServiceExistException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } catch (CategoryNotFoundException | ServiceProviderNotFoundException | TagNotFoundException ex) {
                throw new CreateNewServiceException("An error has occurred while creating the new service: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }

    }

    @Override
    public List<Service> retrieveAllServices() {
        Query query = em.createQuery("SELECT s FROM Service s WHERE s.isDeleted = FALSE ORDER BY s.price ASC");
        List<Service> services = query.getResultList();

        for (Service service : services) {
            service.getBookings().size();
            service.getPromotions().size();
            service.getTags().size();

            service.getCategory();
            service.getServiceProvider();
        }

        return services;
    }

    @Override
    public List<Service> retrieveAllServicesByServiceProvider(Long serviceProviderId) {
        Query query = em.createQuery("SELECT s FROM Service s WHERE s.isDeleted = FALSE ORDER BY s.price ASC");
        List<Service> services = query.getResultList();
        List<Service> newServices = new ArrayList<>();

        for (Service service : services) {
            if (service.getServiceProvider().getServiceProviderId() == serviceProviderId) {
                newServices.add(service);
            }
        }

        for (Service service : newServices) {
            service.getBookings().size();
            service.getPromotions().size();
            service.getTags().size();

            service.getCategory();
            service.getServiceProvider();
        }

        return newServices;
    }

    @Override
    public List<Service> retrieveAllServicesFromCategory(Long categoryId) {
        Query query = em.createQuery("SELECT s FROM Service s WHERE s.category.categoryId = :categoryId AND s.isDeleted = FALSE ORDER BY s.price ASC");
        query.setParameter("categoryId", categoryId);
        List<Service> services = query.getResultList();

        for (Service service : services) {
            service.getBookings().size();
            service.getPromotions().size();
            service.getTags().size();

            service.getCategory();
            service.getServiceProvider();
        }

        return services;
    }

    @Override
    public Service retrieveServiceByServiceId(Long serviceId) throws ServiceNotFoundException {
        Service service = em.find(Service.class, serviceId);

        if (service != null) {
            service.getBookings().size();
            service.getPromotions().size();
            service.getTags().size();

            service.getCategory();
            service.getServiceProvider();

            return service;
        } else {
            throw new ServiceNotFoundException("Service ID " + serviceId + " does not exist!");
        }
    }

    @Override
    public List<Service> filterServicesByName(String searchString, Long categoryId) {
        Query query = em.createQuery("SELECT s FROM Service s WHERE s.serviceName LIKE :inSearchString AND s.isDeleted = FALSE ORDER BY s.price ASC");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<Service> services = query.getResultList();
        List<Service> newServices = new ArrayList<>();

        for (Service service : services) {
            if (service.getCategory().getCategoryId() == categoryId) {
                newServices.add(service);
            }
        }

        for (Service service : newServices) {
            service.getBookings().size();
            service.getPromotions().size();
            service.getTags().size();

            service.getCategory();
            service.getServiceProvider();
        }

        return newServices;
    }

    @Override
    public List<Service> filterServicesByMinimumPrice(BigDecimal minPrice, Long categoryId) {
        Query query = em.createQuery("SELECT s FROM Service s WHERE s.price >= :minPrice AND s.isDeleted = FALSE ORDER BY s.price ASC");
        query.setParameter("minPrice", minPrice);
        List<Service> services = query.getResultList();
        List<Service> newServices = new ArrayList<>();

        for (Service service : services) {
            if (service.getCategory().getCategoryId() == categoryId) {
                newServices.add(service);
            }
        }

        for (Service service : newServices) {
            service.getBookings().size();
            service.getPromotions().size();
            service.getTags().size();

            service.getCategory();
            service.getServiceProvider();
        }

        return newServices;
    }

    @Override
    public List<Service> filterServicesByMaximumPrice(BigDecimal maxPrice, Long categoryId) {
        Query query = em.createQuery("SELECT s FROM Service s WHERE s.price <= :maxPrice AND s.isDeleted = FALSE ORDER BY s.price ASC");
        query.setParameter("maxPrice", maxPrice);
        List<Service> services = query.getResultList();
        List<Service> newServices = new ArrayList<>();

        for (Service service : services) {
            if (service.getCategory().getCategoryId() == categoryId) {
                newServices.add(service);
            }
        }

        for (Service service : newServices) {
            service.getBookings().size();
            service.getPromotions().size();
            service.getTags().size();

            service.getCategory();
            service.getServiceProvider();
        }

        return newServices;
    }

    @Override
    public List<Service> filterServicesByCategory(Long categoryId) throws CategoryNotFoundException {
        List<Service> services = new ArrayList<>();
        Category category = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

        if (category.getSubCategoryEntities().isEmpty()) {
            services = category.getServices();
        } else {
            for (Category subCategory : category.getSubCategoryEntities()) {
                services.addAll(addSubCategoryServices(subCategory));
            }
        }

        for (Service service : services) {
            service.getBookings().size();
            service.getPromotions().size();
            service.getTags().size();

            service.getCategory();
            service.getServiceProvider();
        }

        Collections.sort(services, new Comparator<Service>() {
            public int compare(Service se1, Service se2) {
                return se1.getServiceName().compareTo(se2.getServiceName());
            }
        });

        return services;
    }

    @Override
    public List<Service> filterServicesByTags(List<Long> tagIds, String condition, Long categoryId) {
        List<Service> services = new ArrayList<>();

        if (tagIds == null || tagIds.isEmpty() || (!condition.equals("AND") && !condition.equals("OR"))) {
            return services;
        } else {
            if (condition.equals("OR")) {
                Query query = em.createQuery("SELECT DISTINCT s FROM Service s, IN (s.tags) t WHERE t.tagId IN :inTagIds ORDER BY s.serviceName ASC");
                query.setParameter("inTagIds", tagIds);
                services = query.getResultList();
            } else // AND
            {
                String selectClause = "SELECT s FROM Service s";
                String whereClause = "";
                Boolean firstTag = true;
                Integer tagCount = 1;

                for (Long tagId : tagIds) {
                    selectClause += ", IN (s.tags) t" + tagCount;

                    if (firstTag) {
                        whereClause = "WHERE t1.tagId = " + tagId;
                        firstTag = false;
                    } else {
                        whereClause += " AND t" + tagCount + ".tagId = " + tagId;
                    }

                    tagCount++;
                }

                String jpql = selectClause + " " + whereClause + " ORDER BY s.serviceName ASC";
                Query query = em.createQuery(jpql);
                services = query.getResultList();
            }

            List<Service> newServices = new ArrayList<>();

            for (Service service : services) {
                if (service.getCategory().getCategoryId() == categoryId && service.getIsDeleted() == false) {
                    newServices.add(service);
                }
            }

            for (Service service : newServices) {
                service.getCategory();
                service.getTags().size();
            }

            Collections.sort(newServices, new Comparator<Service>() {
                public int compare(Service se1, Service se2) {
                    return se1.getServiceName().compareTo(se1.getServiceName());
                }
            });

            return newServices;
        }
    }

    @Override
    public void updateService(Service service, Long categoryId, List<Long> tagIds) throws ServiceNotFoundException, CategoryNotFoundException, TagNotFoundException, UpdateServiceException, InputDataValidationException {
        if (service != null && service.getServiceId() != null) {
            Set<ConstraintViolation<Service>> constraintViolations = validator.validate(service);

            if (constraintViolations.isEmpty()) {

                Service serviceToUpdate = retrieveServiceByServiceId(service.getServiceId());
                System.out.println(categoryId);
                if (categoryId != null && (!serviceToUpdate.getCategory().getCategoryId().equals(categoryId))) {
                    Category categoryEntityToUpdate = categorySessionBeanLocal.retrieveCategoryByCategoryId(categoryId);

                    if (!categoryEntityToUpdate.getSubCategoryEntities().isEmpty()) {
                        throw new UpdateServiceException("Selected category for the new service is not a leaf category");
                    }
                    System.out.println(categoryEntityToUpdate);
                    serviceToUpdate.setCategory(categoryEntityToUpdate);
                }

                if (tagIds != null) {
                    for (Tag tagEntity : serviceToUpdate.getTags()) {
                        tagEntity.getServices().remove(serviceToUpdate);
                    }

                    serviceToUpdate.getTags().clear();

                    for (Long tagId : tagIds) {
                        try {
                            Tag tagEntity = tagsSessionBeanLocal.retrieveTagByTagId(tagId);
                            serviceToUpdate.addTag(tagEntity);
                        } catch (TagNotFoundException ex) {
                            Logger.getLogger(ServiceSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                serviceToUpdate.setDescription(service.getDescription());
                serviceToUpdate.setServiceName(service.getServiceName());
                serviceToUpdate.setPrice(service.getPrice());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ServiceNotFoundException("Service ID not provided for service to be updated");
        }
    }

    @Override
    public void deleteService(Long serviceId) throws ServiceNotFoundException, DeleteServiceException {
        Service serviceToRemove = retrieveServiceByServiceId(serviceId);

        serviceToRemove.getCategory().getServices().remove(serviceToRemove);

        for (Tag tag : serviceToRemove.getTags()) {
            tag.getServices().remove(serviceToRemove);
        }

        serviceToRemove.getTags().clear();

        serviceToRemove.setIsDeleted(true);
    }

    private List<Service> addSubCategoryServices(Category categoryEntity) {
        List<Service> services = new ArrayList<>();

        if (categoryEntity.getSubCategoryEntities().isEmpty()) {
            return categoryEntity.getServices();
        } else {
            for (Category subCategoryEntity : categoryEntity.getSubCategoryEntities()) {
                services.addAll(addSubCategoryServices(subCategoryEntity));
            }

            return services;
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Service>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
