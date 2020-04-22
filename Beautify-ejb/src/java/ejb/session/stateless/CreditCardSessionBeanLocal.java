/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CreditCard;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewCreditCardException;
import util.exception.CreditCardExistsException;
import util.exception.CreditCardNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Crystal Lee
 */
@Local
public interface CreditCardSessionBeanLocal {

    public void deleteCreditCard(Long creditCardId) throws CreditCardNotFoundException;

    public List<CreditCard> retrieveAllCreditCardsByServiceProviderId(Long serviceProviderId);

    public List<CreditCard> retrieveAllCreditCardsByCustomerId(Long customerId);

    public CreditCard retrieveCreditCardByCreditCardId(Long creditCardId) throws CreditCardNotFoundException;

    public CreditCard createNewCreditCardEntityForServiceProvider(CreditCard newCreditCard, Long serviceProviderId) throws InputDataValidationException, CreateNewCreditCardException, CreditCardExistsException, UnknownPersistenceException;

    public CreditCard createNewCreditCardEntityForCustomer(CreditCard newCreditCard, Long customerId) throws InputDataValidationException, CreateNewCreditCardException, CreditCardExistsException, UnknownPersistenceException;
    
}
