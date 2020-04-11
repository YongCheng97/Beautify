package jsf.managedbean;

import ejb.session.stateless.CategorySessionBeanLocal;
import entity.Category;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "viewAllCategoriesManagedBean")
@ViewScoped
public class viewAllCategoriesManagedBean implements Serializable{

    @EJB
    private CategorySessionBeanLocal categorySessionBean;
    
    private List<Category> categories;
    
    public viewAllCategoriesManagedBean() {
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
