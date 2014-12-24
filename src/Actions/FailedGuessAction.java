
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
        //System.out.println("Failed Guess");
    }
    
    
    @Override
     public  ArrayList<Action> revert(){
        c.removefailedGuess(n);
        return new ArrayList();
    }
    @Override
    public boolean chainEnd(){
        return false;
    }
}
