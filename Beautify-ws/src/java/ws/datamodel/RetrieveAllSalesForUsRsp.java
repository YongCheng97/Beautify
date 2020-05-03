package ws.datamodel;

import entity.SalesForUs;
import java.util.List;


public class RetrieveAllSalesForUsRsp {
    
    private List<SalesForUs> salesForUs;

    
    
    public RetrieveAllSalesForUsRsp()
    {
    }
    
    
    public RetrieveAllSalesForUsRsp(List<SalesForUs> salesForUs)
    {
        this.salesForUs = salesForUs;
    }

    public List<SalesForUs> getSalesForUs() {
        return salesForUs;
    }

    public void setSalesForUs(List<SalesForUs> salesForUs) {
        this.salesForUs = salesForUs;
    }
}