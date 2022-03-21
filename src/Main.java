import java.util.ArrayList;
import java.util.Scanner;

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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
