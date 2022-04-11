package main;

import java.util.*;

public class PuzzleAlgorithm {

    public List<Integer> shortestPathAlgorithm(PuzzleGraph graph, int startId, int endId) {
        return Dijkstra(graph, startId, endId);
    }

    public List<Integer> BFS(PuzzleGraph graph, int startId, int endId) {
        Set<Integer> visited = new LinkedHashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> pastVertexMap = new HashMap<>();
        queue.add(startId);
        visited.add(startId);
        pastVertexMap.put(startId, null);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            if (vertex == endId) break;
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

    public List<Integer> DFS(PuzzleGraph graph, int startId, int endId) {
        Set<Integer> visited = new LinkedHashSet<>();
        Stack<Integer> stack = new Stack<>();
        Map<Integer, Integer> pastVertexMap = new HashMap<>();
        stack.add(startId);
        visited.add(startId);
        pastVertexMap.put(startId, null);

        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            if (vertex == endId) break;
            for (PuzzleVertex v : graph.getAdjVertices(vertex)) {
                if (!visited.contains(v.getId())) {
                    pastVertexMap.put(v.getId(), vertex);
                    visited.add(v.getId());
                    stack.add(v.getId());
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

    public List<Integer> Dijkstra(PuzzleGraph graph, int startId, int endId) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        Set<Integer> visited = new LinkedHashSet<>();
        Map<Integer, Integer> pastVertexMap = new HashMap<>();
        priorityQueue.add(startId);
        visited.add(startId);
        pastVertexMap.put(startId, null);

        while (!priorityQueue.isEmpty()) {
            int vertex = priorityQueue.remove();
            if (vertex == endId) break;
            for (PuzzleVertex v : graph.getAdjVertices(vertex)) {
                if (!visited.contains(v.getId())) {
                    pastVertexMap.put(v.getId(), vertex);
                    visited.add(v.getId());
                    priorityQueue.add(v.getId());
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

    public List<Integer> AStar(PuzzleGraph graph, int startId, int endId) {
        List<Integer> pathList = new ArrayList<>();
        return pathList;
    }

}
