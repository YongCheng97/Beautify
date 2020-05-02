package ws.datamodel;

public class CreateServiceRsp {
    
    private Long serviceId;

    public CreateServiceRsp() {
    }

    public CreateServiceRsp(Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the serviceId
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    
    
}
