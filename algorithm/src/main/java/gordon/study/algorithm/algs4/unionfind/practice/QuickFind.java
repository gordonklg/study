package gordon.study.algorithm.algs4.unionfind.practice;

import java.util.Scanner;

public class QuickFind {

    private int[] id;

    private int count;

    public QuickFind(int size) {
        this.count = size;
        this.id = new int[size];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
    }

    public void union(int p, int q) {
        if (!isConnected(p, q)) {
            int pId = id[p];
            for (int i = 0; i < id.length; i++) {
                if (id[i] == pId) {
                    id[i] = id[q];
                }
            }
            count--;
        }
    }

    public boolean isConnected(int p, int q) {
        return id[p] == id[q];
    }

    public int find(int p) {
        return id[p];
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream("algs4/unionfind/mediumUF.txt"));
        int n = scanner.nextInt();
        QuickFind instance = new QuickFind(n);
        while (scanner.hasNext()) {
            int p = scanner.nextInt();
            int q = scanner.nextInt();
            instance.union(p, q);
        }
        System.out.println(instance.count() + " components");
        scanner.close();
    }
}
