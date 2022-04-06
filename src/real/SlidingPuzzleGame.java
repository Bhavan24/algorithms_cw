package real;

import java.util.Scanner;

public class SlidingPuzzleGame {

    public static void main(String[] args) {

        // prompt and read the file path
        String gameText = (
                "+ - - - - - - - - - - - - - - - - - - - - - - +\n" +
                "|         WELCOME TO SLIDING PUZZLES          |\n" +
                "+ - - - - - - - - - - - - - - - - - - - - - - +\n" +
                "\nPLEASE ENTER THE PUZZLE FILE PATH: "
        );
        System.out.print(gameText);
//        Scanner scanner = new Scanner(System.in);
//        String puzzleFilePath = scanner.next();
        String puzzleFilePath = "src/test.txt";

        // read the file from using the file handler
        PuzzleFileHandler fileHandler = new PuzzleFileHandler();
        String fileContents = fileHandler.initializeFileContents(puzzleFilePath);
    }

}
