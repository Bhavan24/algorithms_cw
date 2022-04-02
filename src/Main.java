import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            String filename = "src/test.txt";

            InputHandler handler = new InputHandler();
            SquareGraph graph = handler.readMap(filename);

            ArrayList<Node> path = graph.executeAStar();

            if (path == null) {
                System.out.println("There is no path to target");
            } else {
                graph.printPath(path);
                System.out.println("Done!");
                colorPrintGraph(graph.getPathsToList(path), handler.readMapToArray(filename));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void colorPrintGraph(List<List<Integer>> paths, String[][] map) {

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\033[1;91m";

        for (int k = 0; k < paths.size(); k++) {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map.length; j++) {
                    if (k < paths.size()) {
                        if (paths.get(k).get(0) == i && paths.get(k).get(1) == j) {
                            System.out.print(ANSI_RED + map[i][j] + ANSI_RESET);
                        } else {
                            System.out.print(map[i][j]);
                        }
                    } else {
                        System.out.print(map[i][j]);
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
