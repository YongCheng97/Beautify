package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Entity
public class SalesRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesRecordId;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal amount;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dateOfPayment;
    
    public SalesRecord() {
        
    }

    public SalesRecord(BigDecimal amount, Date dateOfPayment) {
        this.amount = amount;
        this.dateOfPayment = dateOfPayment;
    }
    
    

    public Long getSalesRecordId() {
        return salesRecordId;
    }

    public void setSalesRecordId(Long salesRecordId) {
        this.salesRecordId = salesRecordId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (salesRecordId != null ? salesRecordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalesRecord)) {
            return false;
        }
        SalesRecord other = (SalesRecord) object;
        if ((this.salesRecordId == null && other.salesRecordId != null) || (this.salesRecordId != null && !this.salesRecordId.equals(other.salesRecordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SalesRecordEntity[ id=" + salesRecordId + " ]";
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return the dateOfPayment
     */
    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    /**
     * @param dateOfPayment the dateOfPayment to set
     */
    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }
    
}
