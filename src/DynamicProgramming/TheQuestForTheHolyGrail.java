package DynamicProgramming;

import java.util.Objects;
import java.util.Set;

public class TheQuestForTheHolyGrail {
    /**
     * You should implement this method.
     *
     * @param n the number of nodes.
     * @param m the number of edges.
     * @param g the index of the holy grail in V.
     * @param V a list of Nodes, where V[1] is the current state and V[g] is the holy grail. You should not use V[0].
     * @param E a set of Edges
     * @return The length of the shortest path that uses the research team at most once.
     */
    public static double solve(int n, int m, int g, NodeG[] V, Set<EdgeG> E) {
        // OPT(i,1,c) = 0
        // OPT(0,v,c) = Infinity
        // OPT(i,v,c) = min_(w,v) ( c_(w,v) + OPT(i-1,w,c) ) if c=0
        // OPT(i,v,c) = min_(w,v) ( min( c_(w,v) + OPT(i-1,w,c),c_(w,v) + 0.5 * c_(w,v) + OPT(i-1,w,c-1) )

        // Each entry is a node, and for each node, there can either be available collaboration, or not
        double[][] mem = new double[n+1][2];

        // Set all the nodes, except the start node, as unreachable
        for (int i = 2; i <= n; ++i) {
            for (int j = 0; j < 2; j++) {
                mem[i][j] = Integer.MAX_VALUE;
            }
        }

        // Iterate n times
        for (int k = 0; k < n; ++k) {
            // Iterate over all the nodes
            for (int v = 1; v <= n; v++) {
                // Costs: with available collaboration and without it
                double[] minEdges = new double[]{Integer.MAX_VALUE,Integer.MAX_VALUE};
                // For each edge
                for (EdgeG e : E) {
                    // Only consider the edges whose destination is the current node
                    if (e.getTo().getId() != v) continue;
                    int from = e.getFrom().getId();
                    // Either this edge contributes to the path or not
                    minEdges[0] = Math.min(minEdges[0], e.getCost() + mem[from][0]);
                    // Either this edge contributes to the path or not
                    // If it does, then we can either use collaboration or not
                    minEdges[1] = Math.min(minEdges[1], Math.min(e.getCost() + mem[from][1], e.getCost() / 2.0 + mem[from][0]));
                }
                // Update the entries if they are smaller
                mem[v][0] = Math.min(mem[v][0], minEdges[0]);
                mem[v][1] = Math.min(mem[v][1], minEdges[1]);
            }
        }
        // Return the optimal value
        double optValue = mem[g][1];
        return optValue == Integer.MAX_VALUE ? -1 : optValue;
    }
}

class NodeG {

    protected int id;

    public NodeG(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean equals(Object other) {
        if (other instanceof NodeG) {
            NodeG that = (NodeG) other;
            return this.id == that.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

class EdgeG {

    protected int cost;

    protected NodeG from;

    protected NodeG to;

    protected EdgeG(NodeG from, NodeG to, int cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    public NodeG getFrom() {
        return from;
    }

    public NodeG getTo() {
        return to;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EdgeG edge = (EdgeG) o;
        return cost == edge.cost && Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cost, from, to);
    }
}
