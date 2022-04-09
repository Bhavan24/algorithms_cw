package main;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static main.GameConstants.*;

public class SlidingPuzzleGame {

    public static void main(String[] args) {

        System.out.print(WELCOME_TEXT);
        System.out.print(SELECT_MOVABLE_TYPE);
        System.out.print(SELECT_ALGORITHM_TYPE);

        try {

            int puzzleFileType = handleUserInput(ENTER_PUZZLE_FILE_TYPE);
            int directionType = handleUserInput(ENTER_DIRECTION_TYPE);
            int iceState = handleUserInput(ENTER_ICE_STATE_TYPE);

            Scanner scanner = new Scanner(System.in);
            if (puzzleFileType == 1) {
                System.out.print(ENTER_PATH);
                String puzzleFilePath = scanner.next();
                solvePuzzle(puzzleFilePath, directionType, iceState);
            } else if (puzzleFileType == 2) {
                String[] puzzleFiles = loadPuzzleFiles();
                System.out.print(SELECT_FILE);
                int fileId = scanner.nextInt();
                solvePuzzle(puzzleFiles[fileId - 1], directionType, iceState);
            } else {
                System.out.print(ENTER_VALID_VALUE);
            }

        } catch (Exception e) {
            System.out.print(ENTER_VALID_VALUE);
        }
        System.out.print(THANK_YOU);
    }

    public static String[] loadPuzzleFiles() {
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

    public static int handleUserInput(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(message);
        int userInput = -1;
        try {
            userInput = scanner.nextInt();
        } catch (Exception e) {
            System.out.print(ENTER_VALID_VALUE);
        }
        return userInput;
    }

    public static int[][] selectDirectionType(int directionType) {
        int[][] DIRECTIONS = null;
        if (directionType == 1) {
            DIRECTIONS = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        } else if (directionType == 2) {
            DIRECTIONS = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 0}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        } else {
            System.out.print(ENTER_VALID_VALUE);
        }
        return DIRECTIONS;
    }

    public static void solvePuzzle(String puzzleFilePath, int directionType, int iceState) {
        int[][] DIRECTIONS = selectDirectionType(directionType);
        System.out.println(Arrays.deepToString(DIRECTIONS));
        PuzzleFileHandler fileHandler = new PuzzleFileHandler(puzzleFilePath);
        String fileContents = fileHandler.readPuzzleFile();
        if (fileContents != null) {
            PuzzleMap puzzleMap = new PuzzleMap();
            puzzleMap.initializePuzzleMap(fileContents);
            if (iceState == 1) {
                PuzzleSolver2 puzzleSolver2 = new PuzzleSolver2(puzzleMap);
                PuzzleGraph puzzleGraph = new PuzzleGraph();
                puzzleSolver2.solve(puzzleGraph);
            } else if (iceState == 2) {
                PuzzleSolver puzzleSolver = new PuzzleSolver();
                List<PuzzleCoordinate> path = puzzleSolver.solve(puzzleMap);
                puzzleMap.printPath(path);
            } else {
                System.out.print(ENTER_VALID_VALUE);
            }
        }
    }

}
