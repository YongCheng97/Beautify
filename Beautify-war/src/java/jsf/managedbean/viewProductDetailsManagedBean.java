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
    private String backMode;
    private Product productToView;
    
    public viewProductDetailsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        productIdToView = (Long)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("productIdToView");
        backMode = (String)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("backMode");
        
        try
        {            
            productToView = productSessionBeanLocal.retrieveProductByProdId(productIdToView);
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
    
    public void back(ActionEvent event) throws IOException
    {
        if(backMode == null || backMode.trim().length() == 0)
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect("viewAllProducts.xhtml");
        }
        else
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect(backMode + ".xhtml");
        }
    }
    
    
    
    public void foo()
    {        
    }
    
    
    public Product getProductToView() {
        return productToView;
    }

    public void setProductToView(Product productToView) {
        this.productToView = productToView;
    }
}
