package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.CreditCardTypeEnum;

@Entity
public class CreditCard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditCardId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private CreditCardTypeEnum type;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String cardName;
    @Column(nullable = false, length = 19)
    @NotNull
    @Size(max = 19)
    private String cardNumber;
    @Column(nullable = false, length = 5)
    @NotNull
    @Size(max = 5)
    private String expiryDate;
    
    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private Customer customer;
    
    @ManyToOne (optional = true)
    @JoinColumn (nullable = true)
    private ServiceProvider serviceProvider;

    public CreditCard() {
    }

    public CreditCard(CreditCardTypeEnum type, String cardName, String cardNumber, String expiryDate) {
        this.type = type;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
    }

    public Long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Long creditCardId) {
        this.creditCardId = creditCardId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (creditCardId != null ? creditCardId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the creditCardId fields are not set
        if (!(object instanceof CreditCard)) {
            return false;
        }
        CreditCard other = (CreditCard) object;
        if ((this.creditCardId == null && other.creditCardId != null) || (this.creditCardId != null && !this.creditCardId.equals(other.creditCardId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CreditCard[ id=" + creditCardId + " ]";
    }

    public CreditCardTypeEnum getType() {
        return type;
    }

    public void setType(CreditCardTypeEnum type) {
        this.type = type;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the serviceProvider
     */
    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    /**
     * @param serviceProvider the serviceProvider to set
     */
    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
    
}
