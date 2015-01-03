
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
        if(c.debugLevel >= 3){
            System.out.println("Logical Derived set \t to \t " + c.getValue());
        }
    }
    
    @Override
    public  ArrayList<Action> revert(){
        c.undo();
        if(c.debugLevel >= 3){
            System.out.println("Logical Derived reverted \t from \t " + c.getValue());
        }
        return new ArrayList();
    }
    @Override
    public boolean chainEnd(){
        return false;
    }
}
