package DynamicProgramming;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * For this exercise you are given sequences a and b as well as their lengths n and m. You are also given
 * a δ and may assume that αxiyj is alphaDiff when xi 6 = yj , or 0 otherwise. Finally you are given the
 * memory filled using the equations above. Thus mem[i][j] contains exactly the value for OP T (i, j).
 * You are tasked with returning the set of matched indices. Remember that a match is only made when
 * the alpha value is chosen in the minimisation expression.
 */
public class RecoverYourItems {
    /**
     * You should implement this method.
     *
     * @param n the length of the first sequence
     * @param m the length of the second sequence
     * @param x the array containing the first sequence. NB: Stored in indexes 1 <= i <= n (so
     *     ignore x[0])
     * @param y the array containing the second sequence. NB: Stored in indexes 1 <= j <= m (so
     *     ignore y[0])
     * @param delta the delta from the recurrence
     * @param alphaDiff the value for alpha from the recurrence if x[i] != y[j], otherwise alpha in
     *     the recurrence is zero.
     * @param mem the memory filled by the dynamic programming algorithm using the provided
     *     recursive formulation.
     * @return the matchings of characters from the first sequence to characters in the second
     *     sequence.
     */
    public static Set<Match> solve(
        int n, int m, char[] x, char[] y, int delta, int alphaDiff, int[][] mem) {
        // OPT(0,j) = j * delta
        // OPT(i,0) = i * delta
        // OPT(i,j) = min(alpha + OPT(i-1,j-1), delta + OPT(i-1,j), delta+OPT(i,j-1))

        // Current indices of characters in each sequence
        int i = n;
        int j = m;

        // Result
        Set<Match> matches = new HashSet<>();

        // Go all the way down...
        while (i > 0 && j > 0) {
            // Check which cost applies in current case
            int cost = x[i] == y[j] ? 0 : alphaDiff;

            // Check if current characters were matched or not
            if (mem[i][j] == cost + mem[i-1][j-1]) {
                matches.add(new Match(i,j));
                --i;
                --j;
            } else if (mem[i][j] == delta + mem[i-1][j]) {
                --i;
            } else if (mem[i][j] == delta + mem[i][j-1]){
                --j;
            }
        }
        return matches;
    }
}

class Match {

    // The index in the first sequence
    int firstIndex;

    // The index in the second sequence
    int secondIndex;

    public Match(int firstIndex, int secondIndex) {
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return firstIndex == match.firstIndex && secondIndex == match.secondIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstIndex, secondIndex);
    }
}
