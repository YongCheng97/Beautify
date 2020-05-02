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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ServiceProviderExistException;
import util.exception.UnknownPersistenceException;
import ws.datamodel.CreateServiceProviderReq;
import ws.datamodel.CreateServiceProviderRsp;
import ws.datamodel.ErrorRsp;
import ws.datamodel.ServiceProviderLoginRsp;
import ws.datamodel.UpdateServiceProviderReq;

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

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createServiceProvider(CreateServiceProviderReq createServiceProviderReq) {

        if (createServiceProviderReq != null) {
            try {
                Long serviceProviderId = serviceProviderSessionBean.createNewServiceProvider(createServiceProviderReq.getServiceProvider());
                CreateServiceProviderRsp createServiceProviderRsp = new CreateServiceProviderRsp(serviceProviderId);

                return Response.status(Response.Status.OK).entity(createServiceProviderRsp).build();
            } catch (ServiceProviderExistException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create new service provider request");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateServiceProvider(UpdateServiceProviderReq updateServiceProviderReq) 
    {
        if (updateServiceProviderReq != null) 
        {
            try 
            {
                ServiceProvider serviceProvider = serviceProviderSessionBean.serviceProviderLogin(updateServiceProviderReq.getUsername(), updateServiceProviderReq.getPassword());
                System.out.println("********** ServiceProviderResouce.updateServiceProvider(): Service Provider " + serviceProvider.getName() + " login remotely via web service");
                
                serviceProviderSessionBean.updateStaff(updateServiceProviderReq.getServiceProvider());
                
                return Response.status(Response.Status.OK).build();
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
        else 
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update purchased line item request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
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
