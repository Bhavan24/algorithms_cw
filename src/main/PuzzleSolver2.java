package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static main.GameConstants.ROCK_VALUE;
import static main.GameConstants.START_VALUE;


public class PuzzleSolver2 {

    private PuzzleCoordinate[][] puzzleArray;
    private int maxWidth;
    private int maxHeight;
    private PuzzleCoordinate startPoint;
    private PuzzleCoordinate finishPoint;
    private String fileLocation;
    private final int[][] ALL_DIRECTIONS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    private final int[][] CARDINAL_DIRECTIONS = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    int[][] directions = ALL_DIRECTIONS;

    public PuzzleSolver2(PuzzleCoordinate[][] puzzleArray, int maxWidth, int maxHeight, PuzzleCoordinate startPoint, PuzzleCoordinate finishPoint, String fileLocation, int[][] directions) {
        this.puzzleArray = puzzleArray;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        this.fileLocation = fileLocation;
        this.directions = directions;
    }


    public PuzzleCoordinate getPointFromArray(int vertexId) {
        for (PuzzleCoordinate[] points : puzzleArray) {
            for (PuzzleCoordinate point : points) {
                if (point.getId() == vertexId) {
                    return point;
                }
            }
        }
        return null;
    }

    public PuzzleCoordinate getPointFromArray(int x, int y) {
        for (PuzzleCoordinate[] points : puzzleArray) {
            for (PuzzleCoordinate point : points) {
                if (point.getX() == x && point.getY() == y) {
                    return point;
                }
            }
        }
        return null;
    }

    public String getDirectionDetails(PuzzleCoordinate point, String direction) {
        if (point.getValue() == START_VALUE) {
            return "Start at (" + (point.getX() + 1) + "," + (point.getY() + 1) + ")";
        } else {
            return "Move " + direction + " to (" + (point.getX() + 1) + "," + (point.getY() + 1) + ")";
        }
    }

    public List<List<Integer>> getPathList(PuzzleGraph g) {
        List<List<Integer>> result = new ArrayList<>();
        Map<Integer, Integer> pastVertexMap = g.breadthFirstTraversal(g, startPoint.getId());
        List<Integer> pathList = g.findPathList(pastVertexMap, startPoint.getId(), finishPoint.getId());
        for (Integer integer : pathList) {
            PuzzleCoordinate point = getPointFromArray(integer);
            List<Integer> coordinates = new ArrayList<>();
            coordinates.add(point.getX());
            coordinates.add(point.getY());
            result.add(coordinates);
        }
        return result;
    }

    public void printPathDetails(PuzzleGraph g) {
        Map<Integer, Integer> pastVertexMap = g.breadthFirstTraversal(g, startPoint.getId());
        List<Integer> pathList = g.findPathList(pastVertexMap, startPoint.getId(), finishPoint.getId());
        if (pathList.isEmpty()) {
            System.out.println("There is no path!");
        } else {
            PuzzleCoordinate oldPoint = null;
            for (int i = 0; i < pathList.size(); i++) {
                String direction = "";
                PuzzleCoordinate point = getPointFromArray(pathList.get(i));
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

    public PuzzleGraph createGraph(PuzzleGraph g) {
        Stack<Integer> stack = new Stack<>();
        List<Integer> visited = new ArrayList<>();
        PuzzleCoordinate oldPoint = startPoint;
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
                        PuzzleCoordinate newPoint = travel(oldPoint, direction);
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

    public boolean finishVertexInPath(PuzzleCoordinate point) {
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
                PuzzleCoordinate p = getPointFromArray(finishPoint.getX(), i);
                if (p.getValue() == ROCK_VALUE) {
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
                PuzzleCoordinate p = getPointFromArray(i, finishPoint.getY());
                if (p.getValue() == ROCK_VALUE) {
                    return false;
                }
            }
        }
        return true;
    }

    public PuzzleCoordinate travel(PuzzleCoordinate point, int[] direction) {
        PuzzleCoordinate newPoint = point;
        while (canTravel(newPoint, direction)) {
            newPoint = puzzleArray[newPoint.getY() + direction[1]][newPoint.getX() + direction[0]];
        }
        return newPoint;
    }

    public boolean canTravel(PuzzleCoordinate point, int[] direction) {
        int y = point.getY() + direction[1];
        int x = point.getX() + direction[0];
        boolean b = false;
        try {
            b = !(puzzleArray[y][x].getValue() == ROCK_VALUE);
        } catch (Exception ignored) {
        }
        return b;
    }

}
