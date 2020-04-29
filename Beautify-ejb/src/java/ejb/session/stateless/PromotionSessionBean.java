package ejb.session.stateless;

import entity.Product;
import entity.Promotion;
import entity.Service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import util.exception.PromotionNotFoundException;
import util.exception.StaffUsernameExistException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdatePromotionException;

@Stateless
@Local(PromotionSessionBeanLocal.class)

public class PromotionSessionBean implements PromotionSessionBeanLocal {

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public PromotionSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Promotion createNewPromotion(Promotion newPromotion) throws UnknownPersistenceException, InputDataValidationException, PromotionNameExistException {
        try {
            Set<ConstraintViolation<Promotion>> constraintViolations = validator.validate(newPromotion);

            if (constraintViolations.isEmpty()) {
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
            throw new PromotionNotFoundException("Promotion name " + promoCode + " does not exist!");
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
    public void updatePromotion(Promotion promotion) throws PromotionNotFoundException, UpdatePromotionException, InputDataValidationException {
        if (promotion != null && promotion.getPromotionId() != null) {
            Set<ConstraintViolation<Promotion>> constraintViolations = validator.validate(promotion);

            if (constraintViolations.isEmpty()) {
                Promotion promotionToUpdate = retrievePromotionByPromotionId(promotion.getPromotionId());

                if (promotionToUpdate.getPromoCode().equals(promotion.getPromoCode())) {
                    promotionToUpdate.setDiscountRate(promotion.getDiscountRate());
                    promotionToUpdate.setStartDate(promotion.getStartDate());
                    promotionToUpdate.setEndDate(promotion.getEndDate());
                    promotionToUpdate.setName(promotion.getName());

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

        em.remove(promotionToRemove);
    }

    @Override
    public void updateServiceDiscountPrice(Service service) {
        List<Promotion> promotions = service.getPromotions();
        if (promotions.isEmpty()) {
            service.setDiscountPrice(null);
        } else {
            Date date = new Date();
            BigDecimal discountPrice = new BigDecimal("0.00");
            Boolean update = false;

            for (Promotion promotion : promotions) {
                if (promotion.getStartDate().compareTo(date) < 0 && promotion.getEndDate().compareTo(date) > 0) {
                    discountPrice = service.getPrice().multiply(promotion.getDiscountRate());
                    service.setDiscountPrice(discountPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
                    update = true;
                }
            }

            if (!update) {
                service.setDiscountPrice(null);
            }
        }
    }

    @Override
    public void updateProductDiscountPrice(Product product) {
        List<Promotion> promotions = product.getPromotions();
        if (promotions.isEmpty()) {
            product.setDiscountPrice(null);
        } else {
            Date date = new Date();
            BigDecimal discountPrice = new BigDecimal("0.00");
            Boolean update = false;

            for (Promotion promotion : promotions) {
                if (promotion.getStartDate().compareTo(date) < 0 && promotion.getEndDate().compareTo(date) > 0) {
                    discountPrice = product.getPrice().multiply(promotion.getDiscountRate());
                    product.setDiscountPrice(discountPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
                    update = true;
                }
            }

            if (!update) { // no current working promo 
                product.setDiscountPrice(null);
            }
        }
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
