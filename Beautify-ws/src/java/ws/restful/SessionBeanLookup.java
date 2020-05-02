/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.ProductSessionBeanLocal;
import ejb.session.stateless.PurchasedLineItemSessionBeanLocal;
import ejb.session.stateless.ServiceProviderSessionBeanLocal;
import ejb.session.stateless.TagsSessionBeanLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Crystal Lee
 */
class SessionBeanLookup {

    private final String ejbModuleJndiPath;

    public SessionBeanLookup() {
        ejbModuleJndiPath = "java:global/Beautify/Beautify-ejb/";
    }

    public ServiceProviderSessionBeanLocal lookupServiceProviderSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ServiceProviderSessionBeanLocal) c.lookup(ejbModuleJndiPath + "ServiceProviderSessionBean!ejb.session.stateless.ServiceProviderSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public BookingSessionBeanLocal lookupBookingSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (BookingSessionBeanLocal) c.lookup(ejbModuleJndiPath + "BookingSessionBean!ejb.session.stateless.BookingSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public ProductSessionBeanLocal lookupProductSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ProductSessionBeanLocal) c.lookup(ejbModuleJndiPath + "ProductSessionBean!ejb.session.stateless.ProductSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public PurchasedLineItemSessionBeanLocal lookupPurchasedLineItemSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PurchasedLineItemSessionBeanLocal) c.lookup(ejbModuleJndiPath + "PurchasedLineItemSessionBean!ejb.session.stateless.PurchasedLineItemSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public CategorySessionBeanLocal lookupCategorySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CategorySessionBeanLocal) c.lookup("java:global/Beautify/Beautify-ejb/CategorySessionBean!ejb.session.stateless.CategorySessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    public TagsSessionBeanLocal lookupTagsSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TagsSessionBeanLocal) c.lookup("java:global/Beautify/Beautify-ejb/TagsSessionBean!ejb.session.stateless.TagsSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
