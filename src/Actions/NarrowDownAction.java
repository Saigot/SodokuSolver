
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
        System.out.println("Narrow Down Occured");
    }
    
    
    @Override
     public  ArrayList<Action> revert(){
        c.reAddValue(n);
        return null;
    }
    @Override
    public boolean chainEnd(){
        return false;
    }
    
}
