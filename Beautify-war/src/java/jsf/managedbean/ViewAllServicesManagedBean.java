package jsf.managedbean;

import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Service;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

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
    
}
