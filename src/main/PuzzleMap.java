package main;

import java.util.Arrays;
import java.util.List;

import static main.PuzzleConstants.*;

public class PuzzleMap {

    private PuzzleCoordinate[][] puzzleMapArray;
    private PuzzleCoordinate start;
    private PuzzleCoordinate end;

    public void initializePuzzleMap(String fileContents) {

        if (fileContents == null || fileContents.trim().equals("")) {
            System.out.println(FILE_IS_EMPTY);
            return;
        }

        String[] lines = fileContents.split(EOF_REGEX);
        int rows = lines.length;
        int columns = lines[0].length();

        puzzleMapArray = new PuzzleCoordinate[rows][columns];

        int id = 0;
        for (int y = 0; y < rows; y++) {
            if (lines[y].length() != columns) {
                System.out.println(INVALID_DATA);
                return;
            } else {
                for (int x = 0; x < columns; x++) {
                    switch (lines[y].charAt(x)) {
                        case ICE:
                            puzzleMapArray[y][x] = new PuzzleCoordinate(id, x, y, ICE);
                            break;
                        case ROCK:
                            puzzleMapArray[y][x] = new PuzzleCoordinate(id, x, y, ROCK);
                            break;
                        case START:
                            puzzleMapArray[y][x] = new PuzzleCoordinate(id, x, y, START);
                            start = puzzleMapArray[y][x];
                            break;
                        case FINISH:
                            puzzleMapArray[y][x] = new PuzzleCoordinate(id, x, y, FINISH);
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

    public boolean isValidCoordinate(int x, int y) {
        return (x >= 0 && x < getRows()) && (y >= 0 && y < getColumns());
    }

    public boolean isRock(int x, int y) {
        return puzzleMapArray[y][x].getValue() == ROCK;
    }

    public boolean isIce(int x, int y) {
        return puzzleMapArray[y][x].getValue() == ICE;
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

    public boolean isRock(PuzzleCoordinate p) {
        return (p != null) && isRock(p.getX(), p.getY());
    }

    public boolean isIce(PuzzleCoordinate p) {
        return (p != null) && isIce(p.getX(), p.getY());
    }

    public PuzzleCoordinate getPuzzleCoordinate(int id) {
        for (PuzzleCoordinate[] puzzleCoordinates : puzzleMapArray) {
            for (PuzzleCoordinate puzzleCoordinate : puzzleCoordinates) {
                if (puzzleCoordinate.getId() == id) {
                    return puzzleCoordinate;
                }
            }
        }
        return null;
    }

    public PuzzleCoordinate getPuzzleCoordinate(int x, int y) {
        if (isValidCoordinate(x, y)) {
            return puzzleMapArray[y][x];
        }
        return null;
    }

    public void printPath(List<PuzzleCoordinate> path) {
        PuzzleCoordinate[][] tempPuzzleMapArray = Arrays.stream(puzzleMapArray).map(PuzzleCoordinate[]::clone).toArray(PuzzleCoordinate[][]::new);
        int id = 0;
        for (PuzzleCoordinate coordinate : path) {
            if (isStart(coordinate.getX(), coordinate.getY()) || isEnd(coordinate.getX(), coordinate.getY())) {
                continue;
            }
            tempPuzzleMapArray[coordinate.getY()][coordinate.getX()] = new PuzzleCoordinate(id, coordinate.getX(), coordinate.getY(), SHORTEST_PATH);
            id++;
        }
        printPuzzleMap(tempPuzzleMapArray);
    }

    public void printPuzzleMap(PuzzleCoordinate[][] puzzleMapArray) {
        for (PuzzleCoordinate[] puzzleCoordinates : puzzleMapArray) {
            for (PuzzleCoordinate puzzleCoordinate : puzzleCoordinates) {
                switch (puzzleCoordinate.getValue()) {
                    case ICE:
                        System.out.print(ANSI_GREEN + ICE + ANSI_RESET);
                        break;
                    case ROCK:
                        System.out.print(ANSI_BLUE + ROCK + ANSI_RESET);
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
