package jsf.managedbean;

import ejb.session.stateless.CreditCardSessionBeanLocal;
import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.PromotionSessionBeanLocal;
import ejb.session.stateless.PurchasedLineItemSessionBeanLocal;
import ejb.session.stateless.PurchasedSessionBeanLocal;
import entity.CreditCard;
import entity.Customer;
import entity.Item;
import entity.Product;
import entity.Promotion;
import entity.Purchased;
import entity.PurchasedLineItem;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import util.exception.CategoryNotFoundException;
import util.exception.CreateNewPurchaseException;
import util.exception.CreateNewPurchasedLineItemException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.PromotionNotFoundException;
import util.exception.PurchasedExistException;
import util.exception.PurchasedLineItemExistException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProductException;

@Named(value = "shoppingCartManagedBean")
@SessionScoped
public class ShoppingCartManagedBean implements Serializable {

    @EJB
    private ProductSessionBeanLocal productSessionBeanLocal;

    @EJB
    private PromotionSessionBeanLocal promotionSessionBeanLocal;

    @EJB
    private CreditCardSessionBeanLocal creditCardSessionBeanLocal;

    @EJB
    private PurchasedSessionBeanLocal purchasedSessionBeanLocal;

    @EJB
    private PurchasedLineItemSessionBeanLocal purchasedLineItemSessionBeanLocal;

    private Purchased order;

    private Customer currentCustomer;
    private List<String> creditCards;
    private String creditCardNum;
    private String promoCode;

    private List<Item> items;
    private int amountToCart;
    private BigDecimal totalAmount;

    //FacesContext context = FacesContext.getCurrentInstance();
    String msg = null;

    public ShoppingCartManagedBean() {
        this.items = new ArrayList<>();
        this.amountToCart = 0;
        order = new Purchased();
        promoCode = null;
    }

    @PostConstruct
    public void postConstruct() {
    }

