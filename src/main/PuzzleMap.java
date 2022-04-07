package main;

import static main.GameConstants.*;

public class PuzzleMap {

    private int[][] puzzleMapArray;
    private boolean[][] visitedArray;
    private PuzzleCoordinate start;
    private PuzzleCoordinate end;

    public int[][] initializePuzzleMap(String fileContents) {

        if (fileContents == null || fileContents.trim().equals("")) {
            System.out.println(FILE_IS_EMPTY);
            return null;
        }

        String[] lines = fileContents.split("[\r]?\n");
        int rows = lines.length;
        int columns = lines[0].length();

        puzzleMapArray = new int[rows][columns];
        visitedArray = new boolean[rows][columns];

        for (int i = 0; i < rows; i++) {
            if (lines[i].length() != columns) {
                System.out.println(INVALID_DATA);
                return null;
            } else {
                for (int j = 0; j < columns; j++) {
                    switch (lines[i].charAt(j)) {
                        case ICE:
                            puzzleMapArray[i][j] = ICE_VALUE;
                            break;
                        case ROCK:
                            puzzleMapArray[i][j] = ROCK_VALUE;
                            break;
                        case START:
                            puzzleMapArray[i][j] = START_VALUE;
                            start = new PuzzleCoordinate(i, j);
                            break;
                        case FINISH:
                            puzzleMapArray[i][j] = FINISH_VALUE;
                            end = new PuzzleCoordinate(i, j);
                            break;
                    }
                }
            }
        }
        return puzzleMapArray;
    }


    public void printPuzzleMap(int[][] puzzleMapArray) {
        for (int[] ints : puzzleMapArray) {
            for (int anInt : ints) {
                switch (anInt) {
                    case ICE_VALUE:
                        System.out.print(ICE + " ");
                        break;
                    case ROCK_VALUE:
                        System.out.print(ROCK + " ");
                        break;
                    case START_VALUE:
                        System.out.print(START + " ");
                        break;
                    case FINISH_VALUE:
                        System.out.print(FINISH + " ");
                        break;
                }
            }
            System.out.println();
        }
    }
}
