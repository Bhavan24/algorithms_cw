package main;

import static main.GameConstants.ALL_DIRECTIONS;

public class Main {
    public static void main(String[] args) {
        String puzzleFilePath = "src/inputs/test.txt";
        PuzzleFileHandler fileHandler = new PuzzleFileHandler(puzzleFilePath);
        String fileContents = fileHandler.readPuzzleFile();
        PuzzleMap puzzleMap = new PuzzleMap();
        puzzleMap.initializePuzzleMap(fileContents);
        PuzzleSolver2 puzzleSolver2 = new PuzzleSolver2(puzzleMap, ALL_DIRECTIONS);
        PuzzleGraph puzzleGraph = new PuzzleGraph();
        puzzleSolver2.solve(puzzleGraph);
    }
}
