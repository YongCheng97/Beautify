/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Crystal Lee
 */
@Entity
public class PurchasedLineItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchasedLineItemId;
    @Column(nullable = false)
    @NotNull
    private Integer quantity;
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String status;
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price;
    
    @OneToOne(optional = true)
    @JoinColumn(nullable = true)
    private Review review;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Product product;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = true)
    private Purchased purchased;

    public PurchasedLineItem(){
    }
    
    public PurchasedLineItem(Integer quantity, String status, BigDecimal price){
        this();
        
        this.quantity = quantity;
        this.status = status;
        this.price = price;
    }
    public Long getPurchasedLineItemId() {
        return purchasedLineItemId;
    }

    public void setPurchasedLineItemId(Long purchasedLineItemId) {
        this.purchasedLineItemId = purchasedLineItemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (purchasedLineItemId != null ? purchasedLineItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the purchasedLineItemId fields are not set
        if (!(object instanceof PurchasedLineItem)) {
            return false;
        }
        PurchasedLineItem other = (PurchasedLineItem) object;
        if ((this.purchasedLineItemId == null && other.purchasedLineItemId != null) || (this.purchasedLineItemId != null && !this.purchasedLineItemId.equals(other.purchasedLineItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PurchasedLineItem[ id=" + purchasedLineItemId + " ]";
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Purchased getPurchased() {
        return purchased;
    }

    public void setPurchased(Purchased purchased) {
        this.purchased = purchased;
    }
    
}
