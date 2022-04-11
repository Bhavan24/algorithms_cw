package main;

import java.util.*;

public class PuzzleAlgorithm {

    PuzzleMap puzzleMap;
    PuzzleGraph graph;
    int startId;
    int endId;
    PuzzleCoordinate start;
    PuzzleCoordinate target;

    public PuzzleAlgorithm(PuzzleMap puzzleMap, PuzzleGraph graph, PuzzleCoordinate start, PuzzleCoordinate target) {
        this.puzzleMap = puzzleMap;
        this.graph = graph;
        this.start = start;
        this.target = target;
        this.startId = start.getId();
        this.endId = target.getId();
    }

    public List<Integer> BFS() {
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

    public List<Integer> DFS() {
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

    public List<Integer> Dijkstra() {
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

    public List<Integer> AStar() {
        PriorityQueue<PuzzleCoordinate> openNodes = new PriorityQueue<>();
        Set<PuzzleCoordinate> closedNodes = new HashSet<>();
        Map<Integer, Integer> pastVertexMap = new HashMap<>();

        openNodes.add(start);
        start.setOpen();
        pastVertexMap.put(start.getId(), null);
        start.setCostFromStart(0);
        start.setTotalCost(start.getCostFromStart() + calculateDistance(start.getPosition(), target.getPosition()));

        while (!openNodes.isEmpty()) {
            PuzzleCoordinate current = openNodes.remove();
            current.setClosed();
            closedNodes.add(current);
            if (current == target) break;
            for (PuzzleVertex v : graph.getAdjVertices(current.getId())) {
                PuzzleCoordinate neighbour = puzzleMap.getPuzzleCoordinate(v.getId());
                if (!neighbour.isClosed()) {
                    double tentativeCost = current.getCostFromStart() + calculateDistance(current.getPosition(), neighbour.getPosition());
                    pastVertexMap.put(neighbour.getId(), current.getId());
                    closedNodes.add(neighbour);
                    openNodes.add(neighbour);
                    if ((!neighbour.isOpen()) || (tentativeCost < neighbour.getCostFromStart())) {
                        neighbour.setCostFromStart(tentativeCost);
                        neighbour.setTotalCost(neighbour.getCostFromStart() + calculateDistance(neighbour.getPosition(), start.getPosition()));
                        if (!neighbour.isOpen()) openNodes.add(neighbour);
                    }
                }
            }
        }

        int newId = -1;
        List<Integer> pathList = new ArrayList<>();
        int endId = target.getId();
        pathList.add(endId);
        while (newId != start.getId()) {
            newId = pastVertexMap.get(endId);
            pathList.add(newId);
            endId = newId;
        }
        Collections.reverse(pathList);
        return pathList;
    }

    private double calculateDistance(PuzzleCoordinate from, PuzzleCoordinate to) {
        return Math.pow(Math.pow(from.getX() - to.getX(), 2) + Math.pow(from.getY() - to.getY(), 2), 0.5);
    }

}
