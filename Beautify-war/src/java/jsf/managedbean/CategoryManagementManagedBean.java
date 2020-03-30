/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CategorySessionBeanLocal;
import entity.Category;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "categoryManagementManagedBean")
@ViewScoped
public class CategoryManagementManagedBean {

    @EJB
    private CategorySessionBeanLocal categorySessionBean;
    
    private List<Category> categories;


    public CategoryManagementManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        setCategories(categorySessionBean.retrieveAllCategories());
    }

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
    
}
