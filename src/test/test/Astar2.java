package test.test;


import java.util.Arrays;

/**
 * Used to perform the A-Star (A*) Algorithm to find the shortest path from a start to a target node.
 */
public class Astar2 {
    /**
     * Finds the shortest distance between two nodes using the A-star algorithm
     *
     * @param graph     an adjacency-matrix-representation of the graph where (x,y) is the weight of the edge or 0 if there is no edge.
     * @param heuristic an estimation of distance from node x to y that is guaranteed to be lower than the actual distance. E.g. straight-line distance
     * @param start     the node to start from.
     * @param goal      the node we're searching for
     * @return The shortest distance to the goal node. Can be easily modified to return the path.
     */
    public static double aStar(int[][] graph, double[][] heuristic, int start, int goal) {

        //This contains the distances from the start node to all other nodes
        int[] distances = new int[graph.length];
        //Initializing with a distance of "Infinity"
        Arrays.fill(distances, Integer.MAX_VALUE);
        //The distance from the start node to itself is of course 0
        distances[start] = 0;

        //This contains the priorities with which to visit the nodes, calculated using the heuristic.
        double[] priorities = new double[graph.length];
        //Initializing with a priority of "Infinity"
        Arrays.fill(priorities, Integer.MAX_VALUE);
        //start node has a priority equal to straight line distance to goal. It will be the first to be expanded.
        priorities[start] = heuristic[start][goal];

        //This contains whether a node was already visited
        boolean[] visited = new boolean[graph.length];

        //While there are nodes left to visit...
        while (true) {

            // ... find the node with the currently lowest priority...
            double lowestPriority = Integer.MAX_VALUE;
            int lowestPriorityIndex = -1;
            for (int i = 0; i < priorities.length; i++) {
                //... by going through all nodes that haven't been visited yet
                if (priorities[i] < lowestPriority && !visited[i]) {
                    lowestPriority = priorities[i];
                    lowestPriorityIndex = i;
                }
            }

            if (lowestPriorityIndex == -1) {
                // There was no node not yet visited --> Node not found
                return -1;
            } else if (lowestPriorityIndex == goal) {
                // Goal node found
                System.out.println("Goal node found!");
                return distances[lowestPriorityIndex];
            }

            System.out.println("Visiting node " + lowestPriorityIndex + " with currently lowest priority of " + lowestPriority);

            //...then, for all neighboring nodes that haven't been visited yet....
            for (int i = 0; i < graph[lowestPriorityIndex].length; i++) {
                if (graph[lowestPriorityIndex][i] != 0 && !visited[i]) {
                    //...if the path over this edge is shorter...
                    if (distances[lowestPriorityIndex] + graph[lowestPriorityIndex][i] < distances[i]) {
                        //...save this path as new shortest path
                        distances[i] = distances[lowestPriorityIndex] + graph[lowestPriorityIndex][i];
                        //...and set the priority with which we should continue with this node
                        priorities[i] = distances[i] + heuristic[i][goal];
                        System.out.println("Updating distance of node " + i + " to " + distances[i] + " and priority to " + priorities[i]);
                    }
                }
            }

            // Lastly, note that we are finished with this node.
            visited[lowestPriorityIndex] = true;
            //System.out.println("Visited nodes: " + Arrays.toString(visited));
            //System.out.println("Currently lowest distances: " + Arrays.toString(distances));

        }
    }

    public static void main(String[] args) {
        // Smaller graph used for dijkstra
        int[][] small_graph_distances = {
                {0, 3, 4, 0, 0, 0},
                {0, 0, 0, 6, 10, 0},
                {0, 5, 0, 8, 0, 0},
                {0, 0, 0, 0, 7, 3},
                {0, 0, 0, 0, 0, 9},
                {0, 0, 0, 0, 0, 0},
        };
        int[][] small_graph_coordinates = {
                {0, 2},
                {2, 0},
                {2, 4},
                {6, 0},
                {6, 4},
                {8, 2}
        };
        // As a heuristic we use straight line distance
        double[][] heuristic = new double[small_graph_distances.length][small_graph_distances[0].length];
        for (int i = 0; i < small_graph_distances.length; i++) {
            for (int j = 0; j < small_graph_distances[i].length; j++) {
                double x1 = small_graph_coordinates[i][0];
                double y1 = small_graph_coordinates[i][1];
                double x2 = small_graph_coordinates[j][0];
                double y2 = small_graph_coordinates[j][1];
                heuristic[i][j] = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
            }
        }

        System.out.println(Arrays.toString(heuristic));

        // Bigger graph specifically for this algorithm
        // TODO

        // TODO could also use array position as coordinates

        // Should be 12
        // With debug statements, should print:
        // visiting 0 with priority 6
        // Updating distance of node 1 to 3 and priority to 5.3
        // Updating distance of node 2 to 4 and priority to 5.3
        // visiting 1 with priority 5.3
        // Updating distance of node 3 to 9 and priority to 11.3
        // Updating distance of node 4 to 13 and priority to 15.2
        // visiting 2 with priority 5.3
        // (not updating 1)
        // Updating distance of node 4 to 12 and priority to 14.2
        // visiting 3 with priority 11.3
        // (no updating 4)
        // Updating distance of node 5 to 12 and priority to 12
        //



        System.out.println(Astar2.aStar(small_graph_distances, heuristic, 0, 5));
    }
}