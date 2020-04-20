package jsf.managedbean;

import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.TagsSessionBeanLocal;
import entity.Product;
import entity.Tag;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "filterProductsByTagsManagedBean")
@ViewScoped
public class FilterProductsByTagsManagedBean implements Serializable {

    @EJB
    private ProductSessionBeanLocal productSessionBean;
    @EJB
    private TagsSessionBeanLocal tagsSessionBean;

    private String condition;
    private List<Long> selectedTagIds;
    private List<SelectItem> selectItems;
    private List<Product> products;

    public FilterProductsByTagsManagedBean() {
        condition = "OR";
    }

    @PostConstruct
    public void postConstruct() {
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
            products = productSessionBean.filterProductsByTags(selectedTagIds, condition);
        } else {
            products = productSessionBean.retrieveAllProducts();
        }
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

}
