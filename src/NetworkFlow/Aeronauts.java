package NetworkFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * Lyra is an avid fan of hot air balloon flying together with her friend Lee Scoresby. Unfortunately the skies above Cittàgazze are very crowded this time of year, so only a limited number of flying slots are still available and aeronauts from all over the world have applied for these slots. Due to safety regulations only one aeronaut can use each of these slots. Lee is in the running to win the prize of “aeronaut of the year”, awarded to the person that flies his or her balloon most often. Unfortunately his balloon punctured yesterday, so he can no longer fly his balloon.
 *
 * Lyra wants to ensure that Lee can still win the prize, by making sure none of the others fly as much as Lee has done (thus Lee would only win if he has more points than all others, a tie is not enough). Given the amount of flights Lee has flown l, a list of n competing aeronauts that each have already made f_i flights, m remaining slots, and large matrix compatible indicating if aeronaut i is eligible for slot j, Lyra wants to know which aeronaut should use what time slot so that Lee can still win the prize. To this end she does the following:
 */
public class Aeronauts {
    /**
     * @param l          the number of flights Lee has already done
     * @param n          the number of competitors
     * @param m          the number of open slots left
     * @param flights    the number of flights each of the competitors has done. You should use flights[1] to flights[n]
     * @param compatible 2D boolean array such that slot i can be used by competitor j iff compatible[i][j] is true. Note that compatible[0][x] and compatible[x][0] should not be used.
     * @return a boolean indicating whether Lee is still able to win.
     */
    public static boolean solve(int l, int n, int m, int[] flights, boolean[][] compatible) {
        // Create source, sink and the node list
        List<Node> nodes = new ArrayList<>();
        Node source = new Node(-42,0);
        Node sink = new Node(-69,0);
        nodes.add(source);
        nodes.add(sink);

        // Arrays for aeronauts nodes
        Node[] competitors = new Node[n+1];
        // Create the aeronauts nodes
        for (int i = 1; i <= n; ++i) {
            Node competitor = new Node(i,0);
            competitors[i] = competitor;
            // None of the others must fly as much as Lee has done
            source.addEdge(competitor, 0, l - flights[i] - 1);
            nodes.add(competitor);
        }

        // Arrays for time slot nodes
        Node[] openSlots = new Node[m+1];
        // Create the time slots nodes
        for (int i = 1; i <= m; ++i) {
            Node slot = new Node(i,0);
            openSlots[i] = slot;
            // Only one aeronaut can use each of the slots
            slot.addEdge(sink, 0, 1);
            nodes.add(slot);
        }

        // Create the edges based on whether time slot i can be used by aeronaut j
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (compatible[i][j]) {
                    // Both the timeslot and the aeronaut must be selected, thus c(e) = ∞
                    competitors[j].addEdge(openSlots[i], 0, Integer.MAX_VALUE);
                }
            }
        }

        // Run Ford-Fulkerson and get the answer
        // In order for Lee to win, the flow must be equal to the number of slots left
        // Otherwise it will mean that someone else will win
        Graph g = new Graph(nodes, source, sink);
        return MaxFlow.maximizeFlow(g) == m;
    }
}
