package ws.datamodel;

import entity.Service;
import java.util.List;

public class CreateServiceReq {

    private String username;
    private String password;
    private Service service;
    private Long categoryId;
    private List<Long> tagIds;

    public CreateServiceReq() {
    }

    public CreateServiceReq(String username, String password, Service service, Long categoryId, List<Long> tagIds) {
        this.username = username;
        this.password = password;
        this.service = service;
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
     * @return the service
     */
    public Service getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(Service service) {
        this.service = service;
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
