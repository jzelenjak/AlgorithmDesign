package DynamicProgramming;

/**
 * Let G be an undirected graph with n nodes. A subset of the nodes is called an independent set if no two of them are joined by an edge. Finding an independent set of maximum size (or weight) is generally difficult, but it can be done efficiently in special cases.
 *
 * Call a graph G a path if its nodes can be written as v1,v2,…,vn, with an edge between vi and vj if and only if |i−j|=1. Each node vi has a positive integer weight wi.
 *
 * Given the following problem instance, with a path of nodes with weights:
 *  `2 1 6 8 9`
 * We expect 17 as our output.
 */
public class IndependentSet {
    /*
     * Note that entry node[0] should be avoided, as nodes are labelled node[1] through node[n].
     */
    public static int weight(int n, int[] nodes) {
        // Initialize the memoization table
        int[] mem = new int[n+1];
        // In the case of one node, the max size is the weight of this node
        mem[1] = nodes[1];

        // Iterate over all input sizes
        for (int i = 2; i <= n; ++i) {
            // Either take or do not take
            mem[i] = Math.max(nodes[i] + mem[i-2], mem[i-1]);
        }
        return mem[n];
    }
}
