package main;

import java.util.*;

import static main.GameConstants.ROCK;
import static main.GameConstants.START;


public class PuzzleSolver {

    private final PuzzleCoordinate[][] puzzleArray;
    private final PuzzleCoordinate startPoint;
    private final PuzzleCoordinate finishPoint;
    String fileContents;
    PuzzleMap puzzleMap;
    int[][] directions;

    public PuzzleSolver(String fileContents, int[][] directions) {
        this.fileContents = fileContents;
        this.directions = directions;
        this.puzzleMap = new PuzzleMap();
        this.puzzleMap.initializePuzzleMap(fileContents);
        this.puzzleArray = puzzleMap.getPuzzleMapArray();
        this.startPoint = puzzleMap.getStart();
        this.finishPoint = puzzleMap.getEnd();
    }

    public void solveWithFrictionIce() {
        List<PuzzleCoordinate> path = performAlgorithm();
        puzzleMap.printPath(path);
    }

    public void solveWithFrictionlessIce() {
        PuzzleGraph puzzleGraph = createGraph();
        printPathDetails(puzzleGraph);
    }

    public List<PuzzleCoordinate> performAlgorithm() {

        LinkedList<PuzzleCoordinate> nextToVisit = new LinkedList<>();
        PuzzleCoordinate start = puzzleMap.getStart();
        nextToVisit.add(start);

        while (!nextToVisit.isEmpty()) {
            PuzzleCoordinate cur = nextToVisit.remove();

            if (!puzzleMap.isValidCoordinate(cur.getX(), cur.getY()) || puzzleMap.isVisited(cur.getX(), cur.getY())) {
                continue;
            }

            if (puzzleMap.isRock(cur.getX(), cur.getY())) {
                puzzleMap.setVisited(cur.getX(), cur.getY(), true);
                continue;
            }

            if (puzzleMap.isEnd(cur.getX(), cur.getY())) {
                return backtrackPath(cur);
            }

            for (int[] direction : directions) {
                PuzzleCoordinate coordinate = new PuzzleCoordinate(cur.getX() + direction[1], cur.getY() + direction[0], cur);
                nextToVisit.add(coordinate);
                puzzleMap.setVisited(cur.getX(), cur.getY(), true);
            }
        }
        return Collections.emptyList();
    }

    private List<PuzzleCoordinate> backtrackPath(PuzzleCoordinate cur) {
        List<PuzzleCoordinate> path = new ArrayList<>();
        PuzzleCoordinate iter = cur;

        while (iter != null) {
            path.add(iter);
            iter = iter.parent;
        }

        return path;
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
        if (point.getValue() == START) {
            return "Start at (" + (point.getX() + 1) + "," + (point.getY() + 1) + ")";
        } else {
            return "Move " + direction + " to (" + (point.getX() + 1) + "," + (point.getY() + 1) + ")";
        }
    }

    public void printPathDetails(PuzzleGraph g) {
        List<PuzzleCoordinate> result = new ArrayList<>();
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
                result.add(point);
            }
        }
        puzzleMap.printPath(result);
    }

    public PuzzleGraph createGraph() {
        PuzzleGraph g = new PuzzleGraph();
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
            start = Math.min(point.getY(), finishPoint.getY());
            end = Math.max(point.getY(), finishPoint.getY());
            for (int i = start; i <= end; i++) {
                PuzzleCoordinate p = getPointFromArray(finishPoint.getX(), i);
                if (p.getValue() == ROCK) {
                    return false;
                }
            }
        } else {
            start = Math.min(point.getX(), finishPoint.getX());
            end = Math.max(point.getX(), finishPoint.getX());
            for (int i = start; i <= end; i++) {
                PuzzleCoordinate p = getPointFromArray(i, finishPoint.getY());
                if (p.getValue() == ROCK) {
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
            b = !(puzzleArray[y][x].getValue() == ROCK);
        } catch (Exception ignored) {
        }
        return b;
    }
}
