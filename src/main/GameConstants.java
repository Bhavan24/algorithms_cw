package main;

public class GameConstants {

    // Message Constants
    public static final String WELCOME_TEXT = "+ - - - - - - - - - - - - - - - - - - - - - - +\n" + "|         WELCOME TO SLIDING PUZZLES          |\n" + "+ - - - - - - - - - - - - - - - - - - - - - - +\n" + "\nPLEASE ENTER THE PUZZLE FILE PATH: ";
    public static final String PUZZLE_LOADED = "\nPUZZLE LOADED!\n";
    public static final String FILE_DOES_NOT_EXIST = "\nFILE DOES NOT EXIST!";
    public static final String THANK_YOU = "\nGAME OVER!\nTHANK YOU!";
    public static final String FILE_IS_EMPTY = "\nPUZZLE FILE IS EMPTY!";
    public static final String INVALID_DATA = "\nPUZZLE FILE CONTAINS INVALID DATA!";
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
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
}
