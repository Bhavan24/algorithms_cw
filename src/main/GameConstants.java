package main;

public class GameConstants {

    // Message Constants
    public static final String WELCOME_TEXT = (
        "+ - - - - - - - - - - - - - - - - - - - - - - +\n" +
        "|         WELCOME TO SLIDING PUZZLES          |\n" +
        "+ - - - - - - - - - - - - - - - - - - - - - - +\n"
    );
    public static final String ENTER_PATH = "\nPLEASE ENTER THE PUZZLE FILE PATH: ";
    public static final String PUZZLE_LOADED = "\nPUZZLE LOADED!\n";
    public static final String FILE_DOES_NOT_EXIST = "\nFILE DOES NOT EXIST!";
    public static final String THANK_YOU = "\nPLEASE TRY AGAIN!\nTHANK YOU!";
    public static final String FILE_IS_EMPTY = "\nPUZZLE FILE IS EMPTY!";
    public static final String INVALID_DATA = "\nPUZZLE FILE CONTAINS INVALID DATA!";
    public static final String SELECT_FILE = "\nENTER FILE ID (ENTER 0 TO EXIT): ";
    public static final String ENTER_VALID_FILE_ID = "\nPLEASE ENTER A VALID FILE ID!";
    public static final String RUNNING_TIME = "\nALGORITHM RUNNING TIME: ";

    // Map Constants
    public static final char ICE = '.';
    public static final char ROCK = '0';
    public static final char START = 'S';
    public static final char FINISH = 'F';

    // Algorithmic Constants
    public static final int ICE_VALUE = 0;
    public static final int ROCK_VALUE = 1;
    public static final int START_VALUE = 2;
    public static final int FINISH_VALUE = 3;
    public static final int PATH_VALUE = 4;

    // DIRECTIONS: top, right, down, left
    public static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    // Console Colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\033[1;91m";
}
