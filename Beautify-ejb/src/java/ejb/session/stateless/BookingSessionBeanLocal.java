package ejb.session.stateless;

import entity.Booking;
import java.util.List;
import javax.ejb.Local;
import util.exception.BookingExistException;
import util.exception.BookingNotFoundException;
import util.exception.CreateNewBookingException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;


public interface BookingSessionBeanLocal {

    public Booking retrieveBookingByBookingId(Long bookingId) throws BookingNotFoundException;

    public List<Booking> retrieveAllBookings();

    public Booking createNewBooking(Booking newBooking, Long customerId, Long serviceId) throws BookingExistException, UnknownPersistenceException, InputDataValidationException, CreateNewBookingException, CustomerNotFoundException;

    public List<Booking> retrieveAllBookingsByCustomerId(Long customerId);
    
}
