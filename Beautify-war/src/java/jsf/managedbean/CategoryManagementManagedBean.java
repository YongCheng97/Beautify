/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CategorySessionBeanLocal;
import entity.Category;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CategoryNotFoundException;

@Named(value = "categoryManagementManagedBean")
@ViewScoped
public class CategoryManagementManagedBean implements Serializable {

    @EJB
    private CategorySessionBeanLocal categorySessionBean;

    private List<Category> categories;
    private Category newCategory;

    public CategoryManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        categories = categorySessionBean.retrieveAllCategories();
    }

    public void retrieveCategoryId() {
        try {
            Long categoryId = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("param"));
            newCategory = categorySessionBean.retrieveCategoryByCategoryId(categoryId);
        } catch (CategoryNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving category: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    //use requestParameterMap
    /**
     * @return the categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * @param categories the categories to set
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * @return the newCategory
     */
    public Category getNewCategory() {
        if (newCategory != null) {
            System.err.println("HELOOOOOOOO " + newCategory.getCategoryId());
        }
        return newCategory;
    }

    /**
     * @param newCategory the newCategory to set
     */
    public void setNewCategory(Category newCategory) {
        this.newCategory = newCategory;
    }

}
