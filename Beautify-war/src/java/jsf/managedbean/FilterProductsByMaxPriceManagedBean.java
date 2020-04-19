/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author fooyo
 */
@Named(value = "filterProductsByMaxPriceManagedBean")
@ViewScoped
public class FilterProductsByMaxPriceManagedBean implements Serializable {

    @EJB
    private CategorySessionBeanLocal categorySessionBean;

    @EJB
    private ProductSessionBeanLocal productSessionBean;

    private Long categoryId;
    private String maxPrice;
    private List<Product> products;
    private Category category;

    public FilterProductsByMaxPriceManagedBean() {
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
        if (maxPrice == "" || maxPrice.trim().length() == 0) {
            products = (productSessionBean.retrieveAllProductsFromCategory(categoryId));
        } else {
            BigDecimal maxPriceBigDecimal = new BigDecimal(maxPrice);
            products = productSessionBean.filterProductsByMaximumPrice(maxPriceBigDecimal, categoryId);
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

    public void viewAllProducts(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/listingsOfAProductCategory.xhtml?categoryId=" + categoryId);
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
