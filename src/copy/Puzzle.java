package copy;

import java.io.FileReader;
import java.util.*;


public class Puzzle {

    private Point[][] puzzleArray;
    private int maxWidth;
    private int maxHeight;
    private Point startPoint;
    private Point finishPoint;
    private String fileLocation;

    public String readPuzzleFile(String filePath) {
        String fileContents = "";
        try {
            Scanner reader = new Scanner(new FileReader(filePath));
            StringBuilder stringBuilder = new StringBuilder();
            while (reader.hasNext()) {
                stringBuilder.append(reader.nextLine()).append("\n");
            }
            reader.close();
            fileContents = stringBuilder.toString();
            System.out.println("PUZZLE_LOADED");
        } catch (Exception e) {
            System.out.println("FILE_DOES_NOT_EXIST");
            System.out.println("TRY_AGAIN");
        }
        return fileContents;
    }

    public void initializePuzzleMap(String fileContents) {

        if (fileContents == null || fileContents.trim().equals("")) {
            System.out.println("Empty File");
            return;
        }

        String[] lines = fileContents.split("[\r]?\n");
        maxHeight = lines.length;
        maxWidth = lines[0].length();

        puzzleArray = new Point[maxHeight][maxWidth];

        int id = 0;
        for (int x = 0; x < maxHeight; x++) {
            if (lines[x].length() != maxWidth) {
                System.out.println("INVALID_DATA");
                return;
            } else {
                for (int y = 0; y < maxWidth; y++) {
                    char c = lines[x].charAt(y);
                    switch (c) {
                        case 'S':
                            startPoint = new Point(id, x, y, Character.toString(c));
                            break;
                        case 'F':
                            finishPoint = new Point(id, x, y, Character.toString(c));
                            break;
                    }
                    Point point = new Point(id, x, y, Character.toString(c));
                    id++;
                    puzzleArray[y][x] = point;
                }
            }
        }
    }

    public void initializePuzzleMap2(String fileContents) {

        if (fileContents == null || fileContents.trim().equals("")) {
            System.out.println("Empty File");
            return;
        }

        String[] lines = fileContents.split("[\r]?\n");
        maxHeight = lines.length;
        maxWidth = lines[0].length();
        puzzleArray = new Point[maxHeight][maxWidth];

        int id = 0;
        for (int x = 0; x < maxHeight; x++) {
            if (lines[x].length() != maxWidth) {
                System.out.println("INVALID_DATA");
            } else {
                for (int y = 0; y < maxWidth; y++) {
                    char c = lines[x].charAt(y);
                    switch (c) {
                        case 'S':
                            startPoint = new Point(id, y, x, Character.toString(c));
                            break;
                        case 'F':
                            finishPoint = new Point(id, y, x, Character.toString(c));
                            break;
                    }
                    Point point = new Point(id, y, x, Character.toString(c));
                    id++;
                    puzzleArray[x][y] = point;
                }
            }
        }

    }

    public Point getPointFromArray(int vertexId) {
        for (Point[] points : puzzleArray) {
            for (Point point : points) {
                if (point.getId() == vertexId) {
                    return point;
                }
            }
        }
        return null;
    }

    public Point getPointFromArray(int x, int y) {
        for (Point[] points : puzzleArray) {
            for (Point point : points) {
                if (point.getX() == x && point.getY() == y) {
                    return point;
                }
            }
        }
        return null;
    }

    public String getDirectionDetails(Point point, String direction) {
        if (point.getLetter().equals("S")) {
            return "Start at (" + (point.getX() + 1) + "," + (point.getY() + 1) + ")";
        } else {
            return "Move " + direction + " to (" + (point.getX() + 1) + "," + (point.getY() + 1) + ")";
        }
    }

    public List<List<Integer>> getPathList(UndirectedGraph g) {
        List<List<Integer>> result = new ArrayList<>();
        Map<Integer, Integer> pastVertexMap = g.breadthFirstTraversal(g, startPoint.getId());
        List<Integer> pathList = g.findPathList(pastVertexMap, startPoint.getId(), finishPoint.getId());
        for (Integer integer : pathList) {
            Point point = getPointFromArray(integer);
            List<Integer> coordinates = new ArrayList<>();
            coordinates.add(point.getX());
            coordinates.add(point.getY());
            result.add(coordinates);
        }
        return result;
    }

