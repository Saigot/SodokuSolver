/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Michael17
 */
public class UnsolveableException extends Exception {

    /**
     * Creates a new instance of
     * <code>UnsolveableException</code> without detail message.
     */
    public UnsolveableException() {
    }

    /**
     * Constructs an instance of
     * <code>UnsolveableException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public UnsolveableException(String msg) {
        super(msg);
    }
}
