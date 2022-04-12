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

/**
 * <p>The {@code PuzzleConstants} class contains the constants used
 * in this project.</p>
 *
 * <ul>
 *     This class provides the constants related to,
 *     <li>User prompting messages</li>
 *     <li>Puzzle map text file characters</li>
 *     <li>Moving direction coordinates</li>
 *     <li>Java console colors, eof regex strings </li>
 * </ul>
 *
 * <ul>
 *     This class also provides the enums related to,
 *     <li>State of the ice (the character which has the value of '.')</li>
 *     <li>Path directions (up, down, left, right)</li>
 * </ul>
 * <p>
 * Using constants makes the program more scalable and the code more reusable
 *
 * @author Loganathan Bhavaneetharan
 */

public final class PuzzleConstants {

    /**
     * Prompting Message Constants
     */
    public static final String SELECT_PUZZLE_FILE_TYPE =
            "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n" +
            "|                    PUZZLE FILES                   |\n" +
            "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n" +
            "|  1. SELECT A PUZZLE FROM ALL PUZZLES              |\n" +
            "|  2. RUN A PUZZLE FROM FILE PATH                   |\n" +
            "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n";
    public static final String SELECT_MOVING_DIRECTION_TYPE =
            "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n" +
            "|                 MOVING DIRECTIONS                 |\n" +
            "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n" +
            "|  1. 4-CARDINAL DIRECTIONS (TOP,DOWN,RIGHT,LEFT)   |\n" +
            "|  2. 8-CARDINAL DIRECTIONS (ALL DIRECTIONS)        |\n" +
            "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n";
    public static final String SELECT_ICE_STATE_TYPE =
            "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n" +
            "|             STATE OF THE PUZZLE ICE               |\n" +
            "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n" +
            "|  1. FRICTIONLESS ICE (USER WILL SLIDE)            |\n" +
            "|  2. ICE WITH FRICTION (USER WILL NOT SLIDE)       |\n" +
            "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n";
    public static final String ENTER_PUZZLE_FILE_TYPE = "\nPLEASE ENTER THE PUZZLE FILE TYPE: ";
    public static final String ENTER_DIRECTION_TYPE = "\nPLEASE ENTER THE MOVING DIRECTION TYPE: ";
    public static final String ENTER_ICE_STATE_TYPE = "\nPLEASE ENTER THE PUZZLE ICE STATE TYPE: ";
    public static final String ENTER_PATH = "\nPLEASE ENTER THE PUZZLE FILE PATH: ";
    public static final String PUZZLE_LOADED = "\nPUZZLE LOADED!\n";
    public static final String FILE_DOES_NOT_EXIST = "\nFILE DOES NOT EXIST!";
    public static final String TRY_AGAIN = "\nPLEASE TRY AGAIN!";
    public static final String THANK_YOU = "\nTHANK YOU!\n";
    public static final String FILE_IS_EMPTY = "\nPUZZLE FILE IS EMPTY!";
    public static final String CANNOT_SOLVE_PUZZLE = "\nCANNOT SOLVE PUZZLE!";
    public static final String INVALID_DATA = "\nPUZZLE FILE CONTAINS INVALID DATA!";
    public static final String SELECT_FILE = "\nENTER FILE ID: ";
    public static final String ENTER_VALID_VALUE = "\nPLEASE ENTER A VALID VALUE!\n";
    public static final String RUNNING_TIME = "\nALGORITHM RUNNING TIME: ";
    public static final String PUZZLE_FILES = "\n--------PUZZLE FILES--------";

    /**
     * Characters in the input map file
     */
    public static final char ICE = '.';
    public static final char ROCK = '0';
    public static final char START = 'S';
    public static final char FINISH = 'F';
    public static final char SHORTEST_PATH = '*';

    /**
     * Direction coordinates, indicates the moving directions of the puzzle pathfinder
     * The arrays contains all possible movements from a cell
     */
    public static final int[][] ALL_DIRECTIONS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    public static final int[][] CARDINAL_DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    /**
     * ANSI escape codes for printing color text in console
     */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\033[1;91m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Regex for End of the file/line
     */
    public static final String EOF_REGEX = "[\r]?\n";

    /**
     * Enum type that indicates the state of the ice
     */
    public enum IceState {
        FRICTIONLESS, FRICTION
    }

    /**
     * Enum type that indicates all the four path directions
     */
    public enum PathDirection {
        up, down, left, right
    }

}
