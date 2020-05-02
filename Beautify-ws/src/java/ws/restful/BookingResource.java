/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import entity.Booking;
import entity.ServiceProvider;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.BookingNotFoundException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UpdateBookingException;
import util.exception.UpdateProductException;
import ws.datamodel.ErrorRsp;
import ws.datamodel.RetrieveAllBookingsRsp;
import ws.datamodel.RetrieveBookingRsp;
import ws.datamodel.UpdateBookingReq;


@Path("Booking")
public class BookingResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    
    private final ServiceProviderSessionBeanLocal serviceProviderSessionBeanLocal;
    private final BookingSessionBeanLocal bookingSessionBeanLocal;
    
    
    public BookingResource() {
        
        sessionBeanLookup = new SessionBeanLookup();
        
        serviceProviderSessionBeanLocal = sessionBeanLookup.lookupServiceProviderSessionBeanLocal();
        bookingSessionBeanLocal = sessionBeanLookup.lookupBookingSessionBeanLocal();
        
    }

    @Path("retrieveAllBookings")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllBookings(@QueryParam("username") String username, 
                                        @QueryParam("password") String password)
    {
        try
        {
            ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(username, password);
            System.out.println("********** BookingResource.retrieveAllBookings(): Service Provider " + serviceProvider.getName()+ " login remotely via web service");

            List<Booking> bookings = bookingSessionBeanLocal.retrieveAllBookingsByServiceProviderId(serviceProvider.getServiceProviderId());
                                   
            for(Booking booking:bookings)
            {
                if(booking.getReview() != null){
                    booking.getReview().setBooking(null);
                    booking.getReview().setCustomer(null);
                    booking.getReview().setPurchasedLineItem(null);
                }
                
                booking.getCustomer().getBookings().clear();
                booking.getCustomer().getCreditCards().clear();
                booking.getCustomer().getFavouriteProducts().clear();
                booking.getCustomer().getFavouriteServices().clear();
                booking.getCustomer().getReviews().clear();
                booking.getCustomer().getPurchaseds().clear();
                
                booking.getService().getBookings().clear();
                booking.getService().setServiceProvider(null);
                booking.getService().setCategory(null);
                booking.getService().getTags().clear();
                booking.getService().getPromotions().clear();
            }
                
            return Response.status(Response.Status.OK).entity(new RetrieveAllBookingsRsp(bookings)).build();
        }
        catch(InvalidLoginCredentialException ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        }
        catch(Exception ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveBooking/{bookingId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveBooking(@QueryParam("username") String username, 
                                        @QueryParam("password") String password,
                                        @PathParam("bookingId") Long bookingId)
    {
        try
        {
            ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(username, password);
            System.out.println("********** BookingResource.retrieveBooking(): Service Provider " + serviceProvider.getName() + " login remotely via web service");

            Booking booking = bookingSessionBeanLocal.retrieveBookingByBookingId(bookingId);
                      
            if(booking.getReview() != null){
                booking.getReview().setBooking(null);
                booking.getReview().setCustomer(null);
                booking.getReview().setPurchasedLineItem(null);
            }

            booking.getCustomer().getBookings().clear();
            booking.getCustomer().getCreditCards().clear();
            booking.getCustomer().getFavouriteProducts().clear();
            booking.getCustomer().getFavouriteServices().clear();
            booking.getCustomer().getReviews().clear();
            booking.getCustomer().getPurchaseds().clear();

            booking.getService().getBookings().clear();
            booking.getService().setServiceProvider(null);
            booking.getService().setCategory(null);
            booking.getService().getTags().clear();
            booking.getService().getPromotions().clear();
            
            return Response.status(Response.Status.OK).entity(new RetrieveBookingRsp(booking)).build();
        }
        catch(InvalidLoginCredentialException ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        }
        catch(BookingNotFoundException ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
        catch(Exception ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBooking(UpdateBookingReq updateBookingReq)
    {
        if(updateBookingReq != null)
        {
            try
            {                
                ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(updateBookingReq.getUsername(), updateBookingReq.getPassword());
                System.out.println("********** BookingResource.updateBooking(): Service Provider " + serviceProvider.getName()+ " login remotely via web service");
                
                bookingSessionBeanLocal.updateBookingStatus(updateBookingReq.getBookingId(), updateBookingReq.getStatus());
                
                return Response.status(Response.Status.OK).build();
            }
            catch(InvalidLoginCredentialException ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
            }
            catch(UpdateBookingException ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                
                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            }
            catch(Exception ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update booking request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
}
