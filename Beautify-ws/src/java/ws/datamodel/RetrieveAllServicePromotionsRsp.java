/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Promotion;
import java.util.List;

/**
 *
 * @author jilon
 */
public class RetrieveAllServicePromotionsRsp {
    private List<Promotion> promotions; 

    public RetrieveAllServicePromotionsRsp(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public RetrieveAllServicePromotionsRsp() {
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
    
    
    
}
