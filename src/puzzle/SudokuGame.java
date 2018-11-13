package puzzle;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.jar.Attributes;

public class SudokuGame {

    public int dimension;
    ArrayList<Character> symbols;
    PuzzleParser pp;
    protected char[][] originalBoard;
    protected char[][] currentBoard;

    public SudokuGame(String filename) throws Exception {
        this.pp = new PuzzleParser();
        this.originalBoard = buildBoardFromFile(filename);
        this.currentBoard = this.originalBoard;
        this.dimension = this.pp.dimension;
        if(!this.pp.validShape(this.dimension)){
            throw new PuzzleException(printBoard(this.originalBoard) + "\nInvalid Shape.");
        }
        this.symbols = this.pp.symbols;
        if(!this.pp.validSymbols(this.symbols)){
            throw new PuzzleException("Invalid Symbol Detected.");
        }
        if(checkValidBoard() == false){
            throw new PuzzleException("Invalid Board Detected.");
        }
    }

    public String printBoard(char[][] board){
        StringBuilder message = new StringBuilder();
        for(int i=0;i<this.dimension;i++)
        {
            for(int j=0;j<this.dimension;j++)
            {
                message.append(board[i][j]);
                message.append(" ");
            }
            message.append("\n");
        }
        return message.toString();
    }

    public char[][] buildBoardFromFile(String filename) throws FileNotFoundException {
        this.originalBoard = pp.getBoardFromFile(filename);
        return originalBoard;
    }

    public void printSymbols(){
        for(Character i : this.symbols){
            System.out.print(i.toString() + " ");
        }
        System.out.println();
    }

    private boolean checkValidBoard() {
        for(int i = 0;i<dimension;i++){
            for(int j = 0;j<dimension;j++){
                if(originalBoard[i][j] == '-'){
                    continue;
                }
                if(!symbols.contains(originalBoard[i][j])){
                    return false;
                }
            }
        }
        return true;
    }

    //This method solves the whole puzzle using the other methods defined
    protected boolean solveSudoku() {
        for(int r = 0;r < dimension;r++){
            for(int c = 0; c < dimension;c++){
//                System.out.println("currentboard[r][c] is: [" + r + "]" + "[" + c + "]" + ", the value is: " + currentBoard[r][c]);
                if(currentBoard[r][c] == '-'){
                    ArrayList<Character> possibleValues = reduceList(symbols, r, c);
                    for(Character i : possibleValues) {
                        char v = i.charValue();
                        if (placementAllowed(r, c, v)) {
                            currentBoard[r][c] = v;
//                            System.out.println("Changed currentboard[r][c] to: " +  currentBoard[r][c]);
                            if (solveSudoku()) {
                                return true;
                            } else {
                                currentBoard[r][c] = '-';
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<Character> reduceList(ArrayList<Character> potentialValues,int row, int col) {
        // remove all values that appear in the row
        ArrayList<Character> reducedList = (ArrayList<Character>) potentialValues.clone();
        ArrayList<Character> valsToRemove = new ArrayList();
        for (int i = 0; i < dimension; i++) {
                valsToRemove.add(currentBoard[row][i]);
        }
        //remove all values that appear in the same column
        for (int j = 0; j < dimension; j++) {
                valsToRemove.add(currentBoard[j][col]);
        }
        // remove all values that appear in the same box
        int boxSize = (int) Math.sqrt(dimension);
        int r = row - row % boxSize;
        int c = col - col % boxSize;
        for (int i = r; i < r + boxSize; i++) {
            for (int j = c; j < c + boxSize; j++) {
                    valsToRemove.add(currentBoard[i][j]);
            }
        }
        reducedList.removeAll(valsToRemove);
        return reducedList;
    }

    //Check if a value is in the row
    protected boolean inRow(int r, char v){
        for(int i=0;i<dimension;i++) {
            if (currentBoard[r][i] == v) {
//                System.out.println("FOUND IN ROW.");
                return true;
            }
        }
        return false;
    }
    //Check if a value is in the column
    protected boolean inColumn(int c, char v){
        for(int i=0;i<this.dimension;i++){
            if(this.currentBoard[i][c] == v){
//                System.out.println("FOUND IN COLUMN.");
                return true;
            }
        }
        return false;
    }
    // check if a number is in box that is the sqrt of dimensions
    protected boolean inBox(int r, int c, char v){
        int boxSize = (int) Math.sqrt(dimension);
        int row = r - r % boxSize;
        int col = c - c % boxSize;
        for(int i = row; i < row + boxSize; i++){
            for(int j = col; j < col + boxSize; j++){
                if(this.currentBoard[i][j] == v){
//                    System.out.println("FOUND IN BOX.");
                    return true;
                }
            }
        }
        return false;
    }

    // check if placement of a new value is allowed by checking if the value is in a row, in a column, and in a box,
    // if not, the placement is allowed
    protected boolean placementAllowed(int r, int c, char v){
        return !inRow(r, v) && !inColumn(c, v) && !inBox(r,c,v);
    }
}
