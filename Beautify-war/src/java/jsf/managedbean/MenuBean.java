/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CategorySessionBeanLocal;
import entity.Category;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author fooyo
 */
@Named(value = "menuBean")
@ApplicationScoped
public class MenuBean {

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
            DefaultMenuItem item1 = new DefaultMenuItem("Products");
            subMenus.get(i).addElement(item1);
            DefaultMenuItem item2 = new DefaultMenuItem("Services");
            subMenus.get(i).addElement(item2);
//            List<Category> leafCategories = categorySessionBean.retrieveAllLeafCategories();
//            for (Category leafCategory : leafCategories) {
//                if (leafCategory.getParentCategoryEntity().getCategoryId() == rootCategories.get(i).getCategoryId()) {
//                    DefaultMenuItem item = new DefaultMenuItem(leafCategory.getName());
//                    subMenus.get(i).addElement(item);
//                }
//            }
            model.addElement(subMenus.get(i));
        }
    }

    public MenuModel getModel() {
        return model;
    }
}
