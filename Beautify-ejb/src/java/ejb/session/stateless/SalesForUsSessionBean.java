package ejb.session.stateless;

import entity.Booking;
import entity.PurchasedLineItem;
import entity.SalesForUs;
import entity.SalesRecord;
import entity.ServiceProvider;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.BookingNotFoundException;
import util.exception.CreateNewSalesForUsException;
import util.exception.PurchasedLineItemNotFoundException;
import util.exception.SalesForUsNotFoundException;
import util.exception.SalesRecordNotFoundException;
import util.exception.ServiceProviderNotFoundException;

@Stateless
@Local(SalesForUsSessionBeanLocal.class)
public class SalesForUsSessionBean implements SalesForUsSessionBeanLocal {

    @EJB
    private ServiceProviderSessionBeanLocal serviceProviderSessionBeanLocal;
    
    @EJB
    private PurchasedLineItemSessionBeanLocal purchasedLineItemSessionBeanLocal;

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;
    
    @EJB
    private BookingSessionBeanLocal bookingSessionBeanLocal;    
    
    public SalesForUsSessionBean() {
        
    }
    
    @Override
    public SalesForUs createNewSalesForUs(SalesForUs newSalesForUs, Long bookingId, Long purchasedLineItemId) throws CreateNewSalesForUsException
    {
        if(newSalesForUs != null) {
            try {
                if (bookingId != null) {
                    Booking booking = bookingSessionBeanLocal.retrieveBookingByBookingId(bookingId);
                    newSalesForUs.setBooking(booking);
                }
                
                if (purchasedLineItemId != null) {
                    PurchasedLineItem purchasedLineItem = purchasedLineItemSessionBeanLocal.retrievePurchasedLineItemByPurchasedLineItemId(purchasedLineItemId);
                    newSalesForUs.setPurchasedLineItem(purchasedLineItem);
              
                }
                
                em.persist(newSalesForUs);
                em.flush();
                return newSalesForUs;
            }
            catch (PurchasedLineItemNotFoundException | BookingNotFoundException ex) {
                throw new CreateNewSalesForUsException(ex.getMessage());
           }
        }
        else 
        {
            throw new CreateNewSalesForUsException("SalesForUs information not provided");
        }
    }
    
    @Override
    public List<SalesForUs> retrieveAllSalesForUs()
    {
       Query query = em.createQuery("SELECT sfu FROM SalesForUs sfu");
       List<SalesForUs> salesForUslist = query.getResultList();
       
       for (SalesForUs salesForUs : salesForUslist) {
            salesForUs.getBooking();
            salesForUs.getPurchasedLineItem();
        }
       
       return salesForUslist;
    }
    
    
    @Override
    public SalesForUs retrieveSalesForUsRecordById(Long salesForUsId) throws SalesForUsNotFoundException
    {
        SalesForUs salesForUs = em.find(SalesForUs.class, salesForUsId);
        
        if(salesForUs != null)
        {
            salesForUs.getBooking();
            salesForUs.getPurchasedLineItem();
            
            return salesForUs;
        }
        else
        {
            throw new SalesForUsNotFoundException("SalesForUs ID " + salesForUsId + " does not exist!");
        }                
    }
    
    @Override
    public void updateSalesForUsRecord(SalesForUs salesForUs)
    {
        em.merge(salesForUs);
    }
    
    
    @Override
    public void deleteSalesForUsRecord(SalesForUs salesForUs) 
    {
        throw new UnsupportedOperationException();
    }
}
