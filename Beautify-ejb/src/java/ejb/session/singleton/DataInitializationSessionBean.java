/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.CreditCardSessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.PromotionSessionBeanLocal;
import ejb.session.stateless.PurchasedLineItemSessionBeanLocal;
import ejb.session.stateless.PurchasedSessionBeanLocal;
import ejb.session.stateless.ReviewSessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import ejb.session.stateless.TagsSessionBeanLocal;
import entity.Booking;
import entity.Category;
import entity.CreditCard;
import entity.Customer;
import entity.Product;
import entity.Promotion;
import entity.Purchased;
import entity.PurchasedLineItem;
import entity.Review;
import entity.Service;
import entity.ServiceProvider;
import entity.Tag;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import util.exception.CreateNewCreditCardException;
import util.exception.CreateNewProductException;
import util.exception.CreateNewPurchaseException;
import util.exception.CreateNewPurchasedLineItemException;
import util.exception.CreateNewReviewException;
import util.exception.CreateNewServiceException;
import util.exception.CreateNewTagException;
import util.exception.CreditCardExistsException;
import util.exception.CustomerExistException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.ProductExistException;
import util.exception.PromotionNameExistException;
import util.exception.PurchasedExistException;
import util.exception.PurchasedLineItemExistException;
import util.exception.ReviewExistException;
import util.exception.ServiceExistException;
import util.exception.ServiceProviderExistException;
import util.exception.ServiceProviderNotFoundException;
import util.exception.UnknownPersistenceException;

@Singleton
@LocalBean
@Startup

public class DataInitializationSessionBean {

    @EJB(name = "PurchasedSessionBeanLocal")
    private PurchasedSessionBeanLocal purchasedSessionBean;

    @EJB(name = "PurchasedLineItemSessionBeanLocal")
    private PurchasedLineItemSessionBeanLocal purchasedLineItemSessionBean;

    @EJB(name = "PromotionSessionBeanLocal")
    private PromotionSessionBeanLocal promotionSessionBeanLocal;

    @EJB(name = "CreditCardSessionBeanLocal")
    private CreditCardSessionBeanLocal creditCardSessionBeanLocal;

    @EJB(name = "TagsSessionBeanLocal")
    private TagsSessionBeanLocal tagsSessionBeanLocal;

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
            // customer 
            Customer customer1 = customerSessionBeanLocal.retrieveCustomerByCustId(customerSessionBeanLocal.createNewCustomer(new Customer("Bob", "Lim", "boblim@gmail.com", "boblim", "password", Long.parseLong("98023457"))));
            Customer customer2 = customerSessionBeanLocal.retrieveCustomerByCustId(customerSessionBeanLocal.createNewCustomer(new Customer("Mary", "Tan", "marytan@gmail.com", "marytan", "password", Long.parseLong("95634137"))));

            // parent category
            Category categoryNails = categorySessionBeanLocal.createNewCategoryEntity(new Category("Nails", "Nail Services and Products"), null);
            Category categoryHair = categorySessionBeanLocal.createNewCategoryEntity(new Category("Hair", "Hair Services and Products"), null);
            Category categoryFace = categorySessionBeanLocal.createNewCategoryEntity(new Category("Face", "Facial Services and Products"), null);

            // leaf categories
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

            // service providers 
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date[] openingHours = {sdf.parse("09:00"), sdf.parse("09:00"), sdf.parse("09:00"), sdf.parse("09:00"),sdf.parse("09:00"), sdf.parse("09:00"),sdf.parse("09:00")};
            Date[] closingHours = {sdf.parse("17:00"), sdf.parse("17:00"), sdf.parse("17:00"), sdf.parse("17:00"), sdf.parse("17:00"), sdf.parse("17:00"), sdf.parse("17:00")};
            ServiceProvider provider1 = serviceProviderSessionBeanLocal.retrieveServiceProviderById(serviceProviderSessionBeanLocal.createNewServiceProvider(new ServiceProvider("The Nail Lounge", "thenaillounge@gmail.com", "password", "123 Hougang Road",
                    openingHours, closingHours, null, true)));
            ServiceProvider provider2 = serviceProviderSessionBeanLocal.retrieveServiceProviderById(serviceProviderSessionBeanLocal.createNewServiceProvider(new ServiceProvider("The Beautiful Hair Salon", "thebeautifulhairsalon@gmail.com", "password", "3 Yishun Drive",
                    openingHours, closingHours, null, true)));
            ServiceProvider provider3 = serviceProviderSessionBeanLocal.retrieveServiceProviderById(serviceProviderSessionBeanLocal.createNewServiceProvider(new ServiceProvider("The Makeup Place", "themakeupplace@gmail.com", "password", "56 Serangoon Way",
                    openingHours, closingHours, null, true)));

