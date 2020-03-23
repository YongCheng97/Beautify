package ejb.session.stateless;

import entity.Booking;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.BookingExistException;
import util.exception.BookingNotFoundException;
import util.exception.CreateNewBookingException;

import util.exception.InputDataValidationException;

import util.exception.UnknownPersistenceException;

@Stateless
@Local(BookingSessionBeanLocal.class)
public class BookingSessionBean implements BookingSessionBeanLocal {

    @PersistenceContext(unitName = "Beautify-ejbPU")
    private EntityManager em;
    
    private final ValidatorFactory validatorFactory;
    private final Validator validator;
    
    public BookingSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    public Booking createNewBooking(Booking booking, Long customerId, Long servicesId) throws BookingExistException, UnknownPersistenceException, InputDataValidationException, CreateNewBookingException {
        
    }

    
    @Override
    public Booking retrieveBookingByBookingId(Long bookingId) throws BookingNotFoundException {
        Booking booking = em.find(Booking.class, bookingId);

        if (booking != null) {
            booking.getCustomer();
            booking.getService();
            
            return booking;
        } else {
            throw new BookingNotFoundException("Booking ID " + bookingId + " does not exist!");
        }
    }
}
