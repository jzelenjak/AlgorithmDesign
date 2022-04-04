package NetworkFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement a solution to the project selection problem. The input is a list of projects. Each project has a revenue, a cost and a list of required projects. Output the set of projects that return the maximum profit such that for each project selected, its required projects are also selected.
 */
public class ProjectSelection {
    /**
     * You should implement this method
     *
     * @param projects List of projects, you can ignore list.get(0)
     * @return A set of feasible projects that yield the maximum possible profit.
     */
    public static int maximumProjects(List<Project> projects) {
        // Create a list of nodes
        int n = projects.size();
        List<Node> nodes = new ArrayList<>();

        // Create the source
        Node source = new Node(0);
        nodes.add(source);

        // Add a node for every project
        for (int i = 1; i <= n; ++i) nodes.add(new Node(i));

        // Create the sink
        Node sink = new Node(n+1);
        nodes.add(sink);

        // The sum of the profits of all projects with the positive profit
        int C = 0;
        for (int i = 1; i <= n; ++i) {
            Project project = projects.get(i-1);

            int profit = project.getRevenue() - project.getCost();
            // Connect projects with positive profits to the source and projects with negative profits to the sink
            if (profit > 0) {
                source.addEdge(nodes.get(i), profit);
                C += profit;
            } else {
                nodes.get(i).addEdge(sink, -profit);
            }

            // Iterate over the requirements and create the edges that will represent those requirements
            for (Integer req : project.getRequirements()) {
                nodes.get(i).addEdge(nodes.get(req), Integer.MAX_VALUE);
            }
        }
        // Run Ford-Fulkerson and get the answer
        Graph g = new Graph(nodes, source, sink);
        // Note, that the book says c(A',B') = C - profit(A), which is the same as saying that profit(A) = C - c(A',B')
        return C - MaxFlow.maximizeFlow(g);
    }
}

class Project {

    private int id;

    private final int cost;

    private final int revenue;

    private final List<Integer> requirements;

    public Project(int revenue, int cost) {
        this.revenue = revenue;
        this.cost = cost;
        this.requirements = new ArrayList<>();
    }

    public Project(int id, int revenue, int cost) {
        this(revenue, cost);
        this.id = id;
    }

    public void addRequirement(int requirement) {
        requirements.add(requirement);
    }

    public void addRequirements(List<Integer> requirements) {
        this.requirements.addAll(requirements);
    }

    public int getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public int getRevenue() {
        return revenue;
    }

    public List<Integer> getRequirements() {
        return requirements;
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", cost=" + cost + ", revenue=" + revenue + ", requirements=" + requirements + '}' + "\n";
    }
}
