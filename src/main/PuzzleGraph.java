package main;

import java.util.*;

public class PuzzleGraph {

    private Map<PuzzleVertex, List<PuzzleVertex>> adjVertices = new HashMap<>();

    public PuzzleGraph() {
    }

    public PuzzleGraph(Map<PuzzleVertex, List<PuzzleVertex>> adjVertices) {
        this.adjVertices = adjVertices;
    }

    public Map<PuzzleVertex, List<PuzzleVertex>> getAdjVertices() {
        return adjVertices;
    }

    public void setAdjVertices(Map<PuzzleVertex, List<PuzzleVertex>> adjVertices) {
        this.adjVertices = adjVertices;
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

    public Map<Integer, Integer> breadthFirstTraversal(PuzzleGraph graph, int startId, int endId) {
        Set<Integer> visited = new LinkedHashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> pastVertexMap = new HashMap<>();
        queue.add(startId);
        visited.add(startId);
        pastVertexMap.put(startId, null);
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (PuzzleVertex v : graph.getAdjVertices(vertex)) {
                if (!visited.contains(v.getLabel())) {
                    pastVertexMap.put(v.getLabel(), vertex);
                    visited.add(v.getLabel());
                    queue.add(v.getLabel());
                }
            }
        }
        return pastVertexMap;
    }

    public List<Integer> findPathList(Map<Integer, Integer> pastVertexMap, int startId, int endId) {
        int newId = -1;
        List<Integer> pathList = new ArrayList<>();
        pathList.add(endId);
        while (newId != startId) {
            newId = pastVertexMap.get(endId);
            pathList.add(newId);
            endId = newId;
        }
        Collections.reverse(pathList);
        return pathList;
    }

}
