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
public class PurchasedExistException extends Exception {

    /**
     * Creates a new instance of <code>PurchasedExistException</code> without
     * detail message.
     */
    public PurchasedExistException() {
    }

    /**
     * Constructs an instance of <code>PurchasedExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PurchasedExistException(String msg) {
        super(msg);
    }
}
