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

import java.io.File;
import java.util.Scanner;

import static main.PuzzleConstants.*;
import static main.PuzzleConstants.IceState.FRICTION;
import static main.PuzzleConstants.IceState.FRICTIONLESS;

/**
 * <p>The {@code SlidingPuzzle} class is the starting point of the program.</p>
 *
 * <p>By running the {@code main} method the program will begin by calling the
 * startPuzzleGame() method. The user will be prompted a message to input how the
 * puzzle file path should be read, after that the user will be prompted to select
 * the puzzle moving direction, following that the user will be asked to select the
 * state of the ice mentioned in the puzzle (friction/ frictionless)</p>
 *
 * <p>The inputs scenarios will be handled by {@code handleUserInput()} method.
 * all the exceptions and failure scenarios are covered using the try catch block and the
 * user will be prompted a failure message once an exception occurred.
 * </p>
 *
 * <p>The main purpose of this class is to take the necessary user inputs and sending it to
 * {@linkplain PuzzleSolver} class to solve and display the output of the solution </p>
 *
 * @author Loganathan Bhavaneetharan
 */

public class SlidingPuzzle {

    /**
     * Launcher for the application. Initializes the Command Line version.
     * calls the {@code startPuzzleGame} method to launch the game
     */
    public static void main(String[] args) {
        new SlidingPuzzle().startPuzzleGame();
    }

    /**
     * Core method of the program. By prompting and taking the user input for
     * Puzzle file type it will call the {@code selectPuzzleFile} method to select the
     * puzzle file type according to the user input
     * <br/>
     * Exceptions are handled using a try catch block and a message will be displayed in
     * the console indicating failure scenarios
     */
    public void startPuzzleGame() {

        try {
            System.out.print(SELECT_PUZZLE_FILE_TYPE);
            int puzzleFileType = Integer.parseInt(handleUserInput(ENTER_PUZZLE_FILE_TYPE));
            selectPuzzleFile(puzzleFileType);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print(ENTER_VALID_VALUE);
        }

        System.out.print(THANK_YOU);
    }

    /**
     * User inputs will be handled by prompting a message and returning the
     * input provided by the user
     *
     * @param message the prompting message needs to be displayed on the console
     * @return a string representation of the user input
     */
    public String handleUserInput(String message) {
        String userInput;
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        userInput = scanner.next();
        return userInput;
    }

    /**
     * According to the user input the puzzle file paths will be taken
     * by prompting either to select a file from the list or provide a file path.
     * <br/>
     * This will call the {@code selectPuzzleProperties} method with selected/ provided
     * filepath
     * <br/>
     * If the user entered an invalid value for puzzleFileType, an error message will be displayed
     *
     * @param puzzleFileType the file type value (provided by the user)
     */
    public void selectPuzzleFile(int puzzleFileType) {
        if (puzzleFileType == 1) {
            String[] puzzleFiles = loadPuzzleFiles();
            int fileId = Integer.parseInt(handleUserInput(SELECT_FILE));
            String puzzleFilePath = puzzleFiles[fileId - 1];
            selectPuzzleProperties(puzzleFilePath);
        } else if (puzzleFileType == 2) {
            String puzzleFilePath = handleUserInput(ENTER_PATH);
            selectPuzzleProperties(puzzleFilePath);
        } else {
            System.out.print(ENTER_VALID_VALUE);
        }
    }

    /**
     * All the files in the "src/inputs/" directory will be displayed with an id
     * for the user to select the file of their choice. <br/>
     * The puzzle file paths will be saved to an array which we will use
     * to access the path from the user input of the file id in {@code selectPuzzleFile} method
     *
     * @return an array of the files in "src/inputs/" directory
     */
    public String[] loadPuzzleFiles() {
        String basePath = "src/inputs/";
        File[] files = new File(basePath).listFiles();
        assert files != null;
        String[] puzzleFiles = new String[files.length];
        int i = 0;
        System.out.println(PUZZLE_FILES);
        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                String puzzleFilePath = basePath + fileName;
                puzzleFiles[i] = puzzleFilePath;
                System.out.println((++i) + " : " + puzzleFilePath);
            }
        }
        return puzzleFiles;
    }

    /**
     * By taking the puzzle file path as a parameter, if the file exists,
     * the user will be asked to select the moving direction and after that if the
     * user input is a valid value, the user will be asked to select the state of the ice
     * by taking these valid input values, this will call the {@code solvePuzzle} to solve
     * the puzzle problem and output the result
     *
     * @param puzzleFilePath file path of the puzzle
     */
    public void selectPuzzleProperties(String puzzleFilePath) {
        File file = new File(puzzleFilePath);
        if (file.exists()) {
            System.out.print(SELECT_MOVING_DIRECTION_TYPE);
            int directionType = Integer.parseInt(handleUserInput(ENTER_DIRECTION_TYPE));
            int[][] directions = selectDirectionType(directionType);
            if (directions != null) {
                System.out.print(SELECT_ICE_STATE_TYPE);
                int iceStateType = Integer.parseInt(handleUserInput(ENTER_ICE_STATE_TYPE));
                IceState iceState = selectIceStateType(iceStateType);
                if (iceState != null) {
                    solvePuzzle(puzzleFilePath, directions, iceState);
                }
            }
        } else {
            System.out.println(FILE_DOES_NOT_EXIST);
            System.out.println(TRY_AGAIN);
        }
    }

    /**
     * By taking directionType as the input this will return the 2D array
     * which contains all possible movements from a cell
     * These 2D arrays are provided in the {@code PuzzleConstants} class
     *
     * @param directionType user input of the direction type
     * @return 2D array which holds the direction coordinates
     */
    public int[][] selectDirectionType(int directionType) {
        int[][] directions = null;
        if (directionType == 1) {
            directions = CARDINAL_DIRECTIONS;
        } else if (directionType == 2) {
            directions = ALL_DIRECTIONS;
        } else {
            System.out.print(ENTER_VALID_VALUE);
        }
        return directions;
    }

    /**
     * By taking iceStateType as the input this will return the Ice State enum
     * which will be used to decide whether the ice in the puzzle is frictionless or with friction
     * These enums are provided in the {@code PuzzleConstants} class
     *
     * @param iceStateType user input of the ice state type
     * @return IceState of the selected ice state type
     */
    public IceState selectIceStateType(int iceStateType) {
        IceState iceState = null;
        if (iceStateType == 1) {
            iceState = FRICTIONLESS;
        } else if (iceStateType == 2) {
            iceState = FRICTION;
        } else {
            System.out.print(ENTER_VALID_VALUE);
        }
        return iceState;
    }

    /**
     * Core method of the puzzle solving.
     * which will be using {@code PuzzleFileHandler} class to read the puzzle file and
     * if the file has contents this will initialize {@code PuzzleSolver} object with the
     * file contents and directions is using the {@code  solve} method to solve the actual
     * puzzle using the state of the ice
     *
     * @param puzzleFilePath file path of the puzzle
     * @param directions     possible movements from a cell in 2D array
     * @param iceState       state of the ice enum
     */
    public void solvePuzzle(String puzzleFilePath, int[][] directions, IceState iceState) {
        PuzzleFileHandler fileHandler = new PuzzleFileHandler(puzzleFilePath);
        String fileContents = fileHandler.readPuzzleFile();
        if (!fileContents.equals("")) {
            PuzzleSolver puzzleSolver = new PuzzleSolver(fileContents, directions, iceState);
            puzzleSolver.solve();
        }
    }

}
