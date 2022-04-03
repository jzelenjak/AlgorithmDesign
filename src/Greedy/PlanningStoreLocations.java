package Greedy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;


/**
 * You are the owner of a new store chain (called Amazing Donuts, or AD for short), and business is running well, so you decide to open up some more stores. You decide to do this in the town of Scatterville, where the houses of all residents are literally scattered around the village.
 *
 * Of course, you want to reach as many people with your donuts as possible. Since you want to place k new stores in Scatterville, you decide to divide the houses in k “clusters”, and each cluster will get its own store. Every house will belong to exactly one store cluster.
 *
 * Given the coordinates of the houses in Scatterville, and how many stores you will open, can you calculate where the new stores should be placed?
 *
 * Example
 * Consider the following example. Here, three stores will be opened in Scatterville. The first and second clusters contain four houses each, while the third cluster only contains two houses. The stores will be placed at the average x and y coordinate of these clusters.
 */
public class PlanningStoreLocations {
    /**
     * @param n the number of houses
     * @param k the number of stores to place
     * @param houses the houses, indexed 0 to n (with ids 0 to n)
     * @return the places to put the stores.
     */
    public static Set<Store> donutTime(int n, int k, List<House> houses) {
        int m = n * (n - 1) / 2;
        List<Distance> distances = new ArrayList<>(m);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                distances.add(new Distance(houses.get(i), houses.get(j)));
            }
        }
        UnionFindHouses unionFind = new UnionFindHouses(houses);

        // The number of edges in the clustering at the moment
        int edgesAdded = 0;
        // The number of edges that needs to be in the clustering (n-k)
        int edgesToAdd = n-k;

        // Sort the edges (Distances) on increasing distances
        PriorityQueue<Distance> pq = new PriorityQueue<>(Comparator.comparingLong(d -> d.distance));
        for (Distance d : distances) pq.offer(d);

        // While not all clusters have been created
        while (edgesAdded < edgesToAdd) {
            // Add the edge (Greedy.Distance) if it does not create a cycle (is not in the partial MST)
            Distance currDist = pq.poll();
            if (currDist == null) throw new NullPointerException();
            if (unionFind.join(currDist.a, currDist.b)) edgesAdded++;
        }

        // Create the stores based on the produced clusters
        Set<Store> stores = new HashSet<>();
        for (List<House> cluster : unionFind.clusters()) {
            double x = 0.0, y = 0.0;
            for (House house : cluster) {
                x += house.x;
                y += house.y;
            }
            stores.add(new Store(x / cluster.size(), y / cluster.size()));
        }
        return stores;
    }
}

/**
 * Utility classes that have been given in the Library
 */

class House {

    int id, x, y;

    public House(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        House house = (House) o;
        return id == house.id && x == house.x && y == house.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y);
    }
}

class Distance {

    House a, b;

    long distance;

    public Distance(House a, House b) {
        this.a = a;
        this.b = b;
        // Square Euclidean distance, to avoid floating-point errors
        this.distance = (long) (a.x - b.x) * (a.x - b.x) + (long) (a.y - b.y) * (a.y - b.y);
    }
}

class UnionFindHouses {

    private final List<House> houses;

    private final int[] parent;

    private final int[] rank;

    public UnionFindHouses(List<House> houses) {
        this.houses = houses;
        int n = houses.size();
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    /**
     * Joins two disjoint sets together, if they are not already joined.
     *
     * @return false if x and y are in same set, true if the sets of x and y are now joined.
     */
    boolean join(House x, House y) {
        int xrt = find(x.id);
        int yrt = find(y.id);
        if (rank[xrt] > rank[yrt])
            parent[yrt] = xrt;
        else if (rank[xrt] < rank[yrt])
            parent[xrt] = yrt;
        else if (xrt != yrt)
            rank[parent[yrt] = xrt]++;
        return xrt != yrt;
    }

    /**
     * @return The house that is indicated as the "root" of the set of house h.
     */
    House find(House h) {
        return houses.get(find(h.id));
    }

    private int find(int x) {
        return parent[x] == x ? x : (parent[x] = find(parent[x]));
    }

    /**
     * @return All clusters of houses
     */
    Collection<List<House>> clusters() {
        Map<Integer, List<House>> map = new HashMap<>();
        for (int i = 0; i < parent.length; i++) {
            int root = find(i);
            if (!map.containsKey(root))
                map.put(root, new ArrayList<>());
            map.get(root).add(houses.get(i));
        }
        return map.values();
    }
}

class Store {

    double x, y;

    public Store(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Store store = (Store) o;
        return Math.abs(store.x - x) <= 1e-4 && Math.abs(store.y - y) <= 1e-4;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.floor(x), Math.floor(y));
    }

    @Override
    public String toString() {
        return "Greedy.Store{" + "x=" + x + ", y=" + y + '}';
    }
}
