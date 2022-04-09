package main;


import static main.GameConstants.Directions;

public class PuzzleDirections {


    private final int[] directionValues;

    public PuzzleDirections(Directions directionType) {
        int[] direction = new int[2];
        switch (directionType) {
            case TOP:
                direction = new int[]{0, 1};
                break;
            case RIGHT:
                direction = new int[]{1, 0};
                break;
            case DOWN:
                direction = new int[]{0, -1};
                break;
            case LEFT:
                direction = new int[]{-1, 0};
                break;
        }
        this.directionValues = direction;
    }

    public int[] getDirectionValues() {
        return directionValues;
    }
}
