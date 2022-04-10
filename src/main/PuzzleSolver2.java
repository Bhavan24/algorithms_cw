package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static main.GameConstants.*;

public class PuzzleSolver2 {

    PuzzleMap puzzleMap;
    PuzzleCoordinate[][] puzzleCoordinates;
    private PuzzleCoordinate startPoint;
    private PuzzleCoordinate finishPoint;
    int[][] DIRECTIONS;

    public void initializePuzzleMap(String fileContents) {

        if (fileContents == null || fileContents.trim().equals("")) {
            System.out.println(FILE_IS_EMPTY);
            return;
        }

        String[] lines = fileContents.split("[\r]?\n");
        int rows = lines.length;
        int columns = lines[0].length();

        puzzleCoordinates = new PuzzleCoordinate[rows][columns];

        for (int i = 0; i < rows; i++) {
            if (lines[i].length() != columns) {
                System.out.println(INVALID_DATA);
                return;
            } else {
                int id = 0;
                for (int j = 0; j < columns; j++) {
                    switch (lines[i].charAt(j)) {
                        case ICE:
                            puzzleCoordinates[i][j] = new PuzzleCoordinate(id, j, i, ICE, ICE_VALUE);
                            break;
                        case ROCK:
                            puzzleCoordinates[i][j] = new PuzzleCoordinate(id, j, i, ROCK, ROCK_VALUE);
                            break;
                        case START:
                            puzzleCoordinates[i][j] = new PuzzleCoordinate(id, j, i, START, START_VALUE);
                            startPoint = puzzleCoordinates[i][j];
                            break;
                        case FINISH:
                            puzzleCoordinates[i][j] = new PuzzleCoordinate(id, j, i, FINISH, FINISH_VALUE);
                            finishPoint = puzzleCoordinates[i][j];
                            break;
                    }
                    id++;
                }
            }
        }
    }

    public PuzzleSolver2(PuzzleMap puzzleMap, int[][] directions) {
        this.puzzleMap = puzzleMap;
        this.puzzleCoordinates = puzzleMap.getPuzzleMapArray();
        this.startPoint = puzzleMap.getStart();
        this.finishPoint = puzzleMap.getEnd();
        this.DIRECTIONS = directions;
    }

    public void solve(PuzzleGraph puzzleGraph) {
        printPathDetails(createGraph(puzzleGraph));
    }

    public String getDirectionDetails(PuzzleCoordinate point, String direction) {
        if (puzzleMap.isStart(point)) {
            return "Start at (" + (point.getX() + 1) + "," + (point.getY() + 1) + ")";
        } else {
            return "Move " + direction + " to (" + (point.getX() + 1) + "," + (point.getY() + 1) + ")";
        }
    }

