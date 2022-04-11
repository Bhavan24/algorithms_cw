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
 * Run the program by running SlidingPuzzle.main()
 *
 *****************************************************************************/

package main;

/**
 * <p>The {@code PuzzleCoordinate} class is representation of a location in the map
 * as {@code (x,y)} coordinate space, specified in integer precision. </p>
 * <p>
 * This class also holds the value (characters of the input file, ex: ice = '.', rock = '0'), unique id
 * of a specific puzzle coordinate
 *
 * @author Loganathan Bhavaneetharan
 */

public class PuzzleCoordinate {

    /**
     * The X coordinate of this {@code PuzzleCoordinate}.
     * If no X coordinate is set, it will default to 0.
     */
    private final int x;

    /**
     * The Y coordinate of this {@code PuzzleCoordinate}.
     * If no Y coordinate is set, it will default to 0.
     */
    private final int y;

    /**
     * The id of this {@code PuzzleCoordinate}.
     * If no id is set, it will default to 0.
     */
    private final int id;

    /**
     * The value (characters) of this {@code PuzzleCoordinate}.
     * If no id is set, it will default to '\u0000'.
     */
    private final char value;

    /**
     * Constructs and initializes a PuzzleCoordinate at the specified
     * {@code (x,y)} location in the coordinate space.
     *
     * @param id    the id of the newly constructed {@code PuzzleCoordinate}
     * @param x     the X coordinate of the newly constructed {@code PuzzleCoordinate}
     * @param y     the Y coordinate of the newly constructed {@code PuzzleCoordinate}
     * @param value the character value of the newly constructed {@code PuzzleCoordinate}
     */
    public PuzzleCoordinate(int id, int x, int y, char value) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.value = value;
    }

    /**
     * Returns the id of this {@code PuzzleCoordinate}
     *
     * @return id of {@code PuzzleCoordinate}.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the X coordinate of this {@code PuzzleCoordinate}
     *
     * @return X coordinate of {@code PuzzleCoordinate}.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y coordinate of this {@code PuzzleCoordinate}
     *
     * @return Y coordinate of {@code PuzzleCoordinate}.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the value of this {@code PuzzleCoordinate}
     *
     * @return id value {@code PuzzleCoordinate}.
     */
    public char getValue() {
        return value;
    }

    /**
     * Returns a string representation of this PuzzleCoordinate and its
     * location in the {@code (id,x,y,value)} coordinate space. This method is
     * intended to be used only for debugging purposes.
     *
     * @return a string representation of this PuzzleCoordinate
     */
    @Override
    public String toString() {
        return "PuzzleCoordinate {" + "id=" + id + ", x=" + x + ", y=" + y + ", character=" + value + '}';
    }

}
