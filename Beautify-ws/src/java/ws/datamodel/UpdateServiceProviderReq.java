/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.ServiceProvider;

/**
 *
 * @author jilon
 */
public class UpdateServiceProviderReq {
    
    private String username; 
    private String password; 
    private ServiceProvider serviceProvider; 

    public UpdateServiceProviderReq() {
    }

    public UpdateServiceProviderReq(String username, String password, ServiceProvider serviceProvider) {
        this.username = username;
        this.password = password;
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

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
    
    
    
}
