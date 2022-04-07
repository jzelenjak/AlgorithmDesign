package NetworkFlow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class TheGreedyProjectManager {
    /**
     * You should implement the method below.
     *
     * @param n            The number of projects
     * @param benefits     An array of dimension n+1 containing the benefits of all the projects for 1 <= i <= n
     * @param costs        An array of dimension n+1 containing the costs of all the projects for 1 <= i <= n
     * @param dependencies is an array of dimension (n+1)*(n+1) that contains value 1 iff project i depends on j and 0 otherwise, for all 1 <= i,j <= n.
     * @return the set of project ids that are selected. Note that the ids of `Node`s in the graph correspond to the ids of the projects they represent.
     */
    public static Set<Integer> outputSelection(int n, int[] benefits, int[] costs, int[][] dependencies) {
        Graph g = buildGraph(n, benefits, costs, dependencies);
        MaxFlow.maximizeFlow(g);

        // The set of selected projects
        Set<Integer> selection = new HashSet<>();

        // Just perform BFS to find all nodes reachable from source.
        // These nodes will form the min-cut, which is what we need to find
        Queue<Node> q = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        q.offer(g.getSource());
        while (!q.isEmpty()) {
            Node currentNode = q.poll();
            visited.add(currentNode);
            selection.add(currentNode.getId());

            for (Edge e : currentNode.getEdges()) {
                // Only take the edges that are not full
                if (e.getResidual() == 0) continue;
                Node to = e.getTo();
                if (!visited.contains(to)) q.offer(to);
            }
        }
        // Do not include the source in the selection
        //   (it is just a dummy node and not a project)
        selection.remove(g.getSource().getId());
        return selection;
    }

    private static Graph buildGraph(int n, int[] benefits, int[] costs, int[][] dependencies) {
        Node source = new Node(0);
        Node sink = new Node(n + 1);
        ArrayList<Node> nodes = new ArrayList<>(n + 2);
        nodes.add(source);
        for (int i = 1; i <= n; i++) {
            Node newNode = new Node(i);
            nodes.add(newNode);
        }
        nodes.add(sink);
        for (int i = 1; i <= n; i++) {
            source.addEdge(nodes.get(i), benefits[i]);
            nodes.get(i).addEdge(sink, costs[i]);
            for (int j = 1; j <= n; j++) {
                if (dependencies[i][j] > 0) {
                    nodes.get(i).addEdge(nodes.get(j), Integer.MAX_VALUE / 2);
                }
            }
        }
        return new Graph(nodes, source, sink);
    }
}
