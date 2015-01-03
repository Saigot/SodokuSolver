
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
            //c is the object before it is set, so we have to change how it prints
            System.out.println("Logical Derived set \t to \t " + c + c.getPossibleValues().get(0));
        }
    }
    
    @Override
    public  ArrayList<Action> revert(){
        c.undo();
        if(c.debugLevel >= 3){
            System.out.println("Logical Derived reverted \t from \t " + c +  c.getPossibleValues().get(0));
        }
        return new ArrayList();
    }
    @Override
    public boolean chainEnd(){
        return false;
    }
}
