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

/**
 * <p>The {@code PuzzleSolver} is where the algorithm is performed using the data structure
 *
 * @author Loganathan Bhavaneetharan
 */

public class PuzzleSolver {

    /**
     * The 2D array of PuzzleCoordinates of this {@code PuzzleSolver}.
     */
    private final PuzzleCoordinate[][] puzzleArray;

    /**
     * The starting PuzzleCoordinate {@code PuzzleCoordinate}.
     */
    private final PuzzleCoordinate start;

    /**
     * The ending PuzzleCoordinate {@code PuzzleCoordinate}.
     */
    private final PuzzleCoordinate end;

    /**
     * The puzzleMap object to initialize {@code PuzzleMap}.
     */
    private final PuzzleMap puzzleMap;

    /**
     * The int[][] array which contains the movement coordinates of this {@code PuzzleSolver}.
     */
    private final int[][] directions;

    /**
     * Constructs and initializes a {@code PuzzleSolver}.
     * <br/>
     * On initialization the puzzleMap will be initiated.
     * puzzleArray, start, end, directions will be assigned the initial values
     *
     * @param fileContents the file contents of the text file
     * @param directions   array containing movement coordinates of this {@code PuzzleSolver}.
     */
    public PuzzleSolver(String fileContents, int[][] directions) {
        this.directions = directions;
        this.puzzleMap = new PuzzleMap(fileContents);
        this.puzzleArray = puzzleMap.getPuzzleCoordinatesMap();
        this.start = puzzleMap.getStart();
        this.end = puzzleMap.getEnd();
    }

    /**
     * @param iceState
     */
    public void solve(IceState iceState) {
        long startTime = System.currentTimeMillis();
        PuzzleGraph puzzleGraph = createPuzzleGraph(iceState);
        printShortestPath(puzzleGraph);
        long endTime = System.currentTimeMillis();
        printRunningTime(startTime, endTime);
    }

    /**
     * @param startTime
     * @param endTime
     */
    public void printRunningTime(long startTime, long endTime) {
        System.out.println(RUNNING_TIME + ANSI_CYAN + (((float) (endTime - startTime) / 1000)) + ANSI_RESET + " seconds");
    }

    /**
     * @param iceState
     * @return PuzzleGraph
     */
    public PuzzleGraph createPuzzleGraph(IceState iceState) {

        PuzzleGraph graph = new PuzzleGraph();
        Stack<Integer> nextToVisit = new Stack<>();
        List<Integer> visited = new ArrayList<>();

        PuzzleCoordinate currentPuzzleCoordinate = start;
        nextToVisit.push(currentPuzzleCoordinate.getId());
        graph.addVertex(currentPuzzleCoordinate.getId());

        boolean pathExists = false;

        while (!nextToVisit.isEmpty()) {

            int vertexId = nextToVisit.pop();
            visited.add(vertexId);
            currentPuzzleCoordinate = puzzleMap.getPuzzleCoordinate(vertexId);

            if (pathEndsInThisDirection(currentPuzzleCoordinate, iceState)) {
                graph.addVertex(end.getId());
                graph.addEdge(vertexId, end.getId());
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

    /**
     * @param puzzleCoordinate
     * @param iceState
     * @return PuzzleGraph
     */
    public boolean pathEndsInThisDirection(PuzzleCoordinate puzzleCoordinate, IceState iceState) {

        if (iceState.equals(FRICTION)) {
            return puzzleMap.isEnd(puzzleCoordinate);
        }

        if (iceState.equals(FRICTIONLESS)) {
            if (puzzleMap.isEnd(puzzleCoordinate)) {
                return true;
            }
            if (!(puzzleCoordinate.getX() == end.getX() || puzzleCoordinate.getY() == end.getY())) {
                return false;
            }
            if (puzzleCoordinate.getX() == end.getX()) {
                int startingCoordinate = Math.min(puzzleCoordinate.getY(), end.getY());
                int endingCoordinate = Math.max(puzzleCoordinate.getY(), end.getY());
                for (int i = startingCoordinate; i <= endingCoordinate; i++) {
                    PuzzleCoordinate pc = puzzleMap.getPuzzleCoordinate(end.getX(), i);
                    if (puzzleMap.isRock(pc)) {
                        return false;
                    }
                }
            } else {
                int startingCoordinate = Math.min(puzzleCoordinate.getX(), end.getX());
                int endingCoordinate = Math.max(puzzleCoordinate.getX(), end.getX());
                for (int i = startingCoordinate; i <= endingCoordinate; i++) {
                    PuzzleCoordinate pc = puzzleMap.getPuzzleCoordinate(i, end.getY());
                    if (puzzleMap.isRock(pc)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return true;
    }

    /**
     * @param puzzleCoordinate
     * @param direction
     * @return PuzzleGraph
     */
    public boolean canGoInThisDirection(PuzzleCoordinate puzzleCoordinate, int[] direction) {
        boolean canGoInThisDirection;
        int x = puzzleCoordinate.getX() + direction[0];
        int y = puzzleCoordinate.getY() + direction[1];
        canGoInThisDirection = puzzleMap.isValidCoordinate(x, y) && !puzzleMap.isRock(x, y);
        return canGoInThisDirection;
    }

    /**
     * @param puzzleCoordinate
     * @param iceState
     * @param direction
     * @return iceState
     */
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

    /**
     * @param graph
     */
    public void printShortestPath(PuzzleGraph graph) {
        List<PuzzleCoordinate> result = new ArrayList<>();
        List<Integer> paths = breadthFirstSearch(graph, start, end);
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

    /**
     * @param id
     * @param puzzleCoordinate
     * @param direction
     */
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

    /**
     * @param graph
     * @param start
     * @param end
     * @return paths
     */
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
