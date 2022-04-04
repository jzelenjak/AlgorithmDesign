package DynamicProgramming;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * A weighted interval schedule is a schedule of jobs where each of the jobs has a weight attached. Every job j_i for 0 < i ≤ n has a start time s_i, an end time f_i, and has a weight of v_i. The goal is to find a schedule where all jobs are compatible, there are no two jobs that overlap, while maximizing the weight of the schedule.
 */
public class WeightedIntervalScheduling {

    /**
     * Come up with an iterative dynamic programming solution to the weighted interval scheduling problem.
     * NB: You may assume the jobs are sorted by ascending finishing time.
     *
     * @param n the number of jobs
     * @param s the start times of the jobs for jobs 1 through n. Note that s[0] should be ignored.
     * @param f the finish times of the jobs for jobs 1 through n. Note that f[0] should be ignored.
     * @param v the values of the jobs for jobs 1 through n. Note that v[0] should be ignored.
     * @param p the predecessors of the jobs for jobs 1 through n. Note that p[0] should be ignored and that -1 represents there being no predecessor.
     * @return the weight of the maximum weight schedule.
     */
    public static int solve(int n, int[] s, int[] f, int[] v, int[] p) {
        int[] mem = new int[n+1];

        for (int i = 1; i <= n; ++i) {
            // Either take i or do not take i
            mem[i] = Math.max(mem[i-1], v[i] + mem[Math.max(0,p[i])]);
        }
        return mem[n];
    }

    /**
     * Using the memoized values from an iterative dynamic programming solution,
     * retrieve the schedule with the highest total weight.
     * NB: You may assume the jobs are sorted by ascending finishing time.
     * Note: As a tiebreaker, when there are multiple job schedules possible, choose the one that finishes the earliest. Use the same rule for intermediate tiebreakers.
     *
     * @param n the number of jobs
     * @param s the start times of the jobs for jobs 1 through n. Note that s[0] should be ignored.
     * @param f the finish times of the jobs for jobs 1 through n. Note that f[0] should be ignored.
     * @param v the values of the jobs for jobs 1 through n. Note that v[0] should be ignored.
     * @param p the predecessors of the jobs for jobs 1 through n. Note that p[0] should be ignored and that -1 represents there being no predecessor.
     * @param mem where the ith element is the maximum weight of a schedule using the first i jobs.
     * @return list containing the id of the jobs used in the optimal schedule, ordered by ascending finishing time.
     */
    public static List<Integer> recover(int n, int[] s, int[] f, int[] v, int[] p, int[] mem) {
        // The resulting schedule
        LinkedList<Integer> schedule = new LinkedList<>();

        int j = n;
        while (j > 0) {
            // NB! Need to check that this solution is strictly larger.
            if (mem[j] == v[j] + mem[Math.max(0, p[j])] && mem[j] > mem[j-1]) {
                schedule.addFirst(j);
                j = p[j];
                continue;
            }
            --j;
        }
        return schedule;
    }

    /**
     * Find the predecessors of jobs.
     */
    public static int[] findPredecessors(int n, int[] startTimes, int[] finishTimes, int[] values) {
        // Create an array of jobs, and initialize two arrays of indices
        Job[] jobs = new Job[n+1];
        Integer[] s = new Integer[n+1];
        Integer[] f = new Integer[n+1];

        for (int i = 0; i <= n; ++i) {
            jobs[i] = new Job(startTimes[i], finishTimes[i], values[i]);
            s[i] = i;
            f[i] = i;
        }

        // Store job indices by ascending finish times (f_j) in an array f
        Arrays.sort(s, Comparator.comparingInt(s1 -> jobs[s1].s));
        // Store job indices by ascending start times (s_j) in an array s
        Arrays.sort(f, Comparator.comparingInt(f1 -> jobs[f1].f));

        // Initialise an array of predecessors
        int[] predecessors = new int[n+1];
        // Initially, each job has no predecessor
        Arrays.fill(predecessors, -1);

        // For each job, starting with the one that starts the latest,
        //    we try to find the closest job whose finish time does not conflict with the start time of the job under consideration
        int k = n;
        for (int l = n; l > 0; --l) {
            // While there are conflicts, take the previous job in the array f
            while (jobs[s[l]].s < jobs[f[k]].f) --k;
            // Since job 0 is a dummy job, we know that all jobs up to s[l] have no predecessors
            if (k == 0) break;
            // Assign the predecessor
            predecessors[s[l]] = f[k];
        }
        return predecessors;
    }
}

class Job implements Comparable<Job> {
    int s;
    int f;
    int v;

    public Job(int s, int f, int v) {
        this.s = s;
        this.f = f;
        this.v = v;
    }

    @Override
    public int compareTo(Job other) {
        return Integer.compare(this.f, other.f);
    }

    @Override
    public String toString() {
        return String.format("(%d,%d,%d)", s, f, v);
    }

}
