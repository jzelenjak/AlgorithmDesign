package NetworkFlow;

import java.util.List;

public class Graph {

    private final List<Node> nodes;

    private Node source;

    private Node sink;

    public Graph(List<Node> nodes) {
        this.nodes = nodes;
        this.source = null;
        this.sink = null;
    }

    public Graph(List<Node> nodes, Node source, Node sink) {
        this.nodes = nodes;
        this.source = source;
        this.sink = sink;
    }

    public Node getSink() {
        return sink;
    }

    public Node getSource() {
        return source;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public boolean equals(Object other) {
        if (other instanceof Graph) {
            Graph that = (Graph) other;
            return this.nodes.equals(that.nodes);
        }
        return false;
    }

    public boolean hasCirculation() {
        this.removeLowerBounds();
        int D = this.removeSupplyDemand();
        int x = MaxFlow.maximizeFlow(this);
        return x == D;
    }

    private void removeLowerBounds() {
        for (Node n : this.getNodes()) {
            for (Edge e : n.edges) {
                if (e.lower > 0) {
                    e.capacity -= e.lower;
                    e.backwards.capacity -= e.lower;
                    e.backwards.flow -= e.lower;
                    e.from.d += e.lower;
                    e.to.d -= e.lower;
                    e.lower = 0;
                }
            }
        }
    }

    private int removeSupplyDemand() {
        int Dplus = 0, Dmin = 0;
        int maxId = 0;
        for (Node n : this.getNodes()) {
            maxId = Math.max(n.id, maxId);
        }
        Node newSource = new Node(maxId + 1, 0);
        Node newSink = new Node(maxId + 2, 0);
        for (Node n : this.getNodes()) {
            if (n.d < 0) {
                newSource.addEdge(n, 0, -n.d);
                Dmin -= n.d;
            } else if (n.d > 0) {
                n.addEdge(newSink, 0, n.d);
                Dplus += n.d;
            }
            n.d = 0;
        }
        if (Dmin != Dplus) {
            throw new IllegalArgumentException("Demand and supply are not equal!");
        }
        this.nodes.add(newSource);
        this.nodes.add(newSink);
        this.source = newSource;
        this.sink = newSink;
        return Dplus;
    }
}
