package puzzle;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.Duration;

/**
 * Contains some logic for ad hoc testing of the program.
 */
public class Main {

    private static final String FILENAME_0 = "/Users/garrettdimick/SamplePuzzles/Input/Puzzle-16x16-0001.txt";
    private static final String FILENAME_1 = "/Users/garrettdimick/SamplePuzzles/Input/Puzzle-9x9-0001.txt";
    public static void main(String[] args) throws Exception {
        if(args[0].equals("-h")){
            System.out.println("Possible inputs are: \n<input_file_name>\n  reads a sudoku puzzle from the given input file" +
                    "and displays the output to the console\n" +
                    "<input_file_name> <output_file_name>\n  reads a sudoku puzzle from the specified input and " +
                    "writes the output to the specified output file");
            return;
        }
        if(args.length == 1){
            SudokuGame sg = new SudokuGame(args[0]);
            SudokuSolver solver;
            if(sg.dimension <= 9){
                solver = new RegularSolver(sg);
            }
            else{
                solver = new HugeSolver(sg);
            }
            System.out.print(sg.printBoard(sg.originalBoard));
            Instant start = Instant.now();
            if(solver.solveSudoku()){
                System.out.println("Board Solved.");
                System.out.print(sg.printBoard(sg.currentBoard));
                System.out.println("-------------------------");
            }
            else{
                System.out.println("Couldn't solve the thing.");
            }
            Instant finish = Instant.now();
            float timeElapsed = Duration.between(start, finish).toMillis();
            System.out.println("Total Time: " + timeElapsed + " milliseconds");
        }
        if(args.length == 2){
            StringBuilder message = new StringBuilder();
            SudokuGame sg = new SudokuGame(args[0]);
            SudokuSolver solver;
            if(sg.dimension <= 9){
                solver = new RegularSolver(sg);
            }
            else{
                solver = new HugeSolver(sg);
            }
            message.append(sg.printBoard(sg.originalBoard));
            Instant start = Instant.now();
            if(solver.solveSudoku()){
                message.append("\n");
                message.append("Board Solved.\n");
                message.append(sg.printBoard(sg.currentBoard));
                message.append("\n-------------------------\n");
            }
            else{
                message.append("Couldn't solve the thing.");
            }
            Instant finish = Instant.now();
            float timeElapsed = Duration.between(start, finish).toMillis();
            message.append("Total Time: " + timeElapsed + " milliseconds\n");
            sg.pp.saveSolutionToFile(args[1], message.toString());
        }

    }
}
