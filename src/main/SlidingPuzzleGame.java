package main;

import java.io.File;
import java.util.Scanner;

import static main.GameConstants.*;
import static main.GameConstants.IceState.FRICTION;
import static main.GameConstants.IceState.FRICTIONLESS;

public class SlidingPuzzleGame {

    public static void main(String[] args) {
        SlidingPuzzleGame slidingPuzzleGame = new SlidingPuzzleGame();
        slidingPuzzleGame.startPuzzleGame();
    }

    public void startPuzzleGame() {

        System.out.print(WELCOME_TEXT);
        System.out.print(SELECT_MOVABLE_TYPE);
        System.out.print(SELECT_ALGORITHM_TYPE);

        try {
            int puzzleFileType = Integer.parseInt(handleUserInput(ENTER_PUZZLE_FILE_TYPE));
            selectPuzzleFile(puzzleFileType);
        } catch (Exception e) {
            System.out.print(ENTER_VALID_VALUE);
        }

        System.out.print(THANK_YOU);
    }

    public String handleUserInput(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        return scanner.next();
    }

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

    public void selectPuzzleProperties(String puzzleFilePath) {
        File file = new File(puzzleFilePath);
        if (file.exists()) {
            int directionType = Integer.parseInt(handleUserInput(ENTER_DIRECTION_TYPE));
            int[][] directions = selectDirectionType(directionType);
            if (directions != null) {
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

    public void solvePuzzle(String puzzleFilePath, int[][] directions, IceState iceState) {
        PuzzleFileHandler fileHandler = new PuzzleFileHandler(puzzleFilePath);
        String fileContents = fileHandler.readPuzzleFile();
        if (fileContents != null) {
            PuzzleSolver puzzleSolver = new PuzzleSolver(fileContents, directions);
            puzzleSolver.solve(iceState);
        }
    }

}
