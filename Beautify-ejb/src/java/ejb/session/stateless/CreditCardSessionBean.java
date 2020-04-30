/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditCard;
import entity.Customer;
import entity.Purchased;
import entity.ServiceProvider;
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
import util.exception.CreateNewCreditCardException;
import util.exception.CreditCardExistsException;
import util.exception.CreditCardNotFoundException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.ServiceProviderNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Crystal Lee
 */
@Stateless
public class CreditCardSessionBean implements CreditCardSessionBeanLocal {

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB
    private ServiceProviderSessionBeanLocal serviceProviderSessionBeanLocal;

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public CreditCardSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public CreditCard createNewCreditCardEntityForCustomer(CreditCard newCreditCard, Long customerId) throws InputDataValidationException, CreateNewCreditCardException, CreditCardExistsException, UnknownPersistenceException {
        Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(newCreditCard);

        if (constraintViolations.isEmpty()) {
            try {
                if (customerId == null) {
                    throw new CreateNewCreditCardException("A new credit card must be associated with a customer");
                }

                Customer customer = customerSessionBeanLocal.retrieveCustomerByCustId(customerId);

                newCreditCard.setCustomer(customer);
                customer.getCreditCards().add(newCreditCard);

                em.persist(newCreditCard);
                em.flush();

                return newCreditCard;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new CreditCardExistsException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } catch (CustomerNotFoundException ex) {
                throw new CreateNewCreditCardException("An error has occured while creating the new credit card: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public CreditCard createNewCreditCardEntityForServiceProvider(CreditCard newCreditCard, Long serviceProviderId) throws InputDataValidationException, CreateNewCreditCardException, CreditCardExistsException, UnknownPersistenceException {
        Set<ConstraintViolation<CreditCard>> constraintViolations = validator.validate(newCreditCard);

        if (constraintViolations.isEmpty()) {
            try {
                if (serviceProviderId == null) {
                    throw new CreateNewCreditCardException("A new credit card must be associated with a customer");
                }

                ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.retrieveServiceProviderById(serviceProviderId);

                newCreditCard.setServiceProvider(serviceProvider);
                serviceProvider.getCreditCards().add(newCreditCard);

                em.persist(newCreditCard);
                em.flush();

                return newCreditCard;
            } catch (PersistenceException ex) {
                if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                    if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                        throw new CreditCardExistsException();
                    } else {
                        throw new UnknownPersistenceException(ex.getMessage());
                    }
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } catch (ServiceProviderNotFoundException ex) {
                throw new CreateNewCreditCardException("An error has occured while creating the new credit card: " + ex.getMessage());
            }
        } else {
            throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
        }
    }

    @Override
    public CreditCard retrieveCreditCardByCreditCardId(Long creditCardId) throws CreditCardNotFoundException {
        CreditCard creditCard = em.find(CreditCard.class, creditCardId);

        if (creditCard != null) {
            return creditCard;
        } else {
            throw new CreditCardNotFoundException("Credit Card ID " + creditCardId + " does not exist!");
        }
    }

    @Override
    public List<CreditCard> retrieveAllCreditCardsByCustomerId(Long customerId) {
        Query query = em.createQuery("SELECT c FROM CreditCard c WHERE c.customer.customerId = :inCustomerId");
        query.setParameter("inCustomerId", customerId);
        List<CreditCard> creditCards = query.getResultList();

        return creditCards;
    }

    @Override
    public List<CreditCard> retrieveAllCreditCardsByServiceProviderId(Long serviceProviderId) {
        Query query = em.createQuery("SELECT c FROM CreditCard c WHERE c.serviceProvider.serviceProviderId = :inServiceProviderId");
        query.setParameter("inServiceProviderId", serviceProviderId);
        List<CreditCard> creditCards = query.getResultList();

        return creditCards;
    }

    @Override
    public CreditCard retrieveCreditCardByLastFourNum(String creditCardNum) {
        Query query = em.createQuery("SELECT c FROM CreditCard c WHERE c.cardNumber LIKE CONCAT('%', :inCardNumber)");
        query.setParameter("inCardNumber", creditCardNum.substring(12, 16));
        CreditCard cc = (CreditCard) query.getSingleResult();

        return cc;
    }

    @Override
    public void deleteCreditCard(Long creditCardId) throws CreditCardNotFoundException {
        CreditCard creditCardToRemove = retrieveCreditCardByCreditCardId(creditCardId);

        if (creditCardToRemove.getCustomer() != null) {
            Customer customer = creditCardToRemove.getCustomer();
            customer.getCreditCards().remove(creditCardToRemove);
            for (Purchased purchased : creditCardToRemove.getPurchaseds()) {
                purchased.setCreditCard(null);
            }
        }

        if (creditCardToRemove.getServiceProvider() != null) {
            ServiceProvider serviceProvider = creditCardToRemove.getServiceProvider();
            serviceProvider.getCreditCards().remove(creditCardToRemove);
        }

        em.remove(creditCardToRemove);
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<CreditCard>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
