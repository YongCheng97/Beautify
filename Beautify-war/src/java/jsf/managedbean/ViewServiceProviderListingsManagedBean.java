package jsf.managedbean;

import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.PromotionSessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Product;
import entity.Promotion;
import entity.Service;
import entity.ServiceProvider;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named(value = "listingsManagedBean")
@ViewScoped
public class ViewServiceProviderListingsManagedBean implements Serializable {

    @EJB
    private PromotionSessionBeanLocal promotionSessionBean;

    @EJB
    private ServiceProviderSessionBeanLocal serviceProviderSessionBean;

    @EJB
    private ServiceSessionBeanLocal serviceSessionBeanLocal;

    @EJB
    private ProductSessionBeanLocal productSessionBeanLocal;

    private List<Product> productEntities;
    private List<Service> serviceEntities;
    private ServiceProvider providerToView;

    public ViewServiceProviderListingsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        setProviderToView((ServiceProvider) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("serviceProvider"));
        serviceEntities = getProviderToView().getServices();
        for (int i = 0; i <serviceEntities.size(); i++) {
            if (serviceEntities.get(i).getIsDeleted() == true) {
                serviceEntities.remove(serviceEntities.get(i));
            }
        }
        setServiceEntities(serviceEntities);

        productEntities = getProviderToView().getProducts();
        for (Product product : productEntities) {
            if (product.getIsDeleted() == true) {
                productEntities.remove(product);
            }
        }
        setProductEntities(productEntities);

        // promotions
        /*for (Service service : serviceEntities) {
            promotionSessionBean.updateServiceDiscountPrice(service);
            if (service.getDiscountPrice() == null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(service.getServiceName(), false);
            } else {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(service.getServiceName(), true);
            }
        }

        for (Product product : productEntities) {
            promotionSessionBean.updateProductDiscountPrice(product);
            if (product.getDiscountPrice() == null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(product.getName(), false);
            } else {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(product.getName(), true);
            }
        } */
    }

    public void viewProduct(ActionEvent event) throws IOException {
        Long productIdToView = (Long) event.getComponent().getAttributes().get("productId");
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("productIdToView", productIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/viewProductDetails.xhtml");
    }

    public void viewService(ActionEvent event) throws IOException {
        Long serviceIdToView = (Long) event.getComponent().getAttributes().get("serviceId");
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("serviceIdToView", serviceIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/viewServiceDetails.xhtml");
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

    public ServiceProvider getProviderToView() {
        return providerToView;
    }

    public void setProviderToView(ServiceProvider providerToView) {
        this.providerToView = providerToView;
    }

}
