/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.CreditCard;
import entity.Customer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author jilon
 */
@Named(value = "checkoutSessionBean")
@ViewScoped
public class CheckoutSessionBean implements Serializable {

    private Customer currentCustomer;
    private List<String> creditCards;

    /**
     * Creates a new instance of CheckoutSessionBean
     */
    public CheckoutSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        currentCustomer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
        List<String> list = new ArrayList<>(); 
        if (getCurrentCustomer() != null) {
            List<CreditCard> cards = getCurrentCustomer().getCreditCards();
            for (CreditCard cc : cards) { 
                String num = cc.getCardNumber(); 
                list.add(num.replace(num.substring(0, 12), "XXXXXXXXXXXX"));
            }
        }
        
        setCreditCards(list); 
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public List<String> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<String> creditCards) {
        this.creditCards = creditCards;
    }

}
