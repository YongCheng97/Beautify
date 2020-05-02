
package jsf.managedbean;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.PromotionSessionBeanLocal;
import entity.Booking;
import entity.CreditCard;
import entity.Customer;
import entity.Item;
import entity.Promotion;
import entity.Service;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.PromotionNotFoundException;

@Named(value = "serviceBookingManagedBean")
@SessionScoped
public class ServiceBookingManagedBean implements Serializable {


    @EJB(name = "BookingSessionBeanLocal")
    private BookingSessionBeanLocal bookingSessionBeanLocal;
    
    @EJB
    private PromotionSessionBeanLocal promotionSessionBeanLocal;

    private Customer currentCustomer;
    private List<String> creditCards;
    private String creditCardNum;
    private String promoCode = null;
    
    private Service currentService;
    
    private Date appointmentDate;
    private String address;
    private Date startTime;
    private Date endTime;
    private String remarks;
    
    private BigDecimal finalAmount;
    
    private SimpleDateFormat sdf1;
    private SimpleDateFormat sdf2;
    private List<Booking> bookingListInDB;

    //FacesContext context = FacesContext.getCurrentInstance();
    String msg = null;
    /**
     * Creates a new instance of ServiceBookingManagedBean
     */
    public ServiceBookingManagedBean() {
        sdf1 = new SimpleDateFormat("HH:mm");
        this.bookingListInDB = new ArrayList<>();
            
    }
    
    @PostConstruct
    public void postConstruct() {
        this.bookingListInDB = bookingSessionBeanLocal.retrieveAllBookings();
    }
    
    public void directToCheckout(ActionEvent event) throws IOException {
        setCurrentCustomer((Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity"));
        List<String> list = new ArrayList<>();
        if (getCurrentCustomer() != null) {
            List<CreditCard> cards = getCurrentCustomer().getCreditCards();
            for (CreditCard cc : cards) {
                String num = cc.getCardNumber();
                list.add(num.replace(num.substring(0, 12), "XXXXXXXXXXXX"));
            }
        }
        
        setFinalAmount(currentService.getPrice());
        setCreditCards(list);

    }
    
    public void createNewBooking(ActionEvent event) throws IOException {
        System.out.println("Start time: "+ sdf1.format(this.startTime));
        System.out.println("End time: "+ sdf1.format(this.endTime));
        
        FacesContext context = FacesContext.getCurrentInstance();

        int index = sdf1.format(startTime).compareTo(sdf1.format(endTime));
        
        if(index < 0) {// The start time before end time
            
            //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
            boolean bookingExists = checkBookingExists();
            
            
            if(bookingExists == true) { //If new booking date and start time already existed in DB.
                msg = "The time slot has already been booked!";
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
            } else { 
                
            }
            
            
        } else if (index > 0) {
            msg = "The starting time cannot be after the end time";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
            
        } else {
            msg = "The starting time and ending time cannot be the same";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
            
        }
        
    }
    
    public void checkPromoCode(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            Promotion promotion = promotionSessionBeanLocal.retrievePromotionByPromoCode(promoCode);

            Boolean exists = false;

            List<Promotion> servicePromotions = currentService.getPromotions();
            if(servicePromotions.contains(promotion)) {
                exists = true;
            }

            Boolean valid = promotionSessionBeanLocal.checkPromoCode(promoCode); // promo is valid for this date

            if (valid && exists) {
                msg = "Promo Code Applied!";
                BigDecimal promoPrice = new BigDecimal("0.00");

                promoPrice = (currentService.getPrice().multiply(promotion.getDiscountRate())).setScale(2, BigDecimal.ROUND_HALF_UP);

                setFinalAmount(promoPrice);

            } else {
                msg = "Invalid Promo Code!";
            }

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
        } catch (PromotionNotFoundException ex) {
            Logger.getLogger(ShoppingCartManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean checkBookingExists() {
        for (Booking booking : bookingListInDB) {
            if(this.appointmentDate.equals(booking.getDateOfAppointment()) && this.startTime.equals(booking.getStartTime())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @return the currentService
     */
    public Service getCurrentService() {
        return currentService;
    }

    /**
     * @param currentService the currentService to set
     */
    public void setCurrentService(Service currentService) {
        this.currentService = currentService;
    }

    /**
     * @return the bookingDate
     */
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * @param appointmentDate the bookingDate to set
     */
    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    
    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public TimeZone getTimeZone() {
        TimeZone timeZone = TimeZone.getDefault();
        return timeZone;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the currentCustomer
     */
    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    /**
     * @param currentCustomer the currentCustomer to set
     */
    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }
    
    public List<String> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<String> creditCards) {
        this.creditCards = creditCards;
    }

    public String getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(String creditCardNum) {
        this.creditCardNum = creditCardNum;
    }
    
    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    /**
     * @return the finalAmount
     */
    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    /**
     * @param finalAmount the finalAmount to set
     */
    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }
}
