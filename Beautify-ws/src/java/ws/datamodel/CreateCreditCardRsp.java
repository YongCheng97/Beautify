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
public class CreateCreditCardRsp {
    
    private Long creditCardId; 

    public CreateCreditCardRsp() {
    }

    public CreateCreditCardRsp(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }
    
    
    
}
