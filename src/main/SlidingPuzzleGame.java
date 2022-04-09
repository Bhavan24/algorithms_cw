package main;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import static main.GameConstants.*;

public class SlidingPuzzleGame {

    public static void main(String[] args) {

        System.out.print(WELCOME_TEXT);

        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print(ENTER_PROGRAM_TYPE);
                int programType = scanner.nextInt();

                if (programType == 1) {
                    System.out.print(ENTER_PATH);
                    String puzzleFilePath = scanner.next();
                    solvePuzzle(puzzleFilePath);
                } else if (programType == 2) {
                    String[] puzzleFiles = loadPuzzleFiles();
                    System.out.print(SELECT_FILE);
                    int fileId = scanner.nextInt();
                    if (fileId == 0) break;
                    solvePuzzle(puzzleFiles[fileId - 1]);
                } else if (programType == 0) {
                    break;
                } else {
                    System.out.print(ENTER_VALID_VALUE);
                }

            } catch (Exception e) {
                System.out.print(ENTER_VALID_VALUE);
            }
        }
        System.out.print(THANK_YOU);
    }

    public static String[] loadPuzzleFiles() {
        String basePath = "src/inputs/";
        File[] files = new File(basePath).listFiles();
        assert files != null;
        String[] puzzleFiles = new String[files.length];
        int i = 0;
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

    public static void solvePuzzle(String puzzleFilePath) {
        PuzzleFileHandler fileHandler = new PuzzleFileHandler(puzzleFilePath);
        String fileContents = fileHandler.readPuzzleFile();
        if (fileContents != null) {
            PuzzleMap puzzleMap = new PuzzleMap();
            puzzleMap.initializePuzzleMap(fileContents);
            PuzzleSolver puzzleSolver = new PuzzleSolver();
            List<PuzzleCoordinate> path = puzzleSolver.solve(puzzleMap);
            puzzleMap.printPath(path);
        }
    }

}
