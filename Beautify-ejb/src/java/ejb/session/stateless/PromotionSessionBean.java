package ejb.session.stateless;

import entity.Product;
import entity.Promotion;
import entity.Service;
import entity.ServiceProvider;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
import util.exception.CreateNewProductException;
import util.exception.DeletePromotionException;
import util.exception.PromotionNameExistException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.PromotionNotFoundException;
import util.exception.ServiceNotFoundException;
import util.exception.StaffUsernameExistException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePromotionException;

@Stateless
@Local(PromotionSessionBeanLocal.class)

public class PromotionSessionBean implements PromotionSessionBeanLocal {

    @EJB
    private ServiceSessionBeanLocal serviceSessionBeanLocal;

    @EJB
    private ProductSessionBeanLocal productSessionBeanLocal;

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public PromotionSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Promotion createNewServicePromotion(Promotion newPromotion, Long serviceProviderId, Long serviceId) throws UnknownPersistenceException, InputDataValidationException, PromotionNameExistException {
        try {
            Set<ConstraintViolation<Promotion>> constraintViolations = validator.validate(newPromotion);

            if (constraintViolations.isEmpty()) {

                ServiceProvider serviceProvider = em.find(ServiceProvider.class, serviceProviderId);

                newPromotion.setServiceProvider(serviceProvider);
                serviceProvider.getPromotions().add(newPromotion);

                Service service = em.find(Service.class, serviceId);

                newPromotion.setService(service);
                service.getPromotions().add(newPromotion);

                em.persist(newPromotion);
                em.flush();

                return newPromotion;
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new PromotionNameExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public Promotion createNewProductPromotion(Promotion newPromotion, Long serviceProviderId, Long productId) throws UnknownPersistenceException, InputDataValidationException, PromotionNameExistException {
        try {
            Set<ConstraintViolation<Promotion>> constraintViolations = validator.validate(newPromotion);

            if (constraintViolations.isEmpty()) {

                ServiceProvider serviceProvider = em.find(ServiceProvider.class, serviceProviderId);

                newPromotion.setServiceProvider(serviceProvider);
                serviceProvider.getPromotions().add(newPromotion);

                Product product = em.find(Product.class, productId);

                newPromotion.setProduct(product);
                product.getPromotions().add(newPromotion);

                em.persist(newPromotion);
                em.flush();

                return newPromotion;
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new PromotionNameExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public List<Promotion> retrieveAllPromotions() {
        Query query = em.createQuery("SELECT p FROM Promotion p");

        return query.getResultList();
    }

    @Override
    public List<Promotion> retrievePromotionsByServiceProviderId(Long serviceProviderId) {
        Query query = em.createQuery("SELECT DISTINCT p FROM Promotion p WHERE p.serviceProvider.serviceProviderId = :inProviderId");
        query.setParameter("inProviderId", serviceProviderId);

        return query.getResultList();
    }

    @Override
    public Promotion retrievePromotionByPromotionId(Long promotionId) throws PromotionNotFoundException {
        Promotion promotion = em.find(Promotion.class, promotionId);

        if (promotion != null) {
            return promotion;
        } else {
            throw new PromotionNotFoundException("Promotion ID " + promotionId + " does not exist!");
        }
    }

    @Override
    public Promotion retrievePromotionByPromoCode(String promoCode) throws PromotionNotFoundException {

        Query query = em.createQuery("SELECT p FROM Promotion p WHERE p.promoCode = :inPromoCode");
        query.setParameter("inPromoCode", promoCode);

        try {
            return (Promotion) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new PromotionNotFoundException("Promotion Code " + promoCode + " does not exist!");
        }
    }

    @Override
    public Promotion retrievePromotionByName(String name) throws PromotionNotFoundException {
        Query query = em.createQuery("SELECT p FROM Promotion p WHERE p.name = :inName");
        query.setParameter("inName", name);

        try {
            return (Promotion) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new PromotionNotFoundException("Promotion name " + name + " does not exist!");
        }
    }

    @Override
    public List<Promotion> retrieveAllServicePromotionsByServiceProviderId(Long serviceProviderId) {
        Query query = em.createQuery("SELECT DISTINCT p FROM Promotion p WHERE p.serviceProvider.serviceProviderId = :inProviderId");
        query.setParameter("inProviderId", serviceProviderId);

        List<Promotion> promotions = query.getResultList();

        List<Promotion> servicePromotions = new ArrayList<>();

        for (Promotion p : promotions) {
            if (p.getProduct() == null) {
                servicePromotions.add(p);
            }
        }

        return servicePromotions;
    }

    @Override
    public List<Promotion> retrieveAllProductPromotionsByServiceProviderId(Long serviceProviderId) {
        Query query = em.createQuery("SELECT DISTINCT p FROM Promotion p WHERE p.serviceProvider.serviceProviderId = :inProviderId");
        query.setParameter("inProviderId", serviceProviderId);

        List<Promotion> promotions = query.getResultList();

        List<Promotion> productPromotions = new ArrayList<>();

        for (Promotion p : promotions) {
            if (p.getService() == null) {
                productPromotions.add(p);
            }
        }

        return productPromotions;
    }

    @Override
    public void updateProductPromotion(Promotion promotion, Long productId) throws PromotionNotFoundException, UpdatePromotionException, InputDataValidationException {
        if (promotion != null && promotion.getPromotionId() != null) {
            Set<ConstraintViolation<Promotion>> constraintViolations = validator.validate(promotion);

            if (constraintViolations.isEmpty()) {
                Promotion promotionToUpdate = retrievePromotionByPromotionId(promotion.getPromotionId());

                if (promotionToUpdate.getPromoCode().equals(promotion.getPromoCode())) {

                    promotionToUpdate.getProduct().getPromotions().remove(promotionToUpdate);

                    Product product = new Product();

                    if (productId != null) {
                        try {
                            product = productSessionBeanLocal.retrieveProductByProdId(productId);
                        } catch (ProductNotFoundException ex) {
                            Logger.getLogger(PromotionSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        promotionToUpdate.setProduct(product);
                        product.getPromotions().add(promotionToUpdate); 

                        promotionToUpdate.setDiscountRate(promotion.getDiscountRate());
                        promotionToUpdate.setStartDate(promotion.getStartDate());
                        promotionToUpdate.setEndDate(promotion.getEndDate());
                        promotionToUpdate.setName(promotion.getName());
                    }

                } else {
                    throw new UpdatePromotionException("Promo Code of promotion record to be updated does not match the existing record");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new PromotionNotFoundException("Promotion ID not provided for promotion to be updated");
        }
    }

    @Override
    public void updateServicePromotion(Promotion promotion, Long serviceId) throws PromotionNotFoundException, UpdatePromotionException, InputDataValidationException {
        if (promotion != null && promotion.getPromotionId() != null) {
            Set<ConstraintViolation<Promotion>> constraintViolations = validator.validate(promotion);

            if (constraintViolations.isEmpty()) {
                Promotion promotionToUpdate = retrievePromotionByPromotionId(promotion.getPromotionId());

                if (promotionToUpdate.getPromoCode().equals(promotion.getPromoCode())) {

                    promotionToUpdate.getService().getPromotions().remove(promotionToUpdate);

                    Service service = new Service(); 

                    if (serviceId != null) {
                        try {
                            service = serviceSessionBeanLocal.retrieveServiceByServiceId(serviceId); 
                        } catch (ServiceNotFoundException ex) {
                            Logger.getLogger(PromotionSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        promotionToUpdate.setService(service);
                        service.getPromotions().add(promotionToUpdate); 

                        promotionToUpdate.setDiscountRate(promotion.getDiscountRate());
                        promotionToUpdate.setStartDate(promotion.getStartDate());
                        promotionToUpdate.setEndDate(promotion.getEndDate());
                        promotionToUpdate.setName(promotion.getName());
                    }

                } else {
                    throw new UpdatePromotionException("Promo Code of promotion record to be updated does not match the existing record");
                }
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new PromotionNotFoundException("Promotion ID not provided for promotion to be updated");
        }
    }

    @Override
    public void deletePromotion(Long promotionId) throws PromotionNotFoundException, DeletePromotionException {
        Promotion promotionToRemove = retrievePromotionByPromotionId(promotionId);

        promotionToRemove.getServiceProvider().getPromotions().remove(promotionToRemove);
        promotionToRemove.setServiceProvider(null);

        if (promotionToRemove.getProduct() != null) {
            promotionToRemove.getProduct().getPromotions().remove(promotionToRemove);
            promotionToRemove.setProduct(null);
        }

        if (promotionToRemove.getService() != null) {
            promotionToRemove.getService().getPromotions().remove(promotionToRemove);
            promotionToRemove.setService(null);
        }

        em.remove(promotionToRemove);
    }

    @Override
    public Boolean checkPromoCode(String promoCode) {
        Boolean valid = false;

        try {
            Promotion promotion = retrievePromotionByPromoCode(promoCode);

            Date date = new Date();

            if (promotion.getStartDate().compareTo(date) < 0 && promotion.getEndDate().compareTo(date) > 0) {
                valid = true;
            }

        } catch (PromotionNotFoundException ex) {
            Logger.getLogger(PromotionSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return valid;
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Promotion>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

}
