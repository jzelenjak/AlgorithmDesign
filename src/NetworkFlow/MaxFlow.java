package NetworkFlow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MaxFlow {
    private static List<Edge> findPath(Graph g, Node start, Node end) {
        Map<Node, Edge> mapPath = new HashMap<>();
        Queue<Node> sQueue = new LinkedList<>();
        Node currentNode = start;
        sQueue.add(currentNode);
        while (!sQueue.isEmpty() && currentNode != end) {
            currentNode = sQueue.remove();
            for (Edge e : currentNode.getEdges()) {
                Node to = e.getTo();
                if (to != start && mapPath.get(to) == null && e.getResidual() > 0) {
                    sQueue.add(e.getTo());
                    mapPath.put(to, e);
                }
            }
        }
        if (sQueue.isEmpty() && currentNode != end)
            return null;
        LinkedList<Edge> path = new LinkedList<>();
        Node current = end;
        while (mapPath.get(current) != null) {
            Edge e = mapPath.get(current);
            path.addFirst(e);
            current = e.getFrom();
        }
        return path;
    }

    public static int maximizeFlow(Graph g) {
        int f = 0;
        Node sink = g.getSink();
        Node source = g.getSource();
        List<Edge> path;
        while ((path = findPath(g, source, sink)) != null) {
            int r = Integer.MAX_VALUE;
            for (Edge e : path) {
                r = Math.min(r, e.getResidual());
            }
            for (Edge e : path) {
                e.augmentFlow(r);
            }
            f += r;
        }
        return f;
    }
}
