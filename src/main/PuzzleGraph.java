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

public class PuzzleGraph {

    private final Map<PuzzleVertex, List<PuzzleVertex>> adjVertices = new HashMap<>();

    public PuzzleGraph() {
    }

    public void addVertex(int id) {
        adjVertices.putIfAbsent(new PuzzleVertex(id), new ArrayList<>());
    }

    public void addEdge(int id1, int id2) {
        adjVertices.get(new PuzzleVertex(id1)).add(new PuzzleVertex(id2));
    }

    public List<PuzzleVertex> getAdjVertices(int label) {
        return adjVertices.get(new PuzzleVertex(label));
    }

}
