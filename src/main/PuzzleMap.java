package main;

import java.util.Arrays;
import java.util.List;

import static main.GameConstants.*;

public class PuzzleMap {

    private int[][] puzzleMapArray;
    private boolean[][] visitedArray;
    private PuzzleCoordinate start;
    private PuzzleCoordinate end;

    public void initializePuzzleMap(String fileContents) {

        if (fileContents == null || fileContents.trim().equals("")) {
            System.out.println(FILE_IS_EMPTY);
            return;
        }

        String[] lines = fileContents.split("[\r]?\n");
        int rows = lines.length;
        int columns = lines[0].length();

        puzzleMapArray = new int[rows][columns];
        visitedArray = new boolean[rows][columns];

        for (int i = 0; i < rows; i++) {
            if (lines[i].length() != columns) {
                System.out.println(INVALID_DATA);
                return;
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
    }

    public int getRows() {
        return puzzleMapArray.length;
    }

    public int getColumns() {
        return puzzleMapArray[0].length;
    }

    public PuzzleCoordinate getStart() {
        return start;
    }

    public PuzzleCoordinate getEnd() {
        return end;
    }

    public boolean isStart(int x, int y) {
        return x == start.getX() && y == start.getY();
    }

    public boolean isEnd(int x, int y) {
        return x == end.getX() && y == end.getY();
    }

    public boolean isInsideMap(int row, int col) {
        return (row >= 0 && row < getRows()) && (col >= 0 && col < getColumns());
    }

    public boolean isVisited(int row, int col) {
        return visitedArray[row][col];
    }

    public boolean isRock(int row, int col) {
        return puzzleMapArray[row][col] == ROCK_VALUE;
    }

    public boolean isIce(int row, int col) {
        return puzzleMapArray[row][col] == ICE_VALUE;
    }

    public void setVisited(int row, int col, boolean value) {
        visitedArray[row][col] = value;
    }

    public void printPath(List<PuzzleCoordinate> path) {
        int[][] tempPuzzleMapArray = Arrays.stream(puzzleMapArray).map(int[]::clone).toArray(int[][]::new);
        for (PuzzleCoordinate coordinate : path) {
            if (isStart(coordinate.getX(), coordinate.getY()) || isEnd(coordinate.getX(), coordinate.getY())) {
                continue;
            }
            tempPuzzleMapArray[coordinate.getX()][coordinate.getY()] = SHORTEST_PATH_VALUE;
        }
        printPuzzleMap(tempPuzzleMapArray);
    }

    public void printPuzzleMap(int[][] puzzleMapArray) {
        for (int[] ints : puzzleMapArray) {
            for (int anInt : ints) {
                switch (anInt) {
                    case ICE_VALUE:
                        System.out.print(ICE);
                        break;
                    case ROCK_VALUE:
                        System.out.print(ROCK);
                        break;
                    case START_VALUE:
                        System.out.print(ANSI_RED + START + ANSI_RESET);
                        break;
                    case FINISH_VALUE:
                        System.out.print(ANSI_RED + FINISH + ANSI_RESET);
                        break;
                    case SHORTEST_PATH_VALUE:
                        System.out.print(ANSI_RED + SHORTEST_PATH + ANSI_RESET);
                        break;
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

}
