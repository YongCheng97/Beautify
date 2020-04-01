/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import util.exception.CustomerNotFoundException;

@Named(value = "viewCustomerProfileManagedBean")
@ViewScoped
public class ViewCustomerProfileManagedBean implements Serializable
{
    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;
    
    private Long customerIdToView;
    private Customer customerToView;
    
    public ViewCustomerProfileManagedBean() 
    {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        customerIdToView = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("customerIdToView");
        
        try
        {            
            customerToView = customerSessionBeanLocal.retrieveCustomerByCustId(customerIdToView);
        }
        catch(CustomerNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the customer details: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void updateCustomer(ActionEvent event) throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("customerIdToUpdate", customerIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("updateProduct.xhtml");
    }

    public Customer getCustomerToView() {
        return customerToView;
    }

    public void setCustomerToView(Customer customerToView) {
        this.customerToView = customerToView;
    }
    
}
