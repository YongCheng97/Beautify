package ws.datamodel;

import entity.Booking;



public class RetrieveBookingRsp
{
    private Booking booking;


    public RetrieveBookingRsp()
    {
    }

    public RetrieveBookingRsp(Booking booking)
    {
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    
}