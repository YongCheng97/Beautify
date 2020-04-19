package jsf.managedbean;

import ejb.session.stateless.ProductSessionBeanLocal;
import entity.Product;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

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
    
}