            // tags
            Tag tag1 = tagsSessionBeanLocal.createNewTagEntity(new Tag("New"));
            Tag tag2 = tagsSessionBeanLocal.createNewTagEntity(new Tag("Popular"));
            Tag tag3 = tagsSessionBeanLocal.createNewTagEntity(new Tag("Discount"));

            List<Long> tagIdsNew = new ArrayList<>();
            tagIdsNew.add(tag1.getTagId());

            List<Long> tagIdsPopular = new ArrayList<>();
            tagIdsPopular.add(tag2.getTagId());

            List<Long> tagIdsDiscount = new ArrayList<>();
            tagIdsDiscount.add(tag3.getTagId());

            List<Long> tagIdsEmpty = new ArrayList<>();

            // products 
            Product redPolish = productSessionBeanLocal.createNewProduct(new Product("PROD001", "Red Nail Polish", new BigDecimal("20.00"), "Red nail polish is historically bold, daring, and adventurous", 100, null),
                    categoryNailPolish.getCategoryId(), provider1.getServiceProviderId(), tagIdsNew);
            Product yellowPolish = productSessionBeanLocal.createNewProduct(new Product("PROD002", "Yellow Nail Polish", new BigDecimal("20.00"), "Bright and cheery yellow nail polish", 100, null),
                    categoryNailPolish.getCategoryId(), provider1.getServiceProviderId(), tagIdsNew);

            Product shampoo = productSessionBeanLocal.createNewProduct(new Product("PROD003", "Shampoo", new BigDecimal("18.00"), "Cleanse your scalp and leave your hair healthy and smooth", 100, null),
                    categoryHairProducts.getCategoryId(), provider2.getServiceProviderId(), tagIdsEmpty);
            Product hairTreatment = productSessionBeanLocal.createNewProduct(new Product("PROD004", "Hair Treatment", new BigDecimal("18.00"), "Transform your dry and damaged hair", 100, null),
                    categoryHairProducts.getCategoryId(), provider2.getServiceProviderId(), tagIdsDiscount);

            Product lipstick = productSessionBeanLocal.createNewProduct(new Product("PROD005", "Lipstick", new BigDecimal("13.00"), "Hydrating lipstick that adds colour to your lips", 100, null),
                    categoryMakeup.getCategoryId(), provider3.getServiceProviderId(), tagIdsDiscount);
            Product facewash = productSessionBeanLocal.createNewProduct(new Product("PROD006", "Facewash", new BigDecimal("15.00"), "Facewash that revitalises and cleanses your skin", 100, null),
                    categoryFaceCare.getCategoryId(), provider3.getServiceProviderId(), tagIdsPopular);

            // services 
            Service manicure = serviceSessionBeanLocal.createNewService(new Service("Gel Manicure", new BigDecimal("80.00"), "Gels last longer and feels stronger", null, null), provider1.getServiceProviderId(), categoryManicure.getCategoryId(), tagIdsEmpty);
            Service haircut = serviceSessionBeanLocal.createNewService(new Service("Express Hair Cut", new BigDecimal("30.00"), "Express Hair Cut with no washing included", null, null), provider2.getServiceProviderId(), categoryHaircut.getCategoryId(), tagIdsEmpty);
            Service facial = serviceSessionBeanLocal.createNewService(new Service("Facial", new BigDecimal("80.00"), "The best and most relaxing Facial Treatment", null, null), provider3.getServiceProviderId(), categoryFacial.getCategoryId(), tagIdsEmpty);

