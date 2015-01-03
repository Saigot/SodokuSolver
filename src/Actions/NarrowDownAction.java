
package Actions;

import java.util.ArrayList;
import sudokusolver.Cell;

/**
 *
 * @author Michael Scovell
 */
public class NarrowDownAction extends Action{
    Integer n;
    Cell c;

    public NarrowDownAction(Cell c, Integer n) {
        this.c = c;
        this.n = n;
        if(c.debugLevel >= 3){
            System.out.println("Narrow Down set \t to \t" + n + "," + c);
        }
    }
    
    
    @Override
     public  ArrayList<Action> revert(){
        c.reAddValue(n);
        if(c.debugLevel >= 3){
            System.out.println("Narrow Down reverted \t from \t" + n + "," + c);
        }
        return new ArrayList();
    }
    @Override
    public boolean chainEnd(){
        return false;
    }
    
}
