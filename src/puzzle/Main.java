package puzzle;

import java.io.FileNotFoundException;

public class Main {

    private static final String FILENAME = "/Users/garrettdimick/SamplePuzzles/Input/Puzzle-25x25-0901.txt";

    public static void main(String[] args) throws Exception {
	// write your code here
        if(args[0].equals("-h")){
            System.out.println("Possible inputs are: \n<input_file_name>\n  reads a sudoku puzzle from the given input file" +
                    "and displays the output to the console\n" +
                    "<input_file_name> <output_file_name>\n  reads a sudoku puzzle from the specified input and " +
                    "writes the output to the specified output file");
        }

        SudokuGame sg = new SudokuGame(FILENAME);
        System.out.print(sg.printBoard(sg.originalBoard));

//        System.out.println("THE CURRENT BOARD IS: ");
//        System.out.print(sg.printBoard(sg.currentBoard));
//        System.out.println("The current symbols are: ");
//        sg.printSymbols();
        sg.solveSudoku();
        if(sg.solveSudoku()){
            System.out.println("Board Solved.");
            System.out.print(sg.printBoard(sg.currentBoard));
        }
        else{
            System.out.println("Couldn't solve the thing.");
        }
    }
}
