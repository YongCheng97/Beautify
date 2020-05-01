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
public class UpdatePurchasedLineItemException extends Exception {

    /**
     * Creates a new instance of <code>UpdatePurchasedLineItemException</code>
     * without detail message.
     */
    public UpdatePurchasedLineItemException() {
    }

    /**
     * Constructs an instance of <code>UpdatePurchasedLineItemException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdatePurchasedLineItemException(String msg) {
        super(msg);
    }
}
