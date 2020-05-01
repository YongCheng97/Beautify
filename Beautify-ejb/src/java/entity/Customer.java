package entity;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.security.CryptographicHelper;

@Entity

public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String firstName;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String lastName;
    @Column(nullable = false, unique = true, length = 64)
    @NotNull
    @Size(max = 64)
    @Email
    private String email;
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    @NotNull
    private String password;
    @Column(unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String username;
    @Column(nullable = false)
    @NotNull
    @Min(8)
    private Long contactNum;

    @Column(columnDefinition = "CHAR(32) NOT NULL")
    private String salt;

    @OneToMany(mappedBy = "customer")
    private List<CreditCard> creditCards;
    
    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;
    
    @ManyToMany(mappedBy = "favouritedCustomers")
    private List<Service> favouriteServices;
    
    @ManyToMany(mappedBy = "favouritedCustomers")
    private List<Product> favouriteProducts;
    
    @OneToMany(mappedBy = "customer")
    private List<Review> reviews;
    
    @OneToMany(mappedBy = "customer")
    private List<Purchased> purchaseds;

    public Customer() {
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
        
        creditCards = new ArrayList<>();
        reviews = new ArrayList<>();
        bookings = new ArrayList<>();
        favouriteServices = new ArrayList<>();
        favouriteProducts = new ArrayList<>();
        purchaseds = new ArrayList<>();
    }

    public Customer(String firstName, String lastName, String email, String username, String password, Long contactNum) {
        this();
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.contactNum = contactNum;

        setPassword(password);
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the customerId fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + customerId + " ]";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null) {
            this.password = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + this.salt));
        } else {
            this.password = null;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getContactNum() {
        return contactNum;
    }

    public void setContactNum(Long contactNum) {
        this.contactNum = contactNum;
    }

    public String getSalt() {
        return salt;
    }

    // Newly added in v4.5
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return the creditCards
     */
    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    /**
     * @param creditCards the creditCards to set
     */
    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    /**
     * @return the reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * @param reviews the reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * @return the bookings
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * @param bookings the bookings to set
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Service> getFavouriteServices() {
        return favouriteServices;
    }

    public void setFavouriteServices(List<Service> favouriteServices) {
        this.favouriteServices = favouriteServices;
    }

    public List<Product> getFavouriteProducts() {
        return favouriteProducts;
    }

    public void setFavouriteProducts(List<Product> favouriteProducts) {
        this.favouriteProducts = favouriteProducts;
    }

    public List<Purchased> getPurchaseds() {
        return purchaseds;
    }

    public void setPurchaseds(List<Purchased> purchaseds) {
        this.purchaseds = purchaseds;
    }

}
