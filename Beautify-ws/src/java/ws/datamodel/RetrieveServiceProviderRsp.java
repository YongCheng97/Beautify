package ws.datamodel;

import entity.ServiceProvider;



public class RetrieveServiceProviderRsp
{
    private ServiceProvider serviceProvider;


    public RetrieveServiceProviderRsp()
    {
    }

    public RetrieveServiceProviderRsp(ServiceProvider serviceProvider)
    {
        this.serviceProvider = serviceProvider;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    
}