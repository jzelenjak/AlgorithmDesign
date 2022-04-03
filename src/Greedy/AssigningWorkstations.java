package Greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * A cloud service offers the opportunity for n researchers to submit jobs from workstations in a nearby office building. Access to these workstations is controlled by an operator. This operator needs to unlock the workstations for the researchers who come to run their computations at the cloud service. However, this operator is very lazy. She can unlock the machines remotely from her desk, but does not feel that this menial task matches her qualifications. She therefore decides to ignore the security guidelines and to simply ask the researchers not to lock their workstations when they leave, and then assign new researchers to workstations that are not used any more but that are still unlocked. That way, she only needs to unlock each workstation for the first researcher using it. Unfortunately, unused workstations lock themselves automatically if they are unused for more than m minutes. After a workstation has locked itself, the operator has to unlock it again for the next researcher using it.
 *
 * Given the exact schedule of arriving and leaving researchers, can you tell the operator how many unlockings she may save by asking the researchers not to lock their workstations when they leave and assigning arriving researchers to workstations in an optimal way? You may assume that there are always enough workstations available.
 *
 * You are given start times starti and durations durationsi for all 1≤i≤n.
 */
public class AssigningWorkstations {
    /**
     * @param n number of researchers
     * @param m number of minutes after which workstations lock themselves
     * @param start start times of jobs 1 through n. NB: you should ignore start[0]
     * @param duration duration of jobs 1 through n. NB: you should ignore duration[0]
     * @return the number of unlocks that can be saved.
     */
    public static int solve(int n, int m, int[] start, int[] duration) {
        if (start.length <= 1) return 0;
        // Create a list of jobs (research sessions)
        List<Job> jobs = new ArrayList<>(n);
        for (int i = 1; i <= n; ++i) jobs.add(new Job(start[i], start[i] + duration[i]));

        // Sort the jobs on their start times
        Collections.sort(jobs);

        // A priority queue with the workstations that are unlocked for the current job
        PriorityQueue<Integer> unlocked = new PriorityQueue<>();
        unlocked.add(jobs.get(0).end);

        // The number of unlocks that have been saved
        int savedUnlocks = 0;

        // Iterate over all the jobs
        for (int i = 1; i < n; ++i) {
            Job job = jobs.get(i);

            // Case 1: overlap -> unlock another station (no unlocked workstations can be assigned)
            if (!unlocked.isEmpty() && job.start < unlocked.peek()) unlocked.add(job.end);
                // Case 2: no overlaps -> an unlocked workstation might be assigned
            else {
                // Remove all the workstations that are locked at this point
                //  (they will need to be unlocked in the future anyway)
                while (!unlocked.isEmpty() && unlocked.peek() + m < job.start) unlocked.poll();
                if (unlocked.isEmpty()) unlocked.add(job.end);
                else {
                    unlocked.poll();
                    savedUnlocks++;
                    unlocked.add(job.end);
                }
            }
        }
        return savedUnlocks;
    }
}
