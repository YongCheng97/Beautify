/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.BookingSessionBeanLocal;
import entity.Booking;
import entity.Customer;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.BookingNotFoundException;

/**
 *
 * @author Crystal Lee
 */
@Named(value = "bookingManagedBean")
@ViewScoped
public class BookingManagedBean implements Serializable {

    @EJB
    private BookingSessionBeanLocal bookingSessionBeanLocal;
    
    @Inject
    private CustomerManagementManagedBean customerManagementManagedBean;
    
    private Customer currentCustomer;
    
    private Booking bookingToDelete;

    @PostConstruct
    public void PostConstruct() {
        currentCustomer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
    }
    
    public void doDeleteBooking(ActionEvent event) {
        try{
            Booking bookingToDelete = (Booking) event.getComponent().getAttributes().get("bookingToDelete");
            Long bookingToDeleteId = bookingToDelete.getBookingId();
            bookingSessionBeanLocal.deleteBooking(bookingToDeleteId);
            customerManagementManagedBean.getUpcomingBookings().remove(bookingToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Booking cancelled successfully", null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }    
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public Booking getBookingToDelete() {
        return bookingToDelete;
    }

    public void setBookingToDelete(Booking bookingToDelete) {
        this.bookingToDelete = bookingToDelete;
    }
    
}
