package ws.restful;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import entity.Category;
import entity.ServiceProvider;
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
import ws.datamodel.ErrorRsp;
import ws.datamodel.RetrieveAllCategoriesRsp;

@Path("Category")
public class CategoryResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;

    private final CategorySessionBeanLocal categorySessionBean;
    private final ServiceProviderSessionBeanLocal serviceProviderSessionBean;

    public CategoryResource() {
        sessionBeanLookup = new SessionBeanLookup();

        categorySessionBean = sessionBeanLookup.lookupCategorySessionBeanLocal();
        serviceProviderSessionBean = sessionBeanLookup.lookupServiceProviderSessionBeanLocal();
    }

    @Path("retrieveAllProductCategories")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProductCategories(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {
            ServiceProvider serviceProvider = serviceProviderSessionBean.serviceProviderLogin(username, password);
            System.out.println("********** CategoryResource.retrieveAllCategories(): Staff " + serviceProvider.getUsername() + " login remotely via web service");

            List<Category> categoryEntities = categorySessionBean.retrieveAllProductCategories();

            for (Category categoryEntity : categoryEntities) {
                if (categoryEntity.getParentCategoryEntity() != null) {
                    categoryEntity.getParentCategoryEntity().getSubCategoryEntities().clear();
                }

                categoryEntity.getSubCategoryEntities().clear();
                categoryEntity.getParentCategoryEntity().getProducts().clear();
                categoryEntity.getParentCategoryEntity().getServices().clear();
                categoryEntity.getProducts().clear();
                categoryEntity.getServices().clear();
            }
            System.out.println("Product Categories" + categoryEntities);
            return Response.status(Response.Status.OK).entity(new RetrieveAllCategoriesRsp(categoryEntities)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveAllServiceCategories")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllServiceCategories(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {
            ServiceProvider serviceProvider = serviceProviderSessionBean.serviceProviderLogin(username, password);
            System.out.println("********** CategoryResource.retrieveAllCategories(): Staff " + serviceProvider.getUsername() + " login remotely via web service");

            List<Category> categoryEntities = categorySessionBean.retrieveAllServiceCategories();

            for (Category categoryEntity : categoryEntities) {
                if (categoryEntity.getParentCategoryEntity() != null) {
                    categoryEntity.getParentCategoryEntity().getSubCategoryEntities().clear();
                }

                categoryEntity.getSubCategoryEntities().clear();
                categoryEntity.getParentCategoryEntity().getProducts().clear();
                categoryEntity.getParentCategoryEntity().getServices().clear();
                categoryEntity.getProducts().clear();
                categoryEntity.getServices().clear();
            }
            System.out.println("Service Categories" + categoryEntities);
            return Response.status(Response.Status.OK).entity(new RetrieveAllCategoriesRsp(categoryEntities)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
}
