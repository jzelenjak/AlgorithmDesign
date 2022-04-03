package Greedy;

public class Job implements Comparable<Job> {
    int start;
    int end;

public Job(int start, int end) {
    this.start = start;
    this.end = end;
    }

public int compareTo(Job other) {
    return Integer.compare(this.start, other.start);
    }

public String toString() {
    return "[" + this.start + "," + this.end + "]";
    }
}
