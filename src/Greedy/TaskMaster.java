package Greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class TaskMaster {
    /**
     * You should implement this method.
     *
     * @param n the number of tasks available
     * @param taskNames the names of the tasks d_1 through d_n. Note you should only use entries
     *     taskNames[1] up to and including taskNames[n].
     * @param startTimes the start times of the tasks s_1 through s_n. Note you should only use
     *     entries startTimes[1] up to and including startTimes[n].
     * @return the names of a largest possible set of tasks you can complete.
     */
    public static Set<String> winningTheTrophy(int n, String[] taskNames, int[] startTimes) {
        if (n <= 1) return new HashSet<>();

        NamedTask[] tasks = new NamedTask[n];
        for (int i = 1; i <= n; ++i) tasks[i-1] = new NamedTask(startTimes[i], taskNames[i]);

        Arrays.sort(tasks, Comparator.comparingInt(t -> t.start));

        Set<String> possible = new HashSet<>();
        int i = 0;
        int f = 0;
        while (i < n) {
            if (tasks[i].start >= f) {
                possible.add(tasks[i].description);
                f = tasks[i].start + 5;
            }
            ++i;
        }
        return possible;
    }
}

class NamedTask {
    int start;
    String description;

    public NamedTask(int start, String description) {
        this.start = start;
        this.description = description;
    }
}
