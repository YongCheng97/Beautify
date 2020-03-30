package ejb.session.stateless;

import entity.Booking;
import entity.Product;
import entity.SalesRecord;
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
import util.exception.ProductNotFoundException;
import util.exception.SalesRecordNotFoundException;

@Stateless
@Local(SalesRecordSessionBeanLocal.class)

public class SalesRecordSessionBean implements SalesRecordSessionBeanLocal {

    @EJB
    private ProductSessionBeanLocal productSessionBeanLocal;

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
    public SalesRecord createNewSalesRecord(SalesRecord newSalesRecord, Long bookingId, Long productId) throws CreateNewSalesRecordException
    {
        if(newSalesRecord != null)
        {
           try {
              if (bookingId == null) {
                  throw new CreateNewSalesRecordException("A new sales record must be associated with a booking");
              } 
              
              Booking booking = bookingSessionBeanLocal.retrieveBookingByBookingId(bookingId);
              newSalesRecord.setBooking(booking);
              
              if (productId == null) {
                  throw new CreateNewSalesRecordException("A new sales record must be associated with a product");                  
              }
              
              Product product = productSessionBeanLocal.retrieveProductByProdId(productId);
              newSalesRecord.setProduct(product);
              
              em.persist(newSalesRecord);
              em.flush();
              
              return newSalesRecord;
           }
           catch (ProductNotFoundException | BookingNotFoundException ex) {
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
       
       for (SalesRecord salesRecord : salesRecords) {
            salesRecord.getBooking();
            salesRecord.getProduct();
        }
       
       return salesRecords;
    }
    
    
    @Override
    public SalesRecord retrieveSalesRecordBySalesRecordId(Long salesRecordId) throws SalesRecordNotFoundException
    {
        SalesRecord salesRecord = em.find(SalesRecord.class, salesRecordId);
        
        if(salesRecord != null)
        {
            salesRecord.getBooking();
            salesRecord.getProduct();
            
            return salesRecord;
        }
        else
        {
            throw new SalesRecordNotFoundException("Sales Record ID " + salesRecordId + " does not exist!");
        }                
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
