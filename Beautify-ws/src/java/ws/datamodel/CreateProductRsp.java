package ws.datamodel;

public class CreateProductRsp {

    private Long productId;

    public CreateProductRsp() {
    }

    public CreateProductRsp(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
