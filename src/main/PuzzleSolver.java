package main;

import java.util.*;

import static main.PuzzleConstants.*;
import static main.PuzzleConstants.IceState.FRICTION;
import static main.PuzzleConstants.IceState.FRICTIONLESS;

public class PuzzleSolver {

    private final PuzzleCoordinate[][] puzzleArray;
    private final PuzzleCoordinate startPuzzleCoordinate;
    private final PuzzleCoordinate endPuzzleCoordinate;
    private final PuzzleMap puzzleMap;
    private final int[][] directions;

    public PuzzleSolver(String fileContents, int[][] directions) {
        this.directions = directions;
        this.puzzleMap = new PuzzleMap();
        this.puzzleMap.initializePuzzleMap(fileContents);
        this.puzzleArray = puzzleMap.getPuzzleMapArray();
        this.startPuzzleCoordinate = puzzleMap.getStart();
        this.endPuzzleCoordinate = puzzleMap.getEnd();
    }

    public void solve(IceState iceState) {
        PuzzleGraph puzzleGraph = createGraph(iceState);
        printPathDetails(puzzleGraph);
    }

    public PuzzleCoordinate getPointFromArray(int vertexId) {
        for (PuzzleCoordinate[] puzzleCoordinates : puzzleArray) {
            for (PuzzleCoordinate puzzleCoordinate : puzzleCoordinates) {
                if (puzzleCoordinate.getId() == vertexId) {
                    return puzzleCoordinate;
                }
            }
        }
        return null;
    }

    public PuzzleCoordinate getPointFromArray(int x, int y) {
        for (PuzzleCoordinate[] puzzleCoordinates : puzzleArray) {
            for (PuzzleCoordinate puzzleCoordinate : puzzleCoordinates) {
                if (puzzleCoordinate.getX() == x && puzzleCoordinate.getY() == y) {
                    return puzzleCoordinate;
                }
            }
        }
        return null;
    }

    public void printStep(int id, PuzzleCoordinate puzzleCoordinate, String direction) {
        String step;
        if (puzzleMap.isStart(puzzleCoordinate)) {
            step = String.format("%d. Start at (%d,%d)", id, (puzzleCoordinate.getX() + 1), (puzzleCoordinate.getY() + 1));
        } else {
            step = String.format("%d. Move %s to (%d,%d)", id, direction, (puzzleCoordinate.getX() + 1), (puzzleCoordinate.getY() + 1));
            if (puzzleMap.isEnd(puzzleCoordinate)) {
                step += String.format("\n%d. Done!\n", (id + 1));
            }
        }
        System.out.println(step);
    }

    public void printPathDetails(PuzzleGraph graph) {
        List<PuzzleCoordinate> result = new ArrayList<>();
        Map<Integer, Integer> traversalMap = breadthFirstTraversal(graph, startPuzzleCoordinate.getId());
        List<Integer> paths = findPathList(traversalMap, startPuzzleCoordinate.getId(), endPuzzleCoordinate.getId());
        if (paths.isEmpty()) {
            System.out.println(CANNOT_SOLVE_PUZZLE);
        } else {
            PuzzleCoordinate currentCoordinate = null;
            for (int i = 0; i < paths.size(); i++) {
                String direction = "";
                PuzzleCoordinate puzzleCoordinate = getPointFromArray(paths.get(i));
                if (currentCoordinate != null) {
                    if (puzzleCoordinate.getX() == currentCoordinate.getX()) {
                        direction = (puzzleCoordinate.getY() > currentCoordinate.getY()) ? PathDirection.down.toString() : PathDirection.up.toString();
                    } else {
                        direction = (puzzleCoordinate.getX() > currentCoordinate.getX()) ? PathDirection.right.toString() : PathDirection.left.toString();
                    }
                }
                printStep((i + 1), puzzleCoordinate, direction);
                currentCoordinate = puzzleCoordinate;
                result.add(puzzleCoordinate);
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
                if (!visited.contains(v.getId())) {
                    pastVertexMap.put(v.getId(), vertex);
                    visited.add(v.getId());
                    queue.add(v.getId());
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

    public PuzzleGraph createGraph(IceState iceState) {
        PuzzleGraph g = new PuzzleGraph();
        Stack<Integer> stack = new Stack<>();
        List<Integer> visited = new ArrayList<>();
        PuzzleCoordinate oldPoint = startPuzzleCoordinate;
        stack.push(oldPoint.getId());
        g.addVertex(oldPoint.getId());
        boolean pathPresent = false;
        while (!stack.isEmpty()) {
            int vertexId = stack.pop();
            visited.add(vertexId);
            oldPoint = getPointFromArray(vertexId);
            if (canFinishInThisPath(oldPoint, iceState)) {
                g.addVertex(endPuzzleCoordinate.getId());
                g.addEdge(vertexId, endPuzzleCoordinate.getId());
                pathPresent = true;
                stack.clear();
                break;
            } else {
                for (int[] direction : directions) {
                    if (canGo(oldPoint, direction)) {
                        PuzzleCoordinate newPoint = goInThisDirection(oldPoint, direction, iceState);
                        if (!visited.contains(newPoint.getId())) {
                            stack.push(newPoint.getId());
                            g.addVertex(newPoint.getId());
                            g.addEdge(vertexId, newPoint.getId());
                        }
                    }
                }
            }
        }
        return (pathPresent) ? g : null;
    }

    public boolean canFinishInThisPath(PuzzleCoordinate puzzleCoordinate, IceState iceState) {
        if (iceState.equals(FRICTION)) {
            return puzzleMap.isEnd(puzzleCoordinate);
        } else if (iceState.equals(FRICTIONLESS)) {
            if (puzzleMap.isEnd(puzzleCoordinate)) {
                return true;
            }
            if (!(puzzleCoordinate.getX() == endPuzzleCoordinate.getX() || puzzleCoordinate.getY() == endPuzzleCoordinate.getY())) {
                return false;
            }
            int start;
            int end;
            if (puzzleCoordinate.getX() == endPuzzleCoordinate.getX()) {
                start = Math.min(puzzleCoordinate.getY(), endPuzzleCoordinate.getY());
                end = Math.max(puzzleCoordinate.getY(), endPuzzleCoordinate.getY());
                for (int i = start; i <= end; i++) {
                    PuzzleCoordinate p = getPointFromArray(endPuzzleCoordinate.getX(), i);
                    if (puzzleMap.isRock(p)) {
                        return false;
                    }
                }
            } else {
                start = Math.min(puzzleCoordinate.getX(), endPuzzleCoordinate.getX());
                end = Math.max(puzzleCoordinate.getX(), endPuzzleCoordinate.getX());
                for (int i = start; i <= end; i++) {
                    PuzzleCoordinate p = getPointFromArray(i, endPuzzleCoordinate.getY());
                    if (puzzleMap.isRock(p)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return true;
    }

    public PuzzleCoordinate goInThisDirection(PuzzleCoordinate point, int[] direction, IceState iceState) {
        PuzzleCoordinate newPoint = point;
        if (iceState.equals(FRICTION)) {
            newPoint = puzzleArray[newPoint.getY() + direction[1]][newPoint.getX() + direction[0]];
        } else if (iceState.equals(FRICTIONLESS)) {
            while (canGo(newPoint, direction)) {
                newPoint = puzzleArray[newPoint.getY() + direction[1]][newPoint.getX() + direction[0]];
            }
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
