package NetworkFlow;

import java.util.ArrayList;
import java.util.List;

public class ResearchProject {
    /**
     * You should implement the method below. Note that you can use the graph structure below.
     *
     * @param n                            The number of students.
     * @param m                            The number of supervisors.
     * @param student_availability_mon     is an array of size (n+1) that is true iff student i is available on Monday. You should ignore student_availability_mon[0].
     * @param student_availability_tues    is an array of size (n+1) that is true iff student i is available on Tuesday. You should ignore student_availability_tues[0].
     * @param supervisor_availability_mon  is an array of size (m+1) that is true iff supervisor j is available on Monday. You should ignore supervisor_availability_mon[0].
     * @param supervisor_availability_tues is an array of size (m+1) that is true iff supervisor j is available on Tuesday. You should ignore supervisor_availability_tues[0].
     * @param selected                     is an array of size (n+1)x(m+1) that is true iff student i selected supervisor j. You should use entries selected[1][1] through selected[n][m].
     * @return true iff there is a valid allocation of students over supervisors.
     */
    public static boolean areThereGroups(int n, int m, boolean[] student_availability_mon, boolean[] student_availability_tues, boolean[] supervisor_availability_mon, boolean[] supervisor_availability_tues, boolean[][] selected) {
        // List of all nodes in NF graph
        List<Node> nodes = new ArrayList<>();
        // Create source and sink and add them to the list of nodes
        Node source = new Node(-1, 0);
        Node sink = new Node(-2, 0);
        nodes.add(source);
        nodes.add(sink);

        // Initialise arrays of nodes
        Node[] students = new Node[n+1];
        Node[] students_mon = new Node[n+1];
        Node[] students_tues = new Node[n+1];
        Node[] supervisors = new Node[m+1];
        Node[] supervisors_mon1 = new Node[m+1];
        Node[] supervisors_mon2 = new Node[m+1];
        Node[] supervisors_tues1 = new Node[m+1];
        Node[] supervisors_tues2 = new Node[m+1];

        // Create all student nodes, add the necessary edges and add these nodes to the graph
        for (int i = 1; i <= n; ++i) {
            students[i] = new Node(i, 0);
            students_mon[i] = new Node(i, 0);
            students_tues[i] = new Node(i, 0);
            source.addEdge(students[i], 1, 1);
            if (student_availability_mon[i]) students[i].addEdge(students_mon[i], 0, 1);
            if (student_availability_tues[i]) students[i].addEdge(students_tues[i], 0, 1);
            nodes.add(students[i]);
            nodes.add(students_mon[i]);
            nodes.add(students_tues[i]);
        }

        // Create all supervisor nodes, add the necessary edges and add these nodes to the graph
        for (int i = 1; i <= m; ++i) {
            supervisors[i] = new Node(i, 0);
            supervisors_mon1[i] = new Node(i, 0);
            supervisors_mon2[i] = new Node(i, 0);
            supervisors_tues1[i] = new Node(i, 0);
            supervisors_tues2[i] = new Node(i, 0);
            supervisors[i].addEdge(sink, 3, 12);
            if (supervisor_availability_mon[i]) {
                supervisors_mon1[i].addEdge(supervisors[i], 0, 5);
                supervisors_mon2[i].addEdge(supervisors[i], 0, 5);
            }
            if (supervisor_availability_tues[i]) {
                supervisors_tues1[i].addEdge(supervisors[i], 0, 5);
                supervisors_tues2[i].addEdge(supervisors[i], 0, 5);
            }
            nodes.add(supervisors[i]);
            nodes.add(supervisors_mon1[i]);
            nodes.add(supervisors_mon2[i]);
            nodes.add(supervisors_tues1[i]);
            nodes.add(supervisors_tues2[i]);
        }

        // Add the "connections" (edges between groups and supervisors)
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= m; ++j) {
                if (selected[i][j]) {
                    students_mon[i].addEdge(supervisors_mon1[j], 0, 1);
                    students_mon[i].addEdge(supervisors_mon2[j], 0, 1);
                    students_tues[i].addEdge(supervisors_tues1[j], 0, 1);
                    students_tues[i].addEdge(supervisors_tues2[j], 0, 1);
                }
            }
        }
        // Add the magic edge
        sink.addEdge(source, 0, Integer.MAX_VALUE / 2);
        return new Graph(nodes).hasCirculation();
    }
}
