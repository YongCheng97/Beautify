/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author fooyo
 */
public class DeleteServiceException extends Exception {

    /**
     * Creates a new instance of <code>DeleteServiceException</code> without
     * detail message.
     */
    public DeleteServiceException() {
    }

    /**
     * Constructs an instance of <code>DeleteServiceException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DeleteServiceException(String msg) {
        super(msg);
    }
}
