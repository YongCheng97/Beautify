/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String serviceName;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price;
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String description;
    private File photo;
    private Boolean isDeleted;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private ServiceProvider serviceProvider;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Category category;

    @ManyToMany
    private List<Tag> tags;

    @OneToMany
    private List<Promotion> promotions;

    @OneToMany(mappedBy = "service")
    private List<Booking> bookings;

    @ManyToMany
    private List<Customer> favouritedCustomers;

    public Service() {
        this.tags = new ArrayList<>();
        this.promotions = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.favouritedCustomers = new ArrayList<>();
        this.isDeleted = false;
    }

    public Service(String serviceName, BigDecimal price, String description, File photo) {
        this.serviceName = serviceName;
        this.price = price;
        this.description = description;
        this.photo = photo;
        this.isDeleted = false;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serviceId != null ? serviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the serviceId fields are not set
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.serviceId == null && other.serviceId != null) || (this.serviceId != null && !this.serviceId.equals(other.serviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Services[ id=" + serviceId + " ]";
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        if (tag != null) {
            if (!this.tags.contains(tag)) {
                this.tags.add(tag);

                if (!tag.getServices().contains(this)) {
                    tag.getServices().add(this);
                }

            }
        }
    }

    public void removeTag(Tag tag) {
        if (tag != null) {
            if (this.tags.contains(tag)) {
                this.tags.remove(tag);
                if (tag.getServices().contains(this)) {
                    tag.getServices().remove(this);
                }
            }
        }
    }

    public void addPromotion(Promotion promotion) {
        if (promotion != null) {
            if (!this.promotions.contains(promotion)) {
                this.promotions.add(promotion);
            }
        }
    }

    public void removePromotion(Promotion promotion) {
        if (promotion != null) {
            if (this.promotions.contains(promotion)) {
                this.promotions.remove(promotion);
            }
        }
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Customer> getFavouritedCustomers() {
        return favouritedCustomers;
    }

    public void setFavouritedCustomers(List<Customer> favouritedCustomers) {
        this.favouritedCustomers = favouritedCustomers;
    }

    /**
     * @return the isDeleted
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


}
