/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CategorySessionBeanLocal;
import entity.Category;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import util.exception.CategoryNotFoundException;

@Named(value = "categoryManagementManagedBean")
@ViewScoped
public class CategoryManagementManagedBean implements Serializable {

    @EJB
    private CategorySessionBeanLocal categorySessionBean;

    private List<Category> categories;
    private Category newCategory;
    private Long categoryId;

    public CategoryManagementManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        categories = categorySessionBean.retrieveAllCategories();
        categoryId = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("categoryId"));
        try {
            newCategory = categorySessionBean.retrieveCategoryByCategoryId(categoryId);
        } catch (CategoryNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving category: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void goToFilterProductByName(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/products/filterProductsByName.xhtml?categoryId=" + categoryId);
    }

    public void goToFilterProductByMinPrice(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/products/filterProductsByMinPrice.xhtml?categoryId=" + categoryId);
    }

    public void goToFilterProductByMaxPrice(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/products/filterProductsByMaxPrice.xhtml?categoryId=" + categoryId);
    }

    public void goToFilterProductByTags(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/products/filterProductsByTags.xhtml?categoryId=" + categoryId);
    }

    public void goToFilterServiceByName(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/services/filterServicesByName.xhtml?categoryId=" + categoryId);
    }

    public void goToFilterServiceByMinPrice(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/services/filterServicesByMinPrice.xhtml?categoryId=" + categoryId);
    }

    public void goToFilterServiceByMaxPrice(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/services/filterServicesByMaxPrice.xhtml?categoryId=" + categoryId);
    }

    public void goToFilterServiceByTags(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/services/filterServicesByTags.xhtml?categoryId=" + categoryId);
    }

    public void viewProduct(ActionEvent event) throws IOException {
        Long productIdToView = (Long) event.getComponent().getAttributes().get("productId");
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("productIdToView", productIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/viewProductDetails.xhtml");
    }
    
    public void viewService(ActionEvent event) throws IOException {
        Long serviceIdToView = (Long) event.getComponent().getAttributes().get("serviceId");
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("serviceIdToView", serviceIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/viewServiceDetails.xhtml");
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

}
