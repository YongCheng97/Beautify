
package jsf.managedbean;

import ejb.session.stateless.BookingSessionBeanLocal;
import entity.Customer;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;

@Named(value = "serviceBookingManagedBean")
@SessionScoped
public class ServiceBookingManagedBean implements Serializable {

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    @EJB(name = "BookingSessionBeanLocal")
    private BookingSessionBeanLocal bookingSessionBeanLocal;

    private Customer currentCustomer;
    
    private Date bookingDate;
    private String address;
    /**
     * Creates a new instance of ServiceBookingManagedBean
     */
    public ServiceBookingManagedBean() {
    }

    /**
     * @return the bookingDate
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    /**
     * @param bookingDate the bookingDate to set
     */
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
    
}
