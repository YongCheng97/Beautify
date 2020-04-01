/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CategorySessionBeanLocal;
import entity.Category;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author fooyo
 */
@Named(value = "menuBean")
@ViewScoped

public class MenuBean implements Serializable{

    @EJB
    private CategorySessionBeanLocal categorySessionBean;

    private MenuModel model;

    public MenuBean() {
    }

    @PostConstruct
    public void postConstruct() {
        model = new DefaultMenuModel();
        List<Category> rootCategories = categorySessionBean.retrieveAllRootCategories();
        List<DefaultSubMenu> subMenus = new ArrayList<>(rootCategories.size());

        for (int i = 0; i < rootCategories.size(); i++) {
            DefaultSubMenu subMenu = new DefaultSubMenu(rootCategories.get(i).getName());
            subMenus.add(subMenu);
            DefaultSubMenu products1 = new DefaultSubMenu("Products");
            subMenus.get(i).addElement(products1);
            DefaultSubMenu products2 = new DefaultSubMenu("Services");
            subMenus.get(i).addElement(products2);
            List<Category> leafCategories = categorySessionBean.retrieveLeafCategory(rootCategories.get(i).getCategoryId());
            for (Category leafCategory : leafCategories) {
                if (leafCategory.getType().toString() == "PRODUCT") {
                    DefaultMenuItem leafCategoryProduct = new DefaultMenuItem(leafCategory.getName());
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoryId", leafCategory.getCategoryId());
                    //leafCategoryProduct.setParam("categoryId",leafCategory.getCategoryId());
                    leafCategoryProduct.setUrl("http://localhost:8080/Beautify-war/customerOperations/listingsOfACategory.xhtml");
                    products1.addElement(leafCategoryProduct);

                }
                if (leafCategory.getType().toString() == "SERVICE") {
                    DefaultMenuItem leafCategoryService = new DefaultMenuItem(leafCategory.getName());
                    leafCategoryService.setParam("categoryId", leafCategory.getCategoryId());
                    leafCategoryService.setUrl("http://localhost:8080/Beautify-war/customerOperations/listingsOfACategory.xhtml");
                    products2.addElement(leafCategoryService);
                }
            }
            model.addElement(subMenus.get(i));
        }
    }

    public MenuModel getModel() {
        return model;
    }
}
