
package Actions;

import java.util.ArrayList;
import sudokusolver.Cell;

/**
 *
 * @author Michael Scovell
 */
public class CellGuessAction extends Action{
    Cell c;
    
    public CellGuessAction(Cell c){
        this.c = c;
        if(c.debugLevel >= 3){
            System.out.println("Guess Action set \t to \t" + c);
        }
    }
    
    @Override
    public  ArrayList<Action> revert(){
        ArrayList<Action> failedGuesses = c.revert();
        if(c.debugLevel >= 3){
            System.out.println("Guess reverted \t from \t" + c);
        }
        return failedGuesses;
    }
    @Override
    public boolean chainEnd(){
        return true;
    }
}
