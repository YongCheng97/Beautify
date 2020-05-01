package entity;

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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(nullable = false, unique = true, length = 7)
    @NotNull
    @Size(min = 7, max = 7)
    private String skuCode;
    @Column(nullable = false, length = 64)
    @NotNull
    @Size(max = 64)
    private String name;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price;
    @Column(length = 128)
    @Size(max = 128)
    private String description;
    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer quantityOnHand;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Category category;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private ServiceProvider serviceProvider;

    @ManyToMany
    private List<Customer> favouritedCustomers;

    @ManyToMany
    private List<Tag> tags;

    @OneToMany
    private List<Promotion> promotions;

    public Product() {
        this.price = new BigDecimal("0.00");
        this.quantityOnHand = 0;
        this.tags = new ArrayList<>();
        this.favouritedCustomers = new ArrayList<>();
        this.promotions = new ArrayList<>();
    }

    public Product(String skuCode, String name, BigDecimal price, String description, Integer quantityOnHand) {
        this();
        this.skuCode = skuCode;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantityOnHand = quantityOnHand;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the productId fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Product[ id=" + productId + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        if (this.category != null) {
            if (this.category.getProducts().contains(this)) {
                this.category.getProducts().remove(this);
            }
        }

        this.category = category;

        if (this.category != null) {
            if (!this.category.getProducts().contains(this)) {
                this.category.getProducts().add(this);
            }
        }
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
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

                if (!tag.getProducts().contains(this)) {
                    tag.getProducts().add(this);
                }

            }
        }
    }

    public void removeTag(Tag tag) {
        if (tag != null) {
            if (this.tags.contains(tag)) {
                this.tags.remove(tag);
                if (tag.getProducts().contains(this)) {
                    tag.getProducts().remove(this);
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

    public List<Customer> getFavouritedCustomers() {
        return favouritedCustomers;
    }

    public void setFavouritedCustomers(List<Customer> favouritedCustomers) {
        this.favouritedCustomers = favouritedCustomers;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

}
