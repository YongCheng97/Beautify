package ws.datamodel;

import entity.Service;
import java.util.List;

public class RetrieveAllServicesRsp {
    
    private List<Service> services;
    
    public RetrieveAllServicesRsp() {
    }

    public RetrieveAllServicesRsp(List<Service> services) {
        this.services = services;
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
