package DynamicProgramming;

public class FishSalesman {
    /**
     * @param n the number of days
     * @param P the profits that can be made on day 1 through n on location P are stored in P[1] through P[n].
     * @param Q the profits that can be made on day 1 through n on location Q are stored in Q[1] through Q[n].
     * @return the maximum obtainable profit.
     */
    public static int solve(int n, int[] P, int[] Q) {
        // P[j] or Q[j] is the profit at the end of the day at the stand P and Q respectively
        int[][] mem = new int[2][n+1];

        // No profit on the 0th day
        mem[0][0] = 0;
        mem[1][0] = 0;

        // On the 1st day the profit for each stand is just the first predicted profit for that stand
        mem[0][1] = P[1];
        mem[1][1] = Q[1];

        // At the end of each day, being at a stand means that on the previous day you:
        //  * either stayed at this stand (so you get the cumulative profit for that day when being on this stand, plus the profit for this day);
        //  * or came from the other stand (so you have the cost for that stand that you had on the prev-previous day, plus the profit for this day)
        for (int i = 2; i <= n; ++i) {
            mem[0][i] = P[i] + Math.max(mem[0][i-1], mem[1][i-2]);
            mem[1][i] = Q[i] + Math.max(mem[1][i-1], mem[0][i-2]);
        }
        // Take the largest result
        return Math.max(mem[0][n], mem[1][n]);
    }
}
