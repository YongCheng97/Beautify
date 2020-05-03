package ws.restful;

import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import ejb.session.stateless.StaffSessionBeanLocal;
import entity.ServiceProvider;
import entity.Staff;
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
import util.exception.StaffUsernameExistException;
import util.exception.UnknownPersistenceException;
import ws.datamodel.CreateServiceProviderReq;
import ws.datamodel.CreateServiceProviderRsp;
import ws.datamodel.CreateStaffReq;
import ws.datamodel.CreateStaffRsp;
import ws.datamodel.ErrorRsp;
import ws.datamodel.ServiceProviderLoginRsp;
import ws.datamodel.StaffLoginRsp;
import ws.datamodel.UpdateServiceProviderReq;

@Path("Staff")
public class StaffResource {

    @Context
    private UriInfo context;

    private StaffSessionBeanLocal staffSessionBean = lookupStaffSessionBeanLocal();

    public StaffResource() {
    }

    @Path("staffLogin")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {
            Staff staff = staffSessionBean.staffLogin(username, password);
            System.out.println("********** StaffResource.staffLogin(): Staff " + staff.getEmail() + " login remotely via web service");

            return Response.status(Status.OK).entity(new StaffLoginRsp(staff)).build();
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

    
    private StaffSessionBeanLocal lookupStaffSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (StaffSessionBeanLocal) c.lookup("java:global/Beautify/Beautify-ejb/StaffSessionBean!ejb.session.stateless.StaffSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
