package NetworkFlow;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The Supreme League is the highest level baseball competition. In September the best m teams play are selected to play in the playoffs to compete for the Cup. The winner of the Cup is the team with the highest number of wins.
 *
 * You are given a list of m baseball teams that are competing for the Cup. For each team i, the current number of wins, and the number of games left to play is given, and it is already decided against whom each team will play their last games.
 *
 * We now ask you to decide for team x whether that team is still able to win the Cup.
 *
 * The input will look as follows: the first line will contain the number of teams, m. The team numbers range from 1 to m. Then 2m lines follow, for each team their team number, i*, the number of wins, w_i, and the number of games left to play, g_i, are printed on one line. The line below will contain g_i numbers separated by spaces, which are the teams that team i still has to play against. Keep in mind that one game is displayed in the lists of both teams.
 *
 * The first team that is entered is team x, i.e. the team of which we want to find out if it can still win.
 * ```
 * 4
 * 4 10 2
 * 2 3
 * 1 12 2
 * 2 3
 * 2 11 3
 * 1 3 4
 * 3 11 3
 * 1 2 4
 * ```
 * Your algorithm should output whether it is possible for team x to still be the winner of Cup, e.g. end up with the highest number of wins, possibly in a tie. In this example the output should be false
 */
public class Baseball {
    /**
     * Returns true if team x can still win the Cup.
     */
    public static boolean solve(InputStream in) {
        Scanner sc = new Scanner(in);               // Scanner to read the input
        int m = sc.nextInt();                       // The number of teams
        int x = sc.nextInt();                       // The team number of the team we are interested in (x)
        int w_x = sc.nextInt();                     // The number of games won by the team x
        int g_x = sc.nextInt();                     // The number of games left to play for the team x
        int m_x = w_x + g_x;                        // The max number of wins for the team x

        // Create the nodes representing the teams
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i <= m + 1; ++i) nodes.add(new Node(i));
        Node source = nodes.get(0);
        Node sink = nodes.get(m+1);

        // Discard the info for the game x
        for (int i = 0; i < g_x; ++i) sc.nextInt();

        // Iterate over the remaining teams
        int totalGamesToPlay = 0;
        for (int k = 2; k <= m; ++k) {
            int i = sc.nextInt();                       // The id (number) of the current team
            int w_i = sc.nextInt();                     // The number of wins
            if (w_i > m_x) return false;                // There is no way for x to win
            int g_i = sc.nextInt();                     // The number of games left to play

            nodes.get(i).addEdge(sink, m_x - w_i);      // This team cannot have more wins than team x

            // This map maps the opponent team id to the number of games to play with that team
            Map<Integer, Integer> opponents = new HashMap<>();

            // Iterate over all the opponent teams for the current team
            for (int j = 0; j < g_i; ++j) {
                int opponent = sc.nextInt();
                // Discard the team x and, in order to prevent duplicates, count only if i < opponent (for the games played together)
                if (opponent == x || opponent < i) continue;

                // Add the count to the opponent team
                if (opponents.get(opponent) == null) opponents.put(opponent, 1);
                else opponents.put(opponent, opponents.get(opponent) + 1);
            }

            // For all the opponents of this team
            for (Integer opponent : opponents.keySet()) {
                int gamesToPlay = opponents.get(opponent);
                totalGamesToPlay += gamesToPlay;

                // Create the 'play' node and create the edges for it
                Node play = new Node(nodes.size());
                play.addEdge(nodes.get(i), Integer.MAX_VALUE);          // Edge to the current team with infinite capacity
                play.addEdge(nodes.get(opponent), Integer.MAX_VALUE);   // Edge to the opponent team with infinite capacity
                source.addEdge(play, gamesToPlay);                      // Edge from source to the 'play' node with the capacity of the number of games to play
                nodes.add(play);
            }
        }
        sc.close();


        // Run Ford-Fulkerson and return the answer
        Graph g = new Graph(nodes, source, sink);
        MaxFlow.maximizeFlow(g);
        int flow = 0;
        for (Edge e : g.getSource().getEdges()) flow += e.getFlow();
        return flow == totalGamesToPlay;
    }
}
