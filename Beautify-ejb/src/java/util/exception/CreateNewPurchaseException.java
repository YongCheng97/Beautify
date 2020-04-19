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
public class CreateNewPurchaseException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewPurchaseException</code> without
     * detail message.
     */
    public CreateNewPurchaseException() {
    }

    /**
     * Constructs an instance of <code>CreateNewPurchaseException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewPurchaseException(String msg) {
        super(msg);
    }
}
