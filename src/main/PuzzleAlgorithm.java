package main;

import java.util.*;

public class PuzzleAlgorithm {

    public List<Integer> BFS(PuzzleGraph graph, int startId, int endId) {
        Set<Integer> visited = new LinkedHashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> pastVertexMap = new HashMap<>();
        queue.add(startId);
        visited.add(startId);
        pastVertexMap.put(startId, null);
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (PuzzleVertex v : graph.getAdjVertices(vertex)) {
                if (!visited.contains(v.getId())) {
                    pastVertexMap.put(v.getId(), vertex);
                    visited.add(v.getId());
                    queue.add(v.getId());
                }
            }
        }

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
