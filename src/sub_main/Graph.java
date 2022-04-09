package sub_main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private Map<Vertex, List<Vertex>> adjVertices;

    Graph() {
        this.adjVertices = new HashMap<Vertex, List<Vertex>>();
    }

    void addVertex(int label) {
        adjVertices.putIfAbsent(new Vertex(label), new ArrayList<>());
    }

    void removeVertex(int label) {
        Vertex v = new Vertex(label);
        adjVertices.values().stream().forEach(e -> e.remove(v));
        adjVertices.remove(new Vertex(label));
    }

    void addEdge(int label1, int label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        adjVertices.get(v1).add(v2);
//        adjVertices.get(v2).add(v1);
    }

    void removeEdge(int label1, int label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        List<Vertex> eV1 = adjVertices.get(v1);
        List<Vertex> eV2 = adjVertices.get(v2);
        if (eV1 != null) eV1.remove(v2);
        if (eV2 != null) eV2.remove(v1);
    }

    List<Vertex> getAdjVertices(int label) {
        return adjVertices.get(new Vertex(label));
    }

    String printGraph() {
        StringBuffer sb = new StringBuffer();
        for (Vertex v : adjVertices.keySet()) {
            sb.append(v);
            sb.append(adjVertices.get(v));
        }
        return sb.toString();
    }

}
