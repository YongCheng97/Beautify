package ws.restful;

import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import entity.ServiceProvider;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.InvalidLoginCredentialException;
import ws.datamodel.ErrorRsp;
import ws.datamodel.ServiceProviderLoginRsp;

@Path("ServiceProvider")
public class ServiceProviderResource {

    @Context
    private UriInfo context;

    private ServiceProviderSessionBeanLocal serviceProviderSessionBean = lookupServiceProviderSessionBeanLocal();

    public ServiceProviderResource() {
    }

    @Path("serviceProviderLogin")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {
            ServiceProvider serviceProvider = serviceProviderSessionBean.serviceProviderLogin(username, password);
            System.out.println("********** StaffResource.staffLogin(): Staff " + serviceProvider.getEmail() + " login remotely via web service");

            serviceProvider.setPassword(null);
            serviceProvider.setSalt(null);
            serviceProvider.getCreditCards().clear();
            serviceProvider.getProducts().clear();
            serviceProvider.getServices().clear();

            return Response.status(Status.OK).entity(new ServiceProviderLoginRsp(serviceProvider)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            System.out.println("test2");
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            System.out.println("test3");

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of
     * ServiceProviderResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
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
