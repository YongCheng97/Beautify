/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PurchasedLineItem;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewPurchasedLineItemException;
import util.exception.InputDataValidationException;
import util.exception.PurchasedLineItemExistException;
import util.exception.PurchasedLineItemNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Crystal Lee
 */
@Local
public interface PurchasedLineItemSessionBeanLocal {

    public PurchasedLineItem createNewPurchasedLineItem(PurchasedLineItem newPurchasedLineItem, Long productId) throws UnknownPersistenceException, CreateNewPurchasedLineItemException, PurchasedLineItemExistException, InputDataValidationException;

    public PurchasedLineItem retrievePurchasedLineItemByPurchasedLineItemId(Long purchasedLineItemId) throws PurchasedLineItemNotFoundException;

    public PurchasedLineItem updatePurchasedLineItem(PurchasedLineItem purchasedLineItem) throws InputDataValidationException, PurchasedLineItemNotFoundException;

    public void deletePurchasedLineItem(Long purchasedLineItemId);

    public List<PurchasedLineItem> retrieveAllPurchasedLineItemByServiceProviderId(Long serviceProviderId) throws PurchasedLineItemNotFoundException;

    public List<PurchasedLineItem> retrieveAllPurchasedLineItemByProduct(Long productId);
    
}
