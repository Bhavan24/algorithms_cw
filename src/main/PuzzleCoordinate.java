package main;

import java.awt.*;

public class PuzzleCoordinate implements Comparable<PuzzleCoordinate> {

    private enum State {
        UNVISITED, OPEN, CLOSED
    }

    private enum Type {
        NORMAL, OBSTACLE
    }

    private double costFromStart;
    private double costToTarget;
    private double totalCost;
    private PuzzleCoordinate parent;
    private State state;
    private Type type;
    private int x;
    private int y;

    public void PuzzleCoordinate() {
        this.totalCost = (this.costFromStart + this.costToTarget);
    }

    public PuzzleCoordinate(int x, int y, String type) {
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

    public PuzzleCoordinate getParent() {
        return parent;
    }

    public void setParent(PuzzleCoordinate parent) {
        this.parent = parent;
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
    public int compareTo(PuzzleCoordinate n) {
        return Double.compare(this.getTotalCost(), n.getTotalCost());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else {
            PuzzleCoordinate puzzleCoordinate = (PuzzleCoordinate) o;
            Point firstPosition = this.getPosition();
            Point secondPosition = puzzleCoordinate.getPosition();
            return firstPosition.equals(secondPosition);
        }
    }
}
