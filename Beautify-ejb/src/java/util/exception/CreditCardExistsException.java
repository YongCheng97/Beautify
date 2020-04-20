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
public class CreditCardExistsException extends Exception {

    /**
     * Creates a new instance of <code>CreditCardExistsException</code> without
     * detail message.
     */
    public CreditCardExistsException() {
    }

    /**
     * Constructs an instance of <code>CreditCardExistsException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CreditCardExistsException(String msg) {
        super(msg);
    }
}