    public void addToCart(Product product) {
        //String msg = null;
        FacesContext context = FacesContext.getCurrentInstance();

        int index = this.existsInCart(product);
        System.out.println("test: " + this.amountToCart);
        if (this.amountToCart == 0) {
            msg = "Choose at least 1";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        } else if (this.amountToCart > product.getQuantityOnHand()) {
            msg = "Available product in stock: " + product.getQuantityOnHand();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        } else {
            if (index == -1) {
                this.items.add(new Item(product, this.amountToCart));
                msg = "Added to the cart";
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));

            } else {
                int quantity = this.items.get(index).getQuantity() + this.amountToCart;
                this.items.get(index).setQuantity(quantity);
                msg = "Updated the quantity";
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
            }
            this.amountToCart = 0;
        }

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("haveItems", true);
    }

    public BigDecimal totalAmount() {
        BigDecimal total = new BigDecimal("0.00");

        for (Item item : this.items) {
            total = total.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        setTotalAmount(total);

        return total;
    }

    public void removeFromCart(Product product) {

        FacesContext context = FacesContext.getCurrentInstance();

        int index = this.existsInCart(product);
        this.items.remove(index);
        msg = "Remove from cart";
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));

        BigDecimal total = new BigDecimal("0.00");

        for (Item item : this.items) {
            total = total.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        setTotalAmount(total);

        if (items.isEmpty()) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("haveItems", false);
        }
    }

    public int existsInCart(Product product) {
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).getProduct().getProductId() == product.getProductId()) {
                return i;
            }
        }
        return -1;
    }

    public void removeAllItems() { // called after checkout

        for (Item item : this.getItems()) {
            Product product = item.getProduct(); 
            product.setQuantityOnHand(item.getProduct().getQuantityOnHand() - item.getQuantity());
            try { 
                productSessionBeanLocal.updateProduct(product, product.getCategory().getCategoryId());
            } catch (ProductNotFoundException ex) {
                Logger.getLogger(ShoppingCartManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CategoryNotFoundException ex) {
                Logger.getLogger(ShoppingCartManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UpdateProductException ex) {
                Logger.getLogger(ShoppingCartManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InputDataValidationException ex) {
                Logger.getLogger(ShoppingCartManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.items.clear();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ShoppingCartManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("haveItems", false);
    }

    public void directToCheckout(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/customerOperations/checkout.xhtml");

        setCurrentCustomer((Customer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCustomerEntity"));
        List<String> list = new ArrayList<>();
        if (getCurrentCustomer() != null) {
            List<CreditCard> cards = getCurrentCustomer().getCreditCards();
            for (CreditCard cc : cards) {
                String num = cc.getCardNumber();
                list.add(num.replace(num.substring(0, 12), "XXXXXXXXXXXX"));
            }
        }

        setCreditCards(list);

    }

    public void createNewOrder(ActionEvent event) {

        FacesContext context = FacesContext.getCurrentInstance();

        if (items.isEmpty()) {
            msg = "Add item to cart before checkout!";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("haveItems", false);

        } else {
            List<Long> lineItemsIds = new ArrayList<>();

            BigDecimal totalPrice = new BigDecimal("0.00");
            BigDecimal promoPrice = new BigDecimal("0.00");
            Date date = new Date();

            Boolean success = false;

            try {
                Promotion promotion = promotionSessionBeanLocal.retrievePromotionByPromoCode(promoCode);
                success = true;
            } catch (PromotionNotFoundException ex) {
            }

            try {
                if (promoCode != null && !promoCode.isEmpty() && success) {
                    Promotion promotion = promotionSessionBeanLocal.retrievePromotionByPromoCode(promoCode);
                    for (Item item : items) {
                        List<Promotion> itemPromotions = item.getProduct().getPromotions();
                        if (itemPromotions.contains(promotion)) {
                            if (promotionSessionBeanLocal.checkPromoCode(promoCode) == true) {
                                promoPrice = (item.getProduct().getPrice().multiply(promotion.getDiscountRate())).setScale(2, BigDecimal.ROUND_HALF_UP);

                                PurchasedLineItem lineItem = new PurchasedLineItem(item.getQuantity(), "Order Confirmed", promoPrice.multiply(BigDecimal.valueOf(item.getQuantity())));
                                lineItemsIds.add((purchasedLineItemSessionBeanLocal.createNewPurchasedLineItem(lineItem, item.getProduct().getProductId())).getPurchasedLineItemId());
                                totalPrice = totalPrice.add(promoPrice.multiply(BigDecimal.valueOf(item.getQuantity())));
                            } else {
                                PurchasedLineItem lineItem = new PurchasedLineItem(item.getQuantity(), "Order Confirmed", item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                                lineItemsIds.add((purchasedLineItemSessionBeanLocal.createNewPurchasedLineItem(lineItem, item.getProduct().getProductId())).getPurchasedLineItemId());

                                totalPrice = totalPrice.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                            }
                        } else {
                            PurchasedLineItem lineItem = new PurchasedLineItem(item.getQuantity(), "Order Confirmed", item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                            lineItemsIds.add((purchasedLineItemSessionBeanLocal.createNewPurchasedLineItem(lineItem, item.getProduct().getProductId())).getPurchasedLineItemId());

                            totalPrice = totalPrice.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                        }
                    }
                } else {
                    for (Item item : this.getItems()) {
                        PurchasedLineItem lineItem = new PurchasedLineItem(item.getQuantity(), "Order Confirmed", item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                        lineItemsIds.add((purchasedLineItemSessionBeanLocal.createNewPurchasedLineItem(lineItem, item.getProduct().getProductId())).getPurchasedLineItemId());

                        totalPrice = totalPrice.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                    }
                }

                setTotalAmount(totalPrice);

                Purchased purchased = new Purchased(date, totalPrice, order.getAddress());
                CreditCard cc = creditCardSessionBeanLocal.retrieveCreditCardByLastFourNum(creditCardNum);

                setOrder(purchasedSessionBeanLocal.createNewPurchased(purchased, currentCustomer.getCustomerId(), lineItemsIds, cc.getCreditCardId()));

            } catch (UnknownPersistenceException | CreateNewPurchasedLineItemException | PurchasedLineItemExistException | InputDataValidationException | CustomerNotFoundException
                    | PurchasedExistException | CreateNewPurchaseException | PromotionNotFoundException ex) {
                Logger.getLogger(ShoppingCartManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void checkPromoCode(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        Boolean success = false;

        try {
            Promotion promotion = promotionSessionBeanLocal.retrievePromotionByPromoCode(promoCode);

            success = true;
        } catch (PromotionNotFoundException ex) {
            msg = "Invalid Promo Code!";

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
            Logger.getLogger(ShoppingCartManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (success) {

                try {
                    Promotion promotion = promotionSessionBeanLocal.retrievePromotionByPromoCode(promoCode);

                    Boolean exists = false;

                    for (Item item : items) { // there is an item in cart w that promotion
                        List<Promotion> itemPromotions = item.getProduct().getPromotions();
                        if (itemPromotions.contains(promotion)) {
                            exists = true;
                        }
                    }

                    Boolean valid = promotionSessionBeanLocal.checkPromoCode(promoCode); // promo is valid for this date

                    if (valid && exists) {
                        msg = "Promo Code Applied!";

                        BigDecimal totalPrice = new BigDecimal("0.00");
                        BigDecimal promoPrice = new BigDecimal("0.00");

                        for (Item item : items) { // promo item
                            List<Promotion> itemPromotions = item.getProduct().getPromotions();
                            if (itemPromotions.contains(promotion)) {
                                promoPrice = (item.getProduct().getPrice().multiply(promotion.getDiscountRate())).setScale(2, BigDecimal.ROUND_HALF_UP);
                                totalPrice = totalPrice.add(promoPrice.multiply(BigDecimal.valueOf(item.getQuantity())));
                            } else {
                                totalPrice = totalPrice.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                            }
                        }

                        setTotalAmount(totalPrice);

                    } else {
                        msg = "Invalid Promo Code!";
                    }

                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
                } catch (PromotionNotFoundException ex) {
                    Logger.getLogger(ShoppingCartManagedBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * @return the items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * @return the amountToCart
     */
    public int getAmountToCart() {
        return amountToCart;
    }

    /**
     * @param amountToCart the amountToCart to set
     */
    public void setAmountToCart(int amountToCart) {
        this.amountToCart = amountToCart;
    }

    public Purchased getOrder() {
        return order;
    }

    public void setOrder(Purchased order) {
        this.order = order;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public List<String> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<String> creditCards) {
        this.creditCards = creditCards;
    }

    public String getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(String creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

}
