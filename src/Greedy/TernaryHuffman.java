package Greedy;

import java.util.Comparator;
import java.util.PriorityQueue;

public class TernaryHuffman {
    /**
     * You should implement this method.
     *
     * @param encrypted the encrypted message to decipher (a string of '0's, '1's, and '2's)
     * @param root the root of the Ternary Huffman tree
     * @return the unencrypted message
     */
    public static String decode(String encrypted, TerHuffmanNode root) {
        StringBuilder sb = new StringBuilder();
        TerHuffmanNode currNode = root;

        for (char c : encrypted.toCharArray()) {
            if (c == '0') currNode = currNode.getLeftChild();
            else if (c == '1') currNode = currNode.getMiddleChild();
            else if (c == '2') currNode = currNode.getRightChild();

            if (currNode.getLeftChild() == null && currNode.getMiddleChild() == null && currNode.getRightChild() == null) {
                if (currNode.getSymbol() != (char) 0) sb.append(currNode.getSymbol());
                currNode = root;
            }
        }
        return sb.toString();
    }

    /**
     * You should implement this method. Remember to look at the even/oddness of the number
     * characters!
     *
     * @param n the number of characters that need to be encoded.
     * @param characters The characters c_1 through c_n. Note you should use only characters[1] up
     *     to and including characters[n]!
     * @param frequencies The frequencies f_1 through f_n. Note you should use only frequencies[1]
     *     up to and including frequencies[n]!
     * @return The rootnode of an optimal Ternary Huffman tree that represents the encoding of the
     *     characters given.
     */
    public static TerHuffmanNode buildHuffman(int n, char[] characters, double[] frequencies) {
        PriorityQueue<TerHuffmanNode> pq = new PriorityQueue<>(
            Comparator.comparingDouble(n2 -> n2.frequency));

        for (int i = 1; i <= n; ++i) pq.offer(new TerHuffmanNode(characters[i],frequencies[i]));

        if ((n & 1) != 1) pq.offer(new TerHuffmanNode((char) 0, 0.0));

        while (pq.size() > 1) {
            TerHuffmanNode child1 = pq.poll();
            TerHuffmanNode child2 = pq.poll();
            TerHuffmanNode child3 = pq.poll();

            assert child2 != null;
            assert child3 != null;

            double parentFreq = child1.frequency + child2.frequency + child3.frequency;
            TerHuffmanNode parent = new TerHuffmanNode((char) 0, parentFreq);

            parent.setLeftChild(child1);
            parent.setMiddleChild(child2);
            parent.setRightChild(child3);

            child1.setParent(parent);
            child2.setParent(parent);
            child3.setParent(parent);

            pq.offer(parent);
        }
        return pq.poll();
    }
}

class TerHuffmanNode {

    char symbol;

    double frequency;

    TerHuffmanNode parent;

    TerHuffmanNode leftChild;

    TerHuffmanNode rightChild;

    TerHuffmanNode middleChild;

    public TerHuffmanNode(char symbol, double frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
    }

    public TerHuffmanNode(char symbol, double frequency, TerHuffmanNode parent) {
        this(symbol, frequency);
        this.parent = parent;
    }

    public TerHuffmanNode(char symbol, double frequency, TerHuffmanNode leftChild, TerHuffmanNode middleChild, TerHuffmanNode rightChild) {
        this(symbol, frequency);
        this.leftChild = leftChild;
        this.middleChild = middleChild;
        this.rightChild = rightChild;
    }

    public char getSymbol() {
        return symbol;
    }

    public double getFrequency() {
        return frequency;
    }

    public TerHuffmanNode getParent() {
        return parent;
    }

    public void setParent(TerHuffmanNode parent) {
        this.parent = parent;
    }

    public TerHuffmanNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(TerHuffmanNode leftChild) {
        this.leftChild = leftChild;
    }

    public TerHuffmanNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(TerHuffmanNode rightChild) {
        this.rightChild = rightChild;
    }

    public TerHuffmanNode getMiddleChild() {
        return middleChild;
    }

    public void setMiddleChild(TerHuffmanNode middleChild) {
        this.middleChild = middleChild;
    }
}
