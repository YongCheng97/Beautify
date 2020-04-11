/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Booking;
import entity.Review;
import entity.Service;
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

    @EJB
    private ServiceSessionBeanLocal serviceSessionBean;

    @EJB
    private BookingSessionBeanLocal bookingSessionBean;

    @EJB
    private ServiceProviderSessionBeanLocal serviceProviderSessionBean;

    @EJB(name = "ReviewSessionBeanLocal")
    private ReviewSessionBeanLocal reviewSessionBeanLocal;

    private List<Review> reviews;
    private Long providerIdToView;
    private ServiceProvider providerToView;
    private List<Service> services;
    private List<Booking> bookings;

    public ViewServiceProviderReviewsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        providerIdToView = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("serviceProviderId"));
        try {
            providerToView = serviceProviderSessionBean.retrieveServiceProviderById(providerIdToView);
            services = providerToView.getServices();
            for (Service service : services) {
                List<Booking> bookingList = service.getBookings();
                for (Booking booking : bookingList) {
                    bookings.add((Booking) booking);
                }
            }
            for (Booking booking : bookings) {
                reviews.add(booking.getReview());
            }
            setReviews(reviews);
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

}
