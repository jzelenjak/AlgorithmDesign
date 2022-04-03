package Greedy;

public class UnionFind {
    int[] size;
    int[] set;
    int components;

    public UnionFind(int n) {
        this.size = new int[n];
        this.set = new int[n];
        this.components = n;

        for (int i = 0; i < n; i++) set[i] = i;
    }

    public int find(int x) {
        if (set[x] == x) return x;

        int leader = x;
        while (set[leader] != leader) leader = set[leader];

        while (x != leader) {
            int parent = set[x];
            set[x] = leader;
            x = parent;
        }

        return leader;
    }

    public boolean union(int u, int v) {
        int root1 = find(u);
        int root2 = find(v);

        if (root1 == root2) return false;

        if (size[root1] < size[root2]) {
            size[root2] += size[root1];
            set[root1] = root2;
        } else {
            size[root1] += size[root2];
            set[root2] = root1;
        }
        --this.components;
        return true;
    }
}
