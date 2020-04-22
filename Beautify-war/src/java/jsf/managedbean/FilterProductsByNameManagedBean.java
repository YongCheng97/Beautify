package jsf.managedbean;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.ProductSessionBeanLocal;
import entity.Category;
import entity.Product;
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

@Named(value = "filterProductsByNameManagedBean")
@ViewScoped
public class FilterProductsByNameManagedBean implements Serializable {

    @EJB
    private CategorySessionBeanLocal categorySessionBean;

    @EJB
    private ProductSessionBeanLocal productSessionBean;

    private String searchString;
    private Long categoryId;
    private String minPrice;
    private String maxPrice;
    private List<Product> products;
    private Category category;

    public FilterProductsByNameManagedBean() {
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
            Logger.getLogger(FilterProductsByNameManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        products = (productSessionBean.retrieveAllProductsFromCategory(categoryId));
    }

    public void searchProduct() {
        if ((searchString == "" || searchString.trim().length() == 0) && (minPrice == "" || minPrice.trim().length() == 0) && (maxPrice == "" || maxPrice.trim().length() == 0)) {
            products = (productSessionBean.retrieveAllProductsFromCategory(categoryId));
            System.out.println("Test1");
        } else if ((searchString != "" || searchString.trim().length() != 0) && (minPrice == "" || minPrice.trim().length() == 0) && (maxPrice == "" || maxPrice.trim().length() == 0)) {
            products = (productSessionBean.filterProductsByName(searchString, categoryId));
            System.out.println("Test2");
        } else if ((minPrice != "" || minPrice.trim().length() != 0) && (searchString == "" || searchString.trim().length() == 0) && (maxPrice == "" || maxPrice.trim().length() == 0)) {
            BigDecimal minPriceBigDecimal = new BigDecimal(minPrice);
            products = productSessionBean.filterProductsByMinimumPrice(minPriceBigDecimal, categoryId);
            System.out.println("Test3");
        } else if ((minPrice == "" || minPrice.trim().length() == 0) && (searchString == "" || searchString.trim().length() == 0) && (maxPrice != "" || maxPrice.trim().length() != 0)) {
            BigDecimal maxPriceBigDecimal = new BigDecimal(maxPrice);
            products = productSessionBean.filterProductsByMaximumPrice(maxPriceBigDecimal, categoryId);
            System.out.println("Test4");
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

    public void clickLink(Long productId) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/viewProductDetails.xhtml?productId=" + productId);
    }

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

}
