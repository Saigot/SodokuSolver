
package sudokusolver;

import exception.OutOfGuessesException;
import exception.UnsolveableException;
import Actions.Action;
import Actions.FailedGuessAction;
import Actions.NarrowDownAction;
import Actions.CellLogicallyDerivedAction;
import java.util.ArrayList;
/**
 *
 * @author Michael Scovell
 */
public class Cell {
    private Integer value;
    private ArrayList<Integer> possibleValues = new ArrayList();
    private ArrayList<Integer> failedGuesses = new ArrayList();
    int x;
    int y;
    static public byte debugLevel;
    
    public void removefailedGuess(Integer n){
        failedGuesses.remove(n);
    }
    
    public ArrayList<Integer> getPossibleValues(){
        return possibleValues;
    }
    public boolean narrowDown(Integer n, Sodoku s) throws UnsolveableException{
        if(value != null){
            return false;
        }
        boolean success = possibleValues.remove(n);
        if(success && s != null){
            s.actionChain.add(new NarrowDownAction(this,n));
        }
        if(possibleValues.isEmpty()){
            throw new UnsolveableException();
        }else if(possibleValues.size() == 1){
            if(s != null){
                s.actionChain.add(new CellLogicallyDerivedAction(this));
                s.set(this,  possibleValues.get(0),false);
            }
            return true;
        }
        return false;
    }
    
    public Cell(int x, int y){
       resetPossibleValues();
       this.x = x;
       this.y = y;
    }
    
    public void reAddValue(Integer n){
        possibleValues.add(n);
    }
    
    public  ArrayList<Action> revert(){
         ArrayList<Action> actionChain = new ArrayList();
         if(failedGuesses.add(value)){
             actionChain.add(new FailedGuessAction(this, value));
         }
         value = null;
         return actionChain;
    }
    public void undo(){
        value = null;
    }
    
    public void resetPossibleValues(){
        possibleValues.clear();
        for(int i = 1; i <= 9; i++){
            possibleValues.add(i);
        }
    }
    public ArrayList<Integer> getGuessList() throws OutOfGuessesException{
        ArrayList<Integer> ret = (ArrayList<Integer>)possibleValues.clone();
        ret.removeAll(failedGuesses);
        if(ret.size() == 0){
            throw new OutOfGuessesException("No guesses Left");
        }
        return ret;
    }
    
    public void set(Integer n){
      value = n;   
    }
    public Integer getValue(){
        return value;
    }
   
    @Override
    public String toString(){
        String ret = "";
        ret += "(" + Integer.toString(x) + "," + Integer.toString(y) + ") -> ";
        ret += (value == null ? "" : Integer.toString(value));
        return ret;
    }
}
