package main;


public class PuzzleCoordinate {

    private final int id;
    private final int x;
    private final int y;
    private final char value;

    public PuzzleCoordinate(int id, int x, int y, char value) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.value = value;
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

    @Override
    public String toString() {
        return "PuzzleCoordinate {" + "id=" + id + ", x=" + x + ", y=" + y + ", character=" + value + '}';
    }

}
