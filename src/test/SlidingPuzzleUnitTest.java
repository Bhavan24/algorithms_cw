package test;

import main.PuzzleConstants.IceState;
import main.SlidingPuzzle;

public class SlidingPuzzleUnitTest {

    public static void main(String[] args) {

        SlidingPuzzle slidingPuzzle = new SlidingPuzzle();
        String[] files = slidingPuzzle.loadPuzzleFiles();

        int[] arr = new int[]{1, 2};

        for (int directionType : arr) {
            for (int iceStateType : arr) {
                System.out.println("\nDirectionType: " + directionType + " | iceStateType: " + iceStateType + "\n");
                for (String file : files) {
                    try {
                        int[][] directions = slidingPuzzle.selectDirectionType(directionType);
                        IceState iceState = slidingPuzzle.selectIceStateType(iceStateType);
                        slidingPuzzle.solvePuzzle(file, directions, iceState);
                        System.out.println("--------------------" + file + "--------------------");
                    } catch (Exception e) {
                        System.out.println("********************" + file + "********************");
                    }
                }
            }
        }
    }
}
