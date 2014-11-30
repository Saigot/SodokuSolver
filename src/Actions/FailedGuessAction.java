
package Actions;

import java.util.ArrayList;
import sudokusolver.Cell;

/**
 *
 * @author Michael Scovell
 */
public class FailedGuessAction extends Action{
    Integer n;
    Cell c;

    public FailedGuessAction(Cell c, Integer n) {
        this.c = c;
        this.n = n;
    }
    
    
    @Override
     public  ArrayList<Action> revert(){
        c.removefailedGuess(n);
        return null;
    }
    @Override
    public boolean chainEnd(){
        return false;
    }
}
