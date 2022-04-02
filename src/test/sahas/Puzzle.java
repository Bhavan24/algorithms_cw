package test.sahas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Puzzle {

    private Point[][] puzzleArray;
    private int maxWidth;
    private int maxHeight;
    private Point startPoint;
    private Point finishPoint;
    private String fileLocation;

    public void initializePuzzleArray() {
        String data = "";
        int height = 0;
        int width = 0;
        try {
            File myObj = new File(fileLocation);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                height++;
            }
            width = data.length();
            maxHeight = height;
            maxWidth = width;
            myReader.close();
            puzzleArray = new Point[maxHeight][maxWidth];
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void fillPuzzleArray() {
        String data = "";
        int yCord = 0;
        try {
            File myObj = new File(fileLocation);
            Scanner myReader = new Scanner(myObj);
            int id = 0;
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                for (int i = 0; i < data.length(); i++) {
                    char c = data.charAt(i);
                    if (c == 'S') {
                        startPoint = new Point(id, i, yCord, Character.toString(c));
                    }
                    if (c == 'F') {
                        finishPoint = new Point(id, i, yCord, Character.toString(c));
                    }
                    Point point = new Point(id, i, yCord, Character.toString(c));
                    id++;
                    puzzleArray[yCord][i] = point;
                }
                yCord++;
            }
            myReader.close();
            //System.out.println(Arrays.deepToString(puzzleArray).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Point getPointFromArray(int vertexId) {
        for (int i = 0; i < puzzleArray.length; i++) {
            for (int j = 0; j < puzzleArray[i].length; j++) {
                if (puzzleArray[i][j].getId() == vertexId) {
                    return puzzleArray[i][j];
                }
            }
        }
        return null;
    }

    public Point getPointFromArray(int x, int y) {
        for (int i = 0; i < puzzleArray.length; i++) {
            for (int j = 0; j < puzzleArray[i].length; j++) {
                if (puzzleArray[i][j].getX() == x && puzzleArray[i][j].getY() == y) {
                    return puzzleArray[i][j];
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
        Map<Integer, Integer> pastVertexMap = g.breadthFirstTraversal(g, startPoint.getId(), finishPoint.getId());
        List<Integer> pathList = g.findPathList(pastVertexMap, startPoint.getId(), finishPoint.getId());
        for (int i = 0; i < pathList.size(); i++) {
            Point point = getPointFromArray(pathList.get(i));
            List<Integer> coordinates = new ArrayList<>();
            coordinates.add(point.getX());
            coordinates.add(point.getY());
            result.add(coordinates);
        }
        return result;
    }

    public void printPathDetails(UndirectedGraph g) {
        Map<Integer, Integer> pastVertexMap = g.breadthFirstTraversal(g, startPoint.getId(), finishPoint.getId());
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
        Stack<Integer> stack = new Stack<Integer>();
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
        if (!(puzzleArray[point.getY() - 1][point.getX()].getLetter().equals("0"))) {
            return true;
        }
        return false;
    }

    public boolean canTravelSouth(Point point) {
        if (point.getY() == (maxHeight - 1)) {
            return false;
        }
        if (!(puzzleArray[point.getY() + 1][point.getX()].getLetter().equals("0"))) {
            return true;
        }
        return false;
    }

    public boolean canTravelEast(Point point) {
        if (point.getX() == (maxWidth - 1)) {
            return false;
        }
        if (!(puzzleArray[point.getY()][point.getX() + 1].getLetter().equals("0"))) {
            return true;
        }
        return false;
    }

    public boolean canTravelWest(Point point) {
        if (point.getX() == 0) {
            return false;
        }
        if (!(puzzleArray[point.getY()][point.getX() - 1].getLetter().equals("0"))) {
            return true;
        }
        return false;
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
