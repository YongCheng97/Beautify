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
public class CreateNewPurchasedLineItemException extends Exception {

    /**
     * Creates a new instance of
     * <code>CreateNewPurchasedLineItemException</code> without detail message.
     */
    public CreateNewPurchasedLineItemException() {
    }

    /**
     * Constructs an instance of
     * <code>CreateNewPurchasedLineItemException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewPurchasedLineItemException(String msg) {
        super(msg);
    }
}
