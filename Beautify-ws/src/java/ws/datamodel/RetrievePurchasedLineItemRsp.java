package ws.datamodel;

import entity.PurchasedLineItem;



public class RetrievePurchasedLineItemRsp
{
    private PurchasedLineItem purchasedLineItem;


    public RetrievePurchasedLineItemRsp()
    {
    }

    public RetrievePurchasedLineItemRsp(PurchasedLineItem purchasedLineItem)
    {
        this.purchasedLineItem = purchasedLineItem;
    }

    public PurchasedLineItem getPurchasedLineItem() {
        return purchasedLineItem;
    }

    public void setPurchasedLineItem(PurchasedLineItem purchasedLineItem) {
        this.purchasedLineItem = purchasedLineItem;
    }


    
}