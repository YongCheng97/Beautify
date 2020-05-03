/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.PromotionSessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Booking;
import entity.Customer;
import entity.Product;
import entity.Promotion;
import entity.Service;
import entity.ServiceProvider;
import entity.Tag;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.InvalidLoginCredentialException;
import ws.datamodel.CreatePromotionReq;
import ws.datamodel.CreatePromotionRsp;
import ws.datamodel.ErrorRsp;
import ws.datamodel.RetrieveAllPromotionsRsp;

/**
 * REST Web Service
 *
 * @author jilon
 */
@Path("Promotion")
public class PromotionResource {
    
    private final ServiceSessionBeanLocal serviceSessionBeanLocal = lookupServiceSessionBeanLocal();
    
    private final ProductSessionBeanLocal productSessionBeanLocal = lookupProductSessionBeanLocal();
    
    private final ServiceProviderSessionBeanLocal serviceProviderSessionBeanLocal = lookupServiceProviderSessionBeanLocal();
    
    @Context
    private UriInfo context;
    
    private final PromotionSessionBeanLocal promotionSessionBeanLocal = lookupPromotionSessionBeanLocal();

    /**
     * Creates a new instance of PromotionResource
     */
    public PromotionResource() {
    }
    
    @Path("retrieveAllPromotions")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllPromotions(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {
            ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(username, password);
            System.out.println("********** PromotionResource.retrieveAllPromotions(): Service Provider " + serviceProvider.getName() + " login remotely via web service");
            
            List<Promotion> promotions = promotionSessionBeanLocal.retrievePromotionsByServiceProviderId(serviceProvider.getServiceProviderId());
            
            for (Promotion promotion : promotions) {
                if (promotion.getService() != null) {
                    for (Promotion p : promotion.getService().getPromotions()) {
                        p.setService(null);
                        p.setProduct(null);
                        p.setServiceProvider(null);
                    }
                    for (Tag t : promotion.getService().getTags()) {
                        t.getServices().clear();
                        t.getProducts().clear();
                    }
                    for (Booking b : promotion.getService().getBookings()) {
                        b.setReview(null);
                        b.setCustomer(null);
                        b.setService(null);
                        b.setCreditCard(null);
                    }
                    for (Customer c : promotion.getService().getFavouritedCustomers()) {
                        c.getCreditCards().clear();
                        c.getBookings().clear();
                        c.getFavouriteServices().clear();
                        c.getFavouriteProducts().clear();
                        c.getReviews().clear();
                        c.getPurchaseds().clear();
                    }
                }
                
                if (promotion.getProduct() != null) {
                    /* for (Tag t : promotion.getProduct().getTags()) {
                        t.getServices().clear();
                        t.getProducts().clear();
                    }

                    for (Customer c : promotion.getProduct().getFavouritedCustomers()) {
                        c.getCreditCards().clear();
                        c.getBookings().clear();
                        c.getFavouriteServices().clear();
                        c.getFavouriteProducts().clear();
                        c.getReviews().clear();
                        c.getPurchaseds().clear();
                    }

                    for (Promotion p : promotion.getProduct().getPromotions()) {
                        p.setService(null);
                        p.setProduct(null);
                        p.setServiceProvider(null);
                    }
                    
                    promotion.getProduct().getServiceProvider().getCreditCards().clear();
                    promotion.getProduct().getServiceProvider().getProducts().clear(); 
                    promotion.getProduct().getServiceProvider().getServices().clear();
                    promotion.getProduct().getServiceProvider().getPromotions().clear();
                    
                    promotion.getProduct().getCategory().getSubCategoryEntities().clear();
                    promotion.getProduct().getCategory().setParentCategoryEntity(null); 
                    promotion.getProduct().getCategory().getProducts().clear();
                    promotion.getProduct().getCategory().getServices().clear(); 
                     */                    
                    
                    promotion.getProduct().getTags().clear();                    
                    promotion.getProduct().getFavouritedCustomers().clear();                    
                    promotion.getProduct().getPromotions().clear();
                    promotion.getProduct().setServiceProvider(null);
                    promotion.getProduct().setCategory(null);
                    
                }
                
                promotion.setServiceProvider(null);
            }
            
            return Response.status(Response.Status.OK).entity(new RetrieveAllPromotionsRsp(promotions)).build();
            
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPromotion(CreatePromotionReq createPromotionReq) {
        if (createPromotionReq != null) {
            try {
                ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(createPromotionReq.getUsername(), createPromotionReq.getPassword());
                System.out.println("********** PromotionResource.createPromotion(): Service Provider " + serviceProvider.getName() + " login remotely via web service");
                
                Promotion promotion = promotionSessionBeanLocal.createNewPromotion(createPromotionReq.getPromotion(), serviceProvider.getServiceProviderId());

                /* if (createPromotionReq.getProductId() != null) {
                    Product product = productSessionBeanLocal.retrieveProductByProdId(createPromotionReq.getProductId());
                    product.addPromotion(promotion);
                }

                if (createPromotionReq.getServiceId() != null) {
                    Service service = serviceSessionBeanLocal.retrieveServiceByServiceId(createPromotionReq.getServiceId());
                    service.addPromotion(promotion);
                } */
                CreatePromotionRsp createPromotionRsp = new CreatePromotionRsp(promotion.getPromotionId());
                
                return Response.status(Response.Status.OK).entity(createPromotionRsp).build();
                
            } catch (InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                
                return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create new promotion request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    private PromotionSessionBeanLocal lookupPromotionSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PromotionSessionBeanLocal) c.lookup("java:global/Beautify/Beautify-ejb/PromotionSessionBean!ejb.session.stateless.PromotionSessionBeanLocal");
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
    
    private ProductSessionBeanLocal lookupProductSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ProductSessionBeanLocal) c.lookup("java:global/Beautify/Beautify-ejb/ProductSessionBean!ejb.session.stateless.ProductSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private ServiceSessionBeanLocal lookupServiceSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ServiceSessionBeanLocal) c.lookup("java:global/Beautify/Beautify-ejb/ServiceSessionBean!ejb.session.stateless.ServiceSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
