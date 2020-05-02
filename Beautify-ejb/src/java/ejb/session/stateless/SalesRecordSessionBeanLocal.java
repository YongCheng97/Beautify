package ejb.session.stateless;

import entity.SalesRecord;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewSalesRecordException;
import util.exception.SalesRecordNotFoundException;


public interface SalesRecordSessionBeanLocal {

    public List<SalesRecord> retrieveAllSalesRecord();

    public SalesRecord retrieveSalesRecordBySalesRecordId(Long salesRecordId) throws SalesRecordNotFoundException;

    public void deleteSalesRecord(SalesRecord salesRecord) throws SalesRecordNotFoundException;

    public void updateSalesRecord(SalesRecord salesRecord) throws SalesRecordNotFoundException;

    public SalesRecord createNewSalesRecordPurchasedLineItem(SalesRecord newSalesRecord, Long purchasedLineItemId) throws CreateNewSalesRecordException;

    public SalesRecord createNewSalesRecordBooking(SalesRecord newSalesRecord, Long bookingId) throws CreateNewSalesRecordException;

    public List<SalesRecord> retrieveAllPurchasedLineItemSalesRecordByServiceProviderId(Long serviceProviderId);

    public List<SalesRecord> retrieveAllBookingSalesRecordByServiceProviderId(Long serviceProviderId);
    
}
