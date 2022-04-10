package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzleGraph {

    private final Map<PuzzleVertex, List<PuzzleVertex>> adjVertices = new HashMap<>();

    public PuzzleGraph() {
    }

    public void addVertex(int label) {
        adjVertices.putIfAbsent(new PuzzleVertex(label), new ArrayList<>());
    }

    public void addEdge(int label1, int label2) {
        PuzzleVertex v1 = new PuzzleVertex(label1);
        PuzzleVertex v2 = new PuzzleVertex(label2);
        adjVertices.get(v1).add(v2);
        //adjVertices.get(v2).add(v1);
    }

    public List<PuzzleVertex> getAdjVertices(int label) {
        return adjVertices.get(new PuzzleVertex(label));
    }

}
