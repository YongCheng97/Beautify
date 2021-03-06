/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.datamodel;

import entity.Category;

/**
 *
 * @author Zheng Yang
 */
public class CreateCategoryReq {

    private String username;
    private String password;
    private Category newCategory;
    private String categoryType;
    private Long categoryId;
    
    public CreateCategoryReq() {
        
    }

    public CreateCategoryReq(String username, String password, Category newCategory, String categoryType, Long categoryId) {
        this.username = username;
        this.password = password;
        this.newCategory = newCategory;
        this.categoryType = categoryType;
        this.categoryId = categoryId;
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
     * @return the newCategory
     */
    public Category getNewCategory() {
        return newCategory;
    }

    /**
     * @param newCategory the newCategory to set
     */
    public void setNewCategory(Category newCategory) {
        this.newCategory = newCategory;
    }
    
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the categoryType
     */
    public String getCategoryType() {
        return categoryType;
    }

    /**
     * @param categoryType the categoryType to set
     */
    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
    
}
