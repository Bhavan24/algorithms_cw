package main;


public class PuzzleCoordinate implements Comparable<PuzzleCoordinate> {

    int id;
    int x;
    int y;
    char value;
    private double costFromStart;
    private double costToTarget;
    private double totalCost;

    public PuzzleCoordinate(int id, int x, int y, char value) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.value = value;
        this.costFromStart = 0;
        this.costToTarget = 0;
        this.totalCost = 0;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getValue() {
        return value;
    }

    public double getCostFromStart() {
        return costFromStart;
    }

    public void setCostFromStart(double costFromStart) {
        this.costFromStart = costFromStart;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public PuzzleCoordinate getPosition() {
        return new PuzzleCoordinate(id, x, y, value);
    }

    @Override
    public String toString() {
        return "PuzzleCoordinate {" + "id=" + id + ", x=" + x + ", y=" + y + ", character=" + value + '}';
    }

    @Override
    public int compareTo(PuzzleCoordinate n) {
        return Double.compare(this.getTotalCost(), n.getTotalCost());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else {
            PuzzleCoordinate n = (PuzzleCoordinate) o;
            PuzzleCoordinate firstPosition = this.getPosition();
            PuzzleCoordinate secondPosition = n.getPosition();
            return firstPosition.equals(secondPosition);
        }
    }
}
