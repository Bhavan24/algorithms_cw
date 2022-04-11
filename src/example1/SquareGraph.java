package example1;

import java.awt.*;
import java.util.List;
import java.util.*;

public class SquareGraph {

    private final Node[][] map;
    private final Point startPosition;
    private final Point targetPosition;
    private final PriorityQueue<Node> openNodes;
    private final Set<Node> closedNodes;

    public SquareGraph(int mapDimension) {
        map = new Node[mapDimension][mapDimension];
        startPosition = new Point();
        targetPosition = new Point();
        openNodes = new PriorityQueue<>();
        closedNodes = new HashSet<>();
    }

    public Node getMapCell(Point coord) {
        return map[(int) coord.getX()][(int) coord.getY()];
    }

    public void setMapCell(Point coord, Node n) {
        map[(int) coord.getX()][(int) coord.getY()] = n;
    }

    public Point getStartPosition() {
        return startPosition;
    }

    public Point getTargetPosition() {
        return targetPosition;
    }

    public void setStartPosition(Point coord) {
        startPosition.setLocation(coord);
    }

    public void setTargetPosition(Point coord) {
        targetPosition.setLocation(coord);
    }

    public int getDimension() {
        return map.length;
    }

    public void addToOpenNodes(Node n) {
        n.setOpen();
        openNodes.add(n);
    }

    public Node popBestOpenNode() {
        return openNodes.remove();
    }

    public void addToClosedNodes(Node n) {
        n.setClosed();
        closedNodes.add(n);
    }

    public boolean isInsideMap(Point p) {
        return ((p.getX() >= 0) && (p.getX() < getDimension()) && (p.getY() >= 0) && (p.getY() < getDimension()));
    }

    public Set<Node> getNeighbours(Node n) {
        Set<Node> neighbours = new HashSet<>();
        int[][] DIRECTIONS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};;
//        int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] d : DIRECTIONS) {
            if (!(d[0] == 0 && d[1] == 0)) {
                Point point = new Point(n.getX() + d[0], n.getY() + d[1]);
                if (isInsideMap(point)) {
                    Node temp = getMapCell(point);
                    if (!temp.isObstacle()) {
                        if (temp.isNormal()) {
                            neighbours.add(temp);
                        }
                    }
                }
            }
        }
        return neighbours;
    }

    static double calculateDistance(Point from, Point to) {
        return Math.pow(Math.pow(from.getX() - to.getX(), 2) + Math.pow(from.getY() - to.getY(), 2), 0.5);
    }

    public ArrayList<Node> reconstructPath(Node target) {
        ArrayList<Node> path = new ArrayList<>();
        Node current = target;

        while (current.getParent() != null) {
            path.add(current.getParent());
            current = current.getParent();
        }
        Collections.reverse(path);
        return path;
    }


    public List<List<Integer>> getPathsToList(ArrayList<Node> path) {
        List<List<Integer>> paths = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            Node node = path.get(i);
            List<Integer> newList = new ArrayList<>();
            newList.add(node.getX());
            newList.add(node.getY());
            paths.add(i, newList);
        }
        return paths;
    }

    public void printPath(ArrayList<Node> path) {
        for (int i = 0; i < path.size(); i++) {
            Node node = path.get(i);
            if (i == 0) {
                System.out.printf("Start at (%d,%d) \n", (node.getY() + 1), (node.getX() + 1));
            } else {
                System.out.printf("Move to (%d,%d) \n", (node.getY() + 1), (node.getX() + 1));
            }
        }
    }

    public ArrayList<Node> executeAStar() {
        Node start = getMapCell(getStartPosition());
        Node target = getMapCell(getTargetPosition());
        addToOpenNodes(start);

        start.setCostFromStart(0);
        start.setTotalCost(start.getCostFromStart() + calculateDistance(start.getPosition(), target.getPosition()));
        while (!openNodes.isEmpty()) {
            Node current = popBestOpenNode();
            if (current.equals(target)) {
                return reconstructPath(target);
            }

            addToClosedNodes(current);
            Set<Node> neighbours = getNeighbours(current);
            for (Node neighbour : neighbours) {
                if (!neighbour.isClosed()) {
                    double tentativeCost = current.getCostFromStart() + calculateDistance(current.getPosition(), neighbour.getPosition());

                    if ((!neighbour.isOpen()) || (tentativeCost < neighbour.getCostFromStart())) {
                        neighbour.setParent(current);
                        neighbour.setCostFromStart(tentativeCost);
                        neighbour.setTotalCost(neighbour.getCostFromStart() + calculateDistance(neighbour.getPosition(), start.getPosition()));
                        if (!neighbour.isOpen()) addToOpenNodes(neighbour);
                    }
                }

            }
        }

        return null;
    }

}
