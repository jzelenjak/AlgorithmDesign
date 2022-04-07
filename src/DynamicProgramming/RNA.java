package DynamicProgramming;

/**
 * The problem of understanding the two-dimensional structure of an RNA-molecule (RNA Secondary Structure) is defined as follows:
 *  given a sequence of bases B = b1, ..., bn with bi ∈ {A, U, C, G},
 *  find a set S of base pairs (i, j) that is as large as possible and meets some criteria.
 *  If a base pair (i, j) meets these criteria, we write p(i, j). (This function is given in the form of a compatibility matrix.)
 */
public class RNA {
    /**
     * You should implement this method.
     *
     * @param n the number of bases.
     * @param b an array of size n+1, containing the bases b_1 through b_n. Note that you should use b[1] through b[n]
     * @param p an array of size n+1 by n+1, containing the validity for pair (i,j). A pair (i,j) is valid iff p[i][j] == true. Note that you should use p[1][1] through p[n][n].
     * @return The size of the largest set of base pairs.
     */
    public static int solve(int n, char[] b, boolean[][] p) {
        // Create a memoization table
        int[][] mem = new int[n+1][n+1];

        // For each step size (k >= 5 since j > i + 4)
        for (int k = 5; k < n; ++k) {
            for (int i = 1; i <= n-k; ++i) {
                int j = i + k;  // End of the sequence
                mem[i][j] = mem[i][j-1];
                // Get the maximum matching of t and j for some t between i and j
                for (int t = i; t < j-4; ++t) {
                    if (p[t][j]) {
                        mem[i][j] = Math.max(mem[i][j], 1 + mem[i][t-1] + mem[t+1][j-1]);
                    }
                }
            }
        }
        return mem[1][n];
    }
}
