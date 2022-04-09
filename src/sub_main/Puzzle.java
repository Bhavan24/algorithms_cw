package sub_main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static main.GameConstants.*;

public class Puzzle {

    private Point[][] points;
    private int maxWidth;
    private int maxHeight;
    private Point startPoint;
    private Point finishPoint;
    private String fileContents;

    public Puzzle(String fileContents) {
        this.fileContents = fileContents;
    }

    public void solve() {
        initializePuzzleMap();
        Graph graph = new Graph();
        printPathDetails(createGraph(graph));
    }

    public void printArray(Point[][] points) {
        for (Point[] point : points) {
            for (Point p : point) {
                System.out.println(p.getX() + " " + p.getY() + " " + p.getLetter());
            }
        }
    }

    public String getDirectionDetails(Point point, String direction) {
        if (point.getLetter() == START) {
            return "Start at (" + (point.getX() + 1) + "," + (point.getY() + 1) + ")";
        } else {
            return "Move " + direction + " to (" + (point.getX() + 1) + "," + (point.getY() + 1) + ")";
        }
    }

    public void printPathDetails(Graph graph) {
        Map<Integer, Integer> pastVertexMap = GraphTraversal.breadthFirstTraversal(graph, startPoint.getId());
        List<Integer> pathList = GraphTraversal.findPathList(pastVertexMap, startPoint.getId(), finishPoint.getId());
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

    private void initializePuzzleMap() {

        if (fileContents == null || fileContents.trim().equals("")) {
            System.out.println(FILE_IS_EMPTY);
            return;
        }

        String[] lines = fileContents.split("[\r]?\n");
        int rows = lines.length;
        int columns = lines[0].length();

        points = new Point[rows][columns];

        for (int x = 0; x < rows; x++) {
            if (lines[x].length() != columns) {
                System.out.println(INVALID_DATA);
                return;
            } else {
                int id = 0;
                for (int y = 0; y < columns; y++) {
                    switch (lines[x].charAt(y)) {
                        case ICE:
                            points[x][y] = new Point(id, x, y, ICE);
                            break;
                        case ROCK:
                            points[x][y] = new Point(id, x, y, ROCK);
                            break;
                        case START:
                            points[x][y] = new Point(id, x, y, START);
                            startPoint = points[x][y];
                            break;
                        case FINISH:
                            points[x][y] = new Point(id, x, y, FINISH);
                            finishPoint = points[x][y];
                            break;
                    }
                    id++;
                }
            }
        }
    }

    public Point getPointFromArray(int vertexId) {
        for (Point[] point : points) {
            for (Point p : point) {
                if (p.getId() == vertexId) {
                    return p;
                }
            }
        }
        return null;
    }

    public Point getPointFromArray(int x, int y) {
        for (Point[] point : points) {
            for (Point p : point) {
                if (p.getX() == x && p.getY() == y) {
                    return p;
                }
            }
        }
        return null;
    }

    public Graph createGraph(Graph graph) {

        Stack<Integer> stack = new Stack<>();
        List<Integer> visited = new ArrayList<>();
        Point oldPoint = startPoint;
        stack.push(oldPoint.getId());
        graph.addVertex(oldPoint.getId());
        boolean pathPresent = false;

        while (!stack.isEmpty()) {

            int vertexId = stack.pop();
            visited.add(vertexId);
            oldPoint = getPointFromArray(vertexId);

            if (finishVertexInPath(oldPoint)) {
                graph.addVertex(finishPoint.getId());
                graph.addEdge(vertexId, finishPoint.getId());
                pathPresent = true;
                stack.clear();
                break;
            } else {
                if (canTravelNorth(oldPoint)) {
                    Point newPoint = travelNorthPoint(oldPoint);
                    if (!visited.contains(newPoint.getId())) {
                        stack.push(newPoint.getId());
                        graph.addVertex(newPoint.getId());
                        graph.addEdge(vertexId, newPoint.getId());
                    }
                }
                if (canTravelSouth(oldPoint)) {
                    Point newPoint = travelSouthPoint(oldPoint);
                    if (!visited.contains(newPoint.getId())) {
                        stack.push(newPoint.getId());
                        graph.addVertex(newPoint.getId());
                        graph.addEdge(vertexId, newPoint.getId());
                    }
                }
                if (canTravelEast(oldPoint)) {
                    Point newPoint = travelEastPoint(oldPoint);
                    if (!visited.contains(newPoint.getId())) {
                        stack.push(newPoint.getId());
                        graph.addVertex(newPoint.getId());
                        graph.addEdge(vertexId, newPoint.getId());
                    }
                }
                if (canTravelWest(oldPoint)) {
                    Point newPoint = travelWestPoint(oldPoint);
                    if (!visited.contains(newPoint.getId())) {
                        stack.push(newPoint.getId());
                        graph.addVertex(newPoint.getId());
                        graph.addEdge(vertexId, newPoint.getId());
                    }
                }
            }
        }
        if (pathPresent) {
            return graph;
        } else {
            return null;
        }
    }

    public Point travelNorthPoint(Point point) {
        Point newPoint = point;
        while (canTravelNorth(newPoint)) {
            newPoint = points[newPoint.getX()][newPoint.getY() - 1];
        }
        return newPoint;
    }

    public Point travelSouthPoint(Point point) {
        Point newPoint = point;
        while (canTravelSouth(newPoint)) {
            newPoint = points[newPoint.getX()][newPoint.getY() + 1];
        }
        return newPoint;
    }

    public Point travelEastPoint(Point point) {
        Point newPoint = point;
        while (canTravelEast(newPoint)) {
            newPoint = points[newPoint.getX() + 1][newPoint.getY()];
        }
        return newPoint;
    }

    public Point travelWestPoint(Point point) {
        Point newPoint = point;
        while (canTravelWest(newPoint)) {
            newPoint = points[newPoint.getX() - 1][newPoint.getY()];
        }
        return newPoint;
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
                if (p.getLetter() == ROCK) {
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
                if (p.getLetter() == ROCK) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canTravelNorth(Point point) {
        if (point.getY() == 0) {
            return false;
        }
        return !(points[point.getX()][point.getY() - 1].getLetter() == ROCK);
    }

    public boolean canTravelSouth(Point point) {
        if (point.getY() == (maxHeight - 1)) {
            return false;
        }
        return !(points[point.getX()][point.getY() + 1].getLetter() == ROCK);
    }

    public boolean canTravelEast(Point point) {
        if (point.getX() == (maxWidth - 1)) {
            return false;
        }
        return !(points[point.getX() + 1][point.getY()].getLetter() == ROCK);
    }

    public boolean canTravelWest(Point point) {
        if (point.getX() == 0) {
            return false;
        }
        return !(points[point.getX() - 1][point.getY()].getLetter() == ROCK);
    }

}
