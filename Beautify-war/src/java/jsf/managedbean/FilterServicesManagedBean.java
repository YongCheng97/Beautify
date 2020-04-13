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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.CategoryNotFoundException;

@Named(value = "filterServicesManagedBean")
@ViewScoped
public class FilterServicesManagedBean implements Serializable{

    @EJB
    private ServiceSessionBeanLocal serviceSessionBean;

    @EJB
    private CategorySessionBeanLocal categorySessionBean;

    private String searchString;
    private Long categoryId;
    private String minPrice;
    private String maxPrice;
    private List<Service> services;
    private Category category;

    public FilterServicesManagedBean() {
        searchString = "";
        minPrice = "";
        maxPrice = "";
    }

    @PostConstruct
    public void postConstruct() {
        //searchString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("productSearchString");
        categoryId = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("categoryId"));

        try {
            category = categorySessionBean.retrieveCategoryByCategoryId(categoryId);
        } catch (CategoryNotFoundException ex) {
            Logger.getLogger(FilterProductsManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        services = (serviceSessionBean.retrieveAllServicesFromCategory(categoryId));
    }

    public void searchService() {
        if ((searchString == "" || searchString.trim().length() == 0) && (minPrice == "" || minPrice.trim().length() == 0) && (maxPrice == "" || maxPrice.trim().length() == 0)) {
            services = (serviceSessionBean.retrieveAllServicesFromCategory(categoryId));
        } else if ((searchString != "" || searchString.trim().length() != 0) && (minPrice == "" || minPrice.trim().length() == 0) && (maxPrice == "" || maxPrice.trim().length() == 0)) {
            services = (serviceSessionBean.filterServicesByName(searchString, categoryId));
        } else if ((minPrice != "" || minPrice.trim().length() != 0) && (searchString == "" || searchString.trim().length() == 0) && (maxPrice == "" || maxPrice.trim().length() == 0)) {
            BigDecimal minPriceBigDecimal = new BigDecimal(minPrice);
            services = serviceSessionBean.filterServicesByMinimumPrice(minPriceBigDecimal, categoryId);
        } else if ((minPrice == "" || minPrice.trim().length() == 0) && (searchString == "" || searchString.trim().length() == 0) && (maxPrice != "" || maxPrice.trim().length() != 0)) {
            BigDecimal maxPriceBigDecimal = new BigDecimal(maxPrice);
            services = serviceSessionBean.filterServicesByMaximumPrice(maxPriceBigDecimal, categoryId);
        }
    }

    public void clickLink(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/viewProductDetails.xhtml?categoryId=" + categoryId);
    }

    /**
     * @return the searchString
     */
    public String getSearchString() {
        return searchString;
    }

    /**
     * @param searchString the searchString to set
     */
    public void setSearchString(String searchString) {
        this.searchString = searchString;
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
     * @return the maxPrice
     */
    public String getMaxPrice() {
        return maxPrice;
    }

    /**
     * @param maxPrice the maxPrice to set
     */
    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
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
