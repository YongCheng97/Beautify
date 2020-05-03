package ws.restful;

import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import ejb.session.stateless.StaffSessionBeanLocal;
import ejb.session.stateless.TagsSessionBeanLocal;
import entity.ServiceProvider;
import entity.Staff;
import entity.Tag;
import java.util.List;
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
import util.exception.CreateNewTagException;
import util.exception.InvalidLoginCredentialException;
import ws.datamodel.CreateTagReq;
import ws.datamodel.CreateTagRsp;
import ws.datamodel.ErrorRsp;
import ws.datamodel.RetrieveAllTagsRsp;

@Path("Tag")
public class TagResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;

    private final ServiceProviderSessionBeanLocal serviceProviderSessionBean;
    private final StaffSessionBeanLocal staffSessionBean;
    private final TagsSessionBeanLocal tagsSessionBean;

    public TagResource() {
        sessionBeanLookup = new SessionBeanLookup();

        serviceProviderSessionBean = sessionBeanLookup.lookupServiceProviderSessionBeanLocal();
        staffSessionBean = sessionBeanLookup.lookupStaffSessionBeanLocal();
        tagsSessionBean = sessionBeanLookup.lookupTagsSessionBeanLocal();
    }

    @Path("retrieveAllTags")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTags(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {

            ServiceProvider serviceProvider = serviceProviderSessionBean.serviceProviderLogin(username, password);
            System.out.println("********** TagResource.retrieveAllTags(): ServiceProvider " + serviceProvider.getUsername() + " login remotely via web service");

            List<Tag> tagEntities = tagsSessionBean.retrieveAllTags();

            for (Tag tagEntity : tagEntities) {
                tagEntity.getProducts().clear();
                tagEntity.getServices().clear();
            }
            return Response.status(Response.Status.OK).entity(new RetrieveAllTagsRsp(tagEntities)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveAllTagsForStaff")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTagsForStaff(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {

            Staff staff = staffSessionBean.staffLogin(username, password);
            System.out.println("********** TagResource.retrieveAllTags(): Staff " + staff.getUsername() + " login remotely via web service");

            List<Tag> tagEntities = tagsSessionBean.retrieveAllTags();

            for (Tag tagEntity : tagEntities) {
                tagEntity.getProducts().clear();
                tagEntity.getServices().clear();
            }
            return Response.status(Response.Status.OK).entity(new RetrieveAllTagsRsp(tagEntities)).build();
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
    public Response createTag(CreateTagReq createTagReq) {
        if (createTagReq != null) {
            try {
                Staff staff = staffSessionBean.staffLogin(createTagReq.getUsername(), createTagReq.getPassword());
                System.out.println("********** TagResource.createTag(): Staff " + staff.getUsername() + " login remotely via web service");

                Tag newTag = tagsSessionBean.createNewTagEntity(createTagReq.getNewTag());
                CreateTagRsp createTagRsp = new CreateTagRsp(newTag.getTagId());

                return Response.status(Response.Status.OK).entity(createTagRsp).build();
            } catch (InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();

            } catch (CreateNewTagException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
            } catch (Exception ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create new tag request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
}
