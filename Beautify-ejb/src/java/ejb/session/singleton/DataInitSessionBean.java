/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Category;
import entity.Customer;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CreateNewCategoryException;
import util.exception.CustomerExistException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

@Stateless
@LocalBean
@Startup
public class DataInitSessionBean {

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;

    @EJB(name = "CategorySessionBeanLocal")
    private CategorySessionBeanLocal categorySessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    public DataInitSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        initialiseData();
    }

    private void initialiseData() {
        try {
            customerSessionBeanLocal.createNewCustomer(new Customer("Bob", "Lim", "boblim@gmail.com", "boblim", "password", Long.parseLong("98023457")));
            
            Category categoryNails = categorySessionBeanLocal.createNewCategoryEntity(new Category("Nails", "Nail Services"), null); 
            Category categoryHair = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hair", "Hair Services"), null); 
            Category categoryFace = categorySessionBeanLocal.createNewCategoryEntity(new Category("Face", "Facial Services"), null); 
            
            Category categoryGelNails = categorySessionBeanLocal.createNewCategoryEntity(new Category("Gel Nails", "Gel Nail Services"), categoryNails.getCategoryId()); 
            Category categoryHaircut = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hair cut", "Hait Cut Services"), categoryHair.getCategoryId());
            Category categoryMakeup = categorySessionBeanLocal.createNewCategoryEntity(new Category("Makeup", "Makeup Services"), categoryFace.getCategoryId());
            
        } catch (CustomerExistException | UnknownPersistenceException | InputDataValidationException | CreateNewCategoryException ex) {
            ex.printStackTrace(); 
        }
    }

}
