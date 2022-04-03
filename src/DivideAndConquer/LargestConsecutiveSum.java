package DivideAndConquer;

/**
 * Implement the function largestSum(int[] arr), that takes as input an array of integers, find the largest sum using consecutive elements in this array using a divide and conquer approach.
 *
 * As an example, given the input array [2, -3 , 2, 1], the largest consecutive sum would use the last two elements to sum to 3.
 */
public class LargestConsecutiveSum {
    public static int largestSum(int[] arr) {
        if (arr.length == 0) return 0;
        return largestSum(arr, 0, arr.length);
    }

    private static int largestSum(int[] arr, int start, int end) {
        // Base case (differ by 1)
        if (start == end - 1) return Math.max(0, arr[start]);

        // Divide...
        int m = (start + end) / 2;
        int largestLeft = largestSum(arr, start, m);
        int largestRight = largestSum(arr, m, end);

        // ... and Conquer
        int largestCombined = 0;
        int currSum = 0;
        // Go to the left
        for (int x = m; x >= start; --x) {
            currSum += arr[x];
            if (currSum > largestCombined) largestCombined = currSum;
        }
        currSum = largestCombined;
        //Go to the right
        for (int x = m+1; x < end; ++x) {
            currSum += arr[x];
            if (currSum > largestCombined) largestCombined = currSum;
        }
        // Return the largest sum
        return Math.max(largestCombined, Math.max(largestLeft, largestRight));
    }
}
