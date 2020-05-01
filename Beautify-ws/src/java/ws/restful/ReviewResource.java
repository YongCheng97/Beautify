/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import entity.Review;
import entity.ServiceProvider;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.InvalidLoginCredentialException;
import util.exception.ReviewNotFoundException;
import ws.datamodel.ErrorRsp;
import ws.datamodel.RetrieveAllProductReviewsRsp;
import ws.datamodel.RetrieveAllServiceReviewsRsp;
import ws.datamodel.RetrieveReviewRsp;

/**
 * REST Web Service
 *
 * @author jilon
 */
@Path("Review")
public class ReviewResource {

    @Context
    private UriInfo context;

    private final ServiceProviderSessionBeanLocal serviceProviderSessionBeanLocal = lookupServiceProviderSessionBeanLocal();

    private final ReviewSessionBeanLocal reviewSessionBeanLocal = lookupReviewSessionBeanLocal();

    /**
     * Creates a new instance of ReviewResource
     */
    public ReviewResource() {
    }

    @Path("retrieveAllServiceReviews")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllServiceReviews(@QueryParam("username") String username,
            @QueryParam("password") String password) {

        try {
            ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(username, password);
            System.out.println("********** ReviewResource.retrieveAllServiceReviews(): Service Provider " + serviceProvider.getName() + " login remotely via web service");

            List<Review> reviews = reviewSessionBeanLocal.retrieveServiceReviewsByServiceProviderId(serviceProvider.getServiceProviderId());

            for (Review review : reviews) {
                if (review.getBooking() != null) {
                    /*review.getBooking().setCustomer(null);
                    review.getBooking().setReview(null);
                    review.getBooking().setService(null);
                     */
                    review.getBooking().getCustomer().getBookings().clear();
                    review.getBooking().getCustomer().getCreditCards().clear();
                    review.getBooking().getCustomer().getFavouriteProducts().clear();
                    review.getBooking().getCustomer().getFavouriteServices().clear();
                    review.getBooking().getCustomer().getReviews().clear();
                    review.getBooking().getCustomer().getPurchaseds().clear();
                    review.getBooking().setReview(null);
                    review.getBooking().getService().getBookings().clear();
                    review.getBooking().getService().getPromotions().clear();
                    review.getBooking().getService().setCategory(null);
                    review.getBooking().getService().setServiceProvider(null);
                    review.getBooking().getService().getTags().clear();
                    review.getBooking().getService().getFavouritedCustomers().clear();
                }

                if (review.getPurchasedLineItem() != null) {
                    //review.getPurchasedLineItem().setProduct(null);
                    review.getPurchasedLineItem().setReview(null);
                    review.getPurchasedLineItem().getProduct().setCategory(null);
                    review.getPurchasedLineItem().getProduct().setServiceProvider(null);
                    review.getPurchasedLineItem().getProduct().getFavouritedCustomers().clear();
                    review.getPurchasedLineItem().getProduct().getTags().clear();
                    review.getPurchasedLineItem().getProduct().getPromotions().clear();
                }

                review.getCustomer().getBookings().clear();
                review.getCustomer().getCreditCards().clear();
                review.getCustomer().getFavouriteProducts().clear();
                review.getCustomer().getFavouriteServices().clear();
                review.getCustomer().getReviews().clear();
                review.getCustomer().getPurchaseds().clear();
            }

            System.out.println("********** ReviewResource.retrieveAllServiceReviews(): " + reviews.size());

            return Response.status(Response.Status.OK).entity(new RetrieveAllServiceReviewsRsp(reviews)).build();

        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveAllProductReviews")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProductReviews(@QueryParam("username") String username,
            @QueryParam("password") String password) {

        try {
            ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(username, password);
            System.out.println("********** ReviewResource.retrieveAllProductReviews(): Service Provider " + serviceProvider.getName() + " login remotely via web service");

            List<Review> reviews = reviewSessionBeanLocal.retrieveProductReviewsByServiceProviderId(serviceProvider.getServiceProviderId());

            for (Review review : reviews) {
                if (review.getBooking() != null) {
                    /*review.getBooking().setCustomer(null);
                    review.getBooking().setReview(null);
                    review.getBooking().setService(null);
                     */
                    review.getBooking().setCustomer(null);
                    review.getBooking().setReview(null);
                    review.getBooking().getService().getBookings().clear();
                    review.getBooking().getService().getPromotions().clear();
                    review.getBooking().getService().setCategory(null);
                    review.getBooking().getService().setServiceProvider(null);
                    review.getBooking().getService().getTags().clear();
                    review.getBooking().getService().getFavouritedCustomers().clear();
                }

                if (review.getPurchasedLineItem() != null) {
                    //review.getPurchasedLineItem().setProduct(null);
                    review.getPurchasedLineItem().setReview(null);
                    review.getPurchasedLineItem().getProduct().setCategory(null);
                    review.getPurchasedLineItem().getProduct().setServiceProvider(null);
                    review.getPurchasedLineItem().getProduct().getFavouritedCustomers().clear();
                    review.getPurchasedLineItem().getProduct().getTags().clear();
                    review.getPurchasedLineItem().getProduct().getPromotions().clear();
                    review.getPurchasedLineItem().getPurchased().setCreditCard(null);
                    review.getPurchasedLineItem().getPurchased().setCustomer(null);
                    review.getPurchasedLineItem().getPurchased().getPurchasedLineItems().clear(); 
                }

                review.getCustomer().getBookings().clear();
                review.getCustomer().getCreditCards().clear();
                review.getCustomer().getFavouriteProducts().clear();
                review.getCustomer().getFavouriteServices().clear();
                review.getCustomer().getReviews().clear();
                review.getCustomer().getPurchaseds().clear();
            }

            System.out.println("********** ReviewResource.retrieveAllProductReviews(): " + reviews.size());

            return Response.status(Response.Status.OK).entity(new RetrieveAllProductReviewsRsp(reviews)).build();

        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveReview/{reviewId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveReview(@QueryParam("username") String username,
            @QueryParam("password") String password,
            @PathParam("reviewId") Long reviewId) {
        try {
            ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(username, password);
            System.out.println("********** BookingResource.retrieveReview(): Service Provider " + serviceProvider.getName() + " login remotely via web service");

            Review review = reviewSessionBeanLocal.retrieveReviewByReviewId(reviewId);

            review.getBooking().setReview(null);
            review.getPurchasedLineItem().setReview(null);
            review.getCustomer().getBookings().clear();

            return Response.status(Response.Status.OK).entity(new RetrieveReviewRsp(review)).build();

        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }

    }

    private ReviewSessionBeanLocal lookupReviewSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ReviewSessionBeanLocal) c.lookup("java:global/Beautify/Beautify-ejb/ReviewSessionBean!ejb.session.stateless.ReviewSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ServiceProviderSessionBeanLocal lookupServiceProviderSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ServiceProviderSessionBeanLocal) c.lookup("java:global/Beautify/Beautify-ejb/ServiceProviderSessionBean!ejb.session.stateless.ServiceProviderSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
