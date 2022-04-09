package main;

public class GameConstants {

    // Message Constants
    public static final String WELCOME_TEXT =
                    "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n" +
                    "|                    PUZZLE FILES                   |\n" +
                    "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n" +
                    "|  1. RUN A PUZZLE FROM FILE PATH                   |\n" +
                    "|  2. SELECT A PUZZLE FROM ALL PUZZLES              |\n" +
                    "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n";
    public static final String SELECT_MOVABLE_TYPE =
                    "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n" +
                    "|                 MOVING DIRECTIONS                 |\n" +
                    "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n" +
                    "|  1. 4-CARDINAL DIRECTIONS (TOP,DOWN,RIGHT,LEFT)   |\n" +
                    "|  2. 8-CARDINAL DIRECTIONS (ALL DIRECTIONS)        |\n" +
                    "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n";
    public static final String SELECT_ALGORITHM_TYPE =
                    "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n" +
                    "|             STATE OF THE PUZZLE ICE               |\n" +
                    "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n" +
                    "|  1. FRICTIONLESS ICE (USER WILL SLIDE)            |\n" +
                    "|  2. ICE WITHOUT FRICTION (USER WILL NOT SLIDE)    |\n" +
                    "+ - - - - - - - - - - - - - - - - - - - - - - - - - +\n";
    public static final String ENTER_PUZZLE_FILE_TYPE = "\nPLEASE ENTER THE PUZZLE FILE TYPE: ";
    public static final String ENTER_DIRECTION_TYPE = "\nPLEASE ENTER THE MOVING DIRECTION TYPE: ";
    public static final String ENTER_ICE_STATE_TYPE = "\nPLEASE ENTER THE PUZZLE ICE STATE TYPE: ";
    public static final String ENTER_PATH = "\nPLEASE ENTER THE PUZZLE FILE PATH: ";
    public static final String PUZZLE_LOADED = "\nPUZZLE LOADED!\n";
    public static final String FILE_DOES_NOT_EXIST = "\nFILE DOES NOT EXIST!";
    public static final String TRY_AGAIN = "\nPLEASE TRY AGAIN!";
    public static final String THANK_YOU = "\nTHANK YOU!";
    public static final String FILE_IS_EMPTY = "\nPUZZLE FILE IS EMPTY!";
    public static final String INVALID_DATA = "\nPUZZLE FILE CONTAINS INVALID DATA!";
    public static final String SELECT_FILE = "\nENTER FILE ID (ENTER 0 TO EXIT): ";
    public static final String ENTER_VALID_VALUE = "\nPLEASE ENTER A VALID VALUE!\n";
    public static final String RUNNING_TIME = "\nALGORITHM RUNNING TIME: ";
    public static final String PUZZLE_FILES = "\n--------PUZZLE FILES--------";

    // Map Constants
    public static final char ICE = '.';
    public static final char ROCK = '0';
    public static final char START = 'S';
    public static final char FINISH = 'F';
    public static final char SHORTEST_PATH = '*';

    // Algorithmic Constants
    public static final int ICE_VALUE = 0;
    public static final int ROCK_VALUE = 1;
    public static final int START_VALUE = 2;
    public static final int FINISH_VALUE = 3;
    public static final int SHORTEST_PATH_VALUE = 4;

    // DIRECTIONS: top, right, down, left
//    public static final int[][] DIRECTIONS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 0}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    public static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public enum Directions {
        TOP, RIGHT, LEFT, DOWN
    }

    // Console Colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\033[1;91m";
}
