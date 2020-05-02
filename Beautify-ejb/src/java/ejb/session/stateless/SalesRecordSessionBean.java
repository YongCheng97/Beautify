package ejb.session.stateless;

import entity.Booking;
import entity.PurchasedLineItem;
import entity.SalesRecord;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.BookingNotFoundException;
import util.exception.CreateNewSalesRecordException;
import util.exception.PurchasedLineItemNotFoundException;
import util.exception.SalesRecordNotFoundException;

@Stateless
@Local(SalesRecordSessionBeanLocal.class)

public class SalesRecordSessionBean implements SalesRecordSessionBeanLocal {

    @EJB
    private PurchasedLineItemSessionBeanLocal purchasedLineItemSessionBeanLocal;

    @EJB
    private BookingSessionBeanLocal bookingSessionBeanLocal;

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public SalesRecordSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public SalesRecord createNewSalesRecordBooking(SalesRecord newSalesRecord, Long bookingId) throws CreateNewSalesRecordException
    {
        if(newSalesRecord != null)
        {
           try {
                if (bookingId == null) {
                      throw new CreateNewSalesRecordException("A new sales record must be associated with a booking");
                } else {
                  Booking booking = bookingSessionBeanLocal.retrieveBookingByBookingId(bookingId);
                  newSalesRecord.setBooking(booking);
                }
              
                em.persist(newSalesRecord);
                em.flush();
              
                return newSalesRecord;
           }
           catch (BookingNotFoundException ex) {
                throw new CreateNewSalesRecordException(ex.getMessage());
           }
        }
        else 
        {
            throw new CreateNewSalesRecordException("Sales Record information not provided");
        }
    }
    
    @Override
    public SalesRecord createNewSalesRecordPurchasedLineItem(SalesRecord newSalesRecord, Long purchasedLineItemId) throws CreateNewSalesRecordException
    {
        if(newSalesRecord != null)
        {
           try {
                if (purchasedLineItemId == null) {
                      throw new CreateNewSalesRecordException("A new sales record must be associated with a purchased item");
                } else {
                  PurchasedLineItem purchasedLineItem = purchasedLineItemSessionBeanLocal.retrievePurchasedLineItemByPurchasedLineItemId(purchasedLineItemId);
                  newSalesRecord.setPurchasedLineItem(purchasedLineItem);
                }
              
                em.persist(newSalesRecord);
                em.flush();
              
                return newSalesRecord;
           }
           catch (PurchasedLineItemNotFoundException ex) {
                throw new CreateNewSalesRecordException(ex.getMessage());
           }
        }
        else 
        {
            throw new CreateNewSalesRecordException("Sales Record information not provided");
        }
    }
    
    @Override
    public List<SalesRecord> retrieveAllSalesRecord()
    {
       Query query = em.createQuery("SELECT sr FROM SalesRecord sr");
       List<SalesRecord> salesRecords = query.getResultList();
       
       
       return salesRecords;
    }
    
    
    @Override
    public SalesRecord retrieveSalesRecordBySalesRecordId(Long salesRecordId) throws SalesRecordNotFoundException
    {
        SalesRecord salesRecord = em.find(SalesRecord.class, salesRecordId);
        
        if(salesRecord != null)
        {
            return salesRecord;
        }
        else
        {
            throw new SalesRecordNotFoundException("Sales Record ID " + salesRecordId + " does not exist!");
        }                
    }
    
    @Override
    public List<SalesRecord> retrieveAllBookingSalesRecordByServiceProviderId(Long serviceProviderId) {
        Query query = em.createQuery("SELECT s FROM SalesRecord s WHERE s.booking IS NOT NULL ORDER BY s.salesRecordId DESC");
        
        List<SalesRecord> salesRecordsBooking = query.getResultList();
        List<SalesRecord> returnList = new ArrayList<>();
        
        for (SalesRecord salesRecord:salesRecordsBooking) {
            if (salesRecord.getBooking().getService().getServiceProvider().getServiceProviderId() == serviceProviderId) {
                returnList.add(salesRecord);
            }
        }

        return returnList;
    }    
    
    @Override
    public List<SalesRecord> retrieveAllPurchasedLineItemSalesRecordByServiceProviderId(Long serviceProviderId) {
        Query query = em.createQuery("SELECT s FROM SalesRecord s WHERE s.purchasedLineItem IS NOT NULL ORDER BY s.salesRecordId DESC");
        
        List<SalesRecord> salesRecordsPurchasedLineItem = query.getResultList();
        List<SalesRecord> returnList = new ArrayList<>();
        
        for (SalesRecord salesRecord:salesRecordsPurchasedLineItem) {
            if (salesRecord.getPurchasedLineItem().getProduct().getServiceProvider().getServiceProviderId() == serviceProviderId) {
                returnList.add(salesRecord);
            }
        }

        return returnList;
    }    
    
    @Override
    public void updateSalesRecord(SalesRecord salesRecord)
    {
        em.merge(salesRecord);
    }
    
    
    @Override
    public void deleteSalesRecord(SalesRecord salesRecord) 
    {
        throw new UnsupportedOperationException();
    }
}
