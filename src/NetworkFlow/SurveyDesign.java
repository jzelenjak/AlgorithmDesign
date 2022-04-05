package NetworkFlow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A candy store wants to review their collection of k sweets for the upcoming Halloween Holiday. A set of n frequent users was selected to participate in a sweets testing event. Every participant has a demand of di sweets intake to participate in the review. However, to avoid any major sugar spikes, each is allowed only a maximum amount of ai sweets. (1 <= i <= n) Moreover, each person has a list of candy he doesn’t like, that he/she will not review (such as liquorice). For the testing event, a quantity qi of each candy is provided. Moreover, for a candy to have a valid review, a minimum of ci persons need to approve it. (1 <= j <= k)
 *
 * The owner of the store tasks you to find if the chosen frequent users are enough to review all his candy.
 */
public class SurveyDesign {
    /**
     * You should implement this method
     *
     * @param n           the number of frequent users
     * @param k           the number of candy to be reviewed
     * @param connections a set of connections (x, y) representing that person x does _not_ like candy y.
     * @param candyQuantities the amount qi of each candy provided for the event (1<=i<=k) (q)
     * @param  personMinCandy the amount of candy for a person such that the review is worthwhile (d)
     * @param personMaxCandy the safe amount of candy without risking a sugar spike for a person (a)
     * @param candyMinApprovers the number of people needed to try a specific candy (c)
     * @return true, if the testing event is successful in reviewing all the candy, false otherwise.
     */
    public static boolean isCandyProofingPossible(int n, int k, Set<Connection> connections, int[] personMinCandy, int[] personMaxCandy, int[] candyMinApprovers, int[] candyQuantities) {
        List<Node> nodes = new ArrayList<>();
        Node source = new Node(-42);
        Node sink = new Node(-69);
        nodes.add(source);
        nodes.add(sink);

        // Lower and upper bound on the capacity of the t-s edge
        int minSTCap = 0, maxSTCap = 0;

        // Create people nodes
        Node[] people = new Node[n+1];
        for (int i = 1; i <= n; ++i) {
            Node person = new Node(i);
            people[i] = person;
            minSTCap += personMinCandy[i];
            maxSTCap += personMaxCandy[i];
            source.addEdge(person, personMinCandy[i], personMaxCandy[i]);
            nodes.add(person);
        }

        // Create candies nodes
        Node[] candies = new Node[k+1];
        for (int i = 1; i <= k; ++i) {
            Node candy = new Node(i);
            candies[i] = candy;
            candy.addEdge(sink, candyMinApprovers[i], candyQuantities[i]);
            nodes.add(candy);
        }

        // Add the magic st edge
        sink.addEdge(source, minSTCap, maxSTCap);

        // Create the edges based on the provided Connections set
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= k; ++j) {
                if (!connections.contains(new Connection(i,j))) {
                    people[i].addEdge(candies[j], Integer.MAX_VALUE);
                }
            }
        }

        Graph g = new Graph(nodes, source, sink);
        return g.hasCirculation();
    }
}
