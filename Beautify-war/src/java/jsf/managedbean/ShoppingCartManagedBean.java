package jsf.managedbean;

import entity.Item;
import entity.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "shoppingCartManagedBean")
@SessionScoped
public class ShoppingCartManagedBean implements Serializable {

    private List<Item> items;
    private int amountToCart;

    public ShoppingCartManagedBean() {
        this.items = new ArrayList<Item>();
    }

    public void addToCart(Product product) {
        String msg = null;
        FacesContext context = FacesContext.getCurrentInstance();

        int index = this.existsInCart(product);

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
    }

public int existsInCart(Product product) {
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).getProduct().getProductId() == product.getProductId()) {
                return i;
            }
        }
        return -1;
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

}
