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
 * <p>The {@code PuzzleSolver} is where the bfs algorithm is performed
 * to determine the shortest path using the graph data structure and puzzleCoordinate
 * 2D array.</p>
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
     * The ice state of the current {@code PuzzleMap}. (friction/ frictionless)
     */
    private final IceState iceState;

    /**
     * Constructs and initializes a {@code PuzzleSolver}.
     * <br/>
     * On initialization the puzzleMap will be initiated.
     * puzzleArray, start, end, directions, iceState will be assigned the relevant values
     *
     * @param fileContents the file contents of the text file
     * @param directions   array containing movement coordinates of this {@code PuzzleSolver}.
     * @param iceState     the state of the ice (friction/ frictionless) of this {@code PuzzleSolver}.
     */
    public PuzzleSolver(String fileContents, int[][] directions, IceState iceState) {
        this.directions = directions;
        this.iceState = iceState;
        this.puzzleMap = new PuzzleMap(fileContents);
        this.puzzleArray = puzzleMap.getPuzzleCoordinatesMap();
        this.start = puzzleMap.getStart();
        this.end = puzzleMap.getEnd();
    }

    /**
     * The core methods where the puzzle map is converted to a graph
     * and the bfs algorithm finds the and prints the shortest path of that graph
     * <br/>
     * If graph is not null the algorithm running time is calculated by {@linkplain System#currentTimeMillis()}and
     * finally the time is printed out by calling the {@linkplain #printRunningTime(long start, long end)} method
     */
    public void solve() {
        long startTime = System.currentTimeMillis();
        PuzzleGraph puzzleGraph = createPuzzleGraph();
        if (puzzleGraph != null) {
            printShortestPath(puzzleGraph);
            long endTime = System.currentTimeMillis();
            printRunningTime(startTime, endTime);
        } else {
            System.out.println(CANNOT_SOLVE_PUZZLE);
        }
    }

    /**
     * The elapsed time is calculated by start time and end time and converted into milliseconds to seconds
     *
     * @param startTime staring time of the algorithm
     * @param endTime   ending time of the algorithm
     */
    public void printRunningTime(long startTime, long endTime) {
        System.out.println(RUNNING_TIME + ANSI_CYAN + (((float) (endTime - startTime) / 1000)) + ANSI_RESET + " seconds");
    }

    /**
     * The core method to create the graph using {@linkplain PuzzleGraph} data structure
     * <br/>
     * Using {@linkplain Stack}, the next to visit coordinate ids will be tracked.
     * <br/>
     * Using {@linkplain List}, already visited coordinate ids will be tracked.
     * <br/>
     * <b>beginning from the starting coordinate, until the next to visit stack becomes empty,
     * the possible path coordinate ids will be added to the graph.
     * </b>
     */
    public PuzzleGraph createPuzzleGraph() {

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

            if (endingCoordinateFound(currentPuzzleCoordinate)) {
                graph.addVertex(end.getId());
                graph.addEdge(vertexId, end.getId());
                pathExists = true;
                nextToVisit.clear();
                break;
            } else {
                for (int[] direction : directions) {
                    if (canGoInThisDirection(currentPuzzleCoordinate, direction)) {
                        PuzzleCoordinate newPuzzleCoordinate = goInThisDirection(currentPuzzleCoordinate, direction);
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
     * By taking the current path coordinate and ice state as arguments, this method
     * will return a boolean whether the path ends if we continue going on this direction
     * <p>
     * if the ice is with friction it only checks whether the current coordinate is the end or not
     * <p>
     * if the ice is frictionless it will check the current coordinate and the remaining coordinates
     * in the specific directions
     *
     * @param puzzleCoordinate the current coordinate
     * @return boolean value whether the path ends in the direction or not
     */
    public boolean endingCoordinateFound(PuzzleCoordinate puzzleCoordinate) {

        if (iceState.equals(FRICTION)) {
            return puzzleMap.isEnd(puzzleCoordinate);
        }

        if (iceState.equals(FRICTIONLESS)) {
            if (puzzleMap.isEnd(puzzleCoordinate)) {
                return true;
            }
            for (int[] direction : directions) {
                PuzzleCoordinate coordinate = puzzleCoordinate;
                while (!coordinate.equals(end)) {
                    coordinate = puzzleMap.getPuzzleCoordinate(coordinate.getX() + direction[0], coordinate.getY() + direction[1]);
                    if (coordinate == null) break;
                    if (puzzleMap.isRock(coordinate)) {
                        return false;
                    }
                    if (puzzleMap.isEnd(coordinate)) {
                        return true;
                    }
                }
            }
            return true;
        }
        return true;
    }

    /**
     * Verify whether we can go on this direction without any obstacles
     *
     * @param puzzleCoordinate which holds the current PuzzleCoordinate
     * @param direction        the array which contains the [x,y] coordinate values
     * @return boolean value indicating whether we can go on this direction or not
     */
    public boolean canGoInThisDirection(PuzzleCoordinate puzzleCoordinate, int[] direction) {
        boolean canGoInThisDirection;
        int x = puzzleCoordinate.getX() + direction[0];
        int y = puzzleCoordinate.getY() + direction[1];
        canGoInThisDirection = puzzleMap.isValidCoordinate(x, y) && !puzzleMap.isRock(x, y);
        return canGoInThisDirection;
    }

    /**
     * Travel in the selected direction from the current puzzle coordinate
     * <p>
     * if the ice is with friction it will only move one tile from the current coordinate to the
     * selected direction
     * <p>
     * if the ice is frictionless the last point which we slide (go) upto will be returned
     *
     * @param puzzleCoordinate current path coordinate
     * @param direction        the path directions
     * @return puzzle coordinate of the next/ last possible coordinate
     */
    public PuzzleCoordinate goInThisDirection(PuzzleCoordinate puzzleCoordinate, int[] direction) {
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
     * Prints the shortest path in both text format and graph format
     * <p>
     * {@linkplain PathDirection} enum is being used for the directions
     *
     * @param graph the graph which holds the details about the vertices and edges
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
     * Breath first search algorithm to calculate the shortest path
     *
     * @param graph the puzzle graph
     * @param start the starting coordinate
     * @param end   the ending coordinate
     * @return the shortest path puzzle coordinate ids to a list
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

    /**
     * Printing the puzzle coordinate in text format
     *
     * @param id               the id of each step
     * @param puzzleCoordinate the puzzle coordinate of each step
     * @param direction        the path direction of each step
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

}
