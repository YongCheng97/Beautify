/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ReviewSessionBeanLocal;
import entity.Review;
import entity.ServiceProvider;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.ServiceProviderNotFoundException;

/**
 *
 * @author jilon
 */
@Named(value = "viewServiceProviderReviewsManagedBean")
@ViewScoped
public class ViewServiceProviderReviewsManagedBean implements Serializable {

    @EJB(name = "ReviewSessionBeanLocal")
    private ReviewSessionBeanLocal reviewSessionBeanLocal;

    private List<Review> productReviews;
    private List<Review> serviceReviews;
    private Long providerIdToView;
    private ServiceProvider providerToView;

    public ViewServiceProviderReviewsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("********** ViewServiceProviderReviewsManagedBean");
        setProviderToView((ServiceProvider) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("serviceProvider"));
        try {
            setProviderIdToView(getProviderToView().getServiceProviderId());
            this.serviceReviews = reviewSessionBeanLocal.retrieveServiceReviewsByServiceProviderId(getProviderIdToView());
            System.out.println("********** serviceReviews: " + serviceReviews.size());
                    
            setProductReviews(reviewSessionBeanLocal.retrieveProductReviewsByServiceProviderId(getProviderIdToView()));
        } catch (ServiceProviderNotFoundException ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the service provider details: " + ex.getMessage(), null));
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public Long getProviderIdToView() {
        return providerIdToView;
    }

    public void setProviderIdToView(Long providerIdToView) {
        this.providerIdToView = providerIdToView;
    }

    public ServiceProvider getProviderToView() {
        return providerToView;
    }

    public void setProviderToView(ServiceProvider providerToView) {
        this.providerToView = providerToView;
    }

    public List<Review> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(List<Review> productReviews) {
        this.productReviews = productReviews;
    }

    public List<Review> getServiceReviews() {
        return serviceReviews;
    }

    public void setServiceReviews(List<Review> serviceReviews) {
        this.serviceReviews = serviceReviews;
    }

}
