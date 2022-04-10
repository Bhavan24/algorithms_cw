package copy;

import java.io.FileReader;
import java.util.*;


public class Puzzle {

    private Point[][] puzzleArray;
    private int maxWidth;
    private int maxHeight;
    private Point startPoint;
    private Point finishPoint;
    private String fileLocation;
    private final int[][] ALL_DIRECTIONS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    private final int[][] CARDINAL_DIRECTIONS = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    int[][] directions = ALL_DIRECTIONS;

    public String readPuzzleFile(String filePath) {
        String fileContents = "";
        try {
            Scanner reader = new Scanner(new FileReader(filePath));
            StringBuilder stringBuilder = new StringBuilder();
            while (reader.hasNext()) {
                stringBuilder.append(reader.nextLine()).append("\n");
            }
            reader.close();
            fileContents = stringBuilder.toString();
            System.out.println("PUZZLE_LOADED");
        } catch (Exception e) {
            System.out.println("FILE_DOES_NOT_EXIST");
            System.out.println("TRY_AGAIN");
        }
        return fileContents;
    }

    public void initializePuzzleMap(String fileContents) {

        if (fileContents == null || fileContents.trim().equals("")) {
            System.out.println("Empty File");
            return;
        }

        String[] lines = fileContents.split("[\r]?\n");
        maxHeight = lines.length;
        maxWidth = lines[0].length();

        puzzleArray = new Point[maxHeight][maxWidth];

        int id = 0;
        for (int y = 0; y < maxHeight; y++) {
            if (lines[y].length() != maxWidth) {
                System.out.println("INVALID_DATA");
                return;
            } else {
                for (int x = 0; x < maxWidth; x++) {
                    char c = lines[y].charAt(x);
                    switch (c) {
                        case 'S':
                            startPoint = new Point(id, x, y, Character.toString(c));
                            break;
                        case 'F':
                            finishPoint = new Point(id, x, y, Character.toString(c));
                            break;
                    }
                    Point point = new Point(id, x, y, Character.toString(c));
                    id++;
                    puzzleArray[y][x] = point;
                }
            }
        }
    }

    public Point getPointFromArray(int vertexId) {
        for (Point[] points : puzzleArray) {
            for (Point point : points) {
                if (point.getId() == vertexId) {
                    return point;
                }
            }
        }
        return null;
    }

    public Point getPointFromArray(int x, int y) {
        for (Point[] points : puzzleArray) {
            for (Point point : points) {
                if (point.getX() == x && point.getY() == y) {
                    return point;
                }
            }
        }
        return null;
    }

    public String getDirectionDetails(Point point, String direction) {
        if (point.getLetter().equals("S")) {
            return "Start at (" + (point.getX() + 1) + "," + (point.getY() + 1) + ")";
        } else {
            return "Move " + direction + " to (" + (point.getX() + 1) + "," + (point.getY() + 1) + ")";
        }
    }

    public List<List<Integer>> getPathList(UndirectedGraph g) {
        List<List<Integer>> result = new ArrayList<>();
        Map<Integer, Integer> pastVertexMap = g.breadthFirstTraversal(g, startPoint.getId());
        List<Integer> pathList = g.findPathList(pastVertexMap, startPoint.getId(), finishPoint.getId());
        for (Integer integer : pathList) {
            Point point = getPointFromArray(integer);
            List<Integer> coordinates = new ArrayList<>();
            coordinates.add(point.getX());
            coordinates.add(point.getY());
            result.add(coordinates);
        }
        return result;
    }

    public void printPathDetails(UndirectedGraph g) {
        Map<Integer, Integer> pastVertexMap = g.breadthFirstTraversal(g, startPoint.getId());
        List<Integer> pathList = g.findPathList(pastVertexMap, startPoint.getId(), finishPoint.getId());
        if (pathList.isEmpty()) {
            System.out.println("There is no path!");
        } else {
            Point oldPoint = null;
            for (int i = 0; i < pathList.size(); i++) {
                String direction = "";
                Point point = getPointFromArray(pathList.get(i));
                if (oldPoint != null) {
                    if (point.getY() == oldPoint.getY()) {
                        if (point.getX() > oldPoint.getX()) {
                            direction = "right";
                        } else {
                            direction = "left";
                        }
                    } else {
                        if (point.getY() > oldPoint.getY()) {
                            direction = "down";
                        } else {
                            direction = "up";
                        }
                    }
                }
                System.out.println(i + ". " + getDirectionDetails(point, direction));
                oldPoint = point;
                if (i == pathList.size() - 1) {
                    System.out.println(i + 1 + ". Done!");
                }
            }
        }
    }

    public UndirectedGraph createGraph(UndirectedGraph g) {
        Stack<Integer> stack = new Stack<>();
        List<Integer> visited = new ArrayList<>();
        Point oldPoint = startPoint;
        stack.push(oldPoint.getId());
        g.addVertex(oldPoint.getId());
        boolean pathPresent = false;
        while (!stack.isEmpty()) {
            int vertexId = stack.pop();
            visited.add(vertexId);
            oldPoint = getPointFromArray(vertexId);
            if (finishVertexInPath(oldPoint)) {
                g.addVertex(finishPoint.getId());
                g.addEdge(vertexId, finishPoint.getId());
                pathPresent = true;
                stack.clear();
                break;
            } else {
                for (int[] direction : directions) {
                    if (canTravel(oldPoint, direction)) {
                        Point newPoint = travel(oldPoint, direction);
                        if (!visited.contains(newPoint.getId())) {
                            stack.push(newPoint.getId());
                            g.addVertex(newPoint.getId());
                            g.addEdge(vertexId, newPoint.getId());
                        }
                    }
                }
            }
        }
        if (pathPresent) {
            return g;
        } else {
            return null;
        }
    }

    public boolean finishVertexInPath(Point point) {
        if (point.getId() == finishPoint.getId()) {
            return true;
        }
        if (!(point.getX() == finishPoint.getX() || point.getY() == finishPoint.getY())) {
            return false;
        }
        int start;
        int end;
        if (point.getX() == finishPoint.getX()) {
            if (point.getY() < finishPoint.getY()) {
                start = point.getY();
                end = finishPoint.getY();
            } else {
                start = finishPoint.getY();
                end = point.getY();
            }
            for (int i = start; i <= end; i++) {
                Point p = getPointFromArray(finishPoint.getX(), i);
                if (p.getLetter().equals("0")) {
                    return false;
                }
            }
        } else {
            if (point.getX() < finishPoint.getX()) {
                start = point.getX();
                end = finishPoint.getX();
            } else {
                start = finishPoint.getX();
                end = point.getX();
            }
            for (int i = start; i <= end; i++) {
                Point p = getPointFromArray(i, finishPoint.getY());
                if (p.getLetter().equals("0")) {
                    return false;
                }
            }
        }
        return true;
    }

    public Point travel(Point point, int[] direction) {
        Point newPoint = point;
        while (canTravel(newPoint, direction)) {
            newPoint = puzzleArray[newPoint.getY() + direction[1]][newPoint.getX() + direction[0]];
        }
        return newPoint;
    }

    public boolean canTravel(Point point, int[] direction) {
        int y = point.getY() + direction[1];
        int x = point.getX() + direction[0];
        boolean b = false;
        try {
            b = !(puzzleArray[y][x].getLetter().equals("0"));
        } catch (Exception ignored) {
        }
        return b;
    }

}
