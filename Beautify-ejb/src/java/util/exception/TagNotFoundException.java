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
public class TagNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>TagsNotFoundException</code> without
     * detail message.
     */
    public TagNotFoundException() {
    }

    /**
     * Constructs an instance of <code>TagsNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TagNotFoundException(String msg) {
        super(msg);
    }
}
