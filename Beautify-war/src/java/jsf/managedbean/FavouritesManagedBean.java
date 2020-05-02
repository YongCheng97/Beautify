/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Customer;
import entity.Product;
import entity.Service;
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
import util.exception.ServiceNotFoundException;

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
    
    @Inject
    private viewServiceDetailsManagedBean viewServiceDetailsManagedBean;
            
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

            customerManagementManagedBean.getFavouriteProducts().add(productToFavourite);
            viewProductDetailsManagedBean.setProductFavourited(true);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product favourited successfully", null));
        } catch (CustomerNotFoundException | ProductNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while favouriting product: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    
    public void removeFavouriteProduct(ActionEvent event) {
        try {
            Product productToFavourite = (Product) event.getComponent().getAttributes().get("productToFavourite");
            customerSessionBeanLocal.removeFavouriteProduct(currentCustomer.getCustomerId(), productToFavourite.getProductId());

            customerManagementManagedBean.getFavouriteProducts().remove(productToFavourite);
            viewProductDetailsManagedBean.setProductFavourited(false);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product unfavourited successfully", null));
        } catch (CustomerNotFoundException | ProductNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while unfavouriting product: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void addFavouriteService(ActionEvent event) {
        try {
            Service serviceToFavourite = (Service) event.getComponent().getAttributes().get("serviceToFavourite");
            customerSessionBeanLocal.addFavouriteService(currentCustomer.getCustomerId(), serviceToFavourite.getServiceId());

            customerManagementManagedBean.getFavouriteServices().add(serviceToFavourite);
            viewServiceDetailsManagedBean.setServiceFavourited(true);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Service favourited successfully", null));
        } catch (CustomerNotFoundException | ServiceNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while favouriting service: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    
    public void removeFavouriteService(ActionEvent event) {
        try {
            Service serviceToFavourite = (Service) event.getComponent().getAttributes().get("serviceToFavourite");
            customerSessionBeanLocal.removeFavouriteService(currentCustomer.getCustomerId(), serviceToFavourite.getServiceId());

            customerManagementManagedBean.getFavouriteServices().remove(serviceToFavourite);
            viewServiceDetailsManagedBean.setServiceFavourited(false);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Service unfavourited successfully", null));
        } catch (CustomerNotFoundException | ServiceNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while unfavouriting service: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
}
