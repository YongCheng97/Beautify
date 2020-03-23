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
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author jilon
 */
@Named(value = "headerManagedBean")
@ApplicationScoped
public class HeaderManagedBean {

    @EJB(name = "CategorySessionBeanLocal")
    private CategorySessionBeanLocal categorySessionBeanLocal;
    
    private List<Category> categories; 

    public HeaderManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        categories = categorySessionBeanLocal.retrieveAllCategories(); 
    }
    
    public List<Category> getCategories() {
        return categories; 
    }
    
    public void setCategory(List<Category> categories) {
        this.categories = categories; 
    }
    
}
