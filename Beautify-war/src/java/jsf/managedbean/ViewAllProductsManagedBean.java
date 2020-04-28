package jsf.managedbean;

import ejb.session.stateless.ProductSessionBeanLocal;
import entity.Product;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

@Named(value = "viewAllProductsManagedBean")
@ViewScoped
public class ViewAllProductsManagedBean implements Serializable{

    @EJB
    private ProductSessionBeanLocal productSessionBean;

    private List<Product> products;
    
    public ViewAllProductsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        products = productSessionBean.retrieveAllProducts();
    }

    /**
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    public void viewProduct(ActionEvent event) throws IOException {
        Long productIdToView = (Long) event.getComponent().getAttributes().get("productId");
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("productIdToView", productIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/viewProductDetails.xhtml");
    }
}
