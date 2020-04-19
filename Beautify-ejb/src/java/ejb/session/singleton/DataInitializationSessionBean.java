/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import entity.Booking;
import entity.Category;
import entity.Customer;
import entity.Product;
import entity.Review;
import entity.Service;
import entity.ServiceProvider;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.BookingExistException;
import util.exception.CreateNewBookingException;
import util.enumeration.CategoryTypeEnum;
import util.exception.CreateNewCategoryException;
import util.exception.CreateNewProductException;
import util.exception.CreateNewReviewException;
import util.exception.CreateNewServiceException;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.ProductExistException;
import util.exception.ReviewExistException;
import util.exception.ServiceExistException;
import util.exception.ServiceProviderExistException;
import util.exception.ServiceProviderNotFoundException;
import util.exception.UnknownPersistenceException;

@Singleton
@LocalBean
@Startup

public class DataInitializationSessionBean {

    @EJB(name = "BookingSessionBeanLocal")
    private BookingSessionBeanLocal bookingSessionBeanLocal;

    @EJB(name = "ReviewSessionBeanLocal")
    private ReviewSessionBeanLocal reviewSessionBeanLocal;

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
    
    private CategoryTypeEnum type;

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
            Customer customer1 = customerSessionBeanLocal.retrieveCustomerByCustId(customerSessionBeanLocal.createNewCustomer(new Customer("Bob", "Lim", "boblim@gmail.com", "boblim", "password", Long.parseLong("98023457"))));
            Customer customer2 = customerSessionBeanLocal.retrieveCustomerByCustId(customerSessionBeanLocal.createNewCustomer(new Customer("Mary", "Tan", "marytan@gmail.com", "marytan", "password", Long.parseLong("95634137")))); 

            Category categoryNails = categorySessionBeanLocal.createNewCategoryEntity(new Category("Nails", "Nail Services and Products"), null);
            Category categoryHair = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hair", "Hair Services and Products"), null);
            Category categoryFace = categorySessionBeanLocal.createNewCategoryEntity(new Category("Face", "Facial Services and Products"), null);

            Category categoryManicure = categorySessionBeanLocal.createNewCategoryEntity(new Category("Manicure", "Manicure Services", type.SERVICE), categoryNails.getCategoryId());
            Category categoryPedicure = categorySessionBeanLocal.createNewCategoryEntity(new Category("Pedicure", "Pedicure Services", type.SERVICE), categoryNails.getCategoryId());
            Category categoryNailPolish = categorySessionBeanLocal.createNewCategoryEntity(new Category("Nail Polish", "Nail Polish Products", type.PRODUCT), categoryNails.getCategoryId());
            Category categoryHaircut = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hair Cut", "Hair Cut Services", type.SERVICE), categoryHair.getCategoryId());
            Category categoryHairColour = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hair Colouring", "Hair Colour Services", type.SERVICE), categoryHair.getCategoryId());
            Category categoryHairProducts = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hair Products", "Hair Products", type.PRODUCT), categoryHair.getCategoryId());
            Category categoryMakeover = categorySessionBeanLocal.createNewCategoryEntity(new Category("Makeover", "Makeup Services", type.SERVICE), categoryFace.getCategoryId());
            Category categoryFacial = categorySessionBeanLocal.createNewCategoryEntity(new Category("Facial", "Facial Services", type.SERVICE), categoryFace.getCategoryId());
            Category categoryFaceCare = categorySessionBeanLocal.createNewCategoryEntity(new Category("Face Care", "Face Care Products", type.PRODUCT), categoryFace.getCategoryId());
            Category categoryMakeup = categorySessionBeanLocal.createNewCategoryEntity(new Category("Makeup", "Makeup Products", type.PRODUCT), categoryFace.getCategoryId());

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            ServiceProvider provider1 = serviceProviderSessionBeanLocal.retrieveServiceProviderById(serviceProviderSessionBeanLocal.createNewServiceProvider(new ServiceProvider("The Nail Lounge", "thenaillounge@gmail.com", "password", "123 Hougang Road",
                    sdf.parse("09:00:00"), sdf.parse("17:00:00"), null, true)));
            ServiceProvider provider2 = serviceProviderSessionBeanLocal.retrieveServiceProviderById(serviceProviderSessionBeanLocal.createNewServiceProvider(new ServiceProvider("The Beautiful Hair Salon", "thebeautifulhairsalon@gmail.com", "password", "3 Yishun Drive",
                    sdf.parse("09:00:00"), sdf.parse("17:00:00"), null, true)));
            ServiceProvider provider3 = serviceProviderSessionBeanLocal.retrieveServiceProviderById(serviceProviderSessionBeanLocal.createNewServiceProvider(new ServiceProvider("The Makeup Place", "themakeupplace@gmail.com", "password", "56 Serangoon Way",
                    sdf.parse("09:00:00"), sdf.parse("17:00:00"), null, true)));

