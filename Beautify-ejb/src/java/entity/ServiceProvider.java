package entity;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.security.CryptographicHelper;

@Entity
public class ServiceProvider implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceProviderId;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String name;
    @Column(nullable = false, unique = true, length = 64)
    @NotNull
    @Size(max = 64)
    @Email
    private String email;
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    @NotNull
    private String password;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String address;
    @Column(nullable = false)
    @NotNull
    private Date[] openingHours;
    @Column(nullable = false)
    @NotNull
    private Date[] closingHours;
    private boolean isApproved;
    @Column(unique = true, nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String username;

    @Column(columnDefinition = "CHAR(32) NOT NULL")
    private String salt;

    @OneToMany(mappedBy = "serviceProvider")
    private List<CreditCard> creditCards;

    @OneToMany(mappedBy = "serviceProvider")
    private List<Product> products;

    @OneToMany(mappedBy = "serviceProvider")
    private List<Service> services;

    @OneToMany(mappedBy = "serviceProvider")
    private List<Promotion> promotions;

    public ServiceProvider() {
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);

        openingHours = new Date[7];
        closingHours = new Date[7];
        creditCards = new ArrayList<>();
        products = new ArrayList<>();
        services = new ArrayList<>();
        promotions = new ArrayList<>();
    }

    public ServiceProvider(String name, String email, String password, String address, Date[] openingHours, Date[] closingHours, boolean isApproved, String username) {

        this();

        this.name = name;
        this.email = email;
        this.address = address;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.isApproved = isApproved;
        this.username = username;

        setPassword(password);
    }

    public Long getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceProviderId != null ? serviceProviderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the serviceProviderId fields are not set
        if (!(object instanceof ServiceProvider)) {
            return false;
        }
        ServiceProvider other = (ServiceProvider) object;
        if ((this.serviceProviderId == null && other.serviceProviderId != null) || (this.serviceProviderId != null && !this.serviceProviderId.equals(other.serviceProviderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ServiceProvider[ id=" + serviceProviderId + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date[] getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(Date[] openingHours) {
        this.openingHours = openingHours;
    }

    public boolean isIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
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
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    // Newly added in v4.5

    public String getSalt() {
        return salt;
    }

    // Newly added in v4.5
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    /**
     * @return the closingHours
     */
    public Date[] getClosingHours() {
        return closingHours;
    }

    /**
     * @param closingHours the closingHours to set
     */
    public void setClosingHours(Date[] closingHours) {
        this.closingHours = closingHours;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addPromotion(Promotion promotion) {
        if (promotion != null) {
            if (!this.promotions.contains(promotion)) {
                this.getPromotions().add(promotion);
            }
        }
    }

    public void removePromotion(Promotion promotion) {
        if (promotion != null) {
            if (this.getPromotions().contains(promotion)) {
                this.getPromotions().remove(promotion);
            }
        }
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

}
