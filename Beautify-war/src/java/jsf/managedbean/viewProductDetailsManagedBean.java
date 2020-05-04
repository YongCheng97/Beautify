/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import entity.Customer;
import entity.Product;
import entity.ServiceProvider;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import util.exception.ProductNotFoundException;
import util.exception.ServiceProviderNotFoundException;

@Named(value = "viewProductDetailsManagedBean")
@ViewScoped
public class viewProductDetailsManagedBean implements Serializable {

    @EJB(name = "ProductSessionBeanLocal")
    private ProductSessionBeanLocal productSessionBeanLocal;

    @EJB
    private ServiceProviderSessionBeanLocal serviceProviderSessionBean;

    @Inject
    private ShoppingCartManagedBean shoppingCartManagedBean;

    private Long productIdToView;
    private Product productToView;
    private List<String> productImages;

    private boolean productFavourited;

    public viewProductDetailsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Customer currentCustomer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");

        productIdToView = (Long) session.getAttribute("productIdToView");
        try {
            productToView = productSessionBeanLocal.retrieveProductByProdId(productIdToView);

            List<Customer> favouriteCustomers = productToView.getFavouritedCustomers();
            if (!favouriteCustomers.isEmpty() && currentCustomer != null) {
                for (Customer customer : favouriteCustomers) {
                    if (customer.getCustomerId() == currentCustomer.getCustomerId()) {
                        productFavourited = true;
                        break;
                    } else {
                        productFavourited = false;
                    }
                }
            }
            System.out.println("test load");
            productImages = new ArrayList<String>();

            for (int i = 0; i <= 2; i++) {
                if (i == 0) {
                    productImages.add(productToView.getName() + ".jpg");
                } else {
                    productImages.add(productToView.getName() + i + ".jpg");
                }
            }

        } catch (ProductNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the product details: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void foo() {
    }

    public void viewServiceProvider(Long serviceProviderId) throws IOException {
        try {
            ServiceProvider serviceProvider = serviceProviderSessionBean.retrieveServiceProviderById(serviceProviderId);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("serviceProvider", serviceProvider);
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/serviceProviderProfile.xhtml");
        } catch (ServiceProviderNotFoundException ex) {
            Logger.getLogger(viewAllServiceProvidersManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> getProductImages() {
        return productImages;
    }

    public Product getProductToView() {
        return productToView;
    }

    public void setProductToView(Product productToView) {
        this.productToView = productToView;
    }

    /**
     * @return the shoppingCartManagedBean
     */
    public ShoppingCartManagedBean getShoppingCartManagedBean() {
        return shoppingCartManagedBean;
    }

    /**
     * @param shoppingCartManagedBean the shoppingCartManagedBean to set
     */
    public void setShoppingCartManagedBean(ShoppingCartManagedBean shoppingCartManagedBean) {
        this.shoppingCartManagedBean = shoppingCartManagedBean;
    }

    public boolean isProductFavourited() {
        return productFavourited;
    }

    public void setProductFavourited(boolean productFavourited) {
        this.productFavourited = productFavourited;
    }
}
