/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ProductSessionBeanLocal;
import entity.Product;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.ProductNotFoundException;


@Named(value = "viewProductDetailsManagedBean")
@ViewScoped
public class viewProductDetailsManagedBean implements Serializable 
{

    @EJB(name = "ProductSessionBeanLocal")
    private ProductSessionBeanLocal productSessionBeanLocal;

    private Long productIdToView;
    private Product productToView;
    private List<String> productImages;
    
    public viewProductDetailsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        productIdToView = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("productIdToView");
        try
        {            
            productToView = productSessionBeanLocal.retrieveProductByProdId(productIdToView);
            productImages = new ArrayList<String>();
            
            for (int i=1; i<=5; i++) {
                productImages.add(productToView.getName() + i + ".jpg");
            }
        }
        catch(ProductNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the product details: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void foo()
    {        
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
}