            Product redPolish = productSessionBeanLocal.createNewProduct(new Product("PROD001", "Red Nail Polish", new BigDecimal("20.00"), "Red nail polish is historically bold, daring, and adventurous", null),
                    categoryNailPolish.getCategoryId(), provider1.getServiceProviderId());
            Product yellowPolish = productSessionBeanLocal.createNewProduct(new Product("PROD002", "Yellow Nail Polish", new BigDecimal("20.00"), "Bright and cheery yellow nail polish", null),
                    categoryNailPolish.getCategoryId(), provider1.getServiceProviderId());

            Product shampoo = productSessionBeanLocal.createNewProduct(new Product("PROD003", "Shampoo", new BigDecimal("18.00"), "Cleanse your scalp and leave your hair healthy and smooth", null),
                    categoryHairProducts.getCategoryId(), provider2.getServiceProviderId());
            Product hairTreatment = productSessionBeanLocal.createNewProduct(new Product("PROD004", "Hair Treatment", new BigDecimal("18.00"), "Transform your dry and damaged hair", null),
                    categoryHairProducts.getCategoryId(), provider2.getServiceProviderId());

            Product lipstick = productSessionBeanLocal.createNewProduct(new Product("PROD005", "Lipstick", new BigDecimal("13.00"), "Hydrating lipstick that adds colour to your lips", null),
                    categoryMakeup.getCategoryId(), provider3.getServiceProviderId());
            Product facewash = productSessionBeanLocal.createNewProduct(new Product("PROD006", "Facewash", new BigDecimal("15.00"), "Facewash that revitalises and cleanses your skin", null),
                    categoryFaceCare.getCategoryId(), provider3.getServiceProviderId());

            Service manicure = serviceSessionBeanLocal.createNewService(new Service("Gel Manicure", new BigDecimal("80.00"), "Gels last longer and feels stronger", null), provider1.getServiceProviderId(), categoryManicure.getCategoryId());
            Service haircut = serviceSessionBeanLocal.createNewService(new Service("Express Hair Cut", new BigDecimal("30.00"), "Express Hair Cut with no washing included", null), provider2.getServiceProviderId(), categoryHaircut.getCategoryId());
            Service facial = serviceSessionBeanLocal.createNewService(new Service("Facial", new BigDecimal("80.00"), "The best and most relaxing Facial Treatement", null), provider3.getServiceProviderId(), categoryFacial.getCategoryId());

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yy HH:mm");

            Booking booking1 = bookingSessionBeanLocal.createNewBooking(new Booking(sdf1.parse("01/04/2020 12:00"), "Completed", "remarks", sdf1.parse("03/04/2020 00:00"), new Time(12, 0, 0), new Time(13, 0, 0)), customer1.getCustomerId(), manicure.getServiceId());
            Booking booking2 = bookingSessionBeanLocal.createNewBooking(new Booking(sdf1.parse("02/04/2020 12:00"), "Approved", "remarks", sdf1.parse("20/04/2020 00:00"), new Time(12, 0, 0), new Time(13, 0, 0)), customer1.getCustomerId(), haircut.getServiceId());

            Review review1 = reviewSessionBeanLocal.createNewServiceReview(new Review(5, "Very good service", null), customer1.getCustomerId(), manicure.getServiceId());
            Review review2 = reviewSessionBeanLocal.createNewServiceReview(new Review(5, "Excellent hair cut!", null), customer2.getCustomerId(), haircut.getServiceId()); 

            /*
            Category categoryGelNails = categorySessionBeanLocal.createNewCategoryEntity(new Category("Gel Nails", "Gel Nail Services"), categoryNails.getCategoryId());
            Category categoryANails = categorySessionBeanLocal.createNewCategoryEntity(new Category("A Nails", "Gel Nail Services"), categoryNails.getCategoryId());
            Category categoryBNails = categorySessionBeanLocal.createNewCategoryEntity(new Category("B Nails", "Gel Nail Services"), categoryNails.getCategoryId());
            Category categoryCNails = categorySessionBeanLocal.createNewCategoryEntity(new Category("C Nails", "Gel Nail Services"), categoryNails.getCategoryId());
            Category categoryHaircut = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hair cut", "Hait Cut Services"), categoryHair.getCategoryId());
            Category categoryMakeup = categorySessionBeanLocal.createNewCategoryEntity(new Category("Makeup", "Makeup Services"), categoryFace.getCategoryId());
             */
        } catch (CustomerExistException | UnknownPersistenceException | InputDataValidationException | CreateNewCategoryException | ParseException | ServiceProviderExistException | ServiceProviderNotFoundException
                | ProductExistException | CreateNewProductException | ServiceExistException | CreateNewServiceException | CustomerNotFoundException | BookingExistException | CreateNewBookingException | ReviewExistException | CreateNewReviewException ex) {
            ex.printStackTrace();
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
