package jsf.managedbean;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.TagsSessionBeanLocal;
import entity.Category;
import entity.Product;
import entity.Tag;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CategoryNotFoundException;

@Named(value = "filterProductsByTagsManagedBean")
@ViewScoped
public class FilterProductsByTagsManagedBean implements Serializable {

    @EJB
    private CategorySessionBeanLocal categorySessionBean;
    @EJB
    private ProductSessionBeanLocal productSessionBean;
    @EJB
    private TagsSessionBeanLocal tagsSessionBean;

    private String condition;
    private List<Long> selectedTagIds;
    private List<SelectItem> selectItems;
    private List<Product> products;
    private Long categoryId;
    private Category category;

    public FilterProductsByTagsManagedBean() {
        condition = "OR";
    }

    @PostConstruct
    public void postConstruct() {
        categoryId = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("categoryId"));
        try {
            category = categorySessionBean.retrieveCategoryByCategoryId(categoryId);
        } catch (CategoryNotFoundException ex) {
            Logger.getLogger(FilterProductsByNameManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Tag> tagEntities = tagsSessionBean.retrieveAllTags();
        selectItems = new ArrayList<>();

        for (Tag tagEntity : tagEntities) {
            selectItems.add(new SelectItem(tagEntity.getTagId(), tagEntity.getName(), tagEntity.getName()));
        }

        // Optional demonstration of the use of custom converter
        // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("TagEntityConverter_tagEntities", tagEntities);
        condition = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("productFilterCondition");
        selectedTagIds = (List<Long>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("productFilterTags");

        filterProduct();
    }

    public void filterProduct() {
        if (selectedTagIds != null && selectedTagIds.size() > 0) {
            products = productSessionBean.filterProductsByTags(selectedTagIds, condition, categoryId);
        } else {
            products = productSessionBean.retrieveAllProductsFromCategory(categoryId);
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

    public void viewAllProducts(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/listingsOfAProductCategory.xhtml?categoryId=" + categoryId);
    }

    public void viewProduct(ActionEvent event) throws IOException {
        Long productIdToView = (Long) event.getComponent().getAttributes().get("productId");
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("productIdToView", productIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/viewProductDetails.xhtml");
    }

    /**
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * @return the selectedTagIds
     */
    public List<Long> getSelectedTagIds() {
        return selectedTagIds;
    }

    /**
     * @param selectedTagIds the selectedTagIds to set
     */
    public void setSelectedTagIds(List<Long> selectedTagIds) {
        this.selectedTagIds = selectedTagIds;
    }

    /**
     * @return the selectItems
     */
    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    /**
     * @param selectItems the selectItems to set
     */
    public void setSelectItems(List<SelectItem> selectItems) {
        this.selectItems = selectItems;
    }

    /**
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
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
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

}
