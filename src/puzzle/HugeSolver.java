package puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;

/**
 * HugeSolver is used when puzzles are larger than the standard 9x9.
 */
public class HugeSolver extends SudokuSolver{
        protected HashMap<Character, Integer> appearances;

        public HugeSolver(SudokuGame game){
            super.sg = game;
            appearances = new HashMap();
            appearances.putAll(getAppearances(super.sg.originalBoard));
        }

    public ArrayList<Character> nextValues(int r, int c) {
        ArrayList<Character> pv = sortByAppearance(appearances);
        ArrayList<Character> possibleValues = reduceList(pv, r, c);
        return possibleValues;
    }

    /**
         * Reduces the list of symbols to only possible values for a given cell located at [row][col]
         * @param potentialValues
         * @param row
         * @param col
         * @return
         */
        public ArrayList<Character> reduceList(ArrayList<Character> potentialValues, int row, int col) {
            // remove all values that appear in the row
            ArrayList<Character> reducedList = (ArrayList<Character>) potentialValues.clone();
            ArrayList<Character> valsToRemove = new ArrayList();
            for (int i = 0; i < super.sg.dimension; i++) {
                valsToRemove.add(super.sg.currentBoard[row][i]);
            }
            //remove all values that appear in the same column
            for (int j = 0; j < super.sg.dimension; j++) {
                valsToRemove.add(super.sg.currentBoard[j][col]);
            }
            // remove all values that appear in the same box
            int boxSize = (int) Math.sqrt(super.sg.dimension);
            int r = row - row % boxSize;
            int c = col - col % boxSize;
            for (int i = r; i < r + boxSize; i++) {
                for (int j = c; j < c + boxSize; j++) {
                    valsToRemove.add(super.sg.currentBoard[i][j]);
                }
            }
            reducedList.removeAll(valsToRemove);
            return reducedList;
        }

        /**
         * Returns a hashmap containing the values as keys and the number of times they appear on the board as values.
         * This helps optimize the algorithm by starting down more likely paths
         * @param board
         * @return
         */
        protected HashMap<Character, Integer> getAppearances(char[][] board){
            HashMap<Character, Integer> appearanceCount = new HashMap<Character, Integer>();
            for(int k=0;k<super.sg.symbols.size();k++){
                appearanceCount.put(super.sg.symbols.get(k), 0);
            }
            for(int i = 0;i<super.sg.dimension;i++){
                for(int j = 0;j<super.sg.dimension;j++){
                    Character key = board[i][j];
                    if(key == '-'){
                        continue;
                    }
                    appearanceCount.put(key, appearanceCount.get(key) + 1);
                }
            }
            return appearanceCount;
        }

        /**
         * updates the number of times a value has been used in a solution
         * @param k
         */
        protected void updateCount(Character k){
            appearances.put(k, appearances.get(k) + 1);
        }

        /**
         * Sorts the values in the puzzle by the number of times they have been used.
         */
        protected ArrayList<Character> sortByAppearance(HashMap<Character,Integer> hash){
            Map<Character,Integer> sorted = hash.entrySet().stream().sorted(comparingByValue()).collect(toMap(Map.Entry::getKey,
                    Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
            ArrayList<Character> sortedList = new ArrayList<>(sorted.keySet());
            return sortedList;
        }
    }

