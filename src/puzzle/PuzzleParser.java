package puzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 */
public class PuzzleParser {

    int dimension;
    ArrayList<Character> symbols = new ArrayList<Character>();

    public PuzzleParser(){}

    public char[][] getBoardFromFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner s = new Scanner(file);
        int dim = s.nextInt();
        dimension = dim;
        for(int i=0;i<dim;i++){
            symbols.add(s.next().charAt(0));
        }
        char[][] board = new char[dim][dim];
        int rCounter = 0;
        int cCounter = 0;
        while (s.hasNext() && rCounter < dim) {
            char val = s.next().charAt(0);
            board[rCounter][cCounter] = val;
            cCounter++;
            if (cCounter == dim) {
                cCounter = 0;
                rCounter++;
            }
        }
        return board;
    }

    public boolean validSymbols(ArrayList<Character> symbolList){
        for(Character i : symbolList){
            if(!Character.isLetterOrDigit(i)){
                return false;
            }
        }
        return true;
    }

    public boolean validShape(int dim){
        if(Arrays.asList(4, 9, 16, 25, 36).contains(dim)){
            return true;
        }
        return false;
    }
}
