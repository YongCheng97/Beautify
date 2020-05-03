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
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.CreateNewProductException;
import util.exception.DeleteProductException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ProductNotFoundException;
import util.exception.UpdateProductException;
import ws.datamodel.CreateProductReq;
import ws.datamodel.CreateProductRsp;
import ws.datamodel.ErrorRsp;
import ws.datamodel.RetrieveAllProductsRsp;
import ws.datamodel.UpdateProductReq;

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
                product.getFavouritedCustomers().clear();
                product.getServiceProvider().getCreditCards().clear();
                product.getServiceProvider().getProducts().clear();
                product.getServiceProvider().getServices().clear();
                product.getServiceProvider().getPromotions().clear(); 
            }

            System.out.println("Products: " + products);

            return Response.status(Status.OK).entity(new RetrieveAllProductsRsp(products)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(CreateProductReq createProductReq) {
        if (createProductReq != null) {
            try {
                ServiceProvider serviceProvider = serviceProviderSessionBean.serviceProviderLogin(createProductReq.getUsername(), createProductReq.getPassword());
                System.out.println("********** ProductResource.createProduct(): Staff " + serviceProvider.getUsername() + " login remotely via web service");

                Product product = productSessionBean.createNewProduct(createProductReq.getProduct(), createProductReq.getCategoryId(), serviceProvider.getServiceProviderId(), createProductReq.getTagIds());
                CreateProductRsp createProductRsp = new CreateProductRsp(product.getProductId());

                return Response.status(Response.Status.OK).entity(createProductRsp).build();
            } catch (InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
            } catch (CreateNewProductException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create new product request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(UpdateProductReq updateProductReq) {
        if (updateProductReq != null) {
            try {
                ServiceProvider serviceProvider = serviceProviderSessionBean.serviceProviderLogin(updateProductReq.getUsername(), updateProductReq.getPassword());
                System.out.println("********** ProductResource.createProduct(): ServiceProvider " + serviceProvider.getUsername() + " login remotely via web service");

                productSessionBean.updateProduct(updateProductReq.getProduct(), updateProductReq.getCategoryId(), updateProductReq.getTagIds());

                return Response.status(Response.Status.OK).build();
            } catch (InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
            } catch (UpdateProductException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update product request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    @Path("{productId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@QueryParam("username") String username,
            @QueryParam("password") String password,
            @PathParam("productId") Long productId) {
        try {
            ServiceProvider serviceProvider = serviceProviderSessionBean.serviceProviderLogin(username, password);
            System.out.println("********** ProductResource.createProduct(): ServiceProvider " + serviceProvider.getUsername() + " login remotely via web service");

            productSessionBean.deleteProduct(productId);

            return Response.status(Status.OK).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (ProductNotFoundException | DeleteProductException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
}
