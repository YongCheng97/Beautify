package jsf.managedbean;

import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Service;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

@Named(value = "viewAllServicesManagedBean")
@ViewScoped
public class ViewAllServicesManagedBean implements Serializable{

    @EJB
    private ServiceSessionBeanLocal serviceSessionBean;

    private List<Service> services;
    
    public ViewAllServicesManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        services = serviceSessionBean.retrieveAllServices();
    }

    /**
     * @return the services
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     * @param services the services to set
     */
    public void setServices(List<Service> services) {
        this.services = services;
    }
    
    public void viewService(ActionEvent event) throws IOException {
        Long serviceIdToView = (Long) event.getComponent().getAttributes().get("serviceId");
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("serviceIdToView", serviceIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/viewServiceDetails.xhtml");
    }
}
