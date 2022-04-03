package Greedy;

import java.util.ArrayList;
import java.util.List;

/**
 * As the owner of a shipping company, you won a large contract requiring you to ship a certain amount of boxes nto a certain destination. The contract gives the weight of the boxes in order of their arrival. This is also the order in which the boxes must be shipped. To maximize profit, you want to minimize the number of trucks used. Each truck can carry boxes up to a total of weight W. Every box i with 1≤i≤n has a weight wi≤W.
 *
 * Implement an algorithm that determines the minimal amount of trucks needed to ship the boxes to the destination.
 *
 * If we can carry at most 48 weight units in one truck (W=48) then the following shipment of boxes should result in an output of 3.
 */
public class PackingTrucks {
    /**
     * @param n the number of packages
     * @param weights the weights of all packages 1 through n. Note that weights[0] should be ignored!
     * @param maxWeight the maximum weight a truck can carry
     * @return the minimal number of trucks required to ship the packages _in the given order_.
     */
    public static int minAmountOfTrucks(int n, int[] weights, int maxWeight) {
        // Edge case: no boxes => no trucks needed
        if (weights.length <= 1) return 0;

        // A list with weights in the trucks (e.g trucks[0] = 43 means truck 0 has weight 43)
        List<Integer> trucks = new ArrayList<>();
        trucks.add(0);

        // The index of the last truck that we can load
        int currTruck = 0;

        // Iterate through all the weights (boxes)
        for (int w = 1; w <= n; ++w) {
            // If the last truck has enough space, put the current box (weight)
            // Otherwise put the current box into a new truck
            if (trucks.get(currTruck) + weights[w] <= maxWeight) {
                trucks.set(currTruck, trucks.get(currTruck) + weights[w]);
            } else {
                trucks.add(weights[w]);
                ++currTruck;
            }
        }
        return trucks.size();
    }
}
