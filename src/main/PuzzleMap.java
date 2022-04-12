/******************************************************************************
 * Name: Loganathan Bhavaneetharan
 * UOW ID: w1810599
 * IIT ID: 20201212
 * B.Eng.Software Engineering, 2nd Year
 *
 *  Description:
 *        The programs takes the filepath, moving direction, state of the ice
 *		  as inputs and will provide the shortest path from point 'S' to
 *		  point 'F' using breadth-first search algorithm
 *
 * Run the program by running SlidingPuzzle.main()
 *
 *****************************************************************************/

package main;

import java.util.Arrays;
import java.util.List;

import static main.PuzzleConstants.*;

/**
 * <p>The {@code PuzzleMap} the map where the text file is converted into a 2D
 * {@code PuzzleCoordinate} array
 *
 * @author Loganathan Bhavaneetharan
 */

public class PuzzleMap {

    /**
     * The 2D array of PuzzleCoordinates of this {@code PuzzleMap}.
     */
    private PuzzleCoordinate[][] puzzleCoordinatesMap;

    /**
     * The starting PuzzleCoordinate {@code PuzzleMap}.
     * The PuzzleCoordinate which has the character 'S'
     */
    private PuzzleCoordinate start;

    /**
     * The ending PuzzleCoordinate {@code PuzzleMap}.
     * The PuzzleCoordinate which has the character 'F'
     */
    private PuzzleCoordinate end;

    public PuzzleMap(String fileContents) {
        initializePuzzleMap(fileContents);
    }

    /**
     * Populate the puzzleCoordinatesMap 2D array with PuzzleCoordinates
     * puzzleCoordinatesMap has the rows value which is equal to the lines in the
     * file contents and columns value is the length of a single line of that text file
     * <br/>
     * if the text file contains in consistent values the program will display an error message
     * each array is populated with a {@code PuzzleCoordinate(id, x-coordinate, y-coordinate, character);}
     *
     * @param fileContents the file contents of the puzzle text file
     */
    public void initializePuzzleMap(String fileContents) {

        if (fileContents == null || fileContents.trim().equals("")) {
            System.out.println(FILE_IS_EMPTY);
            return;
        }

        String[] lines = fileContents.split(EOF_REGEX);
        int rows = lines.length;
        int columns = lines[0].length();

        puzzleCoordinatesMap = new PuzzleCoordinate[rows][columns];

        int id = 0;
        for (int y = 0; y < rows; y++) {
            if (lines[y].length() != columns) {
                System.out.println(INVALID_DATA);
                return;
            } else {
                for (int x = 0; x < columns; x++) {
                    switch (lines[y].charAt(x)) {
                        case ICE:
                            puzzleCoordinatesMap[y][x] = new PuzzleCoordinate(id, x, y, ICE);
                            break;
                        case ROCK:
                            puzzleCoordinatesMap[y][x] = new PuzzleCoordinate(id, x, y, ROCK);
                            break;
                        case START:
                            puzzleCoordinatesMap[y][x] = new PuzzleCoordinate(id, x, y, START);
                            start = puzzleCoordinatesMap[y][x];
                            break;
                        case FINISH:
                            puzzleCoordinatesMap[y][x] = new PuzzleCoordinate(id, x, y, FINISH);
                            end = puzzleCoordinatesMap[y][x];
                            break;
                    }
                    id++;
                }
            }
        }
    }

    /**
     * Returns the data populated the puzzleCoordinatesMap of this {@code PuzzleMap}.
     *
     * @return the puzzleCoordinatesMap (2D Array)
     */
    public PuzzleCoordinate[][] getPuzzleCoordinatesMap() {
        return puzzleCoordinatesMap;
    }

    public int getRows() {
        return puzzleCoordinatesMap.length;
    }

    public int getColumns() {
        return puzzleCoordinatesMap[0].length;
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
        return puzzleCoordinatesMap[y][x].getValue() == ROCK;
    }

    public boolean isIce(int x, int y) {
        return puzzleCoordinatesMap[y][x].getValue() == ICE;
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
        for (PuzzleCoordinate[] puzzleCoordinates : puzzleCoordinatesMap) {
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
            return puzzleCoordinatesMap[y][x];
        }
        return null;
    }

    public void printPath(List<PuzzleCoordinate> path) {
        PuzzleCoordinate[][] tempPuzzleCoordinatesMap = Arrays.stream(puzzleCoordinatesMap).map(PuzzleCoordinate[]::clone).toArray(PuzzleCoordinate[][]::new);
        int id = 0;
        for (PuzzleCoordinate coordinate : path) {
            if (isStart(coordinate.getX(), coordinate.getY()) || isEnd(coordinate.getX(), coordinate.getY())) {
                continue;
            }
            tempPuzzleCoordinatesMap[coordinate.getY()][coordinate.getX()] = new PuzzleCoordinate(id, coordinate.getX(), coordinate.getY(), SHORTEST_PATH);
            id++;
        }
        printPuzzleCoordinatesMap(tempPuzzleCoordinatesMap);
    }

    public void printPuzzleCoordinatesMap(PuzzleCoordinate[][] puzzleCoordinatesMap) {
        for (PuzzleCoordinate[] puzzleCoordinates : puzzleCoordinatesMap) {
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
