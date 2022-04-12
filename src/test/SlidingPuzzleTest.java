/******************************************************************************
 * Name: Loganathan Bhavaneetharan
 * UOW ID: w1810599
 * IIT ID: 20201212
 * B.Eng.Software Engineering, 2nd Year
 *
 *  Description:
 *        The programs takes the filepath, moving direction, state of the ice
 *		  as inputs and will provide the shortest path from point 'S' to
 *		  point 'F' using breadth-first search algorithm
 *
 * Run the test by running SlidingPuzzleTest.main()
 *
 *****************************************************************************/

package test;

import main.PuzzleConstants.IceState;
import main.SlidingPuzzle;

/**
 * <p>The {@code SlidingPuzzleTest} class contains the tests for the
 * sliding puzzle</p>
 *
 * @author Loganathan Bhavaneetharan
 */
public class SlidingPuzzleTest {

    private static final int directionType = 1;
    private static final int iceStateType = 1;
    private static final boolean runAllFiles = false;
    private static final int fileIndex = 29;

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
