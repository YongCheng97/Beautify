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
public class PurchasedLineItemExistException extends Exception {

    /**
     * Creates a new instance of <code>PurchasedLineItemExistException</code>
     * without detail message.
     */
    public PurchasedLineItemExistException() {
    }

    /**
     * Constructs an instance of <code>PurchasedLineItemExistException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public PurchasedLineItemExistException(String msg) {
        super(msg);
    }
}
