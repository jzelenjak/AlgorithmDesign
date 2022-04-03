package Greedy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A maze is represented by a directed (and weighted, but you only need that for the next problem) graph G=(V,E), where V denotes the set containing n vertices and E the set containing m directed edges. Each vertex represents an intersection or end point in the maze and the edges represent paths between them. A directed edge is used for (downhill) tunnels and holes that you can jump into, but where it is impossible to get back. Because of this, it may become impossible to reach the exit.
 *
 * Design and implement an algorithm that determines whether it is possible to get from a given point s to the exit of the maze t. Your algorithm should return true or false and run in O(n+m) time.
 */
public class CanWeGetOut {
    /**
     * @param nodes the nodes in the graph
     * @param s the starting node
     * @param t the final node
     * @return true iff there is a path from the start node to the final node
     */
    public static boolean solve(Set<Node> nodes, Node s, Node t) {
        // If the destination node is reached, return true
        if (s == t) return true;

        // Mark the current node as visited
        s.marked = true;

        // For all outgoing edges
        for (Node node : s.outgoingEdges) {
            // Perform DFS on the node if it has not been visited
            if (!node.marked && solve(nodes, node, t)) return true;
        }
        // Impossible to reach the destination node
        return false;
    }
}

class Node {

    List<Node> outgoingEdges;

    boolean marked;

    public Node() {
        this.outgoingEdges = new ArrayList<>();
        this.marked = false;
    }
}
