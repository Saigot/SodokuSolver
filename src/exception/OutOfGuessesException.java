/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Michael17
 */
public class OutOfGuessesException extends Exception {

    /**
     * Creates a new instance of
     * <code>OutOfGuessesException</code> without detail message.
     */
    public OutOfGuessesException() {
    }

    /**
     * Constructs an instance of
     * <code>OutOfGuessesException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public OutOfGuessesException(String msg) {
        super(msg);
    }
}
