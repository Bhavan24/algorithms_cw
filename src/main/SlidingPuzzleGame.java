package main;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import static main.GameConstants.*;

public class SlidingPuzzleGame {

    public static void main(String[] args) {

        System.out.print(WELCOME_TEXT);
        String[] puzzleFiles = loadPuzzleFiles();

        while (true) {
            try {
                System.out.print(SELECT_FILE);
                Scanner scanner = new Scanner(System.in);
                int fileId = scanner.nextInt();
                if (fileId == 0) break;
                solvePuzzle(puzzleFiles[fileId - 1]);
            } catch (Exception e) {
                System.out.print(ENTER_VALID_FILE_ID);
            }
        }

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
        PuzzleMap puzzleMap = new PuzzleMap();
        puzzleMap.initializePuzzleMap(fileContents);
        PuzzleSolver puzzleSolver = new PuzzleSolver();
        List<PuzzleCoordinate> path = puzzleSolver.solve(puzzleMap);
        puzzleMap.printPath(path);
    }

}
