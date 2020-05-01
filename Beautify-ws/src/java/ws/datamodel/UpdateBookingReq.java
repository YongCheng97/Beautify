package ws.datamodel;

import entity.Booking;


public class UpdateBookingReq
{
    private String username;
    private String password;
    private Booking booking;

    
    
    public UpdateBookingReq()
    {        
    }

    
    
    public UpdateBookingReq(String username, String password, Booking booking) 
    {
        this.username = username;
        this.password = password;
        this.booking = booking;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    
}