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
@Named(value = "reviewsManagedBean")
@ViewScoped
public class ViewServiceProviderReviewsManagedBean implements Serializable {

    @EJB(name = "ReviewSessionBeanLocal")
    private ReviewSessionBeanLocal reviewSessionBeanLocal;

    private List<Review> reviews;
    private Long providerIdToView;
    private ServiceProvider providerToView; 

    public ViewServiceProviderReviewsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        setProviderToView((ServiceProvider) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("serviceProvider"));
        try {
            setProviderIdToView(getProviderToView().getServiceProviderId());
            setReviews(reviewSessionBeanLocal.retrieveReviewsByServiceProviderId(getProviderIdToView())); 
        } catch (ServiceProviderNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the service provider details: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
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

}
