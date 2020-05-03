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
    
    private Long promotionId; 

    public CreatePromotionRsp() {
    }

    public CreatePromotionRsp(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }
    
    
    
}
