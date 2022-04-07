package NetworkFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * With libraries being closed all over the country thanks to Covid, the AD4LIFE (Active
 * Demonstrators for Literacy In Friends & Enemies) foundation has decided to use this period to redistribute
 * their n ≥ 1 books over the m ≥ 1 libraries in the country. Each book has exactly one author, and bi ≥ 1
 * copies available (for 1 ≤ i ≤ n). Furthermore each library has a capacity for cj ≥ 1 books (for 1 ≤ j ≤ m).
 * AD4LIFE is looking to redistribute the books in such a way that every library has at least 3 different books
 * from every author and no more than 1 copy of a single book.
 */
public class AD4LIFE {
    /**
     * You should implement this method to inform AD4LIFE about the (im)possibility of this redistribution process
     *
     * @param n the number of books
     * @param m the number of libraries
     * @param z the number of authors
     * @param a the author of a book 1 <= i < = n (ignore a[0]), represented as an integer between 1
     *     and z (inclusive)
     * @param b the number of copies we have of a book 1 <= i <= n (ignore b[0])
     * @param c the maximum number of books a library 1 <= j <= m can hold (ignore c[0])
     * @return true iff AD4LIFE can redistribute all the books
     */
    public static boolean canWeRedistribute(int n, int m, int z, int[] a, int[] b, int[] c) {
        List<Node> nodes = new ArrayList<>();
        Node source = new Node(-42, 0);
        Node sink = new Node(-69, 0);
        sink.addEdge(source, 0, Integer.MAX_VALUE / 2);
        nodes.add(source);
        nodes.add(sink);

        // Create all library nodes
        Node[] libraries = new Node[m+1];
        for (int i = 1; i <= m; ++i) {
            libraries[i] = new Node(i,0);
            source.addEdge(libraries[i], 0, c[i]);
            nodes.add(libraries[i]);
        }

        // Create all book nodes
        Node[] books = new Node[n+1];
        for (int i = 1; i <= n; ++i) {
            books[i] = new Node(i,0);
            books[i].addEdge(sink, 0, b[i]);
            nodes.add(books[i]);
        }

        // Create all library-author book nodes
        Node[][] libraryAuthor = new Node[m+1][z+1];
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= z; ++j) {
                Node libraryAuthorNode = new Node(42, 0);
                libraryAuthor[i][j] = libraryAuthorNode;
                libraries[i].addEdge(libraryAuthor[i][j], 3, Integer.MAX_VALUE);
                nodes.add(libraryAuthorNode);
            }
        }

        // Add all "connections"
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= z; ++j) {
                for (int k = 1; k <= n; ++k) {
                    if (a[k] == j) {
                        libraryAuthor[i][j].addEdge(books[k], 0, 1);
                    }
                }
            }
        }


        Graph g = new Graph(nodes, source, sink);
        return g.hasCirculation();
    }
}
