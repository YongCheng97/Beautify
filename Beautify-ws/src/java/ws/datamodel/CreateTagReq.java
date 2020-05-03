/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Tag;

/**
 *
 * @author Zheng Yang
 */
public class CreateTagReq {
    
    private String username;
    private String password;
    private Tag newTag;
    
    public CreateTagReq() {
        
    }
    
    public CreateTagReq(String username, String password, Tag newTag) {
        this.username = username;
        this.password = password;
        this.newTag = newTag;
    }

    /**
     * @return the newTag
     */
    public Tag getNewTag() {
        return newTag;
    }

    /**
     * @param newTag the newTag to set
     */
    public void setNewTag(Tag newTag) {
        this.newTag = newTag;
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
    
}
