/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import entity.Product;
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
import javax.ws.rs.core.Response.Status;
import util.exception.InvalidLoginCredentialException;
import ws.datamodel.ErrorRsp;
import ws.datamodel.RetrieveAllProductsRsp;

@Path("Product")
public class ProductResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;

    private final ProductSessionBeanLocal productSessionBean;
    private final ServiceProviderSessionBeanLocal serviceProviderSessionBean;

    public ProductResource() {
        sessionBeanLookup = new SessionBeanLookup();

        serviceProviderSessionBean = sessionBeanLookup.lookupServiceProviderSessionBeanLocal();
        productSessionBean = sessionBeanLookup.lookupProductSessionBeanLocal();
    }

    @Path("retrieveAllProducts")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllProducts(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {
            ServiceProvider serviceProvider = serviceProviderSessionBean.serviceProviderLogin(username, password);
            System.out.println("********** ProductResource.retrieveAllProducts(): Staff " + serviceProvider.getUsername() + " login remotely via web service");

            List<Product> products = productSessionBean.retrieveAllProductsByServiceProvider(serviceProvider.getServiceProviderId());

            for (Product product : products) {
                if (product.getCategory().getParentCategoryEntity() != null) {
                    product.getCategory().getParentCategoryEntity().getSubCategoryEntities().clear();
                }

                product.getCategory().getProducts().clear();
                product.getCategory().getServices().clear();

                for (Tag tag : product.getTags()) {
                    tag.getProducts().clear();
                    tag.getServices().clear();
                }

                product.getPromotions().clear();
            }

            return Response.status(Status.OK).entity(new RetrieveAllProductsRsp(products)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of ProductResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

}
