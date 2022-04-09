package sub_main;

import main.PuzzleFileHandler;

public class Main {
    public static void main(String[] args) {
        String puzzleFilePath = "src/inputs/test.txt";
        main.PuzzleFileHandler fileHandler = new PuzzleFileHandler(puzzleFilePath);
        String fileContents = fileHandler.readPuzzleFile();
        Puzzle puzzle = new Puzzle(fileContents);
        puzzle.solve();
    }
}
