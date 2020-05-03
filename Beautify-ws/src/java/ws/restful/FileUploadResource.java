package ws.restful;

import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import entity.ServiceProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.InvalidLoginCredentialException;
import ws.datamodel.ErrorRsp;
import ws.datamodel.UploadFilesReq;

@Path("FileUpload")
public class FileUploadResource {

    @Context
    private UriInfo context;

    private final SessionBeanLookup sessionBeanLookup;

    private final ServiceProviderSessionBeanLocal serviceProviderSessionBean;

    public FileUploadResource() {
        sessionBeanLookup = new SessionBeanLookup();

        serviceProviderSessionBean = sessionBeanLookup.lookupServiceProviderSessionBeanLocal();
    }

     
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fileUpload(UploadFilesReq uploadFilesReq) {
        if (uploadFilesReq != null) {
            try {
                ServiceProvider serviceProvider = serviceProviderSessionBean.serviceProviderLogin(uploadFilesReq.getUsername(), uploadFilesReq.getPassword());
                System.out.println("********** ProductResource.retrieveAllProducts(): Staff " + serviceProvider.getUsername() + " login remotely via web service");

                File[] filesToUpload = uploadFilesReq.getUploadedFiles();
                for (int i = 0; i < filesToUpload.length; i++) {
                    String uploadFileLocation = "C:/glassfish-5.1.0-uploadedfiles/uploadedFiles/" + uploadFilesReq.getProduct().getName() + (i + 1) + ".jpg";

                    File file = new File(uploadFileLocation);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);

                    int a;
                    int BUFFER_SIZE = 8192;
                    byte[] buffer = new byte[BUFFER_SIZE];

                    InputStream inputStream = new FileInputStream(filesToUpload[i]);

                    while (true) {
                        a = inputStream.read(buffer);

                        if (a < 0) {
                            break;
                        }

                        fileOutputStream.write(buffer, 0, a);
                        fileOutputStream.flush();
                    }

                    fileOutputStream.close();
                    inputStream.close();
                }

                return Response.status(Response.Status.OK).build();

            } catch (InvalidLoginCredentialException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
            } catch (IOException ex) {
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());

                return Response.status(Response.Status.UNAUTHORIZED).entity(errorRsp).build();
            }
        } else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid upload files request");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
}
