/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.SalesRecordSessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import entity.Booking;
import entity.SalesRecord;
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
import ws.datamodel.RetrieveAllSalesRecordRsp;
import ws.datamodel.RetrieveBookingRsp;
import ws.datamodel.UpdateBookingReq;


@Path("SalesRecord")
public class SalesRecordResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    
    private final ServiceProviderSessionBeanLocal serviceProviderSessionBeanLocal;
    private final SalesRecordSessionBeanLocal salesRecordSessionBeanLocal;
    
    
    public SalesRecordResource() {
        
        sessionBeanLookup = new SessionBeanLookup();
        
        serviceProviderSessionBeanLocal = sessionBeanLookup.lookupServiceProviderSessionBeanLocal();
        salesRecordSessionBeanLocal = sessionBeanLookup.lookupSalesRecordSessionBeanLocal();
        
    }

    @Path("retrieveAllSalesRecordBooking")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllSalesRecordBooking(@QueryParam("username") String username, 
                                                  @QueryParam("password") String password)
    {
        try
        {
            ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(username, password);
            System.out.println("********** SalesRecordResource.retrieveAllSalesRecordBooking(): Service Provider " + serviceProvider.getUsername()+ " login remotely via web service");

            List<SalesRecord> salesRecords = salesRecordSessionBeanLocal.retrieveAllBookingSalesRecordByServiceProviderId(serviceProvider.getServiceProviderId());
                                   
            for(SalesRecord salesRecord:salesRecords)
            {
                
                if(salesRecord.getBooking().getReview() != null){
                    salesRecord.getBooking().getReview().setBooking(null);
                    salesRecord.getBooking().getReview().setCustomer(null);
                    salesRecord.getBooking().getReview().setPurchasedLineItem(null);
                }
                
                salesRecord.getBooking().getCustomer().getBookings().clear();
                salesRecord.getBooking().getCustomer().getCreditCards().clear();
                salesRecord.getBooking().getCustomer().getFavouriteProducts().clear();
                salesRecord.getBooking().getCustomer().getFavouriteServices().clear();
                salesRecord.getBooking().getCustomer().getReviews().clear();
                salesRecord.getBooking().getCustomer().getPurchaseds().clear();
                
                salesRecord.getBooking().getService().getBookings().clear();
                salesRecord.getBooking().getService().setServiceProvider(null);
                salesRecord.getBooking().getService().setCategory(null);
                salesRecord.getBooking().getService().getTags().clear();
                salesRecord.getBooking().getService().getPromotions().clear();
                
            }
                
            return Response.status(Response.Status.OK).entity(new RetrieveAllSalesRecordRsp(salesRecords)).build();
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
    
    @Path("retrieveAllSalesRecordPurchasedLineItem")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllSalesRecordPurchasedLineItem(@QueryParam("username") String username, 
                                                            @QueryParam("password") String password)
    {
        try
        {
            ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(username, password);
            System.out.println("********** SalesRecordResource.retrieveAllSalesRecordPurchasedLineItem(): Service Provider " + serviceProvider.getUsername()+ " login remotely via web service");

            List<SalesRecord> salesRecords = salesRecordSessionBeanLocal.retrieveAllPurchasedLineItemSalesRecordByServiceProviderId(serviceProvider.getServiceProviderId());
                                   
            for(SalesRecord salesRecord:salesRecords)
            {
                
                if(salesRecord.getPurchasedLineItem().getReview() != null){
                    salesRecord.getPurchasedLineItem().getReview().setBooking(null);
                    salesRecord.getPurchasedLineItem().getReview().setCustomer(null);
                    salesRecord.getPurchasedLineItem().getReview().setPurchasedLineItem(null);
                }
                
                salesRecord.getPurchasedLineItem().getProduct().setCategory(null);
                salesRecord.getPurchasedLineItem().getProduct().setServiceProvider(null);
                salesRecord.getPurchasedLineItem().getProduct().getFavouritedCustomers().clear();
                salesRecord.getPurchasedLineItem().getProduct().getTags().clear();
                salesRecord.getPurchasedLineItem().getProduct().getPromotions().clear();
                
                salesRecord.getPurchasedLineItem().getPurchased().getPurchasedLineItems().clear();
                salesRecord.getPurchasedLineItem().getPurchased().setCreditCard(null);
                salesRecord.getPurchasedLineItem().getPurchased().getCustomer().getBookings().clear();
                salesRecord.getPurchasedLineItem().getPurchased().getCustomer().getCreditCards().clear();
                salesRecord.getPurchasedLineItem().getPurchased().getCustomer().getFavouriteProducts().clear();
                salesRecord.getPurchasedLineItem().getPurchased().getCustomer().getFavouriteServices().clear();
                salesRecord.getPurchasedLineItem().getPurchased().getCustomer().getReviews().clear();
                salesRecord.getPurchasedLineItem().getPurchased().getCustomer().getPurchaseds().clear();
                
            }
                
            return Response.status(Response.Status.OK).entity(new RetrieveAllSalesRecordRsp(salesRecords)).build();
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

}
