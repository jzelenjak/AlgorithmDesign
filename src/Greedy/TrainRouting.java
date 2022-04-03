package Greedy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * The train company that you work for is looking to have trains run in cycles instead of between two endpoints. To speed up the decision process, they asked you to create an algorithm to determine quickly if it is even possible for a piece of map to create a cyclic route.
 *
 * The map is represented by a directed graph G=(V,E), where V denotes the set containing n vertices and E the set containing m directed edges. Each vertex represents a station on the map and the edges represent railways between them. The company also provides a central station, s, from which it should be possible to reach the cycle, otherwise they will not consider it.
 *
 * Design and implement an algorithm that determines whether there exist a cycle reachable from s in the map. Your algorithm should print yes or no and run in O(n+m) time.
 */
public class TrainRouting {
    /**
     * @param n the number of nodes
     * @param m the number of edges
     * @param edges the set of edges, with endpoints labelled between 1 and n inclusive.
     * @param s the starting point
     * @return true iff there is a cycle reachable from s
     */
    public static boolean isThereACycle(int n, int m, Set<Edge> edges, int s) {
        // Edge case - graph with < 2 nodes cannot have cycles
        if (n < 2) return false;

        // Create adjacency list for DFS ( O(n + m) )
        List<List<Edge>> graph =  createAdjacencyList(n, edges);

        // The stack for **ITERATIVE** DFS
        LinkedList<Integer> stack = new LinkedList<>();
        // Push the src node
        stack.addFirst(s);
        // The set of visited nodes
        Set<Integer> visited = new HashSet<>();

        // Perform the **ITERATIVE** DFS
        while (!stack.isEmpty()) {
            // Pop the current node
            s = stack.removeFirst();
            // Visit the current node
            visited.add(s);

            // For each outgoing edge
            for (Edge e : graph.get(s)) {
                // If the (dst) node has not been visited, push the (dst) node for further traversal
                if (!visited.contains(e.to)) stack.addFirst(e.to);
                else return true; // Else the back-edge has been found => cycle
            }
        }
        // No cycles has been found
        return false;
    }

    /**
     * A helper method to create an adjacency list graph out of edge list graph
     */
    private static List<List<Edge>> createAdjacencyList(int n, Set<Edge> edges) {
        // Create an empty adjacency list ( O(n) )
        List<List<Edge>> list = new ArrayList<>();
        // Initialise the elements (lists) ( O(n) )
        for (int i = 0; i <= n; ++i) list.add(new ArrayList<>());
        // Fill in the adjacency list ( O(m) )
        for (Edge e : edges) list.get(e.from).add(e);
        // Return the resulting adjacency list
        return list;
    }
}

