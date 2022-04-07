package main;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import static main.GameConstants.WELCOME_TEXT;

public class SlidingPuzzleGame {

    public static void main(String[] args) {

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
        System.out.print("ENTER FILE NUMBER: ");
        Scanner scanner = new Scanner(System.in);
        int file = scanner.nextInt();
        solvePuzzle(puzzleFiles[file - 1]);
    }

    public static void solvePuzzle(String puzzleFilePath) {
        System.out.print(WELCOME_TEXT);
        // read the file from using the file handler
        PuzzleFileHandler fileHandler = new PuzzleFileHandler(puzzleFilePath);
        String fileContents = fileHandler.readPuzzleFile();
        PuzzleMap puzzleMap = new PuzzleMap();
        puzzleMap.initializePuzzleMap(fileContents);
        PuzzleSolver puzzleSolver = new PuzzleSolver();
        List<PuzzleCoordinate> path = puzzleSolver.solve(puzzleMap);
        puzzleMap.printPath(path);
    }

}
