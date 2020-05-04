package ejb.session.stateless;

import entity.Booking;
import entity.CreditCard;
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
import util.exception.CreditCardNotFoundException;
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
    
    @EJB
    private CreditCardSessionBeanLocal creditCardSessionBeanLocal;

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;
    
    @EJB
    private BookingSessionBeanLocal bookingSessionBeanLocal;    
    
    public SalesForUsSessionBean() {
        
    }
    
    @Override
    public SalesForUs createNewSalesForUsBooking(SalesForUs newSalesForUs, Long bookingId) throws CreateNewSalesForUsException
    {
        if(newSalesForUs != null)
        {
           try {
                if (bookingId == null) {
                      throw new CreateNewSalesForUsException("A new sales for us must be associated with a booking");
                } else {
                  Booking booking = bookingSessionBeanLocal.retrieveBookingByBookingId(bookingId);
                  newSalesForUs.setBooking(booking);
                }
              
                em.persist(newSalesForUs);
                em.flush();
              
                return newSalesForUs;
           }
           catch (BookingNotFoundException ex) {
                throw new CreateNewSalesForUsException(ex.getMessage());
           }
        }
        else 
        {
            throw new CreateNewSalesForUsException("Sales Record information not provided");
        }
    }
    
    @Override
    public SalesForUs createNewSalesForUsPurchasedLineItem(SalesForUs newSalesForUs, Long purchasedLineItemId) throws CreateNewSalesForUsException
    {
        if(newSalesForUs != null)
        {
           try {
                if (purchasedLineItemId == null) {
                      throw new CreateNewSalesForUsException("A new sales for us must be associated with a purchased item");
                } else {
                  PurchasedLineItem purchasedLineItem = purchasedLineItemSessionBeanLocal.retrievePurchasedLineItemByPurchasedLineItemId(purchasedLineItemId);
                  newSalesForUs.setPurchasedLineItem(purchasedLineItem);
                }
              
                em.persist(newSalesForUs);
                em.flush();
              
                return newSalesForUs;
           }
           catch (PurchasedLineItemNotFoundException ex) {
                throw new CreateNewSalesForUsException(ex.getMessage());
           }
        }
        else 
        {
            throw new CreateNewSalesForUsException("Sales For Us information not provided");
        }
    }
    
    @Override
    public SalesForUs createNewSalesForUsServiceProvider(SalesForUs newSalesForUs, Long serviceProviderId, Long creditCardId) throws CreateNewSalesForUsException
    {
        if(newSalesForUs != null)
        {
           try {
                if (serviceProviderId == null || creditCardId == null) {
                      throw new CreateNewSalesForUsException("A new sales for us must be associated with a serviceProviderId and credit card");
                } else {
                  ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.retrieveServiceProviderById(serviceProviderId);
                  newSalesForUs.setServiceProvider(serviceProvider);
                  
                  CreditCard creditCard = creditCardSessionBeanLocal.retrieveCreditCardByCreditCardId(creditCardId);
                  newSalesForUs.setCreditCard(creditCard);
                }
              
                em.persist(newSalesForUs);
                em.flush();
              
                return newSalesForUs;
           }
           catch (ServiceProviderNotFoundException | CreditCardNotFoundException ex) {
                throw new CreateNewSalesForUsException(ex.getMessage());
           }
        }
        else 
        {
            throw new CreateNewSalesForUsException("Sales Record information not provided");
        }
    }
    
    @Override
    public List<SalesForUs> retrieveAllSalesForUs()
    {
        Query query = em.createQuery("SELECT sfu FROM SalesForUs sfu");
        List<SalesForUs> salesForUslist = query.getResultList();
       
        return salesForUslist;
    }
    
    
    @Override
    public SalesForUs retrieveSalesForUsRecordById(Long salesForUsId) throws SalesForUsNotFoundException
    {
        SalesForUs salesForUs = em.find(SalesForUs.class, salesForUsId);
        
        if(salesForUs != null)
        {
            
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
