
package ws.datamodel;

import entity.ServiceProvider;

public class ServiceProviderLoginRsp {
    private ServiceProvider serviceProvider;

    public ServiceProviderLoginRsp() {
    }

    public ServiceProviderLoginRsp(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
    
}
