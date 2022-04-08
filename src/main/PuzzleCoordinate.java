package main;


public class PuzzleCoordinate {

    int x;
    int y;
    PuzzleCoordinate parent;

    public PuzzleCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public PuzzleCoordinate(int x, int y, PuzzleCoordinate parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PuzzleCoordinate getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "PuzzleCoordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
