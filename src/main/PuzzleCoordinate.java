package main;


public class PuzzleCoordinate {

    int id;
    int x;
    int y;
    char character;
    int value;
    PuzzleCoordinate parent;

    public PuzzleCoordinate(int id, int x, int y, char character, int value, PuzzleCoordinate parent) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.character = character;
        this.value = value;
        this.parent = parent;
    }

    public PuzzleCoordinate(int id, int x, int y, char character, int value) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.character = character;
        this.value = value;
    }

    public PuzzleCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public PuzzleCoordinate(int x, int y, PuzzleCoordinate parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
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

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
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
        return "PuzzleCoordinate {" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", character=" + character +
                ", value=" + value +
                '}';
    }
}
