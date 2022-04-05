package NetworkFlow;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Cameras on the highway are there to detect whether a car is exceeding the speed limit. This is done by recording the speed of the passing cars and taking a picture of each car which exceeds the speed limit. Of all the cars that exceed the speed limit, the picture of the car and the speed are given to the police. To be able to tell who the owner of the car is, the license plate needs to be determined. The first step in this process is image segmentation to determine which pixels in the picture belong to the license plate (foreground). The other pixels are labeled background.
 *
 * The police obviously want a reliable suggestion, so each outcome of the image segmentation is rated with a score and higher scores correspond to better segmentation. All pixels have a likelihood to belong either to the foreground or background, for pixel i, these are integers f_i and b_i respectively, where 0 <= f_i, b_i <= 10 So, for each pixel which has a higher likelihood to be in the foreground than in the background, we want to assign that pixel the label of foreground and the other way around. Furthermore, for each pair of neighboring pixels (i, j), where i is in the foreground and j is in the background, we count a penalty of 10, denoted by p_i,j. For pixel pairs where both pixels are foreground or both pixels are background, the penalty is 0. The final score for the image segmentation which gives as outcome the set of foreground pixels F and background pixels B is:
 * ∑_{i∈F}b_i+∑_{i∈B}f_i−∑_{(i,j)∈E, |A∩{i,j}| = 1 }p_ij
 *
 * Your job is now to label each pixel in the picture as belonging either to the background or the foreground and maximize the score. You can regard the pixels as a set of nodes, and all nodes are connected to their neighboring nodes by edges. Each node can have at most 4 neighbours (imagine a grid).
 *
 * Your input will be as follows. The first line contains the number of pixels, n >= 1, followed by the number of undirected edges, m >= 0. All pixels are indicated by their integer id 1<=i<=n. Then for each pixel a line follows with the pixel id, followed by the likelihood that the from pixel belongs to the foreground f_i and the likelihood that the pixel belongs to the background b_i. Then for each edge a line follows with the from and to pixel. Your algorithm should output the score of the segmentation.
 *
 * An example output is given below:
 * ```
 * 2 1
 * 1 9 1
 * 2 8 2
 * 1 2
 * ```
 * The answer should be: 3
 */
public class ImageSegmentation {
    public int solve(InputStream in) {
        Scanner sc = new Scanner(in);
        int n = sc.nextInt();                       // Number of pixels (n >= 1)
        int m = sc.nextInt();                       // Number of undirected edges (m >= 0)

        // Create nodes for each pixel
        List<Node> nodes = new ArrayList<>();
        Node source = new Node(0);
        Node sink = new Node(n + 1);
        nodes.add(source);

        // Create the nodes and connect them to the source and the sink
        for (int k = 1; k <= n; ++k) {
            int i = sc.nextInt();
            int a = sc.nextInt();
            int b = sc.nextInt();

            Node pixelNode = new Node(i);
            nodes.add(pixelNode);

            source.addEdge(pixelNode, a);
            pixelNode.addEdge(sink, b);
        }
        nodes.add(sink);

        // Add all edges
        for (int i = 0; i < m; ++i) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            nodes.get(from).addEdge(nodes.get(to), 10);
            nodes.get(to).addEdge(nodes.get(from), 10);
        }
        sc.close();

        // Run Ford-Fulkerson and get the answer
        Graph g = new Graph(nodes, source, sink);
        MaxFlow.maximizeFlow(g);
        int flow = 0;
        for (Edge e : g.getSource().getEdges()) flow += e.getFlow();
        return flow;
    }
}
