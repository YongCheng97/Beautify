/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Purchased;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewPurchaseException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.PurchasedExistException;
import util.exception.PurchasedNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Crystal Lee
 */
@Local
public interface PurchasedSessionBeanLocal {

    public void deletePurchased(Long purchasedId);

    public Purchased retrievePurchasedByPurchasedId(Long purchasedId) throws PurchasedNotFoundException;

    public Purchased createNewPurchased(Purchased newPurchased, Long customerId, List<Long> purchasedLineItemIds, Long creditCardId) throws UnknownPersistenceException, InputDataValidationException, CustomerNotFoundException, CreateNewPurchaseException, PurchasedExistException;

    
}
