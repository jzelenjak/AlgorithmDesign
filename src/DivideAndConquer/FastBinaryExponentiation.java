package DivideAndConquer;

/**
 * Implement the function computeC(int[] a, int[] b), which takes an array a of length n and an array b of length m as input and returns a nxm matrix. This matrix contains the values ((a^b)_j)_i, where a_i is the i-th value in array a and b_j is the j-th value in array b.
 *
 * In this exercise you are not allowed to use Math.pow() and should implement fast binary exponentiation by yourself.
 */
public class FastBinaryExponentiation {
    /**
     * Computes the matrix C, containing the values for a^b, for all values in a and b.
     *
     * @param a array containing the bases
     * @param b array containing the exponents
     * @return matrix C
     */
    public static int[][] computeC(int[] a, int[] b) {
        int n = a.length, m = b.length;
        int[][] res = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; j++) {
                res[i][j] = pow(a[i],b[j]);
            }
        }
        return res;
    }

    /**
     * Fast exponentiation
     */
    private static int pow(int base, int exp) {
        if (exp == 0) return 1;
        if (exp == 1) return base;
        int x = pow(base, exp / 2);
        return ((exp & 1) == 1) ? x * x * base : x * x;
    }
}
