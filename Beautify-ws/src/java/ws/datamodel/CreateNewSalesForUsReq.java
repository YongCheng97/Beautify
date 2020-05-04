
package ws.datamodel;

import entity.CreditCard;
import entity.Promotion;
import entity.ServiceProvider;

public class CreateNewSalesForUsReq {

    private String username;
    private String password;
    private ServiceProvider serviceProvider;
    private CreditCard creditCard;

    public CreateNewSalesForUsReq() {
    }

    public CreateNewSalesForUsReq(String username, String password, ServiceProvider serviceProvider, CreditCard creditCard) {
        this.username = username;
        this.password = password;
        this.serviceProvider = serviceProvider;
        this.creditCard = creditCard;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
