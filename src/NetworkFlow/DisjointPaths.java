package NetworkFlow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implement a solution to the disjoint path problem. Your input is a set of connections between a set of nodes (labelled 1 to n), the source and sink. Your output should be an integer representing the maximum number of edge disjoint paths.
 */
public class DisjointPaths {
    /**
     * You should implement this method
     *
     * @param n the number of nodes
     * @param sourceId the id (index) of the source node (1 <= sourceId <= n)
     * @param sinkId the id (index) of the sink node (1 <= sinkId <= n)
     * @param connections set of connections between one object from X and one object from Y.
     *     Objects in X and Y are labelled 1 <= label <= n
     * @return the maximum number of disjoint paths
     */
    public static int disjointPaths(int n, int sourceId, int sinkId, Set<Connection> connections) {
        // Create a flow graph from model
        List<Node> nodes = new ArrayList<>();
        // Add all nodes (node with id 0 is a dummy node)
        for (int i = 0; i <= n; ++i) nodes.add(new Node(i));
        // Model the connections in the network flow graph.
        for (Connection conn : connections) nodes.get(conn.x).addEdge(nodes.get(conn.y), 1);
        // Return answer
        Graph g = new Graph(nodes, nodes.get(sourceId), nodes.get(sinkId));
        return MaxFlow.maximizeFlow(g);
    }
}
