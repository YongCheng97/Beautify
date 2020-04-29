package ws.datamodel;

import entity.ServiceProvider;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

public class CreateServiceProviderReq {

    private ServiceProvider serviceProvider;

    public CreateServiceProviderReq() {
    }

    public CreateServiceProviderReq(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println(serviceProvider.getOpeningHours());
    }

    /**
     * @return the serviceProvider
     */
    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    /**
     * @param serviceProvider the serviceProvider to set
     */
    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

}
