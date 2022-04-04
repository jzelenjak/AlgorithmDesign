package NetworkFlow;

public class Edge {
    protected int lower;

    protected int capacity;

    protected int flow;

    protected Node from;

    protected Node to;

    protected Edge backwards;

    private Edge(Edge e) {
        this.lower = 0;
        this.flow = e.getCapacity();
        this.capacity = e.getCapacity();
        this.from = e.getTo();
        this.to = e.getFrom();
        this.backwards = e;
    }

    protected Edge(int lower, int capacity, Node from, Node to) {
        this.lower = lower;
        this.capacity = capacity;
        this.from = from;
        this.to = to;
        this.flow = 0;
        this.backwards = new Edge(this);
    }

    public void augmentFlow(int add) {
        assert (flow + add <= capacity);
        flow += add;
        backwards.setFlow(getResidual());
    }

    public Edge getBackwards() {
        return backwards;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFlow() {
        return flow;
    }

    public Node getFrom() {
        return from;
    }

    public int getResidual() {
        return capacity - flow;
    }

    public Node getTo() {
        return to;
    }

    private void setFlow(int f) {
        assert (f <= capacity);
        this.flow = f;
    }

    public boolean equals(Object other) {
        if (other instanceof Edge) {
            Edge that = (Edge) other;
            return this.capacity == that.capacity && this.flow == that.flow && this.from.getId() == that.getFrom().getId() && this.to.getId() == that.getTo().getId();
        }
        return false;
    }
}
