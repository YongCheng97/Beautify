package ws.datamodel;

import entity.PurchasedLineItem;
import java.util.List;


public class RetrieveAllPurchasedLineItemsRsp {
    
    private List<PurchasedLineItem> purchasedLineItems;

    
    
    public RetrieveAllPurchasedLineItemsRsp()
    {
    }
    
    
    
    public RetrieveAllPurchasedLineItemsRsp(List<PurchasedLineItem> purchasedLineItems)
    {
        this.purchasedLineItems = purchasedLineItems;
    }

    public List<PurchasedLineItem> getPurchasedLineItems() {
        return purchasedLineItems;
    }

    public void setPurchasedLineItems(List<PurchasedLineItem> purchasedLineItems) {
        this.purchasedLineItems = purchasedLineItems;
    }

}
