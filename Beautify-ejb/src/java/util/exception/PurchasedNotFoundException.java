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
public class PurchasedNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>PurchasedNotFoundException</code> without
     * detail message.
     */
    public PurchasedNotFoundException() {
    }

    /**
     * Constructs an instance of <code>PurchasedNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public PurchasedNotFoundException(String msg) {
        super(msg);
    }
}
