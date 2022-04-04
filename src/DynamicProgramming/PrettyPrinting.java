package DynamicProgramming;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This assignment is part of Dynamic Programming TA Review Task 2A.2, please do the theoretical assignment before doing the practical part.
 *
 * Implement the function that finds the minimum total sum of squares of the slacks of all lines and returns a pretty-printed version of the input string.
 *
 * The first line contains the line Length L, the second line contains the amount of words n and the next line contains the input string.
 *
 * Given the following problem instance:
 * ```
 * 42
 * 13
 * The Answer to the Great Question of Life, the Universe and Everything is Forty-two.
 * ```
 * we expect the following as answer:
 * ```
 * ["The Answer to the Great Question of Life,", "the Universe and Everything is Forty-two."]
 * ```
 */
public class PrettyPrinting {
    public static List<String> solve(InputStream in) {
        Scanner sc = new Scanner(in);
        int L = sc.nextInt();
        int n = sc.nextInt();

        String[] words = new String[n];
        for (int i = 0; i < n; i++) {
            words[i] = sc.next();
        }
        sc.close();

        int[][] slacks = calculateSlacks(n, L, words);
        int[] mem = calculateOpt(n, slacks);
        return print(n, mem, slacks, words);
    }

    public static List<String> print(int j, int[] mem, int[][] slacks, String[] words) {
        // j is the number of words that we are given in this call
        if (j == 0) return new ArrayList<>();

        int optVal = Integer.MAX_VALUE;
        // First index to print + 1
        int printVal = 1;

        // We need to look for the index of the word that will be the first one on the current line (in the optimal partition)
        for (int i = 1; i <= j; ++i) {
            if (slacks[i-1][j-1] !=Integer.MAX_VALUE) {
                int squaredSlack = square(slacks[i-1][j-1]);
                if (squaredSlack + mem[i-1] < optVal) {
                    optVal = squaredSlack + mem[i-1];
                    printVal = i;
                }
            }
        }

        // Recursively find the previous lines
        List<String> lines = print(printVal-1, mem, slacks, words);
        // Build the current line
        StringBuilder line = new StringBuilder();
        for (int i = printVal; i < j; ++i) {
            line.append(words[i - 1]).append(" ");
        }
        line.append(words[j-1]);
        lines.add(line.toString());
        return lines;
    }


    public static int[] calculateOpt(int n, int[][] slacks) {
        int[] mem = new int[n+1];

        // Here j is the number of words in a subproblem
        //      i is the max number of lines we can use (<= #words, since we cannot skip lines);
        //      also, i is the first word that we put on this line. Everything from i to j (both inclusive) is on this line.
        // In each iteration of the inner loop we have fixed number of words
        //      and we put word i and everything up to and including j on one line
        // In each iteration of the outer loop we are trying to fit a new word.
        //      (in each iteration of the inner loop we are trying to do so with at most i lines)
        // Note: at most i lines does not mean all are used (e.g opt for 2 lines means opt for at most 2 lines, which might have been just 1 line used)
        // Note: when i=j, it means we are trying to put the new word on a new line
        for (int j = 1; j <= n; ++j) {
            mem[j] = Integer.MAX_VALUE;
            for (int i = 1; i <= j; ++i) {
                if (slacks[i-1][j-1] != Integer.MAX_VALUE) {
                    int squaredSlack = square(slacks[i-1][j-1]);
                    mem[j] = Math.min(mem[j], squaredSlack + mem[i-1]); // The cost of putting word i and everything until j on the current line
                }
            }
        }
        return mem;
    }

    public static int[][] calculateSlacks(int n, int L, String[] words) {
        // Entry i, j is the cost of putting the word i and everything up to and including j on a single line
        int[][] slacks = new int[n][n];

        for (int i = 0; i < n; ++i) {
            int len = words[i].length();
            slacks[i][i] = L - len;
            for (int j = i + 1; j < n; ++j) {
                len += 1 + words[j].length();
                slacks[i][j] = (len <= L) ? L - len : Integer.MAX_VALUE;
            }
        }
        return slacks;
    }

    private static int square(int num) {
        return num * num;
    }
}
