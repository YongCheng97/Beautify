package jsf.managedbean;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import ejb.session.stateless.TagsSessionBeanLocal;
import entity.Category;
import entity.Service;
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

@Named(value = "filterServicesByTagsManagedBean")
@ViewScoped
public class FilterServicesByTagsManagedBean implements Serializable {

    @EJB
    private TagsSessionBeanLocal tagsSessionBean;
    @EJB
    private ServiceSessionBeanLocal serviceSessionBean;
    @EJB
    private CategorySessionBeanLocal categorySessionBean;

    private String condition;
    private List<Long> selectedTagIds;
    private List<SelectItem> selectItems;
    private List<Service> services;
    private Long categoryId;
    private Category category;

    public FilterServicesByTagsManagedBean() {
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
        condition = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("serviceFilterCondition");
        selectedTagIds = (List<Long>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("serviceFilterTags");

        filterService();
    }

    public void filterService() {
        if (selectedTagIds != null && selectedTagIds.size() > 0) {
            services = serviceSessionBean.filterServicesByTags(selectedTagIds, condition, categoryId);
        } else {
            services = serviceSessionBean.retrieveAllServicesFromCategory(categoryId);
        }
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

    public void viewAllServices(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/listingsOfAServiceCategory.xhtml?categoryId=" + categoryId);
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
     * @return the services
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     * @param services the services to set
     */
    public void setServices(List<Service> services) {
        this.services = services;
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
