package DivideAndConquer;

/**
 * Counting the number of inversions in a list allows us to figure out how many elements are out of order in a list. Implement the Sort-and-Count algorithm out of the book. This algorithm calculates the number of inversions while sorting a list. Note that the Merge-and-Count part of this algorithm computes how different two lists are.
 *
 * For example if you get the list [2, 1, 0, 8], your algorithm should print the number of inversion, which is 3 in this example.
 */
public class SortingAndCounting {
    static int countInversions(int[] array) {
        // Base case
        if (array.length <= 1) return 0;

        // Divide...
        int m = array.length / 2;
        int[] left = new int[m];
        int[] right = new int[array.length - m];

        for (int i = 0; i < m; ++i) {
            right[i] = array[i+m];
            left[i] = array[i];
        }
        if (array.length % 2 == 1) right[right.length-1] = array[array.length-1];

        // ... and Conquer
        return countInversions(left) + countInversions(right) + mergeAndCount(left, right, array);
    }

    private static int mergeAndCount(int[] left, int[] right, int[] arr) {
        int l = 0, r = 0, p = 0, inv = 0;

        while (l < left.length && r < right.length) {
            if (left[l] <= right[r]) arr[p++] = left[l++];
            else {
                inv += left.length - l; // The number of inversions (swaps) is equal to the remaining length of the left array
                arr[p++] = right[r++];
            }
        }

        while (l < left.length) arr[p++] = left[l++];
        while (r < right.length) arr[p++] = right[r++];

        return inv;
    }
}
