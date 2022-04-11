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
 * <p>The {@code PuzzleVertex} Vertex: Vertices are the point that joints edges.
 * It represents the data which will be stored in a graph.
 * <p>
 * Each PuzzleVertex will be having an id to represent a vertex in the graph
 * Which is similar to the id we used in {@linkplain PuzzleCoordinate}
 *
 * @author Loganathan Bhavaneetharan
 */

public class PuzzleVertex {

    /**
     * The id of this {@code PuzzleVertex}.
     * If no id is set, it will default to 0.
     */
    private final int id;

    /**
     * Constructs and initializes a PuzzleVertex with the specified {@code id}
     *
     * @param id the id of the newly constructed {@code PuzzleVertex}
     */
    public PuzzleVertex(int id) {
        this.id = id;
    }

    /**
     * Returns the id of this {@code PuzzleVertex}
     *
     * @return id of {@code PuzzleVertex}.
     */
    public int getId() {
        return id;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * The {@code equals} method returns true if both the objects are equal
     *
     * @param obj the reference object with which to compare.
     * @return whether the provided object is equal to this {@code PuzzleVertex}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        PuzzleVertex vertex = (PuzzleVertex) obj;
        return (vertex.id == (this.id));
    }

    /**
     * The hashCode method defined by class {@code PuzzleVertex}
     * does return distinct integers for distinct objects.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return this.id;
    }

    /**
     * Returns a string representation of this PuzzleVertex and its {@code (id)}.
     * <p>This method is intended to be used only for debugging purposes.</p>
     *
     * @return a string representation of this PuzzleVertex
     */
    @Override
    public String toString() {
        return "Vertex [label=" + id + "]";
    }

}
