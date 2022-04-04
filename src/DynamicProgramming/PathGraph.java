package DynamicProgramming;

import java.util.LinkedList;
import java.util.List;

/**
 * We have a path graph where every node has a certain weight. We want to select nodes such that there is at most a gap of 2 between selected nodes, while minimizing the total weight. You should always include the last node.
 *
 * Given the following problem instance:
 * ```
 * 7
 * 1 21 21 1 21 21 1
 * ```
 * we expect the following as answer: `3`
 */
public class PathGraph {
    /**
     * Implement this method to be an iterative approach for the recursive formula given in the slides
     *
     * @param n     The number of nodes
     * @param nodes the different weights. You should use nodes[1] to nodes[n]
     * @return the minimal weight
     */
    public static int solve(int n, int[] nodes) {
        int[] mem = new int[n+1];

        // Base cases
        mem[1] = nodes[1];
        if (n == 1) return mem[1];
        mem[2] = nodes[2];
        if (n == 2) return mem[2];

        // Recursive cases
        for (int i = 3; i <= n; ++i) {
            // Take the minimum value of the three previous nodes
            mem[i] = nodes[i] + Math.min(mem[i-1], Math.min(mem[i-2], mem[i-3]));
        }
        return mem[n];
    }

    /**
     * Recover the solution of a given optimal value and an array containing the memoization values of the following problem:
     *```
     * 7
     * 1 18 18 1 18 18 1
     * ```
     *
     * The optimal value and memoization values are:
     * ```
     * 3
     * mem: [0, 1, 18, 18, 2, 20, 20, 3]
     * ```
     * We expect the following solution:
     *
     * `[1, 4, 7]`
     */
    public static List<Integer> recoverSolution(int n, int[] nodes, int result, int[] mem) {
        LinkedList<Integer> path = new LinkedList<>();

        for (int i = n; i > 0; --i) {
            // Check if the value at this step contributes to the optimal value.
            //    If it does, add the node to the result
            if (mem[i] == result) {
                path.addFirst(i);
                result -= nodes[i];
            }
        }
        return path;
    }
}
