package test.example5;

import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

import static main.GameConstants.*;

class Main {

    // A Queue Node
    static class Node {
        // (x, y) represents matrix cell coordinates, and
        // `dist` represents their minimum distance from the source
        int x, y, dist;

        Node(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    // Below arrays detail all four possible movements from a cell
    private static final int[] row = {-1, 0, 0, 1};
    private static final int[] col = {0, -1, 1, 0};

    // Function to check if it is possible to go to position (row, col)
    // from the current position. The function returns false if (row, col)
    // is not a valid position or has a value 0 or already visited.
    private static boolean isValid(int[][] mat, boolean[][] visited, int row, int col) {
        return (row >= 0) && (row < mat.length) && (col >= 0) && (col < mat[0].length) && mat[row][col] == 1 && !visited[row][col];
    }

    // Find the shortest possible route in a matrix `mat` from source
    // cell (i, j) to destination cell (x, y)
    private static int findShortestPathLength(int[][] mat, int i, int j, int x, int y) {
        // base case: invalid input
        if (mat == null || mat.length == 0 || mat[i][j] == 0 || mat[x][y] == 0) {
            return -1;
        }

        // `M × N` matrix
        int M = mat.length;
        int N = mat[0].length;

        // construct a matrix to keep track of visited cells
        boolean[][] visited = new boolean[M][N];

        // create an empty queue
        Queue<Node> q = new ArrayDeque<>();

        // mark the source cell as visited and enqueue the source node
        visited[i][j] = true;
        q.add(new Node(i, j, 0));

        // stores length of the longest path from source to destination
        int min_dist = Integer.MAX_VALUE;

        // loop till queue is empty
        while (!q.isEmpty()) {
            // dequeue front node and process it
            Node node = q.poll();

            // (i, j) represents a current cell, and `dist` stores its
            // minimum distance from the source
            i = node.x;
            j = node.y;
            int dist = node.dist;

            // if the destination is found, update `min_dist` and stop
            if (i == x && j == y) {
                min_dist = dist;
                break;
            }

            // check for all four possible movements from the current cell
            // and enqueue each valid movement
            for (int k = 0; k < 4; k++) {
                // check if it is possible to go to position
                // (i + row[k], j + col[k]) from current position
                if (isValid(mat, visited, i + row[k], j + col[k])) {
                    // mark next cell as visited and enqueue it
                    visited[i + row[k]][j + col[k]] = true;
                    q.add(new Node(i + row[k], j + col[k], dist + 1));
                }
            }
        }

        if (min_dist != Integer.MAX_VALUE) {
            return min_dist;
        }
        return -1;
    }

    public static void main(String[] args) {

//        int[][] mat = {{1, 1, 1, 1, 1, 0, 0, 1, 1, 1}, {0, 1, 1, 1, 1, 1, 0, 1, 0, 1}, {0, 0, 1, 0, 1, 1, 1, 0, 0, 1}, {1, 0, 1, 1, 1, 0, 1, 1, 0, 1}, {0, 0, 0, 1, 0, 0, 0, 1, 0, 1}, {1, 0, 1, 1, 1, 0, 0, 1, 1, 0}, {0, 0, 0, 0, 1, 0, 0, 1, 0, 1}, {0, 1, 1, 1, 1, 1, 1, 1, 0, 0}, {1, 1, 1, 1, 1, 0, 0, 1, 1, 1}, {0, 0, 1, 0, 0, 1, 1, 0, 0, 1},};
//        int[] start = new int[]{0, 0};
//        int[] end = new int[]{7, 5};



        String filePath = "src/inputs/test.txt";

        String fileContents = "";
        try {
            Scanner reader = new Scanner(new FileReader(filePath));
            StringBuilder stringBuilder = new StringBuilder();
            while (reader.hasNext()) {
                stringBuilder.append(reader.nextLine()).append("\n");
            }
            reader.close();
            fileContents = stringBuilder.toString();
            System.out.println(PUZZLE_LOADED);
        } catch (Exception e) {
            System.out.println(FILE_DOES_NOT_EXIST);
            System.out.println(TRY_AGAIN);
        }

        if (fileContents.trim().equals("")) {
            System.out.println(FILE_IS_EMPTY);
            return;
        }

        String[] lines = fileContents.split("[\r]?\n");
        int rows = lines.length;
        int columns = lines[0].length();
        int[][] mat = new int[rows][columns];
        int[] start = new int[2];
        int[] end = new int[2];

        for (int i = 0; i < rows; i++) {
            if (lines[i].length() != columns) {
                System.out.println(INVALID_DATA);
                return;
            } else {
                for (int j = 0; j < columns; j++) {
                    switch (lines[i].charAt(j)) {
                        case ICE:
                            mat[i][j] = ICE_VALUE;
                            break;
                        case ROCK:
                            mat[i][j] = ROCK_VALUE;
                            break;
                        case START:
                            mat[i][j] = ROCK_VALUE;
                            start = new int[]{i, j};
                            break;
                        case FINISH:
                            mat[i][j] = ROCK_VALUE;
                            end = new int[]{i, j};
                            break;
                    }
                }
            }
        }


        for (int[] m : mat) {
            System.out.println(Arrays.toString(m));
        }
        System.out.println(Arrays.toString(start));
        System.out.println(Arrays.toString(end));

        int min_dist = findShortestPathLength(mat, start[0], start[1], end[0], end[1]);

        if (min_dist != -1) {
            System.out.println("The shortest path from source to destination " + "has length " + min_dist);
        } else {
            System.out.println("Destination cannot be reached from source");
        }
    }
}