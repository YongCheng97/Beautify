
package jsf.managedbean;

import ejb.session.stateless.BookingSessionBeanLocal;
import entity.Customer;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;

@Named(value = "serviceBookingManagedBean")
@SessionScoped
public class ServiceBookingManagedBean implements Serializable {

    

    @EJB(name = "BookingSessionBeanLocal")
    private BookingSessionBeanLocal bookingSessionBeanLocal;

    private Customer currentCustomer;
    
    private Date appointmentDate;
    private String address;
    private Date startTime;
    private Date endTime;
    
    private SimpleDateFormat sdf;
    /**
     * Creates a new instance of ServiceBookingManagedBean
     */
    public ServiceBookingManagedBean() {
        sdf = new SimpleDateFormat("HH:mm");
    }
    
    public void test(ActionEvent event) throws IOException {
        System.out.println("Start time: "+ this.appointmentDate );
    }

    /**
     * @return the bookingDate
     */
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * @param appointmentDate the bookingDate to set
     */
    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    
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

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
}
