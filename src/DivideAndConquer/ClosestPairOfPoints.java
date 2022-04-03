package DivideAndConquer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClosestPairOfPoints {
    /**
     * Takes a list of points and returns the distance between the closest pair.
     * This is done with divide and conquer.
     *
     * @param points - list of points that need to be considered.
     * @return smallest pair-wise distance between points.
     */
    public static double closestPair(List<Point> points) {
        // Edge case
        if (points.size() < 2) return Double.MAX_VALUE;

        // Create lists Px and Py with points sorted by x and y coordinates respectively
        List<Point> Px = new ArrayList<>();
        List<Point> Py = new ArrayList<>();

        for (Point p : points) {
            Px.add(p);
            Py.add(p);
        }
        sortByX(Px);
        sortByY(Py);

        // Return the closest pair of points
        return closestPair(Px, Py);
    }

    public static double closestPair(List<Point> Px, List<Point> Py) {
        // Base case - apply brute-force
        if (Px.size() <= 3) return bruteForce(Px);

        int m = Px.size() / 2;                      // median index
        double median = Px.get(m).x;                // median value (x-coordinate)
        List<Point> Lx = Px.subList(0, m);          // left subarray sorted by x
        List<Point> Ly = new ArrayList<>();         // left subarray sorted by y
        List<Point> Rx = Px.subList(m, Px.size());  // right subarray sorted by x
        List<Point> Ry = new ArrayList<>();         // right subarray sorted by y

        // Place the points into Ly and Ry (sorted by y) according to the x coord
        for (Point p : Py) {
            if (p.x < median) Ly.add(p);
            else Ry.add(p);
        }

        // Solve the subproblems recursively. Get the min distance found in both sides
        double delta = Math.min(closestPair(Lx, Ly), closestPair(Rx, Ry));

        // The strip of points sorted by y that are within delta to the median (x)
        List<Point> midStrip = new ArrayList<>();
        for (Point p : Py) {
            if (Math.abs(p.x - median) < delta) midStrip.add(p);
        }

        // Now interate over all the points (except the last) that are in the mid strip
        for (int i = 0; i < midStrip.size() - 1; ++i) {
            Point p = midStrip.get(i);

            // Take the next 7 (or 11) points and check the distance between the current point and that point
            int j = i+1;
            int pointsLeft= 7;
            while (j < midStrip.size() && pointsLeft > 0) {
                double dist = distance(p, midStrip.get(j));
                if (dist < delta) delta = dist;
                --pointsLeft;
                ++j;
            }
        }
        return delta;
    }

    /**
     * Takes two points and computes the euclidean distance between the two points.
     *
     * @param point1 - first point.
     * @param point2 - second point.
     * @return euclidean distance between the two points.
     * @see <a href="https://en.wikipedia.org/wiki/Euclidean_distance">https://en.wikipedia.org/wiki/Euclidean_distance</a>
     */
    public static double distance(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2.0D) + Math.pow(point1.y - point2.y, 2.0D));
    }

    /**
     * Takes a list of points and sorts it on x (ascending).
     *
     * @param points - points that need to be sorted.
     */
    public static void sortByX(List<Point> points) {
        points.sort(Comparator.comparingDouble(point -> point.x));
    }

    /**
     * Takes a list of points and sorts it on y (ascending) .
     *
     * @param points - points that need to be sorted.
     */
    public static void sortByY(List<Point> points) {
        points.sort(Comparator.comparingDouble(point -> point.y));
    }

    /**
     * Takes a list of points and returns the distance between the closest pair.
     * This is done by brute forcing.
     *
     * @param points - list of points that need to be considered.
     * @return smallest pair-wise distance between points.
     */
    public static double bruteForce(List<Point> points) {
        int size = points.size();
        if (size <= 1)
            return Double.POSITIVE_INFINITY;
        double bestDist = Double.POSITIVE_INFINITY;
        for (int i = 0; i < size - 1; i++) {
            Point point1 = points.get(i);
            for (int j = i + 1; j < size; j++) {
                Point point2 = points.get(j);
                double distance = distance(point1, point2);
                if (distance < bestDist)
                    bestDist = distance;
            }
        }
        return bestDist;
    }


    /**
     * My initial version that cost me a lot of efforts. I tried to do in the way described in the book
     */
    public static double closestPairBad(List<Point> points) {
        if (points.size() <= 1) return Double.POSITIVE_INFINITY;
        List<Point> Px = new ArrayList<>(points.size()); // Points ordered by x
        List<Point> Py = new ArrayList<>(points.size()); // Points ordered by y

        for (Point p : points) {
            Point point = new IPoint(p.x, p.y);
            Px.add(point);
            Py.add(point);
        }

        // Sort points by x and by y
        sortByX(Px);
        sortByY(Py);

        // Create a mappping of a point to its indices in both lists (Px and Py)
        for (int i = 0; i < points.size(); ++i) {
            ((IPoint) Px.get(i)).px = i;
            ((IPoint) Py.get(i)).py = i;
        }

        return closestPairBad(Px, Py);
    }


    public static double closestPairBad(List<Point> Px, List<Point> Py) {
        int n = Px.size();

        // Base case
        if (n <= 3) {
            if (n <= 1) return Double.POSITIVE_INFINITY;
            double d1 = distance(Px.get(0), Px.get(1));
            if (n == 2) return d1;

            double d2 = distance(Px.get(0), Px.get(2));
            double d3 = distance(Px.get(1), Px.get(2));

            return Math.min(d1, Math.min(d2, d3));
        }

        // Divide...
        int m = Px.size() / 2;                // Middle index
        double median = Px.get(m).x;          // X-coordinarte of the median
        List<Point> Qx = new ArrayList<>(m);  // Points to the left, sorted by X
        List<Point> Qy = new ArrayList<>(m);  // Points to the left, sorted by Y
        List<Point> Rx = new ArrayList<>(n-m);// Points to the right, sorted by X
        List<Point> Ry = new ArrayList<>(n-m);// Points to the right, sorted by Y

        // Split the points into Qx and Rx
        for (int i = 0; i < m; ++i) {
            IPoint p = (IPoint)Px.get(i);
            Qx.add(new IPoint(p.x, p.y, i));

            p = (IPoint)Px.get(i + m);
            Rx.add(new IPoint(p.x, p.y, i));
        }
        if ((n & 1) == 1) {
            IPoint p = (IPoint)Px.get(n-1);
            Rx.add(new IPoint(p.x, p.y, n - 1 - m));
        }

        // Add points into Qy and Ry
        for (int i = 0; i < n; ++i) {
            IPoint point = (IPoint)Py.get(i);

            if (point.px < m) {
                IPoint p = (IPoint)Qx.get(point.px);
                p.py = Qy.size();
                Qy.add(p);
            } else {
                IPoint p = (IPoint)Rx.get(point.px - m);
                p.py = Ry.size();
                Ry.add(p);
            }
        }

        double delta = Math.min(closestPairBad(Qx,Qy), closestPairBad(Rx,Ry));

        // ... and Conquer
        List<Point> Sy = new ArrayList<>();

        // Find all the points with the distance to L (median) <= delta
        for (int i = 0; i < n; ++i) {
            IPoint p = (IPoint)Py.get(i);
            if (Math.abs(p.x - median) < delta) Sy.add(p);
        }


        // Check the points in Sy for the min distance
        for (int i = 0; i < Sy.size() - 1; ++i) {
            IPoint p = (IPoint)Sy.get(i);
            int j = i + 1;

            int stop = Math.min(j + 7, Sy.size());
            while (j < stop) {
                double dist = distance(p, Sy.get(j));
                if (dist < delta) delta = dist;
                ++j;
            }
        }
        return delta;
    }
}

/**
 * Class representing a 2D point.
 */
class Point {

    public double x;

    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

/**
 * Indexed point
 */
class IPoint extends Point {
    int px; // The index of the point in the list ordered by x
    int py; // The index of the point in the list ordered by y

    public IPoint(double x, double y) {
        super(x,y);
    }

    public IPoint(double x, double y, int px) {
        super(x, y);
        this.px = px;
    }

    public String toString() {
        return "" + this.x + "," + this.y;
    }
}
