package jsf.managedbean;

import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Product;
import entity.Service;
import entity.ServiceProvider;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import util.exception.ServiceProviderNotFoundException;

@Named(value = "listingsManagedBean")
@ViewScoped
public class ViewServiceProviderListingsManagedBean implements Serializable {

    @EJB
    private ServiceProviderSessionBeanLocal serviceProviderSessionBean;

    @EJB
    private ServiceSessionBeanLocal serviceSessionBeanLocal;

    @EJB
    private ProductSessionBeanLocal productSessionBeanLocal;

    private List<Product> productEntities;
    private List<Service> serviceEntities;
    private Long providerIdToView;
    private ServiceProvider providerToView;

    public ViewServiceProviderListingsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        providerIdToView = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("serviceProviderId"));
        try {
            providerToView = serviceProviderSessionBean.retrieveServiceProviderById(providerIdToView);
            productEntities = providerToView.getProducts();
            setProductEntities(productEntities);

            serviceEntities = providerToView.getServices();
            setServiceEntities(serviceEntities);
        } catch (ServiceProviderNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the service provider details: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void clickLink(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/viewProductDetails.xhtml");
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
