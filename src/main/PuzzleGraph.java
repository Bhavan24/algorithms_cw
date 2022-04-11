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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> The {@code PuzzleGraph} is the data structure implemented to
 * perform the breadth-first search algorithm </p>
 * <p> A graph is nothing but a collection of vertices and edges which can be represented
 * as either an adjacency matrix or an adjacency list.
 * In this {@code PuzzleGraph} class the adjacency list is defined. </p>
 * <p> There are several operations possible on a graph data structure,
 * such as creating, updating or searching through the graph. will be handled by various methods </p>
 * <p> This data structure depends on {@linkplain PuzzleVertex} to store the vertex details </p>
 *
 * @author Loganathan Bhavaneetharan
 */

public class PuzzleGraph {

    /**
     * The adjVertices of this {@code PuzzleGraph}.
     * This is the adjacency list which represents a graph as a HashMap
     */
    private final Map<PuzzleVertex, List<PuzzleVertex>> adjVertices;

    /**
     * Constructs and initializes a PuzzleGraph
     * <br/>
     * On initialization the adjVertices will be inisiated with a HashMap to store te
     * vertex details
     */
    public PuzzleGraph() {
        this.adjVertices = new HashMap<>();
    }

    /**
     * Add a vertex to the graph. by taking the vertex id as a parameter this method
     * will add the vertex if it is not already in the graph
     *
     * @param vertexId the id value of the vertex needs to be added.
     */
    public void addVertex(int vertexId) {
        adjVertices.putIfAbsent(new PuzzleVertex(vertexId), new ArrayList<>());
    }

    /**
     * Remove a vertex from the graph. by taking the vertex id as a parameter this method
     * will remove the vertex if it is in the graph
     *
     * @param vertexId the id value of the vertex needs to be removed.
     */
    public void removeVertex(int vertexId) {
        PuzzleVertex v = new PuzzleVertex(vertexId);
        adjVertices.values().forEach(e -> e.remove(v));
        adjVertices.remove(new PuzzleVertex(vertexId));
    }

    /**
     * Creates a new edge and updates the adjVertices Map.
     * by taking the edge vertex ids as the parameters and combining those two
     * vertices will create an edge.
     *
     * @param vertexId1 the id value of the first vertex.
     * @param vertexId2 the id value of the second vertex.
     */
    public void addEdge(int vertexId1, int vertexId2) {
        PuzzleVertex v1 = new PuzzleVertex(vertexId1);
        PuzzleVertex v2 = new PuzzleVertex(vertexId2);
        adjVertices.get(v1).add(v2);
    }

    /**
     * Removes an edge and updates the adjVertices Map.
     * by taking the edge vertex ids as the parameters the edges related to
     * those vertices will be retrieved and removed from the graph.
     *
     * @param vertexId1 the id value of the first vertex.
     * @param vertexId2 the id value of the second vertex.
     */
    public void removeEdge(int vertexId1, int vertexId2) {
        PuzzleVertex v1 = new PuzzleVertex(vertexId1);
        PuzzleVertex v2 = new PuzzleVertex(vertexId2);
        List<PuzzleVertex> eV1 = adjVertices.get(v1);
        List<PuzzleVertex> eV2 = adjVertices.get(v2);
        if (eV1 != null) eV1.remove(v2);
        if (eV2 != null) eV2.remove(v1);
    }

    /**
     * Returns the adjacent vertices of a particular vertex
     *
     * @param vertexId the id value of the vertex.
     * @return List of vertices near the provided vertex.
     */
    public List<PuzzleVertex> getAdjVertices(int vertexId) {
        return adjVertices.get(new PuzzleVertex(vertexId));
    }

    /**
     * Returns a string representation of this PuzzleGraph and its {@code adjVertices}.
     * <p>This method is intended to be used only for debugging purposes.</p>
     *
     * @return a string representation of this PuzzleGraph
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (PuzzleVertex v : adjVertices.keySet()) {
            sb.append(v);
            sb.append(adjVertices.get(v));
        }
        return sb.toString();
    }

}
