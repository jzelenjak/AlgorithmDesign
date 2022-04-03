package Greedy;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PhorestsEverywhere {
    /**
     * Optimizes the provided Phorest to be as close to an MST as possible.
     *
     * @param nodes the number of nodes in the network
     * @param m the number of edges in the network
     * @param graph all edges in the full graph from index 1 to m
     * @param p the number of edges in the Phorest
     * @param phorest the edges in the Phorest from index 1 to p
     * @return total edge weight of optimized Phorest
     */
    public static int solve(int nodes, int m, Edge[] graph, int p, Edge[] phorest) {
        // Initialize the variables
        UnionFind uf = new UnionFind(nodes);
        int cost = 0;

        // Add all edges of the phorest
        for (int i = 1; i <= p; i++) {
            Edge e = phorest[i];
            uf.union(e.from, e.to);
            cost += e.weight;   // length in this case
        }

        // Create and initialize a priority queue
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        for (int i = 1; i <= m; i++) pq.offer(graph[i]);

        // Add the minimal edges to the tree from the graph
        while (uf.components > 1) {
            // Take the minimal at this point
            Edge e = pq.poll();
            // Discard if adding the edge would create a cycle
            if (e == null) throw new NullPointerException();
            if (!uf.union(e.from, e.to)) continue;
            // Update the costs and increase the number of added edges
            cost += e.weight;
        }
        // Return the result
        return cost;
    }
}
