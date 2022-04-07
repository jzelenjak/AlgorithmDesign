package DynamicProgramming;

/**
 * The game is a simple card game that features a deck of n ≥ 2 cards. The game has both a single-player
 * and two-player variant. In the single-player version your goal is to select a subset of cards from the deck
 * so that the sum of their values is maximised, but you are not allowed to pick two cards that immediately
 * follow each other in the deck.
 * In the two-player variant, players take turns. In your turn you can chose to either take the top card of
 * the deck, or the bottom card. Your goal is to maximise your score assuming your opponent does all in
 * their power to minimise your score.
 * For example, if we have a deck of 4 cards: 3, 5, 18, 7 then in the single player variant we can get to
 * a score of at most 18+3 = 21. In the two-player variant, the starting player takes 3 (because if they
 * take 7, the opponent would take 18 and win the game!), then the other player is forced to take either
 * 5 or 7, meaning the starting player can take 18 and win the game. See the tests in WebLab for more
 * examples. The tests added by the spec tests only test both methods in one, so use the visible tests to
 * debug individual methods.
 */
public class CardGame {
    /**
     * Compute the maximum obtainable score in the single player version of the game.
     *
     * @param n the number of cards
     * @param v the values of the cards 1 <= i <= n, ignore v[0]
     * @return the maximum obtainable score in a single player game
     */
    public static int maximumScoreSinglePlayer(int n, int[] v) {
        int[] mem = new int[n+1];

        // Base case
        mem[1] = v[1];

        // Recursive case
        for (int i = 2; i <= n; ++i) {
            // Either take this card or do not take this card
            mem[i] = Math.max(mem[i-1], v[i] + mem[i-2]);
        }

        return mem[n];
    }

    /**
     * Given optimal play from both players, what is the maximum score the starting player can obtain?
     *
     * @param n the number of cards
     * @param v the values of the cards 1 <= i <= n, ignore v[0]
     * @return the maximum score the starting player can achieve, assuming both players play
     *     optimally.
     */
    public static int maximumScoreTwoPlayer(int n, int[] v) {
        int[][] mem = new int[n+1][n+1];

        // Fill all the values for the base cases (namely, for `i == j` and `i == j + 1`)
        // (~ consider all singletons and all (consequtive) pairs)
        for (int i = 1; i <= n; i++) {
            // We can only pick this one card
            mem[i][i] = v[i];
            // We should pick the larger one
            if (i + 1 <= n) mem[i][i + 1] = Math.max(v[i], v[i + 1]);
        }

        // Consider triples, quadruples, etc
        for (int k = 2; k < n; ++k) {
            for (int i = 1; i <= n-k; ++i) {
                int j = i + k;
                mem[i][j] = Math.max(v[i] + Math.min(mem[i+2][j], mem[i+1][j-1]),
                                     v[j] + Math.min(mem[i][j-2], mem[i+1][j-1]));
            }
        }
        return mem[1][n];
    }
}
