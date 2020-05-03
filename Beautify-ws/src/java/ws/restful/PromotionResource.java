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
import entity.Promotion;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.InvalidLoginCredentialException;
import ws.datamodel.CreateProductPromotionReq;
import ws.datamodel.CreateProductPromotionRsp;
import ws.datamodel.CreatePromotionReq;
import ws.datamodel.CreateServicePromotionReq;
import ws.datamodel.CreateServicePromotionRsp;
import ws.datamodel.ErrorRsp;
import ws.datamodel.RetrieveAllProductPromotionsRsp;
import ws.datamodel.RetrieveAllServicePromotionsRsp;

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

    @Path("retrieveAllProductPromotions")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProductPromotions(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {
            ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(username, password);
            System.out.println("********** PromotionResource.retrieveAllPromotions(): Service Provider " + serviceProvider.getName() + " login remotely via web service");

            List<Promotion> promotions = promotionSessionBeanLocal.retrieveAllProductPromotionsByServiceProviderId(serviceProvider.getServiceProviderId());

            for (Promotion promotion : promotions) {
                if (promotion.getProduct() != null) {
                    promotion.getProduct().getTags().clear();
                    promotion.getProduct().getFavouritedCustomers().clear();
                    promotion.getProduct().getPromotions().clear();
                    promotion.getProduct().setServiceProvider(null);
                    promotion.getProduct().setCategory(null);

                }

                promotion.setServiceProvider(null);
            }

            return Response.status(Response.Status.OK).entity(new RetrieveAllProductPromotionsRsp(promotions)).build();

        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveAllServicePromotions")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllServicePromotions(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {
            ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(username, password);
            System.out.println("********** PromotionResource.retrieveAllPromotions(): Service Provider " + serviceProvider.getName() + " login remotely via web service");

            List<Promotion> promotions = promotionSessionBeanLocal.retrieveAllServicePromotionsByServiceProviderId(serviceProvider.getServiceProviderId());

            for (Promotion promotion : promotions) {
                if (promotion.getService() != null) {
                    promotion.getService().getTags().clear();
                    promotion.getService().getFavouritedCustomers().clear();
                    promotion.getService().getPromotions().clear();
                    promotion.getService().setServiceProvider(null);
                    promotion.getService().setCategory(null);
                }

                promotion.setServiceProvider(null);
            }

            return Response.status(Response.Status.OK).entity(new RetrieveAllServicePromotionsRsp(promotions)).build();

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
                
                Promotion promotion = new Promotion(); 
                
                if (createPromotionReq.getServiceId() != null) {
                    promotion = promotionSessionBeanLocal.createNewProductPromotion(createPromotionReq.getPromotion(), serviceProvider.getServiceProviderId(), createPromotionReq.getServiceId());
                } else {
                    promotion = promotionSessionBeanLocal.createNewProductPromotion(createPromotionReq.getPromotion(), serviceProvider.getServiceProviderId(), createPromotionReq.getProductId());
                }

                CreateProductPromotionRsp createProductPromotionRsp = new CreateProductPromotionRsp(promotion.getPromotionId());

                return Response.status(Response.Status.OK).entity(createProductPromotionRsp).build();

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

    /*
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createServicePromotion(CreateServicePromotionReq createServicePromotionReq) {
        if (createServicePromotionReq != null) {
            try {
                ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(createServicePromotionReq.getUsername(), createServicePromotionReq.getPassword());
                System.out.println("********** PromotionResource.createPromotion(): Service Provider " + serviceProvider.getName() + " login remotely via web service");

                Promotion promotion = promotionSessionBeanLocal.createNewServicePromotion(createServicePromotionReq.getPromotion(), serviceProvider.getServiceProviderId(), createServicePromotionReq.getServiceId());

                CreateServicePromotionRsp createServicePromotionRsp = new CreateServicePromotionRsp(promotion.getPromotionId());

                return Response.status(Response.Status.OK).entity(createServicePromotionRsp).build();

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
    } */
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
