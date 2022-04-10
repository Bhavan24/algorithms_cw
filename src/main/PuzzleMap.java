package main;

import java.util.Arrays;
import java.util.List;

import static main.GameConstants.*;

public class PuzzleMap {

    private PuzzleCoordinate[][] puzzleMapArray;
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

        puzzleMapArray = new PuzzleCoordinate[rows][columns];
        visitedArray = new boolean[rows][columns];

        int id = 0;
        for (int y = 0; y < rows; y++) {
            if (lines[y].length() != columns) {
                System.out.println(INVALID_DATA);
                return;
            } else {
                for (int x = 0; x < columns; x++) {
                    switch (lines[y].charAt(x)) {
                        case ICE:
                            puzzleMapArray[y][x] = new PuzzleCoordinate(id, x, y, ICE, ICE_VALUE);
                            break;
                        case ROCK:
                            puzzleMapArray[y][x] = new PuzzleCoordinate(id, x, y, ROCK, ROCK_VALUE);
                            break;
                        case START:
                            puzzleMapArray[y][x] = new PuzzleCoordinate(id, x, y, START, START_VALUE);
                            start = puzzleMapArray[y][x];
                            break;
                        case FINISH:
                            puzzleMapArray[y][x] = new PuzzleCoordinate(id, x, y, FINISH, FINISH_VALUE);
                            end = puzzleMapArray[y][x];
                            break;
                    }
                    id++;
                }
            }
        }
    }

    public PuzzleCoordinate[][] getPuzzleMapArray() {
        return puzzleMapArray;
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

    public boolean isValidCoordinate(int row, int col) {
        return (row >= 0 && row < getRows()) && (col >= 0 && col < getColumns());
    }

    public boolean isVisited(int row, int col) {
        return visitedArray[col][row];
    }

    public boolean isRock(int row, int col) {
        return puzzleMapArray[col][row].getValue() == ROCK_VALUE;
    }

    public boolean isIce(int row, int col) {
        return puzzleMapArray[col][row].getValue() == ICE_VALUE;
    }

    public boolean isStart(PuzzleCoordinate p) {
        return (p != null) && isStart(p.getX(), p.getY());
    }

    public boolean isEnd(PuzzleCoordinate p) {
        return (p != null) && isEnd(p.getX(), p.getY());
    }

    public boolean isValidCoordinate(PuzzleCoordinate p) {
        return (p != null) && isValidCoordinate(p.getX(), p.getY());
    }

    public boolean isVisited(PuzzleCoordinate p) {
        return (p != null) && isVisited(p.getX(), p.getY());
    }

    public boolean isRock(PuzzleCoordinate p) {
        return (p != null) && isRock(p.getX(), p.getY());
    }

    public boolean isIce(PuzzleCoordinate p) {
        return (p != null) && isIce(p.getX(), p.getY());
    }

    public void setVisited(int row, int col, boolean value) {
        visitedArray[col][row] = value;
    }

    public void printPath(List<PuzzleCoordinate> path) {
        PuzzleCoordinate[][] tempPuzzleMapArray = Arrays.stream(puzzleMapArray).map(PuzzleCoordinate[]::clone).toArray(PuzzleCoordinate[][]::new);
        int id = 0;
        for (PuzzleCoordinate coordinate : path) {
            if (isStart(coordinate.getX(), coordinate.getY()) || isEnd(coordinate.getX(), coordinate.getY())) {
                continue;
            }
            tempPuzzleMapArray[coordinate.getY()][coordinate.getX()] = new PuzzleCoordinate(id, coordinate.getX(), coordinate.getY(), SHORTEST_PATH, SHORTEST_PATH_VALUE);
            id++;
        }
        printPuzzleMap(tempPuzzleMapArray);
    }

    public void printPuzzleMap(PuzzleCoordinate[][] puzzleMapArray) {
        for (PuzzleCoordinate[] puzzleCoordinates : puzzleMapArray) {
            for (PuzzleCoordinate puzzleCoordinate : puzzleCoordinates) {
                switch (puzzleCoordinate.getCharacter()) {
                    case ICE:
                        System.out.print(ICE);
                        break;
                    case ROCK:
                        System.out.print(ROCK);
                        break;
                    case START:
                        System.out.print(ANSI_RED + START + ANSI_RESET);
                        break;
                    case FINISH:
                        System.out.print(ANSI_RED + FINISH + ANSI_RESET);
                        break;
                    case SHORTEST_PATH:
                        System.out.print(ANSI_RED + SHORTEST_PATH + ANSI_RESET);
                        break;
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

}
