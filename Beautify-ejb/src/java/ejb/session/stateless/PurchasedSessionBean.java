/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditCard;
import entity.Customer;
import entity.Purchased;
import entity.PurchasedLineItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CreateNewPurchaseException;
import util.exception.CreditCardNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.PurchasedExistException;
import util.exception.PurchasedLineItemNotFoundException;
import util.exception.PurchasedNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Crystal Lee
 */
@Stateless
public class PurchasedSessionBean implements PurchasedSessionBeanLocal {

    @EJB
    private CreditCardSessionBeanLocal creditCardSessionBeanLocal;

    @EJB
    private PurchasedLineItemSessionBeanLocal purchasedLineItemSessionBeanLocal;
    
    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;
     
    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public PurchasedSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Purchased createNewPurchased(Purchased newPurchased, Long customerId, List<Long> purchasedLineItemIds, Long creditCardId) throws UnknownPersistenceException, InputDataValidationException, CustomerNotFoundException, CreateNewPurchaseException, PurchasedExistException {
        Set<ConstraintViolation<Purchased>> constraintViolations = validator.validate(newPurchased);

        if (constraintViolations.isEmpty()) {
            try {
                if (customerId == null) {
                    throw new CreateNewPurchaseException("A new purchase must be associated with a customer");
                }
                
                Customer customer = customerSessionBeanLocal.retrieveCustomerByCustId(customerId);
                
                newPurchased.setCustomer(customer);
                customer.getPurchaseds().add(newPurchased);
                
                List<PurchasedLineItem> purchasedLineItems = new ArrayList<>();
                
                for (Long purchaseLineItemId:purchasedLineItemIds) {
                    purchasedLineItemSessionBeanLocal.retrievePurchasedLineItemByPurchasedLineItemId(purchaseLineItemId).setPurchased(newPurchased);
                    purchasedLineItems.add(purchasedLineItemSessionBeanLocal.retrievePurchasedLineItemByPurchasedLineItemId(purchaseLineItemId));
                }
                
                newPurchased.setPurchasedLineItems(purchasedLineItems);
                
                CreditCard creditCard = creditCardSessionBeanLocal.retrieveCreditCardByCreditCardId(creditCardId); 
                
                newPurchased.setCreditCard(creditCard);
                creditCard.getPurchaseds().add(newPurchased); 
               
                em.persist(newPurchased);
                em.flush();

                return newPurchased;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new PurchasedExistException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } catch (CustomerNotFoundException | PurchasedLineItemNotFoundException | CreditCardNotFoundException ex) { 
                throw new CreateNewPurchaseException("An error has occured while creating the new purchased: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }
    
    @Override
    public Purchased retrievePurchasedByPurchasedId(Long purchasedId) throws PurchasedNotFoundException {
        Purchased purchased = em.find(Purchased.class, purchasedId);

        if (purchased != null) {
            return purchased;
        } else {
            throw new PurchasedNotFoundException("Purchased ID " + purchasedId + " does not exist!");
        }
    }
    
    @Override
    public void deletePurchased(Long purchasedId){
        Purchased purchasedToDelete = em.find(Purchased.class, purchasedId);
        List<PurchasedLineItem> purchasedLineItems = purchasedToDelete.getPurchasedLineItems();
        
        for (PurchasedLineItem purchasedLineItem:purchasedLineItems){
            purchasedLineItemSessionBeanLocal.deletePurchasedLineItem(purchasedLineItem.getPurchasedLineItemId());
        }
        
        Customer customer = purchasedToDelete.getCustomer();
        customer.getPurchaseds().remove(purchasedToDelete);
        
        em.remove(purchasedToDelete);
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Purchased>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
