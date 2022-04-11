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
 * <p>The {@code PuzzleGraph} is the data structure for the bfs algorithm
 *
 * @author Loganathan Bhavaneetharan
 */

public class PuzzleGraph {

    private final Map<PuzzleVertex, List<PuzzleVertex>> adjVertices;

    public PuzzleGraph() {
        this.adjVertices = new HashMap<>();
    }

    public void addVertex(int vertexId) {
        adjVertices.putIfAbsent(new PuzzleVertex(vertexId), new ArrayList<>());
    }

    public void removeVertex(int vertexId) {
        PuzzleVertex v = new PuzzleVertex(vertexId);
        adjVertices.values().forEach(e -> e.remove(v));
        adjVertices.remove(new PuzzleVertex(vertexId));
    }

    public void addEdge(int vertexId1, int vertexId2) {
        PuzzleVertex v1 = new PuzzleVertex(vertexId1);
        PuzzleVertex v2 = new PuzzleVertex(vertexId2);
        adjVertices.get(v1).add(v2);
    }

    public void removeEdge(int vertexId1, int vertexId2) {
        PuzzleVertex v1 = new PuzzleVertex(vertexId1);
        PuzzleVertex v2 = new PuzzleVertex(vertexId2);
        List<PuzzleVertex> eV1 = adjVertices.get(v1);
        List<PuzzleVertex> eV2 = adjVertices.get(v2);
        if (eV1 != null) eV1.remove(v2);
        if (eV2 != null) eV2.remove(v1);
    }

    public List<PuzzleVertex> getAdjVertices(int vertexId) {
        return adjVertices.get(new PuzzleVertex(vertexId));
    }

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
