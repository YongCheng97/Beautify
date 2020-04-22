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

    private List<Promotion> promotions;

    public ViewServiceProviderListingsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        setProviderToView((ServiceProvider) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("serviceProvider"));
        productEntities = getProviderToView().getProducts();
        setProductEntities(productEntities);

        serviceEntities = getProviderToView().getServices();
        setServiceEntities(serviceEntities);

        // promotions
        promotions = promotionSessionBean.retrieveAllPromotions();
        for (Promotion promotion : promotions) {
            for (Service service : serviceEntities) {
                List<Promotion> servicePromotions = service.getPromotions();
                for (Promotion spromotion : servicePromotions) {
                    if (spromotion.equals(promotion)) {
                        Date date = new Date();
                        BigDecimal discountPrice = new BigDecimal(0);

                        if (promotion.getStartDate().compareTo(date) < 0 && promotion.getEndDate().compareTo(date) > 0) {
                            discountPrice = service.getPrice().multiply(promotion.getDiscountRate());
                            service.setDiscountPrice(discountPrice.setScale(2, BigDecimal.ROUND_HALF_UP));

                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(service.getServiceName(), true);
                            break;
                        }
                    }
                }
            }
        }

        for (Promotion promotion : promotions) {
            for (Product product : productEntities) {
                List<Promotion> productPromotions = product.getPromotions();
                for (Promotion ppromotion : productPromotions) {
                    if (ppromotion.equals(promotion)) {
                        Date date = new Date();
                        BigDecimal discountPrice = new BigDecimal(0);

                        if (promotion.getStartDate().compareTo(date) < 0 && promotion.getEndDate().compareTo(date) > 0) {
                            discountPrice = product.getPrice().multiply(promotion.getDiscountRate());
                            product.setDiscountPrice(discountPrice.setScale(2, BigDecimal.ROUND_HALF_UP));

                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(product.getName(), true);
                            break;
                        }
                    }
                }
            }
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

    public ServiceProvider getProviderToView() {
        return providerToView;
    }

    public void setProviderToView(ServiceProvider providerToView) {
        this.providerToView = providerToView;
    }

}
