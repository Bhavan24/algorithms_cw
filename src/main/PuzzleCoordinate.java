package main;


public class PuzzleCoordinate {

    int id;
    int x;
    int y;
    char character;
    int value;
    PuzzleCoordinate parent;

    public PuzzleCoordinate(int x, int y, PuzzleCoordinate parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public PuzzleCoordinate(int id, int x, int y, char character, int value) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.character = character;
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

    public char getCharacter() {
        return character;
    }

    public int getValue() {
        return value;
    }

    public PuzzleCoordinate getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "PuzzleCoordinate {" + "id=" + id + ", x=" + x + ", y=" + y + ", character=" + character + ", value=" + value + '}';
    }
}
