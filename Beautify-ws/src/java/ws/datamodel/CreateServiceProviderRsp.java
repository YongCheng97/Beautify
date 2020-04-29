package ws.datamodel;

public class CreateServiceProviderRsp {
    
    private Long serviceProviderId;

    public CreateServiceProviderRsp() {
    }

    public CreateServiceProviderRsp(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    /**
     * @return the serviceProviderId
     */
    public Long getServiceProviderId() {
        return serviceProviderId;
    }

    /**
     * @param serviceProviderId the serviceProviderId to set
     */
    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }
    
}
