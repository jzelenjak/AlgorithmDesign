package Greedy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * The Benelux Artistic Pottery Consortium is preparing for an exhibit of its most prized urns and vases at a gallery in Nijmegen. Due to the sheer number of vases to be put on display the gallery has trouble finding a pedestal of the right size for every single vase. They have pedestals available that can either be placed normally or upside down and can be characterised by the diameter of their top and bottom surface. Moreover, the diameter of the top and bottom varies by at most one unit length.
 *
 * For artistic reasons, it is important that the diameter of the base of a vase matches the diameter of the surface of the pedestal it is placed on. You have been asked to find a way to place all the vases on available pedestals. In order to make this work, you might need to turn some of the pedestals upside down. For example, Figure 1 shows a possible assignment of pedestals to vases for sample input 1. Assist the gallery by writing a program to compute such an assignment.
 *
 * Input
 * The first line contains two integers 0≤p,v≤104 the number of pedestals and the number of vases.
 * Then follow p lines, the i-th of which contains two integers 1≤ai,bi≤104 denoting the diameters of the different sides of pedestal i. It is given that |ai−bi|≤1.
 * Then follows a single line containing v integers 1≤c1,…,cv≤104, where ci denotes the diameter of vase i.
 *
 * Output
 * Output v distinct integers 1≤x1,…,xv≤p such that vase i can stand on pedestal xi, or print impossible if no assignment of vases to pedestals exists.
 * If there are multiple possible solutions, you may output any one of them.
 */
public class HistoricExhibition {
    public static void run(InputStream in, PrintStream out) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(in)));
        new HistoricExhibition().solve(sc, out);
        sc.close();
    }

    public void solve(Scanner sc, PrintStream out) {
        int p = sc.nextInt();   // number of pedestrials
        int v = sc.nextInt();   // number of vases

        // A PQ to store pedestrials in the descending order of diameters
        PriorityQueue<Pedestrial> ppq = new PriorityQueue<>();
        for (int i = 1; i <= p; i++) ppq.offer(new Pedestrial(sc.nextInt(), sc.nextInt(), i));

        // A PQ to store vases in the descending order of diameters
        PriorityQueue<Vase> vpq = new PriorityQueue<>();
        for (int i = 1; i <= v; i++) vpq.offer(new Vase(sc.nextInt(), i));

        // Store the pedestrial ids for the vases (for the result)
        int[] pedestrialIds = new int[v];
        int vasesMatched = 0;

        // Iterate over all the vases
        while (!vpq.isEmpty()) {
            Vase vase = vpq.poll();

            // Iterate over the pedestrial until you find a match
            while(!ppq.isEmpty()) {
                Pedestrial pedestrial = ppq.poll();
                if (pedestrial.d1 == vase.d || pedestrial.d2 == vase.d) {
                    pedestrialIds[vase.id-1] = pedestrial.id;
                    ++vasesMatched;
                    break;
                }
            }
            if (ppq.isEmpty()) break;
        }
        // If not all vases have a match, then it is impossible to assign
        if (vasesMatched != v) out.println("impossible");
            // Else give the resulting assignment
        else for (int id : pedestrialIds) out.println(id);
    }
}

class Pedestrial implements Comparable<Pedestrial> {
    int d1; // larger or the same
    int d2; // smaller or the same
    int id; // the id of the pedestrial

    public Pedestrial(int d1, int d2, int id) {
        this.d1 = Math.max(d1,d2);
        this.d2 = Math.min(d1,d2);
        this.id = id;
    }

    @Override
    public int compareTo(Pedestrial p) {
        int comp = Integer.compare(p.d1, this.d1);
        return comp != 0 ? comp : Integer.compare(p.d2, this.d2);
    }

}

class Vase implements Comparable<Vase> {
    int d;
    int id;

    public Vase(int d, int id) {
        this.d = d;
        this.id = id;
    }

    @Override
    public int compareTo(Vase v) {
        return Integer.compare(v.d, this.d);
    }
}
