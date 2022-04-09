package sub_main;


import java.util.*;

import static main.GameConstants.START;

public class GraphTraversal {

    static Set<Integer> depthFirstTraversal(Graph graph, int root) {
        Set<Integer> visited = new LinkedHashSet<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (Vertex v : graph.getAdjVertices(vertex)) {
                    stack.push(v.getVertexLabel());
                }
            }
        }
        return visited;
    }

    static Map<Integer, Integer> breadthFirstTraversal(Graph graph, int root) {
        Set<Integer> visited = new LinkedHashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> pastVertexMap = new HashMap<>();
        queue.add(root);
        visited.add(root);
        pastVertexMap.put(root, null);
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (Vertex v : graph.getAdjVertices(vertex)) {
                if (!visited.contains(v.getVertexLabel())) {
                    pastVertexMap.put(v.getVertexLabel(), vertex);
                    visited.add(v.getVertexLabel());
                    queue.add(v.getVertexLabel());
                }
            }
        }
        return pastVertexMap;
    }

    static List<Integer> findPathList(Map<Integer, Integer> pastVertexMap, int startId, int endId) {
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
