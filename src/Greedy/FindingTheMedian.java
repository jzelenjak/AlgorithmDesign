package Greedy;

/**
 * In this exercise, you will receive a list of n elements. Your task is to sort them and return the median value.
 * Note that if the list has an even length, the median value might be the average of the two middle elements in the array, as in the example below.
 * You are free to choose any sorting algorithm that you like, but the input can be as large as one million numbers, so the runtime complexity of your algorithm should be O(nlogn).
 * We recommend choosing Quicksort or Merge Sort.
 */
public class FindingTheMedian {
    public static double solve(int n, double[] list) {
        // Edge case - no median
        if (list.length == 0) return -1;

        // Sort the array
        double[] sorted = mergeSort(list);

        // Return the median, depending on whether the length of the list is even or odd
        int m = sorted.length / 2;
        return sorted.length % 2 == 0 ? (sorted[m-1]+sorted[m]) / 2 : sorted[m];
    }

    /*
     * A helper method for sorting the array of doubles
     */
    private static double[] mergeSort(double[] list) {
        if (list.length < 2) return list;

        int middle = list.length / 2;
        double[] leftSub = new double[middle];
        double[] rightSub = new double[list.length - middle];

        for (int i = 0; i < middle; ++i) {
            leftSub[i] = list[i];
            rightSub[i] = list[i + middle];
        }
        if (list.length % 2 == 1) rightSub[rightSub.length-1] = list[list.length-1];

        double[] left = mergeSort(leftSub);
        double[] right = mergeSort(rightSub);

        return merge(left, right);
    }


    /*
     * A helper method for merging two sorted arrays of doubles
     */
    private static double[] merge(double[] left, double[] right) {
        int l = 0;
        int r = 0;
        int p = 0;

        double[] arr = new double[left.length + right.length];

        while (l < left.length && r < right.length) {
            if (left[l] <= right[r]) arr[p++] = left[l++];
            else arr[p++] = right[r++];
        }

        while (l < left.length) arr[p++] = left[l++];
        while (r < right.length) arr[p++] = right[r++];

        return arr;
    }
}