    public void printPathDetails(PuzzleGraph g) {
        Map<Integer, Integer> pastVertexMap = g.breadthFirstTraversal(g, startPoint.getId(), finishPoint.getId());
        List<Integer> pathList = g.findPathList(pastVertexMap, startPoint.getId(), finishPoint.getId());
        if (pathList.isEmpty()) {
            System.out.println("There is no path!");
        } else {
            PuzzleCoordinate oldPoint = null;
            for (int i = 0; i < pathList.size(); i++) {
                String direction = "";
                PuzzleCoordinate point = getPointFromArray(pathList.get(i));
                if (oldPoint != null) {
                    if (point != null && point.getY() == oldPoint.getY()) {
                        if (point.getX() > oldPoint.getX()) {
                            direction = "right";
                        } else {
                            direction = "left";
                        }
                    } else {
                        if (point != null && point.getY() > oldPoint.getY()) {
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

    private PuzzleCoordinate getPointFromArray(int vertexId) {
        for (PuzzleCoordinate[] puzzleCoordinate : puzzleCoordinates) {
            for (PuzzleCoordinate coordinate : puzzleCoordinate) {
                if (coordinate.getId() == vertexId) {
                    return coordinate;
                }
            }
        }
        return null;
    }

    private PuzzleCoordinate getPointFromArray(int x, int y) {
        for (PuzzleCoordinate[] puzzleCoordinate : puzzleCoordinates) {
            for (PuzzleCoordinate coordinate : puzzleCoordinate) {
                if (coordinate.getX() == x && coordinate.getY() == y) {
                    return coordinate;
                }
            }
        }
        return null;
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
                if (canGoTop(oldPoint)) {
                    PuzzleCoordinate newPoint = go(oldPoint, DIRECTIONS[0]);
                    if (!visited.contains(newPoint.getId())) {
                        stack.push(newPoint.getId());
                        g.addVertex(newPoint.getId());
                        g.addEdge(vertexId, newPoint.getId());
                    }
                }
                if (canGoDown(oldPoint)) {
                    PuzzleCoordinate newPoint = go(oldPoint, DIRECTIONS[1]);
                    if (!visited.contains(newPoint.getId())) {
                        stack.push(newPoint.getId());
                        g.addVertex(newPoint.getId());
                        g.addEdge(vertexId, newPoint.getId());
                    }
                }
                if (canGoRight(oldPoint)) {
                    PuzzleCoordinate newPoint = go(oldPoint, DIRECTIONS[2]);
                    if (!visited.contains(newPoint.getId())) {
                        stack.push(newPoint.getId());
                        g.addVertex(newPoint.getId());
                        g.addEdge(vertexId, newPoint.getId());
                    }
                }
                if (canGoLeft(oldPoint)) {
                    PuzzleCoordinate newPoint = go(oldPoint, DIRECTIONS[3]);
                    if (!visited.contains(newPoint.getId())) {
                        stack.push(newPoint.getId());
                        g.addVertex(newPoint.getId());
                        g.addEdge(vertexId, newPoint.getId());
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
                if (puzzleMap.isRock(p)) {
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
                if (puzzleMap.isRock(p)) {
                    return false;
                }
            }
        }
        return true;
    }

    public PuzzleCoordinate go(PuzzleCoordinate cur, int[] direction) {
        PuzzleCoordinate newPoint = new PuzzleCoordinate(cur.getX() + direction[0], cur.getY() + direction[1], cur);
        System.out.println(newPoint);
        if (direction[0] == 0 && direction[1] == 1) {
            while (canGoTop(newPoint)) {
                newPoint = new PuzzleCoordinate(newPoint.getX() + direction[0], newPoint.getY() + direction[1], newPoint);
            }
        }
        if (direction[0] == 1 && direction[1] == 0) {
            while (canGoRight(newPoint)) {
                newPoint = new PuzzleCoordinate(newPoint.getX() + direction[0], newPoint.getY() + direction[1], newPoint);
            }
        }
        if (direction[0] == 0 && direction[1] == -1) {
            while (canGoDown(newPoint)) {
                newPoint = new PuzzleCoordinate(newPoint.getX() + direction[0], newPoint.getY() + direction[1], newPoint);
            }
        }
        if (direction[0] == -1 && direction[1] == 0) {
            while (canGoLeft(newPoint)) {
                newPoint = new PuzzleCoordinate(newPoint.getX() + direction[0], newPoint.getY() + direction[1], newPoint);
            }
        }
        return newPoint;
    }

    public boolean canGoTop(PuzzleCoordinate point) {
        if (point.getY() == 0) return false;
        return puzzleMap.isValidCoordinate(point) && !puzzleMap.isRock(point);
    }

    public boolean canGoRight(PuzzleCoordinate point) {
        if (point.getX() == puzzleMap.getColumns() - 1) return false;
        return puzzleMap.isValidCoordinate(point) && !puzzleMap.isRock(point);
    }

    public boolean canGoDown(PuzzleCoordinate point) {
        if (point.getY() == puzzleMap.getRows() - 1) return false;
        return puzzleMap.isValidCoordinate(point) && !puzzleMap.isRock(point);
    }

    public boolean canGoLeft(PuzzleCoordinate point) {
        if (point.getX() == 0) return false;
        return puzzleMap.isValidCoordinate(point) && !puzzleMap.isRock(point);
    }
}