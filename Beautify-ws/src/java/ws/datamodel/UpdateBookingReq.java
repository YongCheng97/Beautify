package ws.datamodel;

import entity.Booking;


public class UpdateBookingReq
{
    private String username;
    private String password;
    private Long bookingId;
    private String status;

    
    
    public UpdateBookingReq()
    {        
    }

    
    
    public UpdateBookingReq(String username, String password, Long bookingId) 
    {
        this.username = username;
        this.password = password;
        this.bookingId = bookingId;
        this.status = status;
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

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}