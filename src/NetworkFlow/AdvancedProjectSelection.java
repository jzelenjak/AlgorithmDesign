package NetworkFlow;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Led by Oliver Stone the camel club has played a part in many important events over the years. Despite Oliver’s insistence on keeping a low profile, the club has recently been discovered by Sean & Michelle who have managed to convince Oliver to start taking cases with them. The camel club now features a large variety in skills among its members and thus when new cases come in, they need to closely assess who takes on what jobs.
 *
 * Every member of the club has a certain set of skills and every job requires a certain set of skills. For instance Caleb is a literature expert, whereas Oliver is an investigator and literature expert. Their latest job which involved some literature research was done by Caleb as a result, whereas Oliver tackled the job before that required both investigation and literature research. Every member still has other obligations however (Caleb still wants to visit the library often for instance), so every member has a limited number of hours they can work on jobs. Furthermore Sean has worked hard to ensure that every job has a clear description of how many hours a certain job will take.
 *
 * To help them with the future assignment of jobs to individual camel club members, you have been asked to devise an algorithm that computes what jobs should be attributed to what person such that the camel club can do the largest number of cases. As the members of the camel club also have regular employment, they can only work on one job.
 *
 * Now that Sean & Michelle are involved, the camel club can finally leave their regular employment behind them and focus full time on doing the different jobs. Every member still has other obligations however (Caleb still wants to visit the library often for instance), so every member has a limited number of hours they can work on jobs. Furthermore Sean has worked hard to ensure that every job has a clear description of how many hours a certain job will take.
 *
 * Now that every member can potentially take on multiple jobs, we ask ourselves a different question. We now ask: is it possible for the camel club to complete all jobs?
 *
 * The first line contains the amount of members,, n and the amount of jobs, m. These are positive integers >=1. Following this, there is a line for each member. Each of these lines has a single word (the name of the member), a number representing the amount of time this member has available t_i >= 0, followed by a positive integer s_i followed by that number of single words (the names of the skills the member has). After that there is a line for each job. Again each of these lines begin with a single word description of the job, a number indicating how much time is required for this job p_i >= 0, and then have a number q_j >= 0 followed by that number of single words (the names of the skills the jobs requires).
 *
 * An example is input:
 * ```
 * 2 3
 * Oliver 5 2 investigation interviewing
 * Caleb 8 2 interviewing lit
 * hire_member 1 1 interviewing
 * interview_author 3 2 interviewing lit
 * solve_crime 4 1 investigation
 * ``
 * Which should output: `true`
 */
public class AdvancedProjectSelection {
    public boolean solve(InputStream in) {
        HashMap<String, Integer> memberToId = new HashMap<>();
        Scanner sc = new Scanner(in);
        int n = sc.nextInt();                                   // Number of members
        int m = sc.nextInt();                                   // Number of jobs
        sc.nextLine();                                          // Discard the newline character

        // Create a list of nodes, source and sink
        List<Node> nodes = new ArrayList<>();
        memberToId.put("source", 0);
        Node source = new Node(0);
        memberToId.put("sink", 1 + n + m);
        Node sink = new Node(1 + n + m);
        nodes.add(source);

        // Maps nodes to the skills that they have
        Map<Node, Set<String>> memberSkills = new HashMap<>();

        // Create the member nodes
        for (int k = 1; k <= n; ++k) {
            memberToId.put(sc.next(), k);
            Node memberNode = new Node(k);
            nodes.add(memberNode);
            source.addEdge(memberNode, sc.nextInt());       // Add "supply" (the number of hours this member can work)

            // Get the skills for this member (node)
            int numSkills = sc.nextInt();
            Set<String> skillSet = new HashSet<>();
            for (int i = 0; i < numSkills; ++i) skillSet.add(sc.next());
            memberSkills.put(memberNode, skillSet);

        }

        // Create the job nodes
        int totalHours = 0;
        for (int k = 1; k <= m; ++k) {
            memberToId.put(sc.next(), n + k);
            Node jobNode = new Node(n + k);
            nodes.add(jobNode);
            int hours = sc.nextInt();
            jobNode.addEdge(sink, hours);            // Add demand (the number of hours that the job requires)
            totalHours += hours;

            // Get the required skills for this job (node)
            int numRequiredSkills = sc.nextInt();
            Set<String> requiredSkills = new HashSet<>();
            for (int i = 0; i < numRequiredSkills; ++i) requiredSkills.add(sc.next());

            // Create the edges between a member node and the job node if the member has all the required skills for this job
            for (Node memberNode : memberSkills.keySet()) {
                if (memberSkills.get(memberNode).containsAll(requiredSkills)) memberNode.addEdge(jobNode, Integer.MAX_VALUE);
            }
        }
        nodes.add(sink);
        sc.close();

        // Run Ford-Fulkerson and get the answer
        Graph g = new Graph(nodes, source, sink);
        MaxFlow.maximizeFlow(g);
        int flow = 0;
        for (Edge e : g.getSource().getEdges()) flow += e.getFlow();
        return flow == totalHours;  // The flow must be equal to the total number of hours in order for all the jobs to be completed
    }
}
