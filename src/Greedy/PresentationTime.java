package Greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Given the names of n speakers, name1, ... namen, the start times of their presentations s1, ..., sn, and
 * the end time of their presentations e1, ... en, return the largest set of names of speakers whose presentations
 * they can attend.
 * You may assume that they can instantly go from one presentation to another: when a presentation ends
 * at time 8 and the next starts at time 8, this forms no problem. They aren’t big on coffee breaks anyway.
 */
public class PresentationTime {
    /**
     * You should implement this method.
     *
     * @param n              the number of students
     * @param presenterNames the names of the presenters p_1 through p_n. Note you should only use entries presenterNames[1] up to and including presenterNames[n].
     * @param startTimes     the start times of the presentations s_1 through s_n. Note you should only use entries startTimes[1] up to and including startTimes[n].
     * @param endTimes       the end times of the presentations e_1 through e_n. Note you should only use entries endTimes[1] up to and including endTimes[n].
     * @return a largest possible set of presenters whose presentation we can attend.
     */
    public static Set<String> whatPresentations(int n, String[] presenterNames, int[] startTimes, int[] endTimes) {
        Presentation[] presentations = new Presentation[n];
        for (int i = 1; i <= n; ++i) {
            presentations[i-1] = new Presentation(presenterNames[i], startTimes[i], endTimes[i]);
        }

        Arrays.sort(presentations, Comparator.comparingInt(p -> p.end));

        Set<String> visited = new HashSet<>();
        int currentEndTime = 0;
        for (Presentation p : presentations) {
            if (p.start >= currentEndTime) {
                visited.add(p.name);
                currentEndTime = p.end;
            }
        }
        return visited;
    }
}

class Presentation {
    String name;
    int start;
    int end;

    public Presentation(String name, int start, int end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }
}
