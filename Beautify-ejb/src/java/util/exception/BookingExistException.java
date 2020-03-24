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
public class BookingExistException extends Exception {

    /**
     * Creates a new instance of <code>BookingExistException</code> without
     * detail message.
     */
    public BookingExistException() {
    }

    /**
     * Constructs an instance of <code>BookingExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BookingExistException(String msg) {
        super(msg);
    }
}
