package test;

import main.PuzzleConstants.IceState;
import main.SlidingPuzzle;

public class SlidingPuzzleTest {

    private static final int directionType = 1;
    private static final int iceStateType = 1;
    private static final boolean runAllFiles = false;
    private static final int fileIndex = 33;

    public static void main(String[] args) {
        SlidingPuzzle slidingPuzzle = new SlidingPuzzle();
        String[] files = slidingPuzzle.loadPuzzleFiles();

        if (runAllFiles) {
            for (String file : files) {
                runSlidingPuzzle(slidingPuzzle, directionType, iceStateType, file);
            }
        } else {
            String file = files[fileIndex - 1];
            runSlidingPuzzle(slidingPuzzle, directionType, iceStateType, file);
        }
    }

    private static void runSlidingPuzzle(SlidingPuzzle slidingPuzzle, int directionType, int iceStateType, String file) {
        try {
            int[][] directions = slidingPuzzle.selectDirectionType(directionType);
            IceState iceState = slidingPuzzle.selectIceStateType(iceStateType);
            slidingPuzzle.solvePuzzle(file, directions, iceState);
            System.out.println(file + " completed running");
        } catch (Exception e) {
            System.out.println("An error occurred in file " + file + " " + e.getMessage());
        }
    }

}
