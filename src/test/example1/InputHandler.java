package test.example1;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class InputHandler {

    static class InvalidLetterException extends Exception {
        public InvalidLetterException(String paramString) {
            super(paramString);
        }
    }

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


    public SquareGraph initializeMap(String[][] map, int mapDimension) throws InvalidLetterException {

        SquareGraph graph = new SquareGraph(mapDimension);

        for (int i = 0; i < mapDimension; i++) {
            for (int j = 0; j < mapDimension; j++) {
                String typeSymbol = map[i][j];
                if (Objects.equals(typeSymbol, ".")) {
                    Node n = new Node(i, j, "NORMAL");
                    graph.setMapCell(new Point(i, j), n);
                } else if (Objects.equals(typeSymbol, "0")) {
                    Node n = new Node(i, j, "OBSTACLE");
                    graph.setMapCell(new Point(i, j), n);
                } else if (Objects.equals(typeSymbol, "S")) {
                    Node n = new Node(i, j, "NORMAL");
                    graph.setMapCell(new Point(i, j), n);
                    graph.setStartPosition(new Point(i, j));
                } else if (Objects.equals(typeSymbol, "F")) {
                    Node n = new Node(i, j, "NORMAL");
                    graph.setMapCell(new Point(i, j), n);
                    graph.setTargetPosition(new Point(i, j));
                } else {
                    throw new InvalidLetterException("There was a wrong character in the text file.The character must be X, ,T or G.");
                }
            }
        }
        return graph;
    }


    public String[][] readMapToArray(String filePath, int length) {

        String[][] graph = new String[length][length];
        try (Scanner scanner = new Scanner(new FileReader(filePath))) {
            int i = 0;
            while (scanner.hasNext()) {
                graph[i] = scanner.nextLine().split("");
                i++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return graph;

    }
}
