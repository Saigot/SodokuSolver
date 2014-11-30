
package sudokusolver;

import exception.OutOfGuessesException;
import exception.UnsolveableException;
import Actions.Action;
import Actions.CellGuessAction;
import java.util.ArrayList;

/**
 *
 * @author Michael Scovell
 */
public class Sodoku {

    private Section rows[] = new Section[9];
    private Section col[] = new Section[9];
    private Section box[] = new Section[9];
    public ArrayList<Action> actionChain = new ArrayList();
    
    private ArrayList<Action> guessChain = new ArrayList();
    
    public Sodoku(){
        reset();
    }
    
    public void reset(){
        for(int i = 0; i < 9; i++){
            rows[i] = new Section();
        }
        for(int i = 0; i < 9; i++){
            col[i] = new Section();
        }
        for(int i = 0; i < 9; i++){
            box[i] = new Section();
        }
        for(int x = 0; x < 9; x++){
            for(int y = 0; y < 9; y++){
                Cell c = new Cell(x,y);
                rows[x].set(y,c);
                col[y].set(x,c);
                int boxnum = ((y/3)*3-1) + (int)Math.ceil((x+1) /3.0);
                box[boxnum].set(y%3 + x%3,c);
            }
        }
    }
    public Cell get(int x, int y){
        return rows[x].get(y);
    }
    
    public Cell get(int n){
        return rows[n%9].get(n/9);
    }
    
    public void revert(){
        ArrayList<Action> toRemove = new ArrayList();
        ArrayList<Action> toAdd = new ArrayList();
        for(Action a : guessChain){
            toAdd.addAll(a.revert());
            toRemove.add(a);
            if(a.chainEnd()){
                break;
            }
        }
        guessChain.removeAll(toRemove);
        guessChain.addAll(toAdd);
    }
    
    public void revertAll(){
         for(Action a : guessChain){
            a.revert();
        }
        guessChain.clear();
    }
    
    public void Solve() throws OutOfGuessesException{
        while(!isDone()){
            try{
                makeGuess();
            }catch(UnsolveableException e){
                revert();
            }
        }
    }
    public void set(Cell changed, Integer val, boolean guess) throws UnsolveableException{
        changed.set(val);
        if(guess){
            actionChain.add(new CellGuessAction(changed));
        }
        for(Cell c : rows[changed.x]){
            if(c != changed){
               c.narrowDown(val, guess ? this : null);
            }
        }
        for(Cell c : col[changed.y]){
            if(c != changed){
               c.narrowDown(val, guess ? this : null);
            }
        }
        for(Cell c : box[changed.y-1 + (int)Math.ceil((changed.x+1) /3.0)]){
            if(c != changed){
               c.narrowDown(val, guess ? this : null);
            }
        }
    }
    
    public void makeGuess() throws UnsolveableException, OutOfGuessesException{
        Cell bestCell = null;
        int size = 0;
        for(Section r : rows){
            for(Cell c : r){
                ArrayList guesses = c.getGuessList();
                if(guesses.size() == 2){
                    set(c,(Integer) guesses.get(0),true);
                    return;
                }else if(guesses.size() < size){
                    size = guesses.size();
                    bestCell = c;
                }
            }
            
        }
        set(bestCell,(Integer) bestCell.getGuessList().get(0),true);
    }
    
    public boolean isDone(){
        for(Section s : rows){
            for(Cell c : s){
                if(c.getValue() == null){
                    return false;
                }
            }
        }
        return true;
    }
}
