
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
    static public byte debugLevel;
    
    //private ArrayList<Action> guessChain = new ArrayList();
    
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
                box[Section.getBoxNum(x, y)].set(Section.getBoxIndex(x, y),c);
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
        for(Action a : actionChain){
            toAdd.addAll(a.revert());
            toRemove.add(a);
            if(a.chainEnd()){
                break;
            }
        }
        actionChain.removeAll(toRemove);
        actionChain.addAll(toAdd);
    }
    
    public void revertAll(){
         for(Action a : actionChain){
            a.revert();
        }
        actionChain.clear();
    }
    
    public void Solve() throws OutOfGuessesException{
        int guesses = 0;
        int revisions = 0;
        if(debugLevel >= 1){
            System.out.println("--------------");
            System.out.println("Solving Now");
            System.out.println("--------------");
        }
        while(!isDone()){
            try{
                makeGuess();
                guesses++;
                if(debugLevel >= 1){
                    if(guesses % 100 == 0 || debugLevel >= 3){
                        print();
                    }
                }
            }catch(UnsolveableException e){
                revert();
                revisions++;
                if(debugLevel >= 2){
                    System.out.println("Revert Guess");
                }
                print();
            }
        }
        if(debugLevel >= 1){
             System.out.println("DONE");
             System.out.println("Guesses: " + guesses + "\t Revisions: " + revisions);
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
        for(Cell c : box[Section.getBoxNum(changed.x, changed.y)]){
            if(c != changed){
               c.narrowDown(val, guess ? this : null);
            }
        }
    }
    
    public void makeGuess() throws UnsolveableException, OutOfGuessesException{
        Cell bestCell = null;
        int size = 10;
        for(Section r : rows){
            for(Cell c : r){
                if(c.getValue() != null){
                    continue;
                }
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
    public void print(){
        for(Section s : col){
            String line = "|";
            for(Cell c : s){
                line += "|" + (c == null ? " " : (c.getValue() == null ? " " : c.getValue().toString()));
            }
            line+="||";
            System.out.println(line);
        }
        System.out.println();
    }
}