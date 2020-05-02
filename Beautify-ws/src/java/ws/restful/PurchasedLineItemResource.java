/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.PurchasedLineItemSessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import entity.Booking;
import entity.PurchasedLineItem;
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
import util.exception.PurchasedLineItemNotFoundException;
import util.exception.UpdateBookingException;
import util.exception.UpdateProductException;
import ws.datamodel.ErrorRsp;
import ws.datamodel.RetrieveAllBookingsRsp;
import ws.datamodel.RetrieveAllPurchasedLineItemsRsp;
import ws.datamodel.RetrieveBookingRsp;
import ws.datamodel.RetrievePurchasedLineItemRsp;
import ws.datamodel.UpdateBookingReq;
import ws.datamodel.UpdatePurchasedLineItemReq;


@Path("PurchasedLineItem")
public class PurchasedLineItemResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;
    
    private final ServiceProviderSessionBeanLocal serviceProviderSessionBeanLocal;
    private final PurchasedLineItemSessionBeanLocal purchasedLineItemSessionBeanLocal;
    
    
    public PurchasedLineItemResource() {
        
        sessionBeanLookup = new SessionBeanLookup();
        
        serviceProviderSessionBeanLocal = sessionBeanLookup.lookupServiceProviderSessionBeanLocal();
        purchasedLineItemSessionBeanLocal = sessionBeanLookup.lookupPurchasedLineItemSessionBeanLocal();
        
    }

    @Path("retrieveAllPurchasedLineItems")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllPurchasedLineItems(@QueryParam("username") String username, 
                                                    @QueryParam("password") String password)
    {
        try
        {
            ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(username, password);
            System.out.println("********** PurchasedLineItemResource.retrieveAllPurchasedLineItems(): Service Provider " + serviceProvider.getName()+ " login remotely via web service");

            List<PurchasedLineItem> purchasedLineItems = purchasedLineItemSessionBeanLocal.retrieveAllPurchasedLineItemByServiceProviderId(serviceProvider.getServiceProviderId());
                                   
            for(PurchasedLineItem purchasedLineItem:purchasedLineItems)
            {
                if(purchasedLineItem.getReview() != null){
                    purchasedLineItem.getReview().setBooking(null);
                    purchasedLineItem.getReview().setCustomer(null);
                    purchasedLineItem.getReview().setPurchasedLineItem(null);
                }
                
                purchasedLineItem.getProduct().setCategory(null);
                purchasedLineItem.getProduct().setServiceProvider(null);
                purchasedLineItem.getProduct().getFavouritedCustomers().clear();
                purchasedLineItem.getProduct().getTags().clear();
                purchasedLineItem.getProduct().getPromotions().clear();
                
                purchasedLineItem.getPurchased().getPurchasedLineItems().clear();
                purchasedLineItem.getPurchased().setCreditCard(null);
                purchasedLineItem.getPurchased().getCustomer().getBookings().clear();
                purchasedLineItem.getPurchased().getCustomer().getCreditCards().clear();
                purchasedLineItem.getPurchased().getCustomer().getFavouriteProducts().clear();
                purchasedLineItem.getPurchased().getCustomer().getFavouriteServices().clear();
                purchasedLineItem.getPurchased().getCustomer().getReviews().clear();
                purchasedLineItem.getPurchased().getCustomer().getPurchaseds().clear();
            }
                
            return Response.status(Response.Status.OK).entity(new RetrieveAllPurchasedLineItemsRsp(purchasedLineItems)).build();
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

    @Path("retrievePurchasedLineItem/{purchasedLineItemId}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrievePurchasedLineItem(@QueryParam("username") String username, 
                                        @QueryParam("password") String password,
                                        @PathParam("purchasedLineItemId") Long purchasedLineItemId)
    {
        try
        {
            ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(username, password);
            System.out.println("********** PurchasedLineItemResource.retrievePurchasedLineItem(): Service Provider " + serviceProvider.getName() + " login remotely via web service");

            PurchasedLineItem purchasedLineItem = purchasedLineItemSessionBeanLocal.retrievePurchasedLineItemByPurchasedLineItemId(purchasedLineItemId);
                      
            if(purchasedLineItem.getReview() != null){
                purchasedLineItem.getReview().setBooking(null);
                purchasedLineItem.getReview().setCustomer(null);
                purchasedLineItem.getReview().setPurchasedLineItem(null);
            }

            purchasedLineItem.getProduct().setCategory(null);
            purchasedLineItem.getProduct().setServiceProvider(null);
            purchasedLineItem.getProduct().getFavouritedCustomers().clear();
            purchasedLineItem.getProduct().getTags().clear();
            purchasedLineItem.getProduct().getPromotions().clear();
            
            purchasedLineItem.getPurchased().getPurchasedLineItems().clear();
            purchasedLineItem.getPurchased().setCreditCard(null);
            purchasedLineItem.getPurchased().getCustomer().getBookings().clear();
            purchasedLineItem.getPurchased().getCustomer().getCreditCards().clear();
            purchasedLineItem.getPurchased().getCustomer().getFavouriteProducts().clear();
            purchasedLineItem.getPurchased().getCustomer().getFavouriteServices().clear();
            purchasedLineItem.getPurchased().getCustomer().getReviews().clear();
            purchasedLineItem.getPurchased().getCustomer().getPurchaseds().clear();
            
            return Response.status(Response.Status.OK).entity(new RetrievePurchasedLineItemRsp(purchasedLineItem)).build();
        }
        catch(InvalidLoginCredentialException ex)
        {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        }
        catch(PurchasedLineItemNotFoundException ex)
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
    public Response updatePurchasedLineItem(UpdatePurchasedLineItemReq updatePurchasedLineItemReq)
    {
        if(updatePurchasedLineItemReq != null)
        {
            try
            {                
                ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(updatePurchasedLineItemReq.getUsername(), updatePurchasedLineItemReq.getPassword());
                System.out.println("********** PurchasedLineItemResource.updatePurchasedLineItem(): Service Provider " + serviceProvider.getName()+ " login remotely via web service");
                
                purchasedLineItemSessionBeanLocal.updatePurchasedLineItem(updatePurchasedLineItemReq.getPurchasedLineItem());
                
                return Response.status(Response.Status.OK).build();
            }
            catch(InvalidLoginCredentialException ex)
            {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
                return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
            }
            catch(PurchasedLineItemNotFoundException ex)
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
            ErrorRsp errorRsp = new ErrorRsp("Invalid update purchased line item request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
}
