/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Crystal Lee
 */
public class PurchasedLineItemNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>PurchasedLineItemNotFoundException</code>
     * without detail message.
     */
    public PurchasedLineItemNotFoundException() {
    }

    /**
     * Constructs an instance of <code>PurchasedLineItemNotFoundException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public PurchasedLineItemNotFoundException(String msg) {
        super(msg);
    }
}
