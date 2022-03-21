import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InputHandler {

    public SquareGraph readMap(String filename) throws IOException, InvalidLetterException {

        File file = new File(filename);
        BufferedReader in = new BufferedReader(new FileReader(file));

        try {

            String dimension = in.readLine();
            int mapDimension = Integer.parseInt(dimension);

            SquareGraph graph = new SquareGraph(mapDimension);

            String line;
            for (int i = 0; i < mapDimension; i++) {
                line = in.readLine();
                for (int j = 0; j < mapDimension; j++) {
                    char typeSymbol = line.charAt(j);
                    if (typeSymbol == '.') {
                        Node n = new Node(i, j, "NORMAL");
                        graph.setMapCell(new Point(i, j), n);
                    } else if (typeSymbol == '0') {
                        Node n = new Node(i, j, "OBSTACLE");
                        graph.setMapCell(new Point(i, j), n);
                    } else if (typeSymbol == 'S') {
                        Node n = new Node(i, j, "NORMAL");
                        graph.setMapCell(new Point(i, j), n);
                        graph.setStartPosition(new Point(i, j));
                    } else if (typeSymbol == 'F') {
                        Node n = new Node(i, j, "NORMAL");
                        graph.setMapCell(new Point(i, j), n);
                        graph.setTargetPosition(new Point(i, j));
                    } else {
                        throw new InvalidLetterException("There was a wrong character in the text file.The character must be X, ,T or G.");
                    }
                }
            }
            return graph;
        } catch (IOException e) {
            throw e;
        } finally {
            in.close();
        }
    }
}
