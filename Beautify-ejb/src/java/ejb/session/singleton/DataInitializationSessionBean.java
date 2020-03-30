/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Category;
import entity.Customer;
import entity.Product;
import entity.Service;
import entity.ServiceProvider;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CreateNewCategoryException;
import util.exception.CreateNewProductException;
import util.exception.CreateNewServiceException;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.ProductExistException;
import util.exception.ServiceExistException;
import util.exception.ServiceProviderExistException;
import util.exception.ServiceProviderNotFoundException;
import util.exception.UnknownPersistenceException;

@Singleton
@LocalBean
@Startup

public class DataInitializationSessionBean {

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;

    @EJB(name = "CategorySessionBeanLocal")
    private CategorySessionBeanLocal categorySessionBeanLocal;

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB(name = "ServiceSessionBeanLocal")
    private ServiceSessionBeanLocal serviceSessionBeanLocal;

    @EJB(name = "ServiceProviderSessionBeanLocal")
    private ServiceProviderSessionBeanLocal serviceProviderSessionBeanLocal;

    @EJB(name = "ProductSessionBeanLocal")
    private ProductSessionBeanLocal productSessionBeanLocal;

    public DataInitializationSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            customerSessionBeanLocal.retrieveCustomerByCustId(Long.valueOf(2));
        } catch (CustomerNotFoundException ex) {
            initialiseData();
        }
    }

    private void initialiseData() {
        try {
            customerSessionBeanLocal.createNewCustomer(new Customer("Bob", "Lim", "boblim@gmail.com", "password", "boblim", Long.parseLong("98023457")));

            Category categoryNails = categorySessionBeanLocal.createNewCategoryEntity(new Category("Nails", "Nail Services and Products"), null);
            Category categoryHair = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hair", "Hair Services and Products"), null);
            Category categoryFace = categorySessionBeanLocal.createNewCategoryEntity(new Category("Face", "Facial Services and Products"), null);

            Category categoryManicure = categorySessionBeanLocal.createNewCategoryEntity(new Category("Manicure", "Manicure Services"), categoryNails.getCategoryId());
            Category categoryPedicure = categorySessionBeanLocal.createNewCategoryEntity(new Category("Pedicure", "Pedicure Services"), categoryNails.getCategoryId());
            Category categoryNailPolish = categorySessionBeanLocal.createNewCategoryEntity(new Category("Nail Polish", "Nail Polish Products"), categoryNails.getCategoryId());
            Category categoryHaircut = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hair Cut", "Hair Cut Services"), categoryHair.getCategoryId());
            Category categoryHairColour = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hair Colouring", "Hair Colour Services"), categoryHair.getCategoryId());
            Category categoryHairProducts = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hair Products", "Hair Products"), categoryHair.getCategoryId());
            Category categoryMakeover = categorySessionBeanLocal.createNewCategoryEntity(new Category("Makeover", "Makeup Services"), categoryFace.getCategoryId());
            Category categoryFacial = categorySessionBeanLocal.createNewCategoryEntity(new Category("Facial", "Facial Services"), categoryFace.getCategoryId());
            Category categoryFaceCare = categorySessionBeanLocal.createNewCategoryEntity(new Category("Face Care", "Face Care Products"), categoryFace.getCategoryId());
            Category categoryMakeup = categorySessionBeanLocal.createNewCategoryEntity(new Category("Makeup", "Makeup Products"), categoryFace.getCategoryId());

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            ServiceProvider provider1 = serviceProviderSessionBeanLocal.retrieveServiceProviderById(serviceProviderSessionBeanLocal.createNewServiceProvider(new ServiceProvider("The Nail Lounge", "thenaillounge@gmail.com", "password", "123 Hougang Road",
                    sdf.parse("09:00:00"), sdf.parse("17:00:00"), null, true)));
            ServiceProvider provider2 = serviceProviderSessionBeanLocal.retrieveServiceProviderById(serviceProviderSessionBeanLocal.createNewServiceProvider(new ServiceProvider("The Beautiful Hair Salon", "thebeautifulhairsalon@gmail.com", "password", "3 Yishun Drive",
                    sdf.parse("09:00:00"), sdf.parse("17:00:00"), null, true)));
            ServiceProvider provider3 = serviceProviderSessionBeanLocal.retrieveServiceProviderById(serviceProviderSessionBeanLocal.createNewServiceProvider(new ServiceProvider("The Makeup Place", "themakeupplace@gmail.com", "password", "56 Serangoon Way",
                    sdf.parse("09:00:00"), sdf.parse("17:00:00"), null, true)));

            productSessionBeanLocal.createNewProduct(new Product("Red Nail Polish", new BigDecimal("20.00"), "Red nail polish is historically bold, daring, and adventurous", null),
                    categoryNailPolish.getCategoryId(), provider1.getServiceProviderId());
            productSessionBeanLocal.createNewProduct(new Product("Yellow Nail Polish", new BigDecimal("20.00"), "Bright and cheery yellow nail polish", null),
                    categoryNailPolish.getCategoryId(), provider1.getServiceProviderId());

            productSessionBeanLocal.createNewProduct(new Product("Shampoo", new BigDecimal("18.00"), "Cleanse your scalp and leave your hair healthy and smooth", null),
                    categoryHairProducts.getCategoryId(), provider2.getServiceProviderId());
            productSessionBeanLocal.createNewProduct(new Product("Hair Treatment", new BigDecimal("18.00"), "Transform your dry and damaged hair", null),
                    categoryHairProducts.getCategoryId(), provider2.getServiceProviderId());

            productSessionBeanLocal.createNewProduct(new Product("Lipstick", new BigDecimal("13.00"), "Hydrating lipstick that adds colour to your lips", null),
                    categoryMakeup.getCategoryId(), provider3.getServiceProviderId());
            productSessionBeanLocal.createNewProduct(new Product("Facewash", new BigDecimal("15.00"), "Facewash that revitalises and cleanses your skin", null),
                    categoryFaceCare.getCategoryId(), provider3.getServiceProviderId());

            serviceSessionBeanLocal.createNewService(new Service("Gel Manicure", new BigDecimal("80.00"), "Gels last longer, feel stronger, and shine like no one's business!", null), provider1.getServiceProviderId(), categoryManicure.getCategoryId());
            serviceSessionBeanLocal.createNewService(new Service("Express Hair Cut", new BigDecimal("30.00"), "Express Hair Cut with no washing included", null), provider2.getServiceProviderId(), categoryHair.getCategoryId());
            serviceSessionBeanLocal.createNewService(new Service("Facial", new BigDecimal("80.00"), "The best and most relaxing Facial Treatement", null), provider3.getServiceProviderId(), categoryFacial.getCategoryId());

            /*
            Category categoryGelNails = categorySessionBeanLocal.createNewCategoryEntity(new Category("Gel Nails", "Gel Nail Services"), categoryNails.getCategoryId());
            Category categoryANails = categorySessionBeanLocal.createNewCategoryEntity(new Category("A Nails", "Gel Nail Services"), categoryNails.getCategoryId());
            Category categoryBNails = categorySessionBeanLocal.createNewCategoryEntity(new Category("B Nails", "Gel Nail Services"), categoryNails.getCategoryId());
            Category categoryCNails = categorySessionBeanLocal.createNewCategoryEntity(new Category("C Nails", "Gel Nail Services"), categoryNails.getCategoryId());
            Category categoryHaircut = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hair cut", "Hait Cut Services"), categoryHair.getCategoryId());
            Category categoryMakeup = categorySessionBeanLocal.createNewCategoryEntity(new Category("Makeup", "Makeup Services"), categoryFace.getCategoryId());
            */
            
        } catch (CustomerExistException | UnknownPersistenceException | InputDataValidationException | CreateNewCategoryException | ParseException | ServiceProviderExistException | ServiceProviderNotFoundException
                | ProductExistException | CreateNewProductException | ServiceExistException | CreateNewServiceException ex) {
            ex.printStackTrace();
        }
    }

}
