package Greedy;

import java.util.PriorityQueue;

public class BinaryHuffman {
    /**
     * You should implement this method.
     *
     * @param node A Node in the Huffman encoding tree
     * @return the encoded string representing the character in this node.
     */
    public static String encode(BinHuffmanNode node) {
        StringBuilder sb = new StringBuilder();

        // Go all the way up to the root
        while (node.getParent() != null) {
            // If the node is the left child of its parent, then the bit is 0
            if (node.getParent().getLeftChild() == node) {
                sb.insert(0, 0);
            } // Else if the node is the right child of its parent, then the bit is 1
            else {
                sb.insert(0, 1);
            }
            node = node.getParent();
        }
        return sb.toString();
    }

    /**
     * You should implement this method.
     *
     * @param n           the number of characters that need to be encoded.
     * @param characters  The characters c_1 through c_n. Note you should use only characters[1] up to and including characters[n]!
     * @param frequencies The frequencies f_1 through f_n. Note you should use only frequencies[1] up to and including frequencies[n]!
     * @return The rootnode of an optimal Huffman tree that represents the encoding of the characters given.
     */
    public static BinHuffmanNode buildHuffman(int n, char[] characters, double[] frequencies) {
        PriorityQueue<BinHuffmanNode> pq = new PriorityQueue<>();

        for (int i = 1; i <= n; ++i) {
            pq.offer(new BinHuffmanNode(characters[i], frequencies[i]));
        }

        while (pq.size() > 1) {
            // Take the two nodes with the lowest frequency
            BinHuffmanNode node1 = pq.poll();
            BinHuffmanNode node2 = pq.poll();

            assert node2 != null;

            // Make them children of a common parent (aka merge),
            //    whose frequency is the sum of their frequencies
            BinHuffmanNode parent = new BinHuffmanNode('$', node1.frequency + node2.frequency);
            node1.setParent(parent);
            node2.setParent(parent);
            parent.setLeftChild(node1);
            parent.setRightChild(node2);

            // Add the parent as a new node to be considered later
            pq.offer(parent);
        }
        return pq.poll();
    }
}

class BinHuffmanNode implements Comparable<BinHuffmanNode> {

    char symbol;

    double frequency;

    BinHuffmanNode parent;

    BinHuffmanNode leftChild;

    BinHuffmanNode rightChild;

    public BinHuffmanNode(char symbol, double frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
    }

    public BinHuffmanNode(char symbol, double frequency, BinHuffmanNode parent) {
        this(symbol, frequency);
        this.parent = parent;
    }

    public BinHuffmanNode(char symbol, double frequency, BinHuffmanNode leftChild, BinHuffmanNode rightChild) {
        this(symbol, frequency);
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public char getSymbol() {
        return symbol;
    }

    public double getFrequency() {
        return frequency;
    }

    public BinHuffmanNode getParent() {
        return parent;
    }

    public void setParent(BinHuffmanNode parent) {
        this.parent = parent;
    }

    public BinHuffmanNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BinHuffmanNode leftChild) {
        this.leftChild = leftChild;
    }

    public BinHuffmanNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(BinHuffmanNode rightChild) {
        this.rightChild = rightChild;
    }

    @Override
    public int compareTo(BinHuffmanNode other) {
        return Double.compare(this.frequency, other.frequency);
    }
}
