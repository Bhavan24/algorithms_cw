package main;

import static main.GameConstants.WELCOME_TEXT;

public class SlidingPuzzleGame {

    public static void main(String[] args) {

        // prompt and read the file path
        System.out.print(WELCOME_TEXT);
//        Scanner scanner = new Scanner(System.in);
//        String puzzleFilePath = scanner.next();
        String puzzleFilePath = "src/test_inputs/test.txt";

        // read the file from using the file handler
        PuzzleFileHandler fileHandler = new PuzzleFileHandler(puzzleFilePath);
        String fileContents = fileHandler.readPuzzleFile();
        fileHandler.prettyPrintFile();
        PuzzleMap puzzleMap = new PuzzleMap();
        puzzleMap.initializePuzzleMap(fileContents);
    }

}
