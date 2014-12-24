
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
        //System.out.println("Guess!");
    }
    
    @Override
    public  ArrayList<Action> revert(){
        c.revert();
        return new ArrayList();
    }
    @Override
    public boolean chainEnd(){
        return true;
    }
}
