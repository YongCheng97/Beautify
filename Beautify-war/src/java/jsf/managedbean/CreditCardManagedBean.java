/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CreditCardSessionBeanLocal;
import entity.CreditCard;
import entity.Customer;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CreditCardNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Crystal Lee
 */
@Named(value = "creditCardManagedBean")
@ViewScoped
public class CreditCardManagedBean implements Serializable{

    @EJB
    private CreditCardSessionBeanLocal creditCardSessionBeanLocal;
    
    private Customer currentCustomer;

    public CreditCardManagedBean() {
        currentCustomer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
    }
    
    public void doDeleteCreditCard(ActionEvent event) {
        try{
            CreditCard creditCardToDelete = (CreditCard) event.getComponent().getAttributes().get("creditCardToDelete");
            Long creditCardToDeleteId = creditCardToDelete.getCreditCardId();
            creditCardSessionBeanLocal.deleteCreditCard(creditCardToDeleteId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Credit Card deleted successfully", null));
        } catch (CreditCardNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }    
        
    }
    
}
