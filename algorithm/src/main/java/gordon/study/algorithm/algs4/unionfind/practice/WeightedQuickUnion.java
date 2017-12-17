package gordon.study.algorithm.algs4.unionfind.practice;

import java.util.Scanner;

public class WeightedQuickUnion {

    private int[] id;

    private int[] size;

    private int count;

    public WeightedQuickUnion(int totalSize) {
        this.count = totalSize;
        this.id = new int[totalSize];
        this.size = new int[totalSize];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) {
            return;
        }
        if (size[pRoot] > size[qRoot]) {
            id[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        } else {
            id[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        }
        count--;
    }

    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        while (id[p] != p) {
            p = id[p];
        }
        return p;
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream("algs4/unionfind/mediumUF.txt"));
        int n = scanner.nextInt();
        WeightedQuickUnion instance = new WeightedQuickUnion(n);
        while (scanner.hasNext()) {
            int p = scanner.nextInt();
            int q = scanner.nextInt();
            instance.union(p, q);
        }
        System.out.println(instance.count() + " components");
        scanner.close();
    }
}
