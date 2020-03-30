package jsf.managedbean;

import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Product;
import entity.Service;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;


@Named(value = "listingsManagedBean")
@ViewScoped
public class ListingsManagedBean implements Serializable {

    @EJB(name = "ServiceSessionBeanLocal")
    private ServiceSessionBeanLocal serviceSessionBeanLocal;

    @EJB(name = "ProductSessionBeanLocal")
    private ProductSessionBeanLocal productSessionBeanLocal;
    
    private List<Product> productEntities; 
    private List<Service> serviceEntities; 

    public ListingsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        setProductEntities(productSessionBeanLocal.retrieveAllProducts());
        setServiceEntities(serviceSessionBeanLocal.retrieveAllServices()); 
    }

    public List<Product> getProductEntities() {
        return productEntities;
    }

    public void setProductEntities(List<Product> productEntities) {
        this.productEntities = productEntities;
    }

    public List<Service> getServiceEntities() {
        return serviceEntities;
    }

    public void setServiceEntities(List<Service> serviceEntities) {
        this.serviceEntities = serviceEntities;
    }
    
    
    
}
