package sub_main;

public class Point {

    private int id;
    private int x;
    private int y;
    private char letter;

    public Point(int id, int x, int y, char letter) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.letter = letter;
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

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }
}
