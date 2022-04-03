package Greedy;

import java.util.Arrays;

public class GreedyKnapsack {
    /**
     * Return the minimum number of items we need to get to the weight we want to get to.
     *
     * @param n      the number of item categories
     * @param w      the weight we want to achieve with as few items as possible.
     * @param num    the number of items in each category c_1 through c_n stored in num[1] through num[n] (NOTE: you should ignore num[0]!)
     * @param weight the weight of items in each category c_1 through c_n stored in weight[1] through weight[n] (NOTE: you should ignore weight[0]!)
     * @return minimum number of items needed to get to the required weight
     */
    public static int run(int n, int w, int[] num, int[] weight) {
        return new GreedyKnapsack().solve(n, w, num, weight);
    }

    public int solve(int n, int w, int[] num, int[] weight) {
        // Create an array of Items (with category id and weight)
        Item[] items = new Item[n];
        for (int i = 1; i <= n; ++i) items[i-1] = new Item(i, weight[i]);

        // Sort the items by weight decreasing (non-increasing)
        Arrays.sort(items, (i1, i2) -> Integer.compare(i2.weight,i1.weight));

        // Variables to keep track of items added
        int numItems = 0;
        int currentWeight = 0;

        // Iterate through all the items
        for (Item item : items) {
            int available = num[item.category]; // Number of items of that category
            int used = 0;                       // Used in this iteration

            // While possible to add items, add then
            while (used++ < available && currentWeight + item.weight <= w) {
                currentWeight += item.weight;
                numItems++;
            }
            if (currentWeight == w) return numItems;
        }
        return numItems;
    }
}

class Item {
    int category;
    int weight;

    public Item(int category, int weight) {
        this.category = category;
        this.weight = weight;
    }
}
