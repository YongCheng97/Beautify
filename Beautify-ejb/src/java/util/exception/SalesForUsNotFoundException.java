/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Zheng Yang
 */
public class SalesForUsNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>SalesForUsNotFoundException</code>
     * without detail message.
     */
    public SalesForUsNotFoundException() {
    }

    /**
     * Constructs an instance of <code>SalesForUsNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public SalesForUsNotFoundException(String msg) {
        super(msg);
    }
}
