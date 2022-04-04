package DynamicProgramming;

import java.util.LinkedList;
import java.util.List;

/**
 * You are given a knapsack that you must fill optimally. Given a knapsack of weight W, and n items each with a weight and value, find a subset of items in the knapsack such that the value of the combined items is maximized. There are no precedence requirements or subset requirements between the subsets themselves, meaning you may add or remove any item as long as the final value is maximized. Implement an algorithm that finds this maximized value, utilizing dynamic programming, in at most O(nW) time.
 *
 * You may assume all given instances have a valid solution, and no trick instances will be given to you.
 */
public class Knapsack {
    /**
     * @param n the number of items
     * @param W the maximum weight
     * @param w the weight of the items, indexed w[1] to w[n].
     * @param v the value of the items, indexed v[1] to v[n];
     * @return the maximum obtainable value.
     */
    public static int mathijsFavouriteProblem(int n, int W, int[] w, int[] v) {
        int[][] mem = new int[n+1][W+1];

        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= W; ++j) {
                if (w[i] > j) mem[i][j] = mem[i-1][j];
                else mem[i][j] = Math.max(mem[i-1][j], v[i] + mem[i-1][j-w[i]]);
            }
        }
        return mem[n][W];
    }

    /**
     * Retrieves the knapsack items used in an optimal solution.
     *
     * @param n the number of items.
     * @param W the maximum weight.
     * @param w the weight of the items, indexed w[1] to w[n].
     * @param v the value of the items, indexed v[1] to v[n].
     * @param mem is a (n x W) integer array, where element mem[i][j] is
     *            the maximum value using only elements 1 to i and max weight of j.
     *
     * @return list containing the id of the items used in the optimal solution, ordered increasingly.
     */
    public static List<Integer> mathijsFavouriteProblem(int n, int W, int[] w, int[] v, int[][] mem) {
        // Solutions (items that we have taken)
        LinkedList<Integer> mathijs = new LinkedList<>();

        // Traverse the memoisation table backwards
        while (n > 0 && W > 0) {
            // Check if we have taken the current item or not
            if (w[n] <= W && mem[n][W] == v[n] + mem[n-1][W-w[n]]) {
                mathijs.addFirst(n);
                W -= w[n];
            }
            --n;
        }
        return mathijs;
    }
}
