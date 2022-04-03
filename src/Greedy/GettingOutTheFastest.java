package Greedy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * A maze is represented by a weighted directed graph G=(V,E), where V denotes the set containing n vertices and E the set containing m directed edges. Each vertex represents an intersection or end point in the maze and the edges represent paths between them. A directed edge is used for (downhill) tunnels and holes that you can jump into, but where it is impossible to get back. Because of this, it may become impossible to reach the exit.
 *
 * Some edges take longer than others, which is expressed in their weight.
 *
 * Additionally, the sheer number of options you can chose from in every vertex overwhelms you quite a bit, so every vertex takes 1 time step per outgoing edge (because you have to find out what is the correct one).
 *
 * Design and implement an algorithm that determines the path from s to t that takes the least amount of time ( which is the sum of lengths of all edges plus for all vertices (except t) the number of outgoing edges). Let the algorithm just print the total time of this path. Aim for the most efficient algorithm you can think of. Extremely slow implementations will not be accepted.
 *
 * The input is structured the same as for the assignment “Getting out of the maze”, except that edges now have a weight.
 */
public class GettingOutTheFastest {
    /**
     * @param n the number of nodes
     * @param m the number of edges
     * @param s the starting vertex (1 <= s <= n)
     * @param t the ending vertex (1 <= t <= n)
     * @param edges the set of edges of the graph, with endpoints labelled between 1 and n inclusive.
     * @return the time required to get out, or -1 if it is not possible to get out.
     */
    public static int getMeOuttaHere(int n, int m, int s, int t, Set<Edge> edges) {
        // Trivial edge case
        if (s == t) return 0;

        // Create an adjacency list graph for convenience
        List<Vertex> graph = createAdjacencyList(n, edges);
        // The destination vertex
        Vertex dest = graph.get(t);
        // Distance to the source vertex is 0
        graph.get(s).dist = 0;

        // Initialise the priority queue
        PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingInt(v -> v.dist));
        pq.offer(graph.get(s));

        // Iterate over all the vertices
        while (!pq.isEmpty()) {
            // Visit the next vertex
            Vertex curr = pq.poll();
            // Mark the vertex as visited
            curr.marked = true;
            // Return the distance to the vertex if this is the destination node
            if (curr == dest) return curr.dist;

            // Every vertex takes 1 time step per outgoing edge
            int penalty = curr.outgoingEdges.size();

            // For all outgoing edges
            for (Edge e : curr.outgoingEdges) {
                Vertex to = graph.get(e.to);
                // Get the distance and the adjacent vertex
                int newDist = curr.dist + e.weight + penalty;

                // Check for the edge case where the distance is actually Integer.MAX_VALUE
                if (to == dest && newDist == Integer.MAX_VALUE) return Integer.MAX_VALUE;

                // If the vertex has not been visited, update the distance if necessary
                if (!to.marked && newDist < to.dist) {
                    pq.remove(to);
                    to.dist = newDist;
                    pq.offer(to);
                }
            }
        }
        // The destination is unreachable
        return -1;
    }

    /**
     * A helper method to create an adjacency list graph out of edge list graph
     */
    private static List<Vertex> createAdjacencyList(int n, Set<Edge> edges) {
        List<Vertex> list = new ArrayList<>();
        for (int i = 0; i <= n; ++i) list.add(new Vertex());
        for (Edge e : edges) list.get(e.from).outgoingEdges.add(e);
        return list;
    }
}

class Vertex {
    List<Edge> outgoingEdges;
    boolean marked;
    int dist;

    public Vertex() {
        this.outgoingEdges = new ArrayList<>();
        this.marked = false;
        this.dist = Integer.MAX_VALUE;
    }
}
