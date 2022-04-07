package DivideAndConquer;

/**
 * Given an integer n and the skills sorted by completion s1, . . . , sn, return the number of completed skills.
 *
 * The first s ≥ 0 entries of the list are skills that have not been completed, followed by t ≥ 0 skills that have been completed. We aim to find this value t.
 */
public class CompletedSkills {
    /**
     * You should implement this method.
     *
     * @param n      the number of elements in skills.
     * @param skills the sorted array of `Skill`s (see Library for their implementation) to look through. Note that you should use entries skills[0] to skills[n]!
     * @return the number of completed skills in the sorted array.
     */
    public static int numberOfCompletedSkills(int n, Skill[] skills) {
        int l = 1;
        int r = n;

        while (l < r) {
            // Compute the index of the median
            int m = (l + r) / 2;

            // Discard the left part up to and including the median
            if (!skills[m].isCompleted()) l = m + 1;
                // Discard the right part up to but excluding the median (it might be the t we need)
            else r = m;
        }

        // This means there is no completed skills at all
        if (!skills[l].isCompleted()) return 0;
        // All the skills after and including this one are completed
        return n - l + 1;
    }
}

class Skill {

    private String name;

    private boolean completed;

    public Skill(String name, boolean completed) {
        this.name = name;
        this.completed = completed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
