package puzzle;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RegularSolver extends SudokuSolver {

    public RegularSolver(SudokuGame game){
        super.sg = game;
    }
    public ArrayList<Character> nextValues(int r, int c) {
        return super.sg.symbols;
    }

    public void updateCount(Character v) {
    }


}
