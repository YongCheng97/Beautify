/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

/**
 *
 * @author Zheng Yang
 */
public class CreateCategoryRsp {
    private Long newCategoryId;

    public CreateCategoryRsp() {
    }

    public CreateCategoryRsp(Long newCategoryId) {
        this.newCategoryId = newCategoryId;
    }

    /**
     * @return the newCategoryId
     */
    public Long getNewCategoryId() {
        return newCategoryId;
    }

    /**
     * @param newCategoryId the newCategoryId to set
     */
    public void setNewCategoryId(Long newCategoryId) {
        this.newCategoryId = newCategoryId;
    }
    
    
}
