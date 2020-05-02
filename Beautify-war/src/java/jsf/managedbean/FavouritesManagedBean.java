/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.Customer;
import entity.Product;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;

/**
 *
 * @author Crystal Lee
 */
@Named(value = "favouritesManagedBean")
@ViewScoped
public class FavouritesManagedBean implements Serializable{

    private Customer currentCustomer;

    @Inject
    private CustomerManagementManagedBean customerManagementManagedBean;

    public FavouritesManagedBean() {
    }

    @PostConstruct
    public void PostConstruct() {
        currentCustomer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
    }

    public void addFavouriteProduct(ActionEvent event) {
        try {
            Product productToFavourite = (Product) event.getComponent().getAttributes().get("productToFavourite");
            currentCustomer.getFavouriteProducts().add(productToFavourite);
            productToFavourite.getFavouritedCustomers().add(currentCustomer);

            customerManagementManagedBean.setFavouriteProducts(currentCustomer.getFavouriteProducts());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product favourited successfully", null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public boolean isFavourited(ActionEvent event) {
        Product productToFavourite = (Product) event.getComponent().getAttributes().get("productToFavourite");
        for(Customer customer:productToFavourite.getFavouritedCustomers()) {
            if (customer.getCustomerId() == currentCustomer.getCustomerId()){
                return true;
            }
        }
        return false;
    }
    
    public void removeFavouriteProduct(ActionEvent event) {
        try {
            Product productToFavourite = (Product) event.getComponent().getAttributes().get("productToFavourite");
            currentCustomer.getFavouriteProducts().remove(productToFavourite);
            productToFavourite.getFavouritedCustomers().remove(currentCustomer);

            customerManagementManagedBean.setFavouriteProducts(currentCustomer.getFavouriteProducts());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product unfavourited successfully", null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
}
