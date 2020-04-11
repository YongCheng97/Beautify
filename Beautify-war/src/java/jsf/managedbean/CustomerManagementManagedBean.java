package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Booking;
import entity.Customer;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCustomerException;

@Named(value = "customerManagementManagedBean")
@ViewScoped

public class CustomerManagementManagedBean implements Serializable {

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    private Customer newCustomer;
    private Customer currentCustomer;

    private Customer selectedCustomerEntityToUpdate;

    private List<Booking> customerBookings;
    private Booking selectedBooking;

    public CustomerManagementManagedBean() {
        newCustomer = new Customer();
    }

    @PostConstruct
    public void postConstruct() {
        currentCustomer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
//        customerBookings = currentCustomer.getBookings();
    }

    public void createNewCustomer(ActionEvent event) throws IOException {
        try {
            Long customerId = customerSessionBeanLocal.createNewCustomer(getNewCustomer());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account successfully created!", null));
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
        } catch (InputDataValidationException | CustomerExistException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating new account: " + ex.getMessage(), null));
        }
    }

    public void doUpdateCustomer(ActionEvent event) {
        selectedCustomerEntityToUpdate = currentCustomer;
    }

    public void updateCustomer(ActionEvent event) {
        try {
            customerSessionBeanLocal.updateCustomerDetails(selectedCustomerEntityToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile updated successfully", null));
        } catch (UpdateCustomerException | CustomerNotFoundException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public Customer getNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(Customer newCustomer) {
        this.newCustomer = newCustomer;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public Customer getSelectedCustomerEntityToUpdate() {
        return selectedCustomerEntityToUpdate;
    }

    public void setSelectedCustomerEntityToUpdate(Customer selectedCustomerEntityToUpdate) {
        this.selectedCustomerEntityToUpdate = selectedCustomerEntityToUpdate;
    }

    public List<Booking> getCustomerBookings() {
        return customerBookings;
    }

    public void setCustomerBookings(List<Booking> customerBookings) {
        this.customerBookings = customerBookings;
    }

    public Booking getSelectedBooking() {
        return selectedBooking;
    }

    public void setSelectedBooking(Booking selectedBooking) {
        this.selectedBooking = selectedBooking;
    }
}
