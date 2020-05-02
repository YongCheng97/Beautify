/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Customer;
import entity.Product;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.ReviewNotFoundException;

/**
 *
 * @author Crystal Lee
 */
@Named(value = "favouritesManagedBean")
@ViewScoped
public class FavouritesManagedBean implements Serializable{

    private Customer currentCustomer;
    
    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @Inject
    private CustomerManagementManagedBean customerManagementManagedBean;
    
    @Inject
    private viewProductDetailsManagedBean viewProductDetailsManagedBean;
            
    public FavouritesManagedBean() {
    }

    @PostConstruct
    public void PostConstruct() {
        currentCustomer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
    }

    public void addFavouriteProduct(ActionEvent event) {
        try {
            Product productToFavourite = (Product) event.getComponent().getAttributes().get("productToFavourite");
            customerSessionBeanLocal.addFavouriteProduct(currentCustomer.getCustomerId(), productToFavourite.getProductId());
            Customer cust = customerSessionBeanLocal.retrieveCustomerByCustId(currentCustomer.getCustomerId());

            customerManagementManagedBean.getFavouriteProducts().add(productToFavourite);
            viewProductDetailsManagedBean.setProductFavourited(true);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product favourited successfully", null));
        } catch (CustomerNotFoundException | ProductNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting review: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    
    public void removeFavouriteProduct(ActionEvent event) {
        try {
            Product productToFavourite = (Product) event.getComponent().getAttributes().get("productToFavourite");
            customerSessionBeanLocal.removeFavouriteProduct(currentCustomer.getCustomerId(), productToFavourite.getProductId());
            Customer cust = customerSessionBeanLocal.retrieveCustomerByCustId(currentCustomer.getCustomerId());

            customerManagementManagedBean.getFavouriteProducts().remove(productToFavourite);
            viewProductDetailsManagedBean.setProductFavourited(false);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product unfavourited successfully", null));
        } catch (CustomerNotFoundException | ProductNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting review: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
}
