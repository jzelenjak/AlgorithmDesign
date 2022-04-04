package DynamicProgramming;

/**
 * You are playing a game and you are faced with a puzzle. In this puzzle, you have to ﬁnd your way past a group of towers. Each tower has a different height, and there is no way to get past it other than by using a ladder to climb to the top. (From the top of the tower, you can then jump down or climb up to the next tower, or the ground if you are at the end.)
 *
 * The towers are arranged in a rectangular n x m grid and you can only move from a tower to the one north, west, south or east of you. If you want to go up any height different N, you will need a ladder of length N as well. If you want to go down, you can simply jump without the use of a ladder. A ladder’s cost increases linearly with its length and you need to get from the northwest tower to the southeast one. Of course, you want the cheapest ladder possible, which means finding the minimum length of ladder needed. You may assume you are dropped on top of the northwest tower at the start of the puzzle and want to get to the southeast tower at the end of the puzzle.
 *
 * Given the following problem instance, of size 2 x 3:
 * ``
 * 3 5 6
 * 4 2 1
 * ``
 * we expect 1 as our output.
 *
 * Give an iterative dynamic solution to find the cheapest ladder you can use to reach the south-eastern-most tower.
 *
 * NB: Please keep in mind that you can take your ladder with you. Thus if you go: 3 -> 4 -> 5 you still only require a ladder of length 1!
 */
public class TowersAndLadders {
    public static int solve(int n, int m, int[][] graph) {
        // An iterative dynamic programming solution to the ladder problem.
        int[][] mem = new int[n][m];

        // Initialize the memoization table with infinities
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                mem[i][j] = Integer.MAX_VALUE;
            }
        }
        print(graph);
        // Base case: at the start, no ladder is needed
        mem[0][0] = 0;

        // We will iterate over the entire n x m grid n*m times (so that the result could propagate)
        // On each tower (i,j) for each side:
        //  * check the required length of the ladder
        //      (either the optimal one used so far for that side or the new one (difference in heights).
        //      So, basically, check if you can still use the optimal ladder used so far for that side)
        //  * check if this required ladder is of the optimal length for this tower
        //      (check if there have been found more optimal ways to reach this tower)
        int nm = n * m;
        for (int k = 0; k < nm; ++k) {
            // Iterate over the grid
            //System.out.println("Iteration " + k);
            //print(mem);
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < m; ++j) {
                    // Ignore the base case
                    if (i == 0 && j == 0) continue;

                    // The height of the current tower
                    int h = graph[i][j];

                    //From up
                    if (i - 1 >= 0) {
                        int ladderLength = Math.max(mem[i-1][j], h - graph[i-1][j]);
                        if (ladderLength < mem[i][j]) mem[i][j] = ladderLength;
                    }
                    // From left
                    if (j - 1 >= 0) {
                        int ladderLength = Math.max(mem[i][j-1], h - graph[i][j-1]);
                        if (ladderLength < mem[i][j]) mem[i][j] = ladderLength;
                    }
                    // From down
                    if (i + 1 < n) {
                        int ladderLength = Math.max(mem[i+1][j], h - graph[i+1][j]);
                        if (ladderLength < mem[i][j]) mem[i][j] = ladderLength;
                    }
                    // From right
                    if (j + 1 < m) {
                        int ladderLength = Math.max(mem[i][j+1], h - graph[i][j+1]);
                        if (ladderLength < mem[i][j]) mem[i][j] = ladderLength;
                    }
                    //print(mem);
                }
            }
        }
        return mem[n-1][m-1];
    }

    private static void print(int[][] table) {
        for (int i = 0; i < table.length; ++i) {
            for (int j = 0; j < table[i].length; ++j) {
                if (table[i][j] == Integer.MAX_VALUE) System.out.print("∞ ");
                else System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
