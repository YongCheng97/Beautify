package ws.datamodel;

import entity.Product;
import java.util.List;

public class CreateProductReq {
    
    private String username;
    private String password;
    private Product product;
    private Long categoryId;
    private List<Long> tagIds;

    public CreateProductReq() {
    }

    public CreateProductReq(String username, String password, Product product, Long categoryId, List<Long> tagIds) {
        this.username = username;
        this.password = password;
        this.product = product;
        this.categoryId = categoryId;
        this.tagIds = tagIds;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return the categoryId
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the tagIds
     */
    public List<Long> getTagIds() {
        return tagIds;
    }

    /**
     * @param tagIds the tagIds to set
     */
    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }
}
