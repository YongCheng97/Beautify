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
public class CreateNewSalesForUsException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewSalesForUsException</code>
     * without detail message.
     */
    public CreateNewSalesForUsException() {
    }

    /**
     * Constructs an instance of <code>CreateNewSalesForUsException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewSalesForUsException(String msg) {
        super(msg);
    }
}
