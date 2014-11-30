
package Actions;

import java.util.ArrayList;

/**
 *
 * @author Michael Scovell
 */
public abstract class Action {

    
    public abstract ArrayList<Action> revert();
    public abstract boolean chainEnd();
}
