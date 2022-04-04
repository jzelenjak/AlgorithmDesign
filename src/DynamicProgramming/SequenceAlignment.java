package DynamicProgramming;

/**
 * The sequence alignment problem aims to find the optimal alignment of two strings such that the mismatch penalty is minimized. For each mismatched character, you are allowed to add a penalty of ‘1’.
 *
 * You are given two strings, of lengths n and m respectively, on two separate rows. The lengths of the strings can be different. The given strings are also entirely lower case, you do not have to re-format the given input to account for that. Your algorithm must calculated the minimum sequence alignment cost, and return it as an integer. An example input is given below.
 *```
 * kitten
 * sitting
 * ```
 * This example should return ‘3’.
 */
public class SequenceAlignment {
    public static int solve(String firstString, String secondString) {
        int n = firstString.length();     // X string
        int m = secondString.length();    // Y string

        int[][] mem = new int[n+1][m+1];  // memoisation table

        // Base cases (only padding): mem[0][j] = 1 * j and mem[i][0] = 1 * i
        for (int i = 1; i <= n; ++i) mem[i][0] = i;
        for (int j = 1; j <= m; ++j) mem[0][j] = j;

        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= m; ++j) {
                // The cost of matching
                int cost = firstString.charAt(i-1) == secondString.charAt(j-1) ? 0 : 1;
                // Take the min cost of matching and not matching
                mem[i][j] = Math.min(cost + mem[i-1][j-1], 1 + Math.min(mem[i-1][j], mem[i][j-1]));
            }
        }
        return mem[n][m];
    }
}
