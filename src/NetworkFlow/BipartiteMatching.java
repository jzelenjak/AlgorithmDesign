package NetworkFlow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implement a solution to the maximum bipartite matching problem. Your input is a set of connections between a set X (with objects labelled 1 to n) and a set Y (with objects labelled 1 to n). Your output should be an integer, the size of the maximum matching between X and Y.
 */
public class BipartiteMatching {
    /**
     * You should implement this method
     *
     * @param n           the number of nodes in X and Y (i.e. n = |X| = |Y|)
     * @param connections set of connections between one object from X and one object from Y. Objects in X and Y are labelled 1 <= label <= n
     * @return the size of the maximum matching
     */
    public static int maximumMatching(int n, Set<Connection> connections) {
        // Create the source and sink nodes
        Node source = new Node(0);
        Node sink = new Node(n + 1);

        // Construct the nodes for the graph
        List<Node> nodes = new ArrayList<>(2 * n + 2);
        // Add source node
        nodes.add(source);
        // Create the nodes in X
        for (int i = 1; i <= n; ++i) {
            Node node = new Node(i);
            source.addEdge(node, 1);        // add the edge from the source
            nodes.add(node);
        }
        // Create the nodes in Y
        for (int i = 1; i <= n; ++i) {
            Node node = new Node(i + n);
            node.addEdge(sink, 1);          // add the edge to the sink
            nodes.add(node);
        }
        // Add the sink
        nodes.add(sink);

        // Use the "suitable pairings" to construct the edges between the nodes in X and Y
        for (Connection connection : connections) {
            nodes.get(connection.x).addEdge(nodes.get(connection.y + n), 1);
        }

        // Run Ford-Fulkerson and get the answer
        return MaxFlow.maximizeFlow(new Graph(nodes, source, sink));
    }

    public static int maximumMatching2(int n, Set<Connection> connections) {
        Node source = new Node(-42);
        Node sink = new Node(-69);
        List<Node> nodes = new ArrayList<>();
        nodes.add(source);

        Node[] X = new Node[n+1];
        Node[] Y = new Node[n+1];

        for (int i = 1; i <= n; ++i) {
            Node x = new Node(i);
            Node y = new Node(i);

            X[i] = x;
            Y[i] = y;

            source.addEdge(x, 1);
            y.addEdge(sink, 1);

            nodes.add(x);
            nodes.add(y);
        }
        nodes.add(sink);

        for (Connection conn : connections) {
            X[conn.x].addEdge(Y[conn.y], 1);
        }

        Graph g = new Graph(nodes, source, sink);
        return MaxFlow.maximizeFlow(g);
    }
}

class Connection {

    int x;

    int y;

    public Connection(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
