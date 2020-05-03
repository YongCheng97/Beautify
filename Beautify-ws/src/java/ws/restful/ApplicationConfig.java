package ws.restful;

import java.util.Set;
import javax.ws.rs.core.Application;



@javax.ws.rs.ApplicationPath("Resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    
    
    private void addRestResourceClasses(Set<Class<?>> resources)
    {
        resources.add(ws.restful.BookingResource.class);
        resources.add(ws.restful.CategoryResource.class);
        resources.add(ws.restful.CreditCardResource.class);
        resources.add(ws.restful.ProductResource.class);
        resources.add(ws.restful.PromotionResource.class);
        resources.add(ws.restful.PurchasedLineItemResource.class);
        resources.add(ws.restful.ReviewResource.class);
        resources.add(ws.restful.SalesForUsResource.class);
        resources.add(ws.restful.SalesRecordResource.class);
        resources.add(ws.restful.ServiceProviderResource.class);
        resources.add(ws.restful.ServiceResource.class);
        resources.add(ws.restful.StaffResource.class);
        resources.add(ws.restful.TagResource.class);
    }
    
}