    public void printPathDetails(UndirectedGraph g) {
        Map<Integer, Integer> pastVertexMap = g.breadthFirstTraversal(g, startPoint.getId());
        List<Integer> pathList = g.findPathList(pastVertexMap, startPoint.getId(), finishPoint.getId());
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

    public UndirectedGraph createGraph(UndirectedGraph g) {
        Stack<Integer> stack = new Stack<>();
        List<Integer> visited = new ArrayList<>();
        Point oldPoint = startPoint;
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
                if (canTravelNorth(oldPoint)) {
                    Point newPoint = travelNorthPoint(oldPoint);
                    if (!visited.contains(newPoint.getId())) {
                        stack.push(newPoint.getId());
                        g.addVertex(newPoint.getId());
                        g.addEdge(vertexId, newPoint.getId());
                    }
                }
                if (canTravelSouth(oldPoint)) {
                    Point newPoint = travelSouthPoint(oldPoint);
                    if (!visited.contains(newPoint.getId())) {
                        stack.push(newPoint.getId());
                        g.addVertex(newPoint.getId());
                        g.addEdge(vertexId, newPoint.getId());
                    }
                }
                if (canTravelEast(oldPoint)) {
                    Point newPoint = travelEastPoint(oldPoint);
                    if (!visited.contains(newPoint.getId())) {
                        stack.push(newPoint.getId());
                        g.addVertex(newPoint.getId());
                        g.addEdge(vertexId, newPoint.getId());
                    }
                }
                if (canTravelWest(oldPoint)) {
                    Point newPoint = travelWestPoint(oldPoint);
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

    public Point travelNorthPoint(Point point) {
        Point newPoint = point;
        while (canTravelNorth(newPoint)) {
            newPoint = puzzleArray[newPoint.getY() - 1][newPoint.getX()];
        }
        return newPoint;
    }

    public Point travelSouthPoint(Point point) {
        Point newPoint = point;
        while (canTravelSouth(newPoint)) {
            newPoint = puzzleArray[newPoint.getY() + 1][newPoint.getX()];
        }
        return newPoint;
    }

    public Point travelEastPoint(Point point) {
        Point newPoint = point;
        while (canTravelEast(newPoint)) {
            newPoint = puzzleArray[newPoint.getY()][newPoint.getX() + 1];
        }
        return newPoint;
    }

    public Point travelWestPoint(Point point) {
        Point newPoint = point;
        while (canTravelWest(newPoint)) {
            newPoint = puzzleArray[newPoint.getY()][newPoint.getX() - 1];
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
                if (p.getLetter().equals("0")) {
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
                if (p.getLetter().equals("0")) {
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
        return !(puzzleArray[point.getY() - 1][point.getX()].getLetter().equals("0"));
    }

    public boolean canTravelSouth(Point point) {
        if (point.getY() == (maxHeight - 1)) {
            return false;
        }
        return !(puzzleArray[point.getY() + 1][point.getX()].getLetter().equals("0"));
    }

    public boolean canTravelEast(Point point) {
        if (point.getX() == (maxWidth - 1)) {
            return false;
        }
        return !(puzzleArray[point.getY()][point.getX() + 1].getLetter().equals("0"));
    }

    public boolean canTravelWest(Point point) {
        if (point.getX() == 0) {
            return false;
        }
        return !(puzzleArray[point.getY()][point.getX() - 1].getLetter().equals("0"));
    }

    public Point[][] getPuzzleArray() {
        return puzzleArray;
    }

    public void setPuzzleArray(Point[][] puzzleArray) {
        this.puzzleArray = puzzleArray;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getFinishPoint() {
        return finishPoint;
    }

    public void setFinishPoint(Point finishPoint) {
        this.finishPoint = finishPoint;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

}
