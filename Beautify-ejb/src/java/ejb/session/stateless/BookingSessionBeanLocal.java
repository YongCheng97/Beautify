package ejb.session.stateless;

import entity.Booking;
import javax.ejb.Local;
import util.exception.BookingNotFoundException;


public interface BookingSessionBeanLocal {

    public Booking retrieveBookingByBookingId(Long bookingId) throws BookingNotFoundException;
    
}
