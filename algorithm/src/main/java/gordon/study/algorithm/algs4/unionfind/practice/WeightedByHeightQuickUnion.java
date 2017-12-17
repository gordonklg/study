package gordon.study.algorithm.algs4.unionfind.practice;

import java.util.Scanner;

public class WeightedByHeightQuickUnion {

    private int[] id;

    private int[] height;

    private int count;

    public WeightedByHeightQuickUnion(int size) {
        this.count = size;
        this.id = new int[size];
        this.height = new int[size];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
            height[i] = 1;
        }
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) {
            return;
        }
        if (height[pRoot] > height[qRoot]) {
            id[qRoot] = pRoot;
        } else {
            id[pRoot] = qRoot;
            if (height[pRoot] == height[qRoot]) {
                height[qRoot]++;
            }
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
        WeightedByHeightQuickUnion instance = new WeightedByHeightQuickUnion(n);
        while (scanner.hasNext()) {
            int p = scanner.nextInt();
            int q = scanner.nextInt();
            instance.union(p, q);
        }
        System.out.println(instance.count() + " components");
        scanner.close();
    }
}
