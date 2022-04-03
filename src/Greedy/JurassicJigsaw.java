package Greedy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Problem taken from DAPC 2018
 */
public class JurassicJigsaw {
    public static void run(InputStream in, PrintStream out) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(in)));
        new JurassicJigsaw().solve(sc, out);
        sc.close();
    }

    public void solve(Scanner in, PrintStream out) {
        int n = in.nextInt();       // the total size
        int k = in.nextInt();       // the length of each of the DNA strings
        in.nextLine();              // skip the newline character

        // Create an array of DNA strings
        DNA[] dnas = new DNA[n];
        for (int i = 0; i < n; i++) dnas[i] = new DNA(in.nextLine(), i);


        // Create the pq of unlikelinesses, i.e "distances" between 2 different DNA strings
        PriorityQueue<Unlikeliness> pq = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                pq.offer(new Unlikeliness(dnas[i], dnas[j]));
            }
        }

        // Prepare the variables for Kruskal MST algorithm
        UnionFind uf = new UnionFind(n);                // Union Find
        int toAdd = n - 1;                              // The size of the tree
        List<Unlikeliness> added = new ArrayList<>(n-1);// The "edges" of the tree
        int minUnlikeliness = 0;                        // Minimal unlikeliness of the tree

        // Perform Kruskal to build MST (minimal evolutionary tree)
        while (added.size() < toAdd) {
            // Pull the cheapest "unlikeliness" (edge)
            Unlikeliness u = pq.poll();

            // Merge if possible. If not, continue
            if (u == null) throw new NullPointerException();
            if (!uf.union(u.dna1.id, u.dna2.id)) continue;

            // Add the "unlikeliness" to the tree and update the minimal unlikeliness
            minUnlikeliness += u.distance;
            added.add(u);
        }

        // Print the solutions
        out.println(minUnlikeliness);
        for (Unlikeliness unlikeliness : added) {
            out.println(unlikeliness.dna1.id + " " + unlikeliness.dna2.id);
        }
    }
}

class DNA {
    String dna;
    int id;

    public DNA(String dna, int id) {
        this.dna = dna;
        this.id = id;
    }

    public static int distance(DNA dna1, DNA dna2) {
        // If lengths are different, then we could treat missing characters as
        //  different characters, or just throw an exception,
        //  depending on the requirements
        // Since all DNA strings are assumed to be unique in this exercise,
        //  I don't consider that case here.

        int diff = 0;
        for (int i = 0; i < dna1.dna.length(); i++) {
            if (dna1.dna.charAt(i) != dna2.dna.charAt(i)) diff++;
        }
        return diff;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof DNA)) return false;

        DNA that = (DNA) other;

        return this.dna.equals(that.dna) && this.id == that.id;
    }
}

class Unlikeliness implements Comparable<Unlikeliness> {
    int distance;
    DNA dna1;
    DNA dna2;

    public Unlikeliness(DNA dna1, DNA dna2) {
        assert dna1.id < dna2.id;

        this.dna1 = dna1;
        this.dna2 = dna2;
        this.distance = DNA.distance(this.dna1, this.dna2);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;

        if (!(other instanceof Unlikeliness)) return false;

        Unlikeliness that = (Unlikeliness) other;

        return this.dna1.equals(that.dna1) && this.dna2.equals(that.dna2)
            && this.distance == that.distance;

    }

    @Override
    public int compareTo(Unlikeliness other) {
        return Integer.compare(this.distance, other.distance);
    }

}
