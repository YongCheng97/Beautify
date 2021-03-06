/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.SalesForUs;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewSalesForUsException;
import util.exception.SalesForUsNotFoundException;

/**
 *
 * @author Zheng Yang
 */
@Local
public interface SalesForUsSessionBeanLocal {

    public List<SalesForUs> retrieveAllSalesForUs();

    public SalesForUs retrieveSalesForUsRecordById(Long salesForUsId) throws SalesForUsNotFoundException;

    public void updateSalesForUsRecord(SalesForUs salesForUs);

    public void deleteSalesForUsRecord(SalesForUs salesForUs);

    public SalesForUs createNewSalesForUsPurchasedLineItem(SalesForUs newSalesForUs, Long purchasedLineItemId) throws CreateNewSalesForUsException;

    public SalesForUs createNewSalesForUsBooking(SalesForUs newSalesForUs, Long bookingId) throws CreateNewSalesForUsException;

    public SalesForUs createNewSalesForUsServiceProvider(SalesForUs newSalesForUs, Long serviceProviderId, Long creditCardId) throws CreateNewSalesForUsException;
    
}
