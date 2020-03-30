package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Customer;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CustomerExistException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

@Named(value = "customerManagementManagedBean")
@ViewScoped

public class CustomerManagementManagedBean implements Serializable{

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    private Customer newCustomer;

    public CustomerManagementManagedBean() {
        newCustomer = new Customer();
    }

    public void createNewCustomer(ActionEvent event) {
        try {
            Long customerId = customerSessionBeanLocal.createNewCustomer(getNewCustomer());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account successfully created!", null));
        } catch (InputDataValidationException | CustomerExistException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating new account: " + ex.getMessage(), null));
        }
    }

    /**
     * @return the newCustomer
     */
    public Customer getNewCustomer() {
        return newCustomer;
    }

    /**
     * @param newCustomer the newCustomer to set
     */
    public void setNewCustomer(Customer newCustomer) {
        this.newCustomer = newCustomer;
    }
}
