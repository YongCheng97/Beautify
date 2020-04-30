/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Crystal Lee
 */
@Entity
public class Purchased implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchasedId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date dateOfPurchase;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal totalPrice;
    @Column(nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String address;
    
    @OneToMany
    private List<PurchasedLineItem> purchasedLineItems;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private CreditCard creditCard; 
    
    public Purchased() {
        purchasedLineItems = new ArrayList<>();
    }
    
    public Purchased(Date dateOfPurchase, BigDecimal totalPrice, String address) {
        this();
        
        this.dateOfPurchase = dateOfPurchase;
        this.totalPrice = totalPrice;
        this.address = address; 
    }

    public Long getPurchasedId() {
        return purchasedId;
    }

    public void setPurchasedId(Long purchasedId) {
        this.purchasedId = purchasedId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (purchasedId != null ? purchasedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the purchasedId fields are not set
        if (!(object instanceof Purchased)) {
            return false;
        }
        Purchased other = (Purchased) object;
        if ((this.purchasedId == null && other.purchasedId != null) || (this.purchasedId != null && !this.purchasedId.equals(other.purchasedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PurchasedProduct[ id=" + purchasedId + " ]";
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<PurchasedLineItem> getPurchasedLineItems() {
        return purchasedLineItems;
    }

    public void setPurchasedLineItems(List<PurchasedLineItem> purchasedLineItems) {
        this.purchasedLineItems = purchasedLineItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
    
}
