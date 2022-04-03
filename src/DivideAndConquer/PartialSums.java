package DivideAndConquer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Implement the function partialSums(Integer[] arr), which takes as input an array of integers and returns a set of all possible sums of the subsets.
 *
 * For this exercise you should use a divide and conquer approach instead of brute forcing.
 *
 * An example input would be the array [1, 2], resulting in the output set {0, 1, 2, 3}.
 */
public class PartialSums {
    /**
     * Computes all possible partial sums given an array of integers.
     *
     * @param arr - all values in the input set
     * @return set of sums
     */
    public static Set<Integer> partialSums(Integer[] arr) {
        // Simple edge case
        if (arr.length == 0) return new HashSet<>(Collections.singletonList(0));

        return partialSums(arr, 0, arr.length);
    }

    private static Set<Integer> partialSums(Integer[] arr, int low, int high) {
        // Where the result is gonna be
        Set<Integer> sums = new HashSet<>();
        // Sum 0 will be in every set
        sums.add(0);

        // Base case
        if (high - low <= 1) {
            sums.add(arr[low]);
            return sums;
        }

        // Divide...
        int m = (low + high) / 2;
        Set<Integer> leftSums = partialSums(arr, low, m);
        Set<Integer> rightSums = partialSums(arr, m, high);

        // ... and Conquer
        sums.addAll(rightSums);
        for (Integer leftSum : leftSums) {
            if (leftSum.equals(0)) continue;
            sums.add(leftSum);
            for (Integer rightSum : rightSums) sums.add(leftSum+rightSum);
        }
        return sums;
    }
}
