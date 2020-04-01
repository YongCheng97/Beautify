package jsf.managedbean;
import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Customer;

import java.io.IOException;
import java.io.Serializable;
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

public class CustomerManagementManagedBean implements Serializable{

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    private Customer newCustomer;
    private Customer currentCustomer;
    
    private Customer selectedCustomerEntityToUpdate;

    public CustomerManagementManagedBean() {
        newCustomer = new Customer();
    }    

    @PostConstruct
    public void postConstruct()
    {
        currentCustomer = (Customer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
    }
    
    public void createNewCustomer(ActionEvent event) {
        try {
            Long customerId = customerSessionBeanLocal.createNewCustomer(getNewCustomer());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account successfully created!", null));
        } catch (InputDataValidationException | CustomerExistException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating new account: " + ex.getMessage(), null));
        }
    }
    
    public void viewCustomerProfile(ActionEvent event) throws IOException
    {
        Long customerIdToView = currentCustomer.getCustomerId();
        
    }
    
    public void doUpdateCustomer(ActionEvent event)
    {
        selectedCustomerEntityToUpdate = (Customer)event.getComponent().getAttributes().get("customerToUpdate");
    }
    
    public void updateCustomer(ActionEvent event)
    {
        try
        {
            customerSessionBeanLocal.updateCustomerDetails(selectedCustomerEntityToUpdate);
    
        }
        catch(UpdateCustomerException | CustomerNotFoundException |InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
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
}
