package DynamicProgramming;

/**
 * You need to finish n assignments. Every assignment is graded on a scale of 1 to g>1. More hours spent on an assignment typically leads to a higher grade. Your goal is to maximise your average grade, but you have only H hours available. For every assignment you made a function: spending 0≤hi≤H hours on assignment i will give you a grade of fi(hi). The functions are monotonically increasing, that is fi(h′)≤fi(h) for any h′≤h. Determine how many (integer) hours you should spend on each assignment such that your average grade is highest.
 *
 * Think of a recursive function that computes the maximum obtainable average. Using that function, implement the method maxGrade iteratively.
 *
 * Hint: maximising the average is the same as maximising the sum of the grades!
 */
public class TightOnTime {
    /**
     * Implement this method
     *
     * @param n - the number of assignments
     * @param H - the number of hours you can spend
     * @param f - the function in the form of a (n + 1) x (h + 1) matrix.
     *          Index 0 of the number of assignments should be ignored.
     *          Index 0 of the number of hours spend is the minimum grade for this assignment.
     * @return the max grade achievable
     */

    public static int maxGrade(int n, int H, int[][] f) {
        int[][] mem = new int[n+1][H+1];

        for (int assignment = 1; assignment <= n; ++assignment) {
            mem[assignment][0] = f[assignment][0] + mem[assignment-1][0];
            for (int hours = 1; hours <= H; ++hours) {
                mem[assignment][hours] = Integer.MIN_VALUE;
                for (int spent = 0; spent <= hours; ++spent) {
                    int grade = f[assignment][spent] + mem[assignment-1][hours-spent];
                    if (grade > mem[assignment][hours]) mem[assignment][hours] = grade;
                }
            }
        }
        return mem[n][H];
    }

    public static int maxGrade2(int n, int h, int[][] f) {
        // Create a memoisation table
        // Entry (j,i) is the optimal solution given j tasks and i hours for all of them
        int[][] mem = new int[n+1][h+1];

        // Base case: OPT(0,h) = 0
        //            OPT(j,0) = f[j][0] + mem[j-1][0]
        // Recursive case: OPT(j, h) = max { f(j,i) +  OPT(j-1, h-i) }

        // Iterate over all the tasks
        for (int j = 1; j <= n; ++j) {
            // Base case: if no time, then take the "base grade"
            //             + OPT for the tasks before (also "base grades")
            mem[j][0] = f[j][0] + mem[j-1][0];

            // Iterate over all the available hours
            for (int i = 1; i <= h; ++i) {
                // Recursive case: the optimal solution for (j,i) is
                //                 max (mem[j-1][i-k] + f[j][k]) for 0 <= k <= j
                // (which means: spend so many hours k on this task
                //   that the grade for previous tasks given i - k hours
                //   plus the grade for k hours on this task is optimal)
                int maxGradeSoFar = -1;
                for (int k = 0; k <= i; k++) {
                    // Grade for i-k hours on prev tasks + grade for k hours on this task
                    maxGradeSoFar = Math.max(maxGradeSoFar, mem[j-1][i-k] + f[j][k]);
                }
                mem[j][i] = maxGradeSoFar;
            }
        }
        return mem[n][h];
    }

    public static int maxGrade3(int n, int H, int[][] f) {
        // OPT(i, h) =
        // 0 if i == 0
        // f[i][0] + OPT(i-1, h) if h == 0
        // max(f[i][j] + OPT(i-1, h-j)) for 0 <= j <= h else

        int[][] mem = new int[n+1][H+1];
        for (int i = 0; i <= n; ++i) {
            for (int h = 0; h <= H; ++h) {
                if (i == 0) {
                    mem[i][h] = 0;
                } else if (h == 0) {
                    mem[i][h] = f[i][0] + mem[i-1][h];
                } else {
                    for (int j = 0; j <= h; ++j) {
                        mem[i][h] = Math.max(mem[i][h], f[i][j] + mem[i-1][h-j]);
                    }
                }
            }
        }
        return mem[n][H];
    }

    public TimeOnAssignment[] recoverSolution(int n, int H, int[][] f, int[][] mem) {
        // Initialise our variables
        int i = n;
        int h = H;
        TimeOnAssignment[] result = new TimeOnAssignment[n+1];

        //
        while(i > 0) {
            for (int j = 0; j <= h; ++j) {
                if (mem[i][h] == f[i][j] + mem[i-1][h-j]) {
                    result[i] = new TimeOnAssignment(i, j);
                    --i;
                    h -= j;
                    break;
                }
            }
        }
        return result;
    }

    static class TimeOnAssignment {
        int assignment;
        int time;

        public TimeOnAssignment(int assignment, int time) {
            this.assignment = assignment;
            this.time = time;
        }
    }
}
