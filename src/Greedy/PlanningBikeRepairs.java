package Greedy;

import java.util.Arrays;
import java.util.Comparator;

public class PlanningBikeRepairs {
    // A smarter way to implement the algorithm
    public static int fixMyBikesPlease(int n, int[] starttimes, int[] durations) {
        // Simple edge case: no tasks => no employees
        if (starttimes.length <= 1) return 0;

        // Create two arrays of tasks, one will be sorted by start times, the other by end times
        Task[] tasksByStart = new Task[n+1];
        Task[] tasksByEnd = new Task[n+1];

        for (int i = 0; i <= n; i++) {
            Task task = new Task(starttimes[i], starttimes[i] + durations[i]);
            tasksByStart[i] = task;
            tasksByEnd[i] = task;
        }

        // Sort the tasks according to their start times
        Arrays.sort(tasksByStart, Comparator.comparingInt(t -> t.start));
        // Sort the tasks according to their end times
        Arrays.sort(tasksByEnd, Comparator.comparingInt(t -> t.end));

        int minRequiredWorkers = 0;
        int currentlyRequiredWorkers = 0;
        int i = 0, j = 0;

        // Iterate through each task
        // tasksByStart[i] is the task that we consider
        // tasksByEnd[j] is the earliest task that might still be conflicting with tasksByStart[i]
        while (i <= n && j <= n) {
            // If there is a conflict,one employee will need to be busy
            if (tasksByStart[i].start < tasksByEnd[j].end) {
                ++currentlyRequiredWorkers;
                // Check if we need to hire another worker
                if (minRequiredWorkers < currentlyRequiredWorkers) minRequiredWorkers = currentlyRequiredWorkers;
                // Proceed to the next task
                ++i;
            } else {
                // One employee becomes free
                // Since tasksByEnd[j] does not conflict with tasksByStart[i],
                //  it will not conflict with any further tasks, so we don't need to consider it anymore
                ++j;
                --currentlyRequiredWorkers;
            }
        }
        // Return the minimum number of employees required for all repairs
        return minRequiredWorkers;
    }

    public static int fixMyBikesPleaseQuadratic(int n, int[] starttimes, int[] durations) {
        // Simple edge case: no tasks => no employees
        if (starttimes.length <= 1) return 0;

        // Create an array of tasks
        Task[] tasks = new Task[n+1];
        for (int i = 0; i <= n; ++i) tasks[i] = (new Task(starttimes[i], starttimes[i] + durations[i]));

        // Sort the tasks according to their start times
        Arrays.sort(tasks, Comparator.comparingInt(t -> t.start));

        // Workers needed
        int maxWorkersNeeded = 1;
        // For each task
        for (int i = 1; i <= n; ++i) {
            int workersNeeded = 1;
            // Check how many of the previous tasks overlap with it
            for (int j = 1; j < i; ++j) {
                if (tasks[i].start < tasks[j].end) ++workersNeeded;
            }
            // Update the maximum number of overlaps
            if (workersNeeded > maxWorkersNeeded) maxWorkersNeeded = workersNeeded;
        }
        return maxWorkersNeeded;
    }
}
