package ejb.session.stateless;

import entity.ServiceProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.DeleteServiceProviderException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ServiceProviderExistException;
import util.exception.ServiceProviderNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateServiceProviderException;
import util.security.CryptographicHelper;

@Stateless
@Local(ServiceProviderSessionBeanLocal.class)

public class ServiceProviderSessionBean implements ServiceProviderSessionBeanLocal {

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ServiceProviderSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewServiceProvider(ServiceProvider newServiceProvider) throws ServiceProviderExistException, UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<ServiceProvider>> constraintViolations = validator.validate(newServiceProvider);

            if (constraintViolations.isEmpty()) {
                em.persist(newServiceProvider);
                em.flush();

                return newServiceProvider.getServiceProviderId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new ServiceProviderExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public List<ServiceProvider> retrieveAllServiceProviders() {
        Query query = em.createQuery("SELECT s FROM ServiceProvider s");

        return query.getResultList();
    }

    @Override
    public ServiceProvider retrieveServiceProviderById(Long serviceProviderId) throws ServiceProviderNotFoundException {
        ServiceProvider serviceProvider = em.find(ServiceProvider.class, serviceProviderId);

        if (serviceProvider != null) {
            return serviceProvider;
        } else {
            throw new ServiceProviderNotFoundException("Service Provider ID " + serviceProviderId + " does not exist!");
        }
    }

    @Override
    public ServiceProvider retrieveServiceProviderByName(String name) throws ServiceProviderNotFoundException {
        Query query = em.createQuery("SELECT s FROM ServiceProvider s WHERE s.name = :inName");
        query.setParameter("inName", name);

        try {
            return (ServiceProvider) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new ServiceProviderNotFoundException("Service Provider name " + name + " does not exist!");
        }
    }

    @Override
    public ServiceProvider serviceProviderLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            ServiceProvider serviceProvider = retrieveServiceProviderByName(username);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + serviceProvider.getSalt()));

            if (serviceProvider.getPassword().equals(passwordHash)) {
                serviceProvider.getCreditCards().size();
                serviceProvider.getProducts().size();

                return serviceProvider;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (ServiceProviderNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    @Override
    public void updateStaff(ServiceProvider serviceProvider) throws ServiceProviderNotFoundException, UpdateServiceProviderException, InputDataValidationException {
        if (serviceProvider != null && serviceProvider.getServiceProviderId() != null) {
            Set<ConstraintViolation<ServiceProvider>> constraintViolations = validator.validate(serviceProvider);

            if (constraintViolations.isEmpty()) {
                ServiceProvider serviceProviderToUpdate = retrieveServiceProviderById(serviceProvider.getServiceProviderId());

                if (serviceProviderToUpdate.getName().equals(serviceProvider.getName())) {
                    serviceProviderToUpdate.setEmail(serviceProvider.getEmail());
                    serviceProviderToUpdate.setAddress(serviceProvider.getAddress());
                    serviceProviderToUpdate.setOpeningHours(serviceProvider.getOpeningHours());
                    // Username and password are deliberately NOT updated to demonstrate that client is not allowed to update account credential through this business method
                } else {
                    throw new UpdateServiceProviderException("Name of service provider record to be updated does not match the existing record");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ServiceProviderNotFoundException("Service Provider ID not provided for service provider to be updated");
        }
    }

    @Override
    public void deleteServiceProvider(Long serviceProviderId) throws ServiceProviderNotFoundException, DeleteServiceProviderException {
        ServiceProvider serviceProviderToRemove = retrieveServiceProviderById(serviceProviderId);

        //if (serviceProviderToRemove.getSaleTransactionEntities().isEmpty()) {
        em.remove(serviceProviderToRemove);
        //} else {
        // New in v4.1 to prevent deleting staff with existing sale transaction(s)
        throw new DeleteServiceProviderException("Staff ID " + serviceProviderId + " is associated with existing sale transaction(s) and cannot be deleted!");
        //}
    }

    @Override
    public List<ServiceProvider> searchServiceProviderByName(String searchString) {
        Query query = em.createQuery("SELECT s FROM ServiceProvider s WHERE s.name LIKE :inSearchString");
        query.setParameter("inSearchString", "%" + searchString + "%");
        List<ServiceProvider> serviceProviders = query.getResultList();

        for (ServiceProvider serviceProvider : serviceProviders) {
            serviceProvider.getServices().size();
            serviceProvider.getCreditCards().size();
            serviceProvider.getProducts().size();
        }

        return serviceProviders;
    }

    @Override
    public List<ServiceProvider> filterServiceProviderByTags(List<Long> tagIds, String condition) {
        List<ServiceProvider> providers = new ArrayList<>();

        if (tagIds == null || tagIds.isEmpty() || (!condition.equals("AND") && !condition.equals("OR"))) {
            return providers;
        } else {
            if (condition.equals("OR")) {
                Query query = em.createQuery("SELECT DISTINCT p FROM ServiceProvider p, IN (p.services) s, IN (s.tags) te WHERE te.tagId IN :inTagIds");
                query.setParameter("inTagIds", tagIds);
                providers = query.getResultList();

                Query query2 = em.createQuery("SELECT DISTINCT p FROM ServiceProvider p, IN (p.products) pe, IN (pe.tags) te WHERE te.tagId IN :inTagIds");
                query2.setParameter("inTagIds", tagIds);
                List<ServiceProvider> result = query2.getResultList();
                for (ServiceProvider p : result) {
                    providers.add(p);
                }
            } else // AND
            {
                String selectClause = "SELECT p FROM ServiceProvider p";
                String whereClause = "";
                Boolean firstTag = true;
                Integer tagCount = 1;

                for (Long tagId : tagIds) {
                    selectClause += ", IN (p.services) s, IN (s.tags) te" + tagCount;

                    if (firstTag) {
                        whereClause = "WHERE te1.tagId = " + tagId;
                        firstTag = false;
                    } else {
                        whereClause += " AND te" + tagCount + ".tagId = " + tagId;
                    }

                    tagCount++;
                }

                String jpql = selectClause + " " + whereClause;
                Query query = em.createQuery(jpql);
                providers = query.getResultList();

                // products 
                String selectClause1 = "SELECT p FROM ServiceProvider p";
                String whereClause1 = "";
                Boolean firstTag1 = true;
                Integer tagCount1 = 1;

                for (Long tagId : tagIds) {
                    selectClause1 += ", IN (p.products) pe, IN (pe.tags) te" + tagCount1;

                    if (firstTag1) {
                        whereClause1 = "WHERE te1.tagId = " + tagId;
                        firstTag1 = false;
                    } else {
                        whereClause1 += " AND te" + tagCount1 + ".tagId = " + tagId;
                    }

                    tagCount1++;
                }

                String jpql1 = selectClause1 + " " + whereClause1;
                Query query1 = em.createQuery(jpql1);
                List<ServiceProvider> result = query1.getResultList();
                for (ServiceProvider sp : result) {
                    providers.add(sp); 
                }
            }

            for (ServiceProvider provider : providers) {
                provider.getProducts().size();
                provider.getServices().size();
            }

            return providers;
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<ServiceProvider>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
