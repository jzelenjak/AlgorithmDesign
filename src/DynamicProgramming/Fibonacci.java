package DynamicProgramming;

/**
 * A Fibonacci sequence is a series of numbers for which it holds that, with the exception of the first number, each subsequent number equals the sum of its two predecessors. An example of the first ten numbers in the sequence would be {0, 1, 1, 2, 3, 5, 8, 13, 21, 34} (note that we start counting at zero).
 *
 * A common method of finding Fibonacci numbers is by utilizing a recursive method which tries to find then numbers by recursively finding the first two values, and then summing them while backtracking. This does, however, require the recalculation of the full series during each step.
 */
public class Fibonacci {
    /**
     * Returns the n'th Fibonacci number
     */
    public static int fibonacci(int n) {
        int[] mem = new int[n+1];

        // Base cases
        mem[0] = 0;
        mem[1] = 1;

        // Iterate through all fibonacci numbers from index 2 up to n.
        for (int i = 2; i <= n; ++i) {
            mem[i] = mem[i-1] + mem[i-2];
        }

        // Return the obtained fibonacci value at index n.
        return mem[n];
    }
}
