
package sudokusolver;

import java.util.Iterator;

/**
 *
 * @author Michael Scovell
 */
public class Section implements Iterable<Cell>{

    private Cell cells[] = new Cell[9];
    static public byte debugLevel;
    
    
    static int getBoxNum(final int x, final int y){
        int col = x/3;
        int row = y/3;
        return row*3 + col;
    }
    static int getBoxIndex(final int x, final int y){
        int col = x%3;
        int row = y%3;
        return row*3 + col;
    }
    public Cell get(int x){
        return cells[x];
    }
    
    public void set(int x, Cell c){
        cells[x] = c;
    }

    @Override
    public Iterator iterator() {
        return new SectionIterator();
    }
    
     // Inner class example
    private class SectionIterator implements
                    Iterator<Cell> {
        int cursor = 0;
        @Override
        public boolean hasNext() {
            return cursor != 9;
        }

        @Override
        public Cell next() {
            cursor++;
            return cells[cursor-1];
        }

        @Override
        public void remove() {
            cells[cursor] = new Cell(cursor%9,cursor/9);
        }
        
    }
}
