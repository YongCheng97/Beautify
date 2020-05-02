/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.CreditCard;
import java.util.List;

/**
 *
 * @author jilon
 */
public class CreateCreditCardReq {
    
    private String username; 
    private String password; 
    private CreditCard creditCard; 
    private Long serviceProviderId; 

    public CreateCreditCardReq() {
    }

    public CreateCreditCardReq(String username, String password, CreditCard creditCard, Long serviceProviderId) {
        this.username = username;
        this.password = password;
        this.creditCard = creditCard;
        this.serviceProviderId = serviceProviderId;
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

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Long getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

}
