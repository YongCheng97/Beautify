package ws.datamodel;

import entity.Booking;
import java.util.List;


public class RetrieveAllBookingsRsp {
    
    private List<Booking> bookings;

    
    
    public RetrieveAllBookingsRsp()
    {
    }
    
    
    
    public RetrieveAllBookingsRsp(List<Booking> bookings)
    {
        this.bookings = bookings;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

}
