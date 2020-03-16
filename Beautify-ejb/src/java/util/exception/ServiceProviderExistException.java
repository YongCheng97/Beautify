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
public class ServiceProviderExistException extends Exception {

    /**
     * Creates a new instance of <code>ServiceProviderExistException</code>
     * without detail message.
     */
    public ServiceProviderExistException() {
    }

    /**
     * Constructs an instance of <code>ServiceProviderExistException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ServiceProviderExistException(String msg) {
        super(msg);
    }
}
