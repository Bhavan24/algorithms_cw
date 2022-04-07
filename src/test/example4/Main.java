package test.example4;

import test.test.ReadInputData;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String file = "src/test/example4/test.txt";
        String[][] result = new ReadInputData().readFromFile(file, 10);
        Puzzle p = new Puzzle();
        p.setFileLocation(file);
        p.initializePuzzleArray();
        p.fillPuzzleArray();
        UndirectedGraph g = new UndirectedGraph();
        p.createGraph(g);
        p.printPathDetails(g);
        colorPrintGraph(p.getPathList(g), result);
    }

    public static void colorPrintGraph(List<List<Integer>> paths, String[][] map) {

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\033[1;91m";

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                List<Integer> coordinates = new ArrayList<>();
                coordinates.add(i);
                coordinates.add(j);
                if (paths.contains(coordinates)) {
                    System.out.print(ANSI_RED + map[i][j] + ANSI_RESET);
                } else {
                    System.out.print(map[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
