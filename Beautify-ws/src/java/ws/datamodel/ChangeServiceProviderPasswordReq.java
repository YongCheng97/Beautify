
package ws.datamodel;

import entity.ServiceProvider;

public class ChangeServiceProviderPasswordReq {
    
    private String username; 
    private String password; 
    private String newPassword; 
    private ServiceProvider serviceProvider;

    public ChangeServiceProviderPasswordReq() {
    }

    public ChangeServiceProviderPasswordReq(String username, String password, String newPassword, ServiceProvider serviceProvider) {
        this.username = username;
        this.password = password;
        this.newPassword = newPassword;
        this.serviceProvider = serviceProvider;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

}
