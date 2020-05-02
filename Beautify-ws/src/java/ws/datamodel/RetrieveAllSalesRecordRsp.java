package ws.datamodel;

import entity.SalesRecord;
import java.util.List;


public class RetrieveAllSalesRecordRsp {
    
    private List<SalesRecord> salesRecords;

    
    
    public RetrieveAllSalesRecordRsp()
    {
    }
    
    
    public RetrieveAllSalesRecordRsp(List<SalesRecord> salesRecords)
    {
        this.salesRecords = salesRecords;
    }

    public List<SalesRecord> getSalesRecords() {
        return salesRecords;
    }

    public void setSalesRecords(List<SalesRecord> salesRecords) {
        this.salesRecords = salesRecords;
    }


}