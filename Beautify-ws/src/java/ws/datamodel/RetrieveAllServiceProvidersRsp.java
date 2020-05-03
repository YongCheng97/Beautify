package ws.datamodel;

import entity.ServiceProvider;
import java.util.List;


public class RetrieveAllServiceProvidersRsp {
    
    private List<ServiceProvider> serviceProviders;

    
    
    public RetrieveAllServiceProvidersRsp()
    {
    }
    
    
    
    public RetrieveAllServiceProvidersRsp(List<ServiceProvider> serviceProviders)
    {
        this.serviceProviders = serviceProviders;
    }

    public List<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    public void setServiceProviders(List<ServiceProvider> serviceProviders) {
        this.serviceProviders = serviceProviders;
    }

}
