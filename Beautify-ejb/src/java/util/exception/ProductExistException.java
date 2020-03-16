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
public class ProductExistException extends Exception {

    /**
     * Creates a new instance of <code>ProductExistException</code> without
     * detail message.
     */
    public ProductExistException() {
    }

    /**
     * Constructs an instance of <code>ProductExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ProductExistException(String msg) {
        super(msg);
    }
}
