package jsf.managedbean;

import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import util.exception.ServiceNotFoundException;


@Named(value = "viewServiceDetailsManagedBean")
@ViewScoped
public class viewServiceDetailsManagedBean implements Serializable {

    @EJB(name = "ServiceSessionBeanLocal")
    private ServiceSessionBeanLocal serviceSessionBeanLocal;

    private Long serviceIdToView;
    private Service serviceToView;
    private List<String> serviceImages;
    
    public viewServiceDetailsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        
        serviceIdToView = (Long)session.getAttribute("serviceIdToView");
        try
        {            
            serviceToView = serviceSessionBeanLocal.retrieveServiceByServiceId(serviceIdToView);
            serviceImages = new ArrayList<String>();
            
            for (int i=1; i<=3; i++) {
                serviceImages.add(serviceToView.getServiceName()+ i + ".jpg");
            }
        }
        catch(ServiceNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the service details: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void foo()
    {        
    }

    
    public Service getServiceToView() {
        return serviceToView;
    }

   
    public void setServiceToView(Service serviceToView) {
        this.serviceToView = serviceToView;
    }

 
    public List<String> getServiceImages() {
        return serviceImages;
    }

    
    public void setServiceImages(List<String> serviceImages) {
        this.serviceImages = serviceImages;
    }
    
    
}
