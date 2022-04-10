package copy;

public class Point {

    private int id;
    private int x;
    private int y;
    private String letter;

    public Point() {
    }

    public Point(int id, int x, int y, String letter) {
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

    public String getLetter() {
        return letter;
    }

    public void setY(String letter) {
        this.letter = letter;
    }

    @Override
    public String toString() {
        return "Point [id=" + id + ", x=" + x + ", y=" + y + ", letter=" + letter + "]";
    }

}
