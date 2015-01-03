
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
        if(c.debugLevel >= 3){
            System.out.println("Failed Guess \t to \t" + c.getValue());
        }
        //System.out.println("Failed Guess");
    }
    
    
    @Override
     public  ArrayList<Action> revert(){
        c.removefailedGuess(n);
        if(c.debugLevel >= 3){
            System.out.println("Failed Guess reverted \t from \t" + c.getValue());
        }
        return new ArrayList();
    }
    @Override
    public boolean chainEnd(){
        return false;
    }
}
