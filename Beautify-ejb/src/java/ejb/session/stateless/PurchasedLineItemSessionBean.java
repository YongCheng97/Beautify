package ejb.session.stateless;

import entity.Product;
import entity.PurchasedLineItem;
import entity.ServiceProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewPurchasedLineItemException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.PurchasedLineItemExistException;
import util.exception.PurchasedLineItemNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Crystal Lee
 */
@Stateless
public class PurchasedLineItemSessionBean implements PurchasedLineItemSessionBeanLocal {
    
    @EJB
    private ProductSessionBeanLocal productSessionBeanLocal;
    
    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public PurchasedLineItemSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public PurchasedLineItem createNewPurchasedLineItem(PurchasedLineItem newPurchasedLineItem, Long productId) throws UnknownPersistenceException, CreateNewPurchasedLineItemException, PurchasedLineItemExistException, InputDataValidationException {
        Set<ConstraintViolation<PurchasedLineItem>> constraintViolations = validator.validate(newPurchasedLineItem);

        if (constraintViolations.isEmpty()) {
            try {
                if (productId == null) {
                    throw new CreateNewPurchasedLineItemException("A new line item must be associated with a product");
                }
                
                Product product = productSessionBeanLocal.retrieveProductByProdId(productId);
                newPurchasedLineItem.setProduct(product);
                
                em.persist(newPurchasedLineItem);
                em.flush();

                return newPurchasedLineItem;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new CreateNewPurchasedLineItemException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } catch (ProductNotFoundException ex) { 
                throw new CreateNewPurchasedLineItemException("An error has occured while creating the new line item: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public PurchasedLineItem retrievePurchasedLineItemByPurchasedLineItemId(Long purchasedLineItemId) throws PurchasedLineItemNotFoundException {
        PurchasedLineItem purchasedLineItem = em.find(PurchasedLineItem.class, purchasedLineItemId);

        if (purchasedLineItem != null) {
            return purchasedLineItem;
        } else {
            throw new PurchasedLineItemNotFoundException("Purchased Line Item ID " + purchasedLineItemId + " does not exist!");
        }
    }
            
    @Override
    public List<PurchasedLineItem> retrieveAllPurchasedLineItemByServiceProviderId(Long serviceProviderId) throws PurchasedLineItemNotFoundException {
        
        Query query = em.createQuery("SELECT p FROM PurchasedLineItem p ORDER BY p.purchasedLineItemId DESC");
        List<PurchasedLineItem> purchasedLineItems = query.getResultList();
        
        List<PurchasedLineItem> purchasedLineItemsSP = new ArrayList<>();
                
        for (PurchasedLineItem purchasedLineItem : purchasedLineItems) {
            Product product = purchasedLineItem.getProduct();
            Long sP = product.getServiceProvider().getServiceProviderId();
            if (sP == serviceProviderId){
                purchasedLineItemsSP.add(purchasedLineItem);
            }
        }

        return purchasedLineItemsSP;
        
    }
            
    @Override
    public PurchasedLineItem updatePurchasedLineItem(PurchasedLineItem purchasedLineItem) throws InputDataValidationException, PurchasedLineItemNotFoundException {
         if (purchasedLineItem != null && purchasedLineItem.getPurchasedLineItemId()!= null) {
            Set<ConstraintViolation<PurchasedLineItem>> constraintViolations = validator.validate(purchasedLineItem);

            if (constraintViolations.isEmpty()) {
                PurchasedLineItem purchasedLineItemToUpdate = retrievePurchasedLineItemByPurchasedLineItemId(purchasedLineItem.getPurchasedLineItemId());

                purchasedLineItemToUpdate.setQuantity(purchasedLineItem.getQuantity());
                purchasedLineItemToUpdate.setStatus(purchasedLineItem.getStatus());
                purchasedLineItemToUpdate.setPrice(purchasedLineItem.getPrice());
                
                return purchasedLineItemToUpdate;
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new PurchasedLineItemNotFoundException("Purchased Line Item ID not provided for line item to be updated");
        }
    }
    
    @Override
    public void deletePurchasedLineItem(Long purchasedLineItemId){
        PurchasedLineItem purchasedLineItemToDelete = em.find(PurchasedLineItem.class, purchasedLineItemId);
        
        em.remove(purchasedLineItemToDelete);
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<PurchasedLineItem>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
