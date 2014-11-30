
package sudokusolver;

import java.util.Iterator;

/**
 *
 * @author Michael Scovell
 */
public class Section implements Iterable<Cell>{

    private Cell cells[] = new Cell[9];
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
        int cursor;
        @Override
        public boolean hasNext() {
            return cursor == 8;
        }

        @Override
        public Cell next() {
            cursor++;
            return cells[cursor];
        }

        @Override
        public void remove() {
            cells[cursor] = new Cell(cursor%9,cursor/9);
        }
        
    }
}
