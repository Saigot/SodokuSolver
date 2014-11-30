
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
    }
    
    @Override
    public  ArrayList<Action> revert(){
        c.revert();
        return null;
    }
    @Override
    public boolean chainEnd(){
        return true;
    }
}
