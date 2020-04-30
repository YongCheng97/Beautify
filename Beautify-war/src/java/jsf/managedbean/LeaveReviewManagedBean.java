/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.PurchasedLineItemSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import entity.Booking;
import entity.Customer;
import entity.Purchased;
import entity.PurchasedLineItem;
import entity.Review;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.RateEvent;
import util.exception.CreateNewReviewException;
import util.exception.InputDataValidationException;
import util.exception.ReviewExistException;
import util.exception.ReviewNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateReviewException;

/**
 *
 * @author Crystal Lee
 */
@Named(value = "leaveReviewManagedBean")
@ViewScoped
public class LeaveReviewManagedBean implements Serializable {

    @EJB
    private ReviewSessionBeanLocal reviewSessionBeanLocal;
    
    @EJB
    private BookingSessionBeanLocal bookingSessionBeanLocal;
    
    @EJB
    private PurchasedLineItemSessionBeanLocal purchasedLineItemSessionBeanLocal;
    
    @Inject
    private CustomerManagementManagedBean customerManagementManagedBean;
    
    @Inject
    private PurchasedManagedBean purchasedManagedBean;

    private Customer currentCustomer;
    private Booking bookingToReview;
    private Review review;

    private PurchasedLineItem purchasedLineItemToReview;

    public LeaveReviewManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        currentCustomer = (Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity");
    }

    //booking
    public void doLeaveReview(ActionEvent event) {
        bookingToReview = (Booking) event.getComponent().getAttributes().get("bookingToReview");

        review = new Review();

    }
    
    //booking
    public void doLeaveProductReview(ActionEvent event) {
        purchasedLineItemToReview = (PurchasedLineItem) event.getComponent().getAttributes().get("purchasedLineItemToReview");

        review = new Review();

    }

    //booking
    public void doViewReview(ActionEvent event) {
        bookingToReview = (Booking) event.getComponent().getAttributes().get("bookingToReview");
        review = bookingToReview.getReview();
    }

    //purchasedlineitem
    public void doViewProductReview(ActionEvent event) {
        purchasedLineItemToReview = (PurchasedLineItem) event.getComponent().getAttributes().get("purchasedLineItemToReview");
        review = purchasedLineItemToReview.getReview();
    }

    //booking
    public void leaveReview(ActionEvent event) {
        Long currentCustomerId = currentCustomer.getCustomerId();
        Long bookingToReviewId = bookingToReview.getBookingId();
        try {
            Review reviewId = reviewSessionBeanLocal.createNewServiceReview(review, currentCustomerId, bookingToReviewId);
            List<Booking> completedBookings = new ArrayList<>();
            List<Booking> bookings = bookingSessionBeanLocal.retrieveAllBookingsByCustomerId(currentCustomerId);
            for (Booking booking : bookings) {
                if (booking.getStatus().equals("Completed")) {
                    completedBookings.add(booking);
                }
            }
            customerManagementManagedBean.setCompletedBookings(completedBookings);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Review posted successfully", null));
        } catch (ReviewExistException | UnknownPersistenceException | InputDataValidationException | CreateNewReviewException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while leaving review: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    //booking
    public void updateBookingReview(ActionEvent event) {
        try {
            reviewSessionBeanLocal.updateReview(review);
            List<Booking> completedBookings = new ArrayList<>();
            List<Booking> bookings = bookingSessionBeanLocal.retrieveAllBookingsByCustomerId(currentCustomer.getCustomerId());
            for (Booking booking : bookings) {
                if (booking.getStatus().equals("Completed")) {
                    completedBookings.add(booking);
                }
            }
            customerManagementManagedBean.setCompletedBookings(completedBookings);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Review updated successfully", null));
        } catch (ReviewNotFoundException | UpdateReviewException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating review: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    //booking
    public void deleteBookingReview(ActionEvent event) {
        
        try {
            Long reviewId = review.getReviewId();
            System.out.println(reviewId);
            reviewSessionBeanLocal.deleteReview(reviewId);
            List<Booking> completedBookings = new ArrayList<>();
            List<Booking> bookings = bookingSessionBeanLocal.retrieveAllBookingsByCustomerId(currentCustomer.getCustomerId());
            for (Booking booking : bookings) {
                if (booking.getStatus().equals("Completed")) {
                    completedBookings.add(booking);
                }
            }
            customerManagementManagedBean.setCompletedBookings(completedBookings);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Review deleted successfully", null));
        } catch (ReviewNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting review: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    //purchasedlineitem
    public void leaveProductReview(ActionEvent event) {
        Long currentCustomerId = currentCustomer.getCustomerId();
        Long purchasedLineItemToReviewId = purchasedLineItemToReview.getPurchasedLineItemId();
        try {
            Review reviewId = reviewSessionBeanLocal.createNewProductReview(review, currentCustomerId, purchasedLineItemToReviewId);
            purchasedLineItemToReview = purchasedLineItemSessionBeanLocal.retrievePurchasedLineItemByPurchasedLineItemId(purchasedLineItemToReviewId);
            for (Purchased purchased:currentCustomer.getPurchaseds()){
                for (PurchasedLineItem purchasedLineItem:purchased.getPurchasedLineItems()){
                    purchasedLineItem.getReview();
                }
            }
            purchasedManagedBean.setPurchaseds(currentCustomer.getPurchaseds());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Review posted successfully", null));
        } catch (ReviewExistException | UnknownPersistenceException | InputDataValidationException | CreateNewReviewException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while leaving review: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    //purchasedlineitem
    public void updateProductReview(ActionEvent event) {
        try {
            reviewSessionBeanLocal.updateReview(review);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Review updated successfully", null));
        } catch (ReviewNotFoundException | UpdateReviewException | InputDataValidationException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating review: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    //purchasedlineitem
    public void deleteProductReview(ActionEvent event) {
        
        try {
            Long reviewId = review.getReviewId();
            System.out.println(reviewId);
            reviewSessionBeanLocal.deleteReview(reviewId);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Review deleted successfully", null));
        } catch (ReviewNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting review: " + ex.getMessage(), null));
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
