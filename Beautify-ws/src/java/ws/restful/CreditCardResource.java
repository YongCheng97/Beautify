/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.CreditCardSessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import entity.Booking;
import entity.CreditCard;
import entity.Purchased;
import entity.ServiceProvider;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.CreditCardNotFoundException;
import util.exception.InvalidLoginCredentialException;
import ws.datamodel.CreateCreditCardReq;
import ws.datamodel.CreateCreditCardRsp;
import ws.datamodel.ErrorRsp;
import ws.datamodel.RetrieveAllCreditCardsRsp;

/**
 * REST Web Service
 *
 * @author jilon
 */
@Path("CreditCard")
public class CreditCardResource {

    @Context
    private UriInfo context;

    private final CreditCardSessionBeanLocal creditCardSessionBeanLocal = lookupCreditCardSessionBeanLocal();

    private final ServiceProviderSessionBeanLocal serviceProviderSessionBeanLocal = lookupServiceProviderSessionBeanLocal();

    /**
     * Creates a new instance of CreditCardResource
     */
    public CreditCardResource() {
    }

    @Path("retrieveAllCreditCards")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllCreditCards(@QueryParam("username") String username,
            @QueryParam("password") String password) {

        try {
            ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(username, password);
            System.out.println("********** CreditCardResource.retrieveAllCreditCards(): Service Provider " + serviceProvider.getName() + " login remotely via web service");

            List<CreditCard> creditCards = creditCardSessionBeanLocal.retrieveAllCreditCardsByServiceProviderId(serviceProvider.getServiceProviderId());

            for (CreditCard cc : creditCards) {
                if (cc.getCustomer() != null) {
                    cc.getCustomer().getBookings().clear();
                    cc.getCustomer().getCreditCards().clear();
                    cc.getCustomer().getFavouriteProducts().clear();
                    cc.getCustomer().getFavouriteServices().clear();
                    cc.getCustomer().getReviews().clear();
                    cc.getCustomer().getPurchaseds().clear();
                }

                if (cc.getServiceProvider() != null) {
                    cc.getServiceProvider().getCreditCards().clear();
                    cc.getServiceProvider().getProducts().clear();
                    cc.getServiceProvider().getServices().clear();
                }

                for (Booking booking : cc.getBookings()) {
                    booking.setCreditCard(null);
                }

                for (Purchased purchased : cc.getPurchaseds()) {
                    purchased.setCreditCard(null);
                }
            }

            return Response.status(Response.Status.OK).entity(new RetrieveAllCreditCardsRsp(creditCards)).build();

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
    public Response createCreditCard(CreateCreditCardReq createCreditCardReq) {
        if (createCreditCardReq != null) {
            try {
                ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(createCreditCardReq.getUsername(), createCreditCardReq.getPassword());
                System.out.println("********** CreditCardResource.createCreditCard(): Service Provider " + serviceProvider.getName() + " login remotely via web service");

                CreditCard creditCard = creditCardSessionBeanLocal.createNewCreditCardEntityForServiceProvider(createCreditCardReq.getCreditCard(), serviceProvider.getServiceProviderId());
                CreateCreditCardRsp createCreditCardRsp = new CreateCreditCardRsp(creditCard.getCreditCardId());

                return Response.status(Response.Status.OK).entity(createCreditCardRsp).build();

            } catch (InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create new credit card request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @Path("{creditCardId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCreditCard(@QueryParam("username") String username,
            @QueryParam("password") String password,
            @PathParam("creditCardId") Long creditCardId) {
        try {
            ServiceProvider serviceProvider = serviceProviderSessionBeanLocal.serviceProviderLogin(username, password);
            System.out.println("********** CreditCardResource.deleteCreditCard(): Service Provider " + serviceProvider.getName() + " login remotely via web service");

            creditCardSessionBeanLocal.deleteCreditCard(creditCardId);

            return Response.status(Status.OK).build();
            
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (CreditCardNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    } 

    private CreditCardSessionBeanLocal lookupCreditCardSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CreditCardSessionBeanLocal) c.lookup("java:global/Beautify/Beautify-ejb/CreditCardSessionBean!ejb.session.stateless.CreditCardSessionBeanLocal");
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
