package ws.restful;

import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Service;
import entity.ServiceProvider;
import entity.Tag;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.DeleteServiceException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ServiceNotFoundException;
import util.exception.UpdateProductException;
import util.exception.UpdateServiceException;
import ws.datamodel.CreateServiceReq;
import ws.datamodel.CreateServiceRsp;
import ws.datamodel.ErrorRsp;
import ws.datamodel.RetrieveAllServicesRsp;
import ws.datamodel.UpdateProductReq;
import ws.datamodel.UpdateServiceReq;

@Path("Service")
public class ServiceResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;

    private final ServiceProviderSessionBeanLocal serviceProviderSessionBean;
    private final ServiceSessionBeanLocal serviceSessionBean;

    public ServiceResource() {
        sessionBeanLookup = new SessionBeanLookup();

        serviceProviderSessionBean = sessionBeanLookup.lookupServiceProviderSessionBeanLocal();
        serviceSessionBean = sessionBeanLookup.lookupServiceSessionBeanLocal();
    }

    @Path("retrieveAllServices")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllServices(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {
            ServiceProvider serviceProvider = serviceProviderSessionBean.serviceProviderLogin(username, password);
            System.out.println("********** ProductResource.retrieveAllProducts(): Staff " + serviceProvider.getUsername() + " login remotely via web service");

            List<Service> services = serviceSessionBean.retrieveAllServicesByServiceProvider(serviceProvider.getServiceProviderId());

            for (Service service : services) {
                if (service.getCategory().getParentCategoryEntity() != null) {
                    service.getCategory().getParentCategoryEntity().getSubCategoryEntities().clear();
                }

                service.getCategory().getServices().clear();
                service.getCategory().getProducts().clear();
                service.getPromotions().clear();
                service.getBookings().clear();
                service.getFavouritedCustomers().clear();
                service.getServiceProvider().getCreditCards().clear();
                service.getServiceProvider().getProducts().clear();
                service.getServiceProvider().getServices().clear();
                
                service.getServiceProvider().getPromotions().clear(); 

                for (Tag tag : service.getTags()) {
                    tag.getServices().clear();
                    tag.getProducts().clear();
                }
            }
            return Response.status(Response.Status.OK).entity(new RetrieveAllServicesRsp(services)).build();
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
    public Response createService(CreateServiceReq createServiceReq) {
        if (createServiceReq != null) {
            try {
                ServiceProvider serviceProvider = serviceProviderSessionBean.serviceProviderLogin(createServiceReq.getUsername(), createServiceReq.getPassword());
                System.out.println("********** ProductResource.createProduct(): Staff " + serviceProvider.getUsername() + " login remotely via web service");

                Service service = serviceSessionBean.createNewService(createServiceReq.getService(), serviceProvider.getServiceProviderId(), createServiceReq.getCategoryId(), createServiceReq.getTagIds());
                CreateServiceRsp createServiceRsp = new CreateServiceRsp(service.getServiceId());

                return Response.status(Response.Status.OK).entity(createServiceRsp).build();
            } catch (InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create new product request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateService(UpdateServiceReq updateServiceReq) {
        if (updateServiceReq != null) {
            try {
                ServiceProvider serviceProvider = serviceProviderSessionBean.serviceProviderLogin(updateServiceReq.getUsername(), updateServiceReq.getPassword());
                System.out.println("********** ProductResource.createProduct(): ServiceProvider " + serviceProvider.getUsername() + " login remotely via web service");

                serviceSessionBean.updateService(updateServiceReq.getService(), updateServiceReq.getCategoryId(), updateServiceReq.getTagIds());

                return Response.status(Response.Status.OK).build();
            } catch (InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
            } catch (UpdateServiceException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update service request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @Path("{serviceId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteService(@QueryParam("username") String username,
            @QueryParam("password") String password,
            @PathParam("serviceId") Long serviceId) {
        try {
            ServiceProvider serviceProvider = serviceProviderSessionBean.serviceProviderLogin(username, password);
            System.out.println("********** ProductResource.createProduct(): ServiceProvider " + serviceProvider.getUsername() + " login remotely via web service");

            serviceSessionBean.deleteService(serviceId);

            return Response.status(Response.Status.OK).build();
        } catch (ServiceNotFoundException | DeleteServiceException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        }

    }
}
