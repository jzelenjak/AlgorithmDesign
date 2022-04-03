package Greedy;

import java.util.Arrays;

/**
 * We have m processors and n jobs. Each job i in {1,…,n} has a processing time of exactly 1 hour. Furthermore, each job i has an integer deadline di in hours. The aim is to find a start time si and processor pi for each job such that no jobs are run at the same processor at the same time and such that the maximum lateness over all jobs is minimized. The lateness is the time a job finishes compared to its deadline, defined here by si + 1 - di. The objective thus is to minimize maxi{si+1−di}.
 *
 * Create a greedy algorithm to determine a schedule that has the smallest maximum lateness.
 */
public class ParallelProcessing {
    /**
     * @param n the number of jobs
     * @param m the number of processors
     * @param deadlines the deadlines of the jobs 1 through n. NB: you should ignore deadlines[0]
     * @return the minimised maximum lateness.
     */
    public static int solve(int n, int m, int[] deadlines) {
        // Sort the jobs on the deadline in increasing order
        Arrays.sort(deadlines);

        // The maximum lateness
        int maxLateness = 0;
        // The current finish time (for all processors)
        int f = 0;
        // The index of the current job
        int i = 1;

        // Iterate through all the jobs
        while (i < deadlines.length) {
            // Increase the finish time
            ++f;
            // Assign a job to each processor
            for (int j = 1; j <= m; ++j) {
                // Compute the lateness
                int lateness = f - deadlines[i];
                // Update the maximum latency if necessary
                if (lateness > maxLateness) maxLateness = lateness;
                // Prepare to take the next job
                ++i;
                // If all jobs have been assigned, return the maximum lateness
                if (i == deadlines.length) return maxLateness;
            }
        }
        // Return the maximum lateness
        return maxLateness;
    }
}
