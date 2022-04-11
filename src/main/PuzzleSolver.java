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
        long startTime = System.currentTimeMillis();
        PuzzleGraph puzzleGraph = createPuzzleGraph(iceState);
        printShortestPath(puzzleGraph);
        long endTime = System.currentTimeMillis();
        printRunningTime(startTime, endTime);
    }

    public void printRunningTime(long startTime, long endTime) {
        System.out.println(RUNNING_TIME + ANSI_CYAN + (((float) (endTime - startTime) / 1000)) + ANSI_RESET + " seconds");
    }

    public PuzzleGraph createPuzzleGraph(IceState iceState) {

        PuzzleGraph graph = new PuzzleGraph();
        Stack<Integer> stack = new Stack<>();
        List<Integer> visited = new ArrayList<>();

        PuzzleCoordinate currentPuzzleCoordinate = startPuzzleCoordinate;
        stack.push(currentPuzzleCoordinate.getId());
        graph.addVertex(currentPuzzleCoordinate.getId());

        boolean pathPresent = false;

        while (!stack.isEmpty()) {

            int vertexId = stack.pop();
            visited.add(vertexId);
            currentPuzzleCoordinate = puzzleMap.getPuzzleCoordinate(vertexId);

            if (pathEndsInThisDirection(currentPuzzleCoordinate, iceState)) {
                graph.addVertex(endPuzzleCoordinate.getId());
                graph.addEdge(vertexId, endPuzzleCoordinate.getId());
                pathPresent = true;
                stack.clear();
                break;
            } else {
                for (int[] direction : directions) {
                    if (canGoInThisDirection(currentPuzzleCoordinate, direction)) {
                        PuzzleCoordinate newPuzzleCoordinate = goInThisDirection(currentPuzzleCoordinate, direction, iceState);
                        if (!visited.contains(newPuzzleCoordinate.getId())) {
                            stack.push(newPuzzleCoordinate.getId());
                            graph.addVertex(newPuzzleCoordinate.getId());
                            graph.addEdge(vertexId, newPuzzleCoordinate.getId());
                        }
                    }
                }
            }
        }

        return (pathPresent) ? graph : null;
    }

    public boolean pathEndsInThisDirection(PuzzleCoordinate puzzleCoordinate, IceState iceState) {
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
                    PuzzleCoordinate pc = puzzleMap.getPuzzleCoordinate(endPuzzleCoordinate.getX(), i);
                    if (puzzleMap.isRock(pc)) {
                        return false;
                    }
                }
            } else {
                start = Math.min(puzzleCoordinate.getX(), endPuzzleCoordinate.getX());
                end = Math.max(puzzleCoordinate.getX(), endPuzzleCoordinate.getX());
                for (int i = start; i <= end; i++) {
                    PuzzleCoordinate pc = puzzleMap.getPuzzleCoordinate(i, endPuzzleCoordinate.getY());
                    if (puzzleMap.isRock(pc)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return true;
    }

    public boolean canGoInThisDirection(PuzzleCoordinate puzzleCoordinate, int[] direction) {
        boolean canGoInThisDirection;
        int x = puzzleCoordinate.getX() + direction[0];
        int y = puzzleCoordinate.getY() + direction[1];
        canGoInThisDirection = puzzleMap.isValidCoordinate(x, y) && !puzzleMap.isRock(x, y);
        return canGoInThisDirection;
    }

    public PuzzleCoordinate goInThisDirection(PuzzleCoordinate puzzleCoordinate, int[] direction, IceState iceState) {
        PuzzleCoordinate newPoint = puzzleCoordinate;
        if (iceState.equals(FRICTION)) {
            newPoint = puzzleArray[newPoint.getY() + direction[1]][newPoint.getX() + direction[0]];
        } else if (iceState.equals(FRICTIONLESS)) {
            while (canGoInThisDirection(newPoint, direction)) {
                newPoint = puzzleArray[newPoint.getY() + direction[1]][newPoint.getX() + direction[0]];
            }
        }
        return newPoint;
    }

    public void printShortestPath(PuzzleGraph graph) {
        List<PuzzleCoordinate> result = new ArrayList<>();
        List<Integer> paths = breadthFirstSearch(graph, startPuzzleCoordinate, endPuzzleCoordinate);
        if (paths.isEmpty()) {
            System.out.println(CANNOT_SOLVE_PUZZLE);
        } else {
            PuzzleCoordinate currentCoordinate = null;
            for (int i = 0; i < paths.size(); i++) {
                String direction = "";
                PuzzleCoordinate puzzleCoordinate = puzzleMap.getPuzzleCoordinate(paths.get(i));
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

    public List<Integer> breadthFirstSearch(PuzzleGraph graph, PuzzleCoordinate start, PuzzleCoordinate end) {

        int startCoordinateId = start.getId();
        int endCoordinateId = end.getId();

        Set<Integer> visited = new LinkedHashSet<>();
        Queue<Integer> nextToVisit = new LinkedList<>();
        Map<Integer, Integer> parentMap = new HashMap<>();

        nextToVisit.add(startCoordinateId);
        visited.add(startCoordinateId);
        parentMap.put(startCoordinateId, null);

        while (!nextToVisit.isEmpty()) {

            int currentVertexId = nextToVisit.poll();

            if (currentVertexId == endCoordinateId) break;

            for (PuzzleVertex neighbourVertex : graph.getAdjVertices(currentVertexId)) {
                int neighbourVertexId = neighbourVertex.getId();

                if (!visited.contains(neighbourVertexId)) {
                    parentMap.put(neighbourVertexId, currentVertexId);
                    visited.add(neighbourVertexId);
                    nextToVisit.add(neighbourVertexId);
                }
            }
        }

        List<Integer> paths = new ArrayList<>();
        paths.add(endCoordinateId);

        for (int coordinateId = -1; coordinateId != startCoordinateId; ) {
            coordinateId = parentMap.get(endCoordinateId);
            paths.add(coordinateId);
            endCoordinateId = coordinateId;
        }

        Collections.reverse(paths);
        return paths;
    }

}
