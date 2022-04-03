package DivideAndConquer;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement the decisionTree method, which takes an integer d and a list of samples and returns an Integer. The list of samples is a list of DivideAndConquer.Pair where the first element is an Array of 1 or 0 values representing the sample and the second element is an Integer either zero or one, which represents the label of the sample. The integer d represents the number of allowed internal node layers in a decision tree. The output will be highest amount of correctly classified samples a decision tree with d internal layers can have on the given list of samples.
 *
 * Your task is to report the optimal result for a binary classification tree. A binary classification tree is a binary tree where each leaf is assigned a label (which is either zero or one) and each internal node is assigned an index. Given an array of 0-1 values, we may use the classification tree to classify it as follows. Start with the root node and repeat the following recursive procedure: If the node is a leaf node, return the label of the node. Otherwise, recurse on the left child node if the index-th element of A is zero, otherwise recurse on the right node.
 */
public class OptimalDecisionTrees {
    public static int decisionTree(int d, List<Pair<Integer[], Integer>> samples) {
        // Base case: G(0, samples) = max(|samples0|, |samples1|) (majority vote)
        if (d == 0) return majorityVote(samples);

        // General case: G(d,samples) = max{G(d-1,samples_{-i}) + G(d-1,samples_{i})} for i in #features
        int maxCorrect = -1;
        int features = samples.get(0).getL().length;
        for (int i = 0; i < features; ++i) {
            List<Pair<Integer[], Integer>> D_i_0 = new ArrayList<>();
            List<Pair<Integer[], Integer>> D_i_1 = new ArrayList<>();

            for (Pair<Integer[], Integer> pair : samples) {
                if (pair.getL()[i] == 0) D_i_0.add(pair);
                else D_i_1.add(pair);
            }

            // Not a successful split
            if (D_i_0.size() == 0 || D_i_1.size() == 0) continue;

            int correct = decisionTree(d-1, D_i_0) + decisionTree(d-1, D_i_1);
            if (correct > maxCorrect) maxCorrect = correct;
        }
        // No successful splits => majority vote
        if (maxCorrect == -1) return majorityVote(samples);
        return maxCorrect;
    }

    private static int majorityVote(List<Pair<Integer[], Integer>> samples) {
        int zeros = 0;
        int ones = 0;

        for (Pair<Integer[], Integer> pair : samples) {
            if (pair.getR() == 0) zeros++;
            else ones++;
        }
        // Note: we are looking for the highest amount of correctly classified samples
        //       This will be the size of the majority "group" (since it will be the one that is classified correctly)
        return Math.max(zeros, ones);
    }
}

class Pair<L, R> {

    private L l;

    private R r;

    /**
     * Constructor
     *
     * @param l left element
     * @param r right element
     */
    public Pair(L l, R r) {
        this.l = l;
        this.r = r;
    }

    /**
     * @return the left element
     */
    public L getL() {
        return l;
    }

    /**
     * @return the right element
     */
    public R getR() {
        return r;
    }

    /**
     * @param l left element
     */
    public void setL(L l) {
        this.l = l;
    }

    /**
     * @param r right element
     */
    public void setR(R r) {
        this.r = r;
    }
}
