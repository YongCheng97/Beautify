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
public class DeleteServiceProviderException extends Exception {

    /**
     * Creates a new instance of <code>DeleteServiceProviderException</code>
     * without detail message.
     */
    public DeleteServiceProviderException() {
    }

    /**
     * Constructs an instance of <code>DeleteServiceProviderException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DeleteServiceProviderException(String msg) {
        super(msg);
    }
}
