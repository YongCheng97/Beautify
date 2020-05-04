
package ws.datamodel;

public class CreateNewSalesForUsRsp {
    
    private Long salesForUsId; 

    public CreateNewSalesForUsRsp() {
    }

    public CreateNewSalesForUsRsp(Long salesForUsId) {
        this.salesForUsId = salesForUsId;
    }

    public Long getSalesForUsId() {
        return salesForUsId;
    }

    public void setSalesForUsId(Long salesForUsId) {
        this.salesForUsId = salesForUsId;
    }
    
}
