package main;


public class PuzzleCoordinate {

    int id;
    int x;
    int y;
    char value;
    PuzzleCoordinate parent;

    public PuzzleCoordinate(int x, int y, PuzzleCoordinate parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public PuzzleCoordinate(int id, int x, int y, char value) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public PuzzleCoordinate getParent() {
        return parent;
    }

    public void setParent(PuzzleCoordinate parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "PuzzleCoordinate {" + "id=" + id + ", x=" + x + ", y=" + y + ", character=" + value + '}';
    }
}
