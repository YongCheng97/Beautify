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
public class CreateTagRsp {
    private Long newTagId;
    
    public CreateTagRsp() {
        
    }
    
    public CreateTagRsp(Long newTagId) {
        this.newTagId = newTagId;
    }

    /**
     * @return the newTagId
     */
    public Long getNewTagId() {
        return newTagId;
    }

    /**
     * @param newTagId the newTagId to set
     */
    public void setNewTagId(Long newTagId) {
        this.newTagId = newTagId;
    }
    
    
    
}
