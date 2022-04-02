import test.ReadInputData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {

            String basePath = "src/test_inputs/";
            File[] files = new File(basePath).listFiles();
            assert files != null;
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    int length = Integer.parseInt(fileName.split("_")[1].replace(".txt", ""));
                    String[][] result = new ReadInputData().readFromFile(basePath + fileName, length);

                    InputHandler handler = new InputHandler();
                    SquareGraph graph = handler.initializeMap(result, length);
                    ArrayList<Node> path = graph.executeAStar();

                    if (path == null) {
                        System.out.println("There is no path to target");
                    } else {
                        graph.printPath(path);
                        System.out.println("Done!");
                        colorPrintGraph(graph.getPathsToList(path), result);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
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
