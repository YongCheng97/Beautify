package ws.datamodel;

import entity.PurchasedLineItem;


public class UpdatePurchasedLineItemReq
{
    private String username;
    private String password;
    private PurchasedLineItem purchasedLineItem;

    
    
    public UpdatePurchasedLineItemReq()
    {        
    }

    
    
    public UpdatePurchasedLineItemReq(String username, String password, PurchasedLineItem purchasedLineItem) 
    {
        this.username = username;
        this.password = password;
        this.purchasedLineItem = purchasedLineItem;
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

    public PurchasedLineItem getPurchasedLineItem() {
        return purchasedLineItem;
    }

    public void setPurchasedLineItem(PurchasedLineItem purchasedLineItem) {
        this.purchasedLineItem = purchasedLineItem;
    }

}