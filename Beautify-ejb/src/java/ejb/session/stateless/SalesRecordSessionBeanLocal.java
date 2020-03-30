package ejb.session.stateless;

import entity.SalesRecord;
import java.util.List;
import javax.ejb.Local;
import util.exception.CreateNewSalesRecordException;
import util.exception.SalesRecordNotFoundException;


public interface SalesRecordSessionBeanLocal {

    public SalesRecord createNewSalesRecord(SalesRecord newSalesRecord, Long bookingId, Long productId) throws CreateNewSalesRecordException;

    public List<SalesRecord> retrieveAllSalesRecord();

    public SalesRecord retrieveSalesRecordBySalesRecordId(Long salesRecordId) throws SalesRecordNotFoundException;

    public void deleteSalesRecord(SalesRecord salesRecord) throws SalesRecordNotFoundException;

    public void updateSalesRecord(SalesRecord salesRecord) throws SalesRecordNotFoundException;
    
}