            // bookings 
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yy HH:mm");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");

            Booking booking1 = bookingSessionBeanLocal.createNewBooking(new Booking(sdf1.parse("01/04/2020 12:00"), "Completed", "remarks", sdf2.parse("03/04/2020"), sdf.parse("12:00"), sdf.parse("13:00")), customer1.getCustomerId(), manicure.getServiceId());
            Booking booking2 = bookingSessionBeanLocal.createNewBooking(new Booking(sdf1.parse("02/04/2020 12:00"), "Completed", "remarks", sdf2.parse("20/04/2020"), sdf.parse("12:00"), sdf.parse("13:00")), customer1.getCustomerId(), haircut.getServiceId());
            Booking booking3 = bookingSessionBeanLocal.createNewBooking(new Booking(sdf1.parse("03/04/2020 12:00"), "Approved", "remarks", sdf2.parse("15/04/2020"), sdf.parse("12:00"), sdf.parse("13:00")), customer1.getCustomerId(), facial.getServiceId());

            // service reviews
            Review review1 = reviewSessionBeanLocal.createNewServiceReview(new Review(5, "Very good service", null), customer1.getCustomerId(), manicure.getServiceId());
            Review review2 = reviewSessionBeanLocal.createNewServiceReview(new Review(5, "Excellent hair cut!", null), customer1.getCustomerId(), haircut.getServiceId());

            // promotion

            Promotion promotion1 = promotionSessionBeanLocal.createNewPromotion(new Promotion("10OFF", "10% off", new BigDecimal("00.90"), sdf2.parse("01/04/2020"), sdf2.parse("30/5/2020")));
            Promotion promotion2 = promotionSessionBeanLocal.createNewPromotion(new Promotion("30OFF", "30% off", new BigDecimal("00.70"), sdf2.parse("10/04/2020"), sdf2.parse("19/5/2020")));
            redPolish.addPromotion(promotion1);
            facial.addPromotion(promotion2);

            // credit cards
            CreditCard creditCard1 = creditCardSessionBeanLocal.createNewCreditCardEntityForCustomer(new CreditCard("VISA", "Bob Lim", "4024007176761897", "02/23"), customer1.getCustomerId());
            CreditCard creditCard2 = creditCardSessionBeanLocal.createNewCreditCardEntityForCustomer(new CreditCard("MasterCard", "Bob Lim", "5381253539232416", "06/23"), customer1.getCustomerId());
            CreditCard creditCard3 = creditCardSessionBeanLocal.createNewCreditCardEntityForServiceProvider(new CreditCard("VISA", "The Nail Lounge", "4929954364297059", "06/24"), provider1.getServiceProviderId());
            CreditCard creditCard4 = creditCardSessionBeanLocal.createNewCreditCardEntityForServiceProvider(new CreditCard("MasterCard", "The Nail Lounge", "5288773275293797", "01/22"), provider1.getServiceProviderId());

            //purchased line items
            PurchasedLineItem purchasedLineItem1 = purchasedLineItemSessionBean.createNewPurchasedLineItem(new PurchasedLineItem(1, "Shipped", new BigDecimal("20.00")), redPolish.getProductId());
            PurchasedLineItem purchasedLineItem2 = purchasedLineItemSessionBean.createNewPurchasedLineItem(new PurchasedLineItem(2, "Shipped", new BigDecimal("20.00")), yellowPolish.getProductId());
            PurchasedLineItem purchasedLineItem3 = purchasedLineItemSessionBean.createNewPurchasedLineItem(new PurchasedLineItem(1, "Order Confirmed", new BigDecimal("18.00")), shampoo.getProductId());
            PurchasedLineItem purchasedLineItem4 = purchasedLineItemSessionBean.createNewPurchasedLineItem(new PurchasedLineItem(2, "Product Received", new BigDecimal("13.00")), lipstick.getProductId());
            
            //purchased
            Purchased purchased1 = purchasedSessionBean.createNewPurchased(new Purchased(sdf2.parse("20/04/2020"), new BigDecimal("60.00"), "address"), customer1.getCustomerId(), Arrays.asList(purchasedLineItem1.getPurchasedLineItemId(), purchasedLineItem2.getPurchasedLineItemId()), creditCard1.getCreditCardId());
            Purchased purchased2 = purchasedSessionBean.createNewPurchased(new Purchased(sdf2.parse("21/04/2020"), new BigDecimal("44.00"), "address"), customer1.getCustomerId(), Arrays.asList(purchasedLineItem3.getPurchasedLineItemId(), purchasedLineItem4.getPurchasedLineItemId()), creditCard2.getCreditCardId());
       
            //favourite products
            customer1.getFavouriteProducts().add(redPolish);
            customer1.getFavouriteProducts().add(shampoo);
            customer1.getFavouriteProducts().add(lipstick);
            redPolish.getFavouritedCustomers().add(customer1);
            shampoo.getFavouritedCustomers().add(customer1);
            lipstick.getFavouritedCustomers().add(customer1);
            
            //favourite services
            customer1.getFavouriteServices().add(manicure);
            customer1.getFavouriteServices().add(haircut);
            manicure.getFavouritedCustomers().add(customer1);
            haircut.getFavouritedCustomers().add(customer1);
            
        } catch (CustomerExistException | UnknownPersistenceException | InputDataValidationException | CreateNewCategoryException | ParseException | ServiceProviderExistException | ServiceProviderNotFoundException
                | ProductExistException | CreateNewProductException | ServiceExistException | CreateNewServiceException | CustomerNotFoundException | BookingExistException | CreateNewBookingException | ReviewExistException | CreateNewReviewException
                | CreateNewTagException | CreateNewCreditCardException | CreditCardExistsException | PromotionNameExistException | CreateNewPurchaseException | CreateNewPurchasedLineItemException | PurchasedExistException | PurchasedLineItemExistException ex) {
            ex.printStackTrace();
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }

}
