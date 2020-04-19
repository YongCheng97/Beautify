/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ReviewSessionBeanLocal;
import entity.Booking;
import entity.Customer;
import entity.Review;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.RateEvent;
import util.exception.CreateNewReviewException;
import util.exception.InputDataValidationException;
import util.exception.ReviewExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Crystal Lee
 */
@Named(value = "leaveReviewManagedBean")
@ViewScoped
public class LeaveReviewManagedBean implements Serializable {

    @EJB
    private ReviewSessionBeanLocal reviewSessionBeanLocal;

    private Customer currentCustomer;
    private Booking bookingToReview;
    private Review review;

    public LeaveReviewManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        currentCustomer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
    }

    public void doLeaveReview(ActionEvent event) {
        bookingToReview = (Booking) event.getComponent().getAttributes().get("bookingToReview");
        if (bookingToReview.getReview() != null) {
            review = bookingToReview.getReview();
        } else {
        review = new Review();
        }
    }

    public void leaveReview(ActionEvent event) {
        Long currentCustomerId = currentCustomer.getCustomerId();
        Long bookingToReviewId = bookingToReview.getBookingId();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Review posted successfully", null));
        try {
            Review reviewId = reviewSessionBeanLocal.createNewReview(review, currentCustomerId, bookingToReviewId);
        } catch (ReviewExistException | UnknownPersistenceException | InputDataValidationException | CreateNewReviewException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating product: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }    
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Booking getBookingToReview() {
        return bookingToReview;
    }

    public void setBookingToReview(Booking bookingToReview) {
        this.bookingToReview = bookingToReview;
    }
}
