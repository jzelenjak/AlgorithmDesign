package DivideAndConquer;

/**
 * Implement the findMin method, this method should use divide and conquer to find the value x for which the difference between two discrete functions is minimal.
 *
 * Take for example the linear equation f(x)=8x−240 and g(x)=6x−156, these two lines will intersect each other in the point (42,96), so the result of this algorithm should be 42.
 *
 * Take note that we are using discrete quadratic functions
 */
public class FindMinimumDifference {
    /**
     * Finds the x coordinate with the smallest distance between two linear equations, by recursively evaluating the median of the range and that integer + 1.
     * Depending on the value, a new evaluation is made with a smaller range to find the x coordinate with the smallest distance.
     *
     * @param e1   the first equation to evaluate
     * @param e2   the second equation to evaluate
     * @param low  the lower boundary of the range
     * @param high the upper boundary of the range
     * @return the x coordinate with the minimum difference between e1 and e2
     */
    public static long findMin(Equation e1, Equation e2, long low, long high) {
        return findMinDiff(e1, e2, low, high).x;
    }

    /**
     * A helper method that returns both x coordinate and the difference at this x coordinate
     * It uses recursion
     */
    private static Diff findMinDiff(Equation e1, Equation e2, long low, long high) {
        // Base case: just return the x coordinate and the difference at this coordinate
        if (high == low) return new Diff(high, Math.abs(e1.evaluate(high) - e2.evaluate(high)));

        // Find the median of the range
        long m = (low + high) / 2L;
        // Evaluate the difference at the x = median and at x = median + 1
        long diff1 = Math.abs(e1.evaluate(m) - e2.evaluate(m));
        long diff2 = Math.abs(e1.evaluate(m+1) - e2.evaluate(m+1));

        // Take the part of the range with the smallest evaluated difference at the endpoint:
        //   [low,m]         if difference at m is smaller
        //   [m+1,high]      if difference at m+1 is smaller
        //  (aka discard the part of the range where the difference is higher)
        if (diff1 < diff2) {
            Diff diffLeft = findMinDiff(e1, e2, low, m);
            // Return the smallest difference
            return (diff1 < diffLeft.diff) ? new Diff(m,diff1) : diffLeft;
        }
        else {
            Diff diffRight = findMinDiff(e1, e2, m+1, high);
            // Return the smallest difference
            return (diff2 < diffRight.diff) ? new Diff(m+1,diff2) : diffRight;
        }
    }
}

class Diff {
    long x;
    long diff;

    public Diff(long x, long diff) {
        this.x = x;
        this.diff = diff;
    }
}

abstract class Equation {

    public abstract long evaluate(long x);
}

class QuadraticEquation extends Equation {

    private final long secondPolynomial;

    private final long firstPolynomial;

    private final long constant;

    /**
     * Constructs a quadratic equation in the form of:
     * f(x) = secondPolynomial * x^2 + firstPolynomial * x + constant
     *
     * @param secondPolynomial the parameter for the second degree polynomial
     * @param firstPolynomial  the parameter for the first degree polynomial
     * @param constant         the parameter for the constant
     */
    public QuadraticEquation(long secondPolynomial, long firstPolynomial, long constant) {
        this.secondPolynomial = secondPolynomial;
        this.firstPolynomial = firstPolynomial;
        this.constant = constant;
    }

    /**
     * Evaluates the equation with the given x.
     *
     * @param x value used to evaluate
     * @return the result of the equation1
     */
    public long evaluate(long x) {
        return secondPolynomial * x * x + firstPolynomial * x + constant;
    }
}
