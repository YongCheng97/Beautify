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
public class ServiceExistException extends Exception {

    /**
     * Creates a new instance of <code>ServiceExistException</code> without
     * detail message.
     */
    public ServiceExistException() {
    }

    /**
     * Constructs an instance of <code>ServiceExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ServiceExistException(String msg) {
        super(msg);
    }
}
