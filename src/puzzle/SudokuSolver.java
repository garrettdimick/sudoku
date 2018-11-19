package puzzle;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class SudokuSolver {
    protected SudokuGame sg;

    /**
     * Solves a sudoku puzzle using backtracking.
     * If the puzzle is large enough (> 9x9), tries to follow paths that are more likely using the sortByAppearance
     * routine, starting with the value that has been seen the least number of times throughout the board.
     * Uses placementAllowed to determine if a value can be placed in that cell.
     * @return true if solved, false if not
     */
    //This method solves the whole puzzle using the other methods defined
    protected boolean solveSudoku() {
        for(int r = 0;r < sg.dimension;r++){
            for(int c = 0; c < sg.dimension;c++){
                if(sg.currentBoard[r][c] == '-'){
                    ArrayList<Character> possibleValues = nextValues(r, c);
                    for(Character i : possibleValues) {
                        char v = i.charValue();
                        if (placementAllowed(r, c, v)) {
                            sg.currentBoard[r][c] = v;
                            updateCount(v);
                            if (solveSudoku()) {
                                return true;
                            } else {
                                sg.currentBoard[r][c] = '-';
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    protected abstract ArrayList<Character> nextValues(int r, int c);
    protected abstract void updateCount(Character v);

    /**
     * Check if a value is in the given row r
     */
    protected boolean inRow(int r, char v){
        for(int i=0;i<sg.dimension;i++) {
            if (sg.currentBoard[r][i] == v) {
                return true;
            }
        }
        return false;
    }
    /**
     * Check if a value is in the column
     */
    protected boolean inColumn(int c, char v){
        for(int i=0;i<sg.dimension;i++){
            if(sg.currentBoard[i][c] == v){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a value is in a Box
     */
    protected boolean inBox(int r, int c, char v){
        int boxSize = (int) Math.sqrt(sg.dimension);
        int row = r - r % boxSize;
        int col = c - c % boxSize;
        for(int i = row; i < row + boxSize; i++){
            for(int j = col; j < col + boxSize; j++){
                if(sg.currentBoard[i][j] == v){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a value can be placed in a cell by calling inRow, inBox, and inColumn
     */
    protected boolean placementAllowed(int r, int c, char v){
        return !inRow(r, v) && !inColumn(c, v) && !inBox(r,c,v);
    }
}
