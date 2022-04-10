package main;

import java.util.*;

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
        PuzzleGraph puzzleGraph = createGraphForSome();
        printPathDetails(puzzleGraph);
    }

    public void solveWithFrictionlessIce() {
        PuzzleGraph puzzleGraph = createGraph();
        printPathDetails(puzzleGraph);
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
        if (puzzleMap.isStart(point)) {
            return "Start at (" + (point.getX() + 1) + "," + (point.getY() + 1) + ")";
        } else {
            return "Move " + direction + " to (" + (point.getX() + 1) + "," + (point.getY() + 1) + ")";
        }
    }

    public void printPathDetails(PuzzleGraph g) {
        List<PuzzleCoordinate> result = new ArrayList<>();
        Map<Integer, Integer> pastVertexMap = breadthFirstTraversal(g, startPoint.getId());
        List<Integer> pathList = findPathList(pastVertexMap, startPoint.getId(), finishPoint.getId());
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

    public Map<Integer, Integer> breadthFirstTraversal(PuzzleGraph graph, int startId) {
        Set<Integer> visited = new LinkedHashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> pastVertexMap = new HashMap<>();
        queue.add(startId);
        visited.add(startId);
        pastVertexMap.put(startId, null);
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (PuzzleVertex v : graph.getAdjVertices(vertex)) {
                if (!visited.contains(v.getLabel())) {
                    pastVertexMap.put(v.getLabel(), vertex);
                    visited.add(v.getLabel());
                    queue.add(v.getLabel());
                }
            }
        }
        return pastVertexMap;
    }

    public List<Integer> findPathList(Map<Integer, Integer> pastVertexMap, int startId, int endId) {
        int newId = -1;
        List<Integer> pathList = new ArrayList<>();
        pathList.add(endId);
        while (newId != startId) {
            newId = pastVertexMap.get(endId);
            pathList.add(newId);
            endId = newId;
        }
        Collections.reverse(pathList);
        return pathList;
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
                    if (canGo(oldPoint, direction)) {
                        PuzzleCoordinate newPoint = goInThisDirection(oldPoint, direction);
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

    public PuzzleGraph createGraphForSome() {
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
            if (oldPoint == finishPoint) {
                g.addVertex(finishPoint.getId());
                g.addEdge(vertexId, finishPoint.getId());
                pathPresent = true;
                stack.clear();
                break;
            } else {
                for (int[] direction : directions) {
                    if (canGo(oldPoint, direction)) {
                        PuzzleCoordinate newPoint = puzzleArray[oldPoint.getY() + direction[1]][oldPoint.getX() + direction[0]];
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
                if (puzzleMap.isRock(p)) {
                    return false;
                }
            }
        } else {
            start = Math.min(point.getX(), finishPoint.getX());
            end = Math.max(point.getX(), finishPoint.getX());
            for (int i = start; i <= end; i++) {
                PuzzleCoordinate p = getPointFromArray(i, finishPoint.getY());
                if (puzzleMap.isRock(p)) {
                    return false;
                }
            }
        }
        return true;
    }

    public PuzzleCoordinate goInThisDirection(PuzzleCoordinate point, int[] direction) {
        PuzzleCoordinate newPoint = point;
        while (canGo(newPoint, direction)) {
            newPoint = puzzleArray[newPoint.getY() + direction[1]][newPoint.getX() + direction[0]];
        }
        return newPoint;
    }

    public boolean canGo(PuzzleCoordinate point, int[] direction) {
        int x = point.getX() + direction[0];
        int y = point.getY() + direction[1];
        boolean canGoInThisDirection = false;
        try {
            canGoInThisDirection = !puzzleMap.isRock(x, y);
        } catch (Exception ignored) {
        }
        return canGoInThisDirection;
    }
}
