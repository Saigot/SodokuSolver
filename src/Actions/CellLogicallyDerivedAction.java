
package Actions;

import java.util.ArrayList;
import sudokusolver.Cell;

/**
 *
 * @author Michael Scovell
 */
public class CellLogicallyDerivedAction extends Action {
    Cell c;
    
    public CellLogicallyDerivedAction(Cell c){
        this.c = c;
        //System.out.println("Logical Derived");
    }
    
    @Override
    public  ArrayList<Action> revert(){
        c.undo();
        return new ArrayList();
    }
    @Override
    public boolean chainEnd(){
        return false;
    }
}
