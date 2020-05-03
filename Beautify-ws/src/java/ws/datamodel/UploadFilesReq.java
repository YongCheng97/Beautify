package ws.datamodel;

import entity.Product;
import java.io.File;

public class UploadFilesReq {
    
    private String username;
    private String password;
    private File[] uploadedFiles;
    private Product product;

    public UploadFilesReq() {
    }

    public UploadFilesReq(String username, String password, File[] uploadedFiles, Product product) {
        this.username = username;
        this.password = password;
        this.uploadedFiles = uploadedFiles;
        this.product = product;
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
     * @return the uploadedFiles
     */
    public File[] getUploadedFiles() {
        return uploadedFiles;
    }

    /**
     * @param uploadedFiles the uploadedFiles to set
     */
    public void setUploadedFiles(File[] uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
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
    
    
}
