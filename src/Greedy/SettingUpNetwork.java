package Greedy;

import java.util.PriorityQueue;

/**
 * To set up internet connections in a large area in Cittagazze, stations are planned at locations with a high population density. These n stations are connected through pairs of quite expensive directional antennas. The price of (a pair of) antennas depends on the required range, and for each pair of locations (u,v) these costs are given by c(u,v)>0. The main question of this exercise is which (undirected) connections to set up such that all locations are connected and the installation costs are as low as possible.
 *
 * Unfortunately, there is insufficient budget for setting up the connected network computed above at once. The government decides to make as many connections of the optimal network as possible, given the available budget B (and to complete the network in upcoming years). For example, if the network looks like this, a budget of 3 would select (a,b) and (d,e) and (f,g).\
 *
 * For any pair of locations that is not given, the costs of connecting can be assumed to be infinite. It is guaranteed that the network can be set up using finite costs.
 *
 * Your output should be two numbers. The first number is the minimum total costs to connect all locations. The second number is the number of connections that can be built using the budget.
 */
public class SettingUpNetwork {

    public long cost, number;

    public SettingUpNetwork(long cost, long number) {
        this.cost = cost;
        this.number = number;
    }

    public static SettingUpNetwork setUpTheNetwork(int n, int m, int b, Edge[] edges) {
        // Create and initialise the data structures
        UnionFind uf = new UnionFind(n);
        PriorityQueue<Edge> pq = new PriorityQueue<>((e1, e2) -> Integer.compare(e1.from,e2.to));
        for (int i = 1; i <= m; ++i) pq.offer(edges[i]);

        // Create the variables
        int cost = 0;
        int edgesAdded = 0;
        int edgesToAdd = n-1;
        int possibleToBuild  = 0;

        // Build a MST using Kruskal's algorithm
        while (edgesAdded < edgesToAdd) {
            // Take the next cheapest edge
            Edge e = pq.poll();

            // If adding the edge will create a cycle, discard it
            if (e == null) throw new NullPointerException();
            if (!uf.union(e.from, e.to)) continue;

            // Update the costs
            ++edgesAdded;
            cost += e.weight;   // length in this case
            // Check if it is still possible to build the connection given the budget
            if (cost <= b) ++possibleToBuild ;
        }

        return new SettingUpNetwork(cost, possibleToBuild );
    }
}
