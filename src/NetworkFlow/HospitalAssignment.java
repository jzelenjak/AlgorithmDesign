package NetworkFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement a solution to the hospital assignment problem. Distribute n patients over k hospitals, where:
 * - Every patient travels a maximum provided distance to an assigned hospital.
 * - Every hospital get at most n/k patients (rounded up).
 * Is there an assignment possible satisfying these constraints?
 */
public class HospitalAssignment {
    /**
     * Checks if all patients can be assigned to the hospital given the constraints.
     *
     * @param n                 The number of patients
     * @param k                 The number of hospitals
     * @param patients          List of patient locations
     * @param hospitals         List of hospital locations
     * @param preferredDistance Preferred distance within which a patient can be assigned to a hospital.
     * @return If all patients can be assigned to at least one hospital
     */
    public static boolean isAssignmentPossible(int n, int k, List<Location> patients,
                                               List<Location> hospitals, int preferredDistance) {
        Node source = new Node(-42);
        Node sink = new Node(-69);
        List<Node> nodes = new ArrayList<>();
        nodes.add(source);

        // The max number of patients that can be assigned to one hospital
        int l = (int) Math.ceil((double) n / k);

        // Create a list of hospital nodes
        Node[] hospitalNodes = new Node[k];
        for (int i = 0; i < k; ++i) {
            Node hospital = new Node(i);
            hospitalNodes[i] = hospital;
            source.addEdge(hospital, l);
            nodes.add(hospital);
        }

        // Create a list of patient nodes
        Node[] patientNodes = new Node[n];
        for (int i = 0; i < n; ++i) {
            Node patient = new Node(i);
            patientNodes[i] = patient;
            patient.addEdge(sink, 1);
            nodes.add(patient);
        }

        nodes.add(sink);

        // For each hospital, check which patients can be assigned to it, and add the corresponding edges
        for (int i = 0; i < k; ++i) {
            for (int j = 0; j < n; ++j) {
                if (patients.get(j).distance(hospitals.get(i)) <= preferredDistance) {
                    hospitalNodes[i].addEdge(patientNodes[j], 1);
                }
            }
        }

        // Run Ford-Fulkerson and get the result
        Graph g = new Graph(nodes, source, sink);
        return MaxFlow.maximizeFlow(g) == n;
    }
}

class Location {

    private final int x;
    private final int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int distance(Location other) {
        return (int) Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
    }
}
