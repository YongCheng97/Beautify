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
public class CreateNewSalesRecordException extends Exception {

    /**
     * Creates a new instance of <code>CreateNewSalesRecordException</code>
     * without detail message.
     */
    public CreateNewSalesRecordException() {
    }

    /**
     * Constructs an instance of <code>CreateNewSalesRecordException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CreateNewSalesRecordException(String msg) {
        super(msg);
    }
}
