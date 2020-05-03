/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

/**
 *
 * @author jilon
 */
public class CreatePromotionRsp {
    Long PromotionId; 

    public CreatePromotionRsp() {
    }

    public CreatePromotionRsp(Long PromotionId) {
        this.PromotionId = PromotionId;
    }

    public Long getPromotionId() {
        return PromotionId;
    }

    public void setPromotionId(Long PromotionId) {
        this.PromotionId = PromotionId;
    }
    
    
}
