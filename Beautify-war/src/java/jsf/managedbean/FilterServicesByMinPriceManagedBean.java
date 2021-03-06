package jsf.managedbean;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Category;
import entity.Service;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.exception.CategoryNotFoundException;

@Named(value = "filterServicesByMinPriceManagedBean")
@ViewScoped
public class FilterServicesByMinPriceManagedBean implements Serializable {

    @EJB
    private ServiceSessionBeanLocal serviceSessionBean;

    @EJB
    private CategorySessionBeanLocal categorySessionBean;

    private Long categoryId;
    private String minPrice;
    private List<Service> services;
    private Category category;

    public FilterServicesByMinPriceManagedBean() {
        minPrice = "";
    }

    @PostConstruct
    public void postConstruct() {
        //searchString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("productSearchString");
        categoryId = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("categoryId"));

        try {
            category = categorySessionBean.retrieveCategoryByCategoryId(categoryId);
        } catch (CategoryNotFoundException ex) {
            Logger.getLogger(FilterProductsByNameManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        services = (serviceSessionBean.retrieveAllServicesFromCategory(categoryId));
    }

    public void searchService() {
        if (minPrice == "" || minPrice.trim().length() == 0) {
            services = (serviceSessionBean.retrieveAllServicesFromCategory(categoryId));
        } else {
            BigDecimal minPriceBigDecimal = new BigDecimal(minPrice);
            services = serviceSessionBean.filterServicesByMinimumPrice(minPriceBigDecimal, categoryId);
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

    public void viewService(ActionEvent event) throws IOException {
        Long serviceIdToView = (Long) event.getComponent().getAttributes().get("serviceId");
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("serviceIdToView", serviceIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/viewServiceDetails.xhtml");
    }

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
     * @return the minPrice
     */
    public String getMinPrice() {
        return minPrice;
    }

    /**
     * @param minPrice the minPrice to set
     */
    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
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
