/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PurchasedLineItemSessionBeanLocal;
import ejb.session.stateless.PurchasedSessionBeanLocal;
import entity.Customer;
import entity.Purchased;
import entity.PurchasedLineItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.exception.InputDataValidationException;
import util.exception.PurchasedLineItemNotFoundException;

/**
 *
 * @author Crystal Lee
 */
@Named(value = "purchasedManagedBean")
@ViewScoped

public class PurchasedManagedBean implements Serializable {

    @EJB
    private PurchasedLineItemSessionBeanLocal purchasedLineItemSessionBeanLocal;

    private Customer currentCustomer;

    private Purchased selectedPurchased;

    private List<Purchased> purchaseds;

    private List<PurchasedLineItem> selectedPurchasedLineItems;

    @PostConstruct
    public void PostConstruct() {
        currentCustomer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");

        if (currentCustomer != null) {
            purchaseds = currentCustomer.getPurchaseds();
        }
    }

    public void doGetPurchasedDetails(ActionEvent event) {
        selectedPurchased = (Purchased) event.getComponent().getAttributes().get("selectedPurchased");
        selectedPurchasedLineItems = selectedPurchased.getPurchasedLineItems();
    }
    
    public void updatePurchasedLineItemStatus(ActionEvent event) {
        try {
            PurchasedLineItem selectedPurchasedLineItem = (PurchasedLineItem) event.getComponent().getAttributes().get("purchasedLineItemToUpdate");
            selectedPurchasedLineItem.setStatus("Product Received");
            purchasedLineItemSessionBeanLocal.updatePurchasedLineItem(selectedPurchasedLineItem);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Order Item Status updated successfully", null));
        } catch (PurchasedLineItemNotFoundException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating line item: " + ex.getMessage(), null));
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

    public List<Purchased> getPurchaseds() {
        return purchaseds;
    }

    public void setPurchaseds(List<Purchased> purchaseds) {
        this.purchaseds = purchaseds;
    }

    public List<PurchasedLineItem> getSelectedPurchasedLineItems() {
        return selectedPurchasedLineItems;
    }

    public void setSelectedPurchasedLineItems(List<PurchasedLineItem> selectedPurchasedLineItems) {
        this.selectedPurchasedLineItems = selectedPurchasedLineItems;
    }

    public Purchased getSelectedPurchased() {
        return selectedPurchased;
    }

    public void setSelectedPurchased(Purchased selectedPurchased) {
        this.selectedPurchased = selectedPurchased;
    }

}
