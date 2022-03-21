import java.awt.*;


class Node implements Comparable<Node> {

    private enum State {
        UNVISITED, OPEN, CLOSED
    }

    private enum Type {
        NORMAL, OBSTACLE
    }

    private double costFromStart;
    private double costToTarget;
    private double totalCost;
    private Node parent;
    private State state;
    private Type type;
    private int x;
    private int y;

    public void UpdateCosts() {
        this.totalCost = (this.costFromStart + this.costToTarget);
    }

    public Node(int x, int y, String type) {
        this.costFromStart = 0;
        this.costToTarget = 0;
        this.totalCost = 0;
        this.parent = null;
        this.state = State.UNVISITED;
        this.type = Type.valueOf(type);
        this.x = x;
        this.y = y;
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node n) {
        this.parent = n;
    }

    public double getCostFromStart() {
        return costFromStart;
    }

    public double getCostToTarget() {
        return costToTarget;
    }

    public void setCostFromStart(double cost) {
        this.costFromStart = cost;
    }

    public void setCostToTarget(double cost) {
        this.costFromStart = cost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double cost) {
        this.totalCost = cost;
    }

    public boolean isObstacle() {
        return this.type == Type.OBSTACLE;
    }

    public boolean isOpen() {
        return this.state == State.OPEN;
    }

    public boolean isClosed() {
        return this.state == State.CLOSED;
    }

    public void setOpen() {
        this.state = State.OPEN;
    }

    public void setClosed() {
        this.state = State.CLOSED;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Node n) {
        if (this.getTotalCost() < n.getTotalCost()) {
            return -1;
        } else if (this.getTotalCost() > n.getTotalCost()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else {
            Node n = (Node) o;
            Point firstPosition = this.getPosition();
            Point secondPosition = n.getPosition();
            return firstPosition.equals(secondPosition);
        }
    }

}
