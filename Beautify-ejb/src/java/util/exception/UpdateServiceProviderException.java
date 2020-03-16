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
public class UpdateServiceProviderException extends Exception {

    /**
     * Creates a new instance of <code>UpdateServiceProviderException</code>
     * without detail message.
     */
    public UpdateServiceProviderException() {
    }

    /**
     * Constructs an instance of <code>UpdateServiceProviderException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdateServiceProviderException(String msg) {
        super(msg);
    }
}
