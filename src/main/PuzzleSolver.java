/******************************************************************************
 * Name: Loganathan Bhavaneetharan
 * UOW ID: w1810599
 * IIT ID: 20201212
 * B.Eng.Software Engineering, 2nd Year
 *
 *  Description:
 *        The programs takes the filepath, moving direction, state of the ice
 *		  as inputs and will provide the shortest path from point 'S' to
 *		  point 'F' using breadth-first search algorithm
 *
 * Run the program by running SlidingPuzzle.main()
 *
 *****************************************************************************/

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
        Stack<Integer> nextToVisit = new Stack<>();
        List<Integer> visited = new ArrayList<>();

        PuzzleCoordinate currentPuzzleCoordinate = startPuzzleCoordinate;
        nextToVisit.push(currentPuzzleCoordinate.getId());
        graph.addVertex(currentPuzzleCoordinate.getId());

        boolean pathExists = false;

        while (!nextToVisit.isEmpty()) {

            int vertexId = nextToVisit.pop();
            visited.add(vertexId);
            currentPuzzleCoordinate = puzzleMap.getPuzzleCoordinate(vertexId);

            if (pathEndsInThisDirection(currentPuzzleCoordinate, iceState)) {
                graph.addVertex(endPuzzleCoordinate.getId());
                graph.addEdge(vertexId, endPuzzleCoordinate.getId());
                pathExists = true;
                nextToVisit.clear();
                break;
            } else {
                for (int[] direction : directions) {
                    if (canGoInThisDirection(currentPuzzleCoordinate, direction)) {
                        PuzzleCoordinate newPuzzleCoordinate = goInThisDirection(currentPuzzleCoordinate, direction, iceState);
                        if (!visited.contains(newPuzzleCoordinate.getId())) {
                            nextToVisit.push(newPuzzleCoordinate.getId());
                            graph.addVertex(newPuzzleCoordinate.getId());
                            graph.addEdge(vertexId, newPuzzleCoordinate.getId());
                        }
                    }
                }
            }
        }

        return (pathExists) ? graph : null;
    }

    public boolean pathEndsInThisDirection(PuzzleCoordinate puzzleCoordinate, IceState iceState) {

        if (iceState.equals(FRICTION)) {
            return puzzleMap.isEnd(puzzleCoordinate);
        }

        if (iceState.equals(FRICTIONLESS)) {
            if (puzzleMap.isEnd(puzzleCoordinate)) {
                return true;
            }
            if (!(puzzleCoordinate.getX() == endPuzzleCoordinate.getX() || puzzleCoordinate.getY() == endPuzzleCoordinate.getY())) {
                return false;
            }
            if (puzzleCoordinate.getX() == endPuzzleCoordinate.getX()) {
                int startingCoordinate = Math.min(puzzleCoordinate.getY(), endPuzzleCoordinate.getY());
                int endingCoordinate = Math.max(puzzleCoordinate.getY(), endPuzzleCoordinate.getY());
                for (int i = startingCoordinate; i <= endingCoordinate; i++) {
                    PuzzleCoordinate pc = puzzleMap.getPuzzleCoordinate(endPuzzleCoordinate.getX(), i);
                    if (puzzleMap.isRock(pc)) {
                        return false;
                    }
                }
            } else {
                int startingCoordinate = Math.min(puzzleCoordinate.getX(), endPuzzleCoordinate.getX());
                int endingCoordinate = Math.max(puzzleCoordinate.getX(), endPuzzleCoordinate.getX());
                for (int i = startingCoordinate; i <= endingCoordinate; i++) {
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
        }

        if (iceState.equals(FRICTIONLESS)) {
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
