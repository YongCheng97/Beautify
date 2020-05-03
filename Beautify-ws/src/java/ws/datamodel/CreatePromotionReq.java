/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Promotion;

/**
 *
 * @author jilon
 */
public class CreatePromotionReq {

    private String username;
    private String password;
    private Promotion promotion;
    private Long serviceId;
    private Long productId; 

    public CreatePromotionReq() {
    }

    public CreatePromotionReq(String username, String password, Promotion promotion, Long serviceId, Long productId) {
        this.username = username;
        this.password = password;
        this.promotion = promotion;
        this.serviceId = serviceId;
        this.productId = productId;
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

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    

}
