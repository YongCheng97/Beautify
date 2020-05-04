package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Promotion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionId;
    @Column(nullable = false, unique = true)
    @NotNull
    @Size(min = 5, max = 10)
    private String promoCode;
    @Column(nullable = false, length = 64)
    @NotNull
    @Size(max = 64)
    private String name;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal discountRate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date endDate;
    
    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private Service service; 
    
    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private Product product; 
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = true)
    private ServiceProvider serviceProvider; 

    public Promotion() {
    }

    public Promotion(String promoCode, String name, BigDecimal discountRate, Date startDate, Date endDate) {
        this.promoCode = promoCode;
        this.name = name;
        this.discountRate = discountRate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promotionId != null ? promotionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the promotionId fields are not set
        if (!(object instanceof Promotion)) {
            return false;
        }
        Promotion other = (Promotion) object;
        if ((this.promotionId == null && other.promotionId != null) || (this.promotionId != null && !this.promotionId.equals(other.promotionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Promotion[ id=" + promotionId + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the promoCode
     */
    public String getPromoCode() {
        return promoCode;
    }

    /**
     * @param promoCode the promoCode to set
     */
    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
    
}
