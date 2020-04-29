package jsf.managedbean;

import ejb.session.stateless.CreditCardSessionBeanLocal;
import ejb.session.stateless.PurchasedLineItemSessionBeanLocal;
import ejb.session.stateless.PurchasedSessionBeanLocal;
import entity.CreditCard;
import entity.Customer;
import entity.Item;
import entity.Product;
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
import util.exception.CreateNewPurchaseException;
import util.exception.CreateNewPurchasedLineItemException;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.PurchasedExistException;
import util.exception.PurchasedLineItemExistException;
import util.exception.UnknownPersistenceException;

@Named(value = "shoppingCartManagedBean")
@SessionScoped
public class ShoppingCartManagedBean implements Serializable {

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

    private List<Item> items;
    private int amountToCart;

    //FacesContext context = FacesContext.getCurrentInstance();
    String msg = null;

    public ShoppingCartManagedBean() {
        this.items = new ArrayList<>();
        this.amountToCart = 0;
        order = new Purchased();
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
        return total;
    }

    public void removeFromCart(Product product) {

        FacesContext context = FacesContext.getCurrentInstance();

        int index = this.existsInCart(product);
        this.items.remove(index);
        msg = "Remove from cart";
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));

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

    public void removeAllItems() {
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
            Date date = new Date();

            try {
                for (Item item : this.getItems()) {
                    PurchasedLineItem lineItem = new PurchasedLineItem(item.getQuantity(), "Order Confirmed", item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                    lineItemsIds.add((purchasedLineItemSessionBeanLocal.createNewPurchasedLineItem(lineItem, item.getProduct().getProductId())).getPurchasedLineItemId());

                    totalPrice = totalPrice.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                }

                Purchased purchased = new Purchased(date, totalPrice, order.getAddress());
                CreditCard cc = creditCardSessionBeanLocal.retrieveCreditCardByLastFourNum(creditCardNum);

                setOrder(purchasedSessionBeanLocal.createNewPurchased(purchased, currentCustomer.getCustomerId(), lineItemsIds, cc.getCreditCardId()));

            } catch (UnknownPersistenceException | CreateNewPurchasedLineItemException | PurchasedLineItemExistException | InputDataValidationException | CustomerNotFoundException
                    | PurchasedExistException | CreateNewPurchaseException ex) {
                Logger.getLogger(ShoppingCartManagedBean.class.getName()).log(Level.SEVERE, null, ex);
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

}
