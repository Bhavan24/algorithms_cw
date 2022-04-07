package real;

import static real.GameConstants.WELCOME_TEXT;

public class SlidingPuzzleGame {

    public static void main(String[] args) {

        // prompt and read the file path
        System.out.print(WELCOME_TEXT);
//        Scanner scanner = new Scanner(System.in);
//        String puzzleFilePath = scanner.next();
        String puzzleFilePath = "src/test.txt";

        // read the file from using the file handler
        PuzzleFileHandler fileHandler = new PuzzleFileHandler(puzzleFilePath);
        String fileContents = fileHandler.readPuzzleFile();
        int[][] puzzleMap = fileHandler.initializePuzzleMap();
        fileHandler.printPuzzleMap(puzzleMap);

    }

}
