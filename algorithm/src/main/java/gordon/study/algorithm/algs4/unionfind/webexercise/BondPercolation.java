package gordon.study.algorithm.algs4.unionfind.webexercise;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class BondPercolation {

    private WeightedQuickUnionUF uf;

    private int size;

    private int openedBondCount;

    public BondPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.size = n;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 0; i < n; i++) {
            uf.union(n * n, i);
            uf.union(n * n + 1, n * n - n + i);
        }
    }

    public void union(int from, int to) {
        if ((from < 0) || (from >= size * size) || (to < 0) || (to > size * size)) {
            throw new IllegalArgumentException();
        }

        uf.union(from, to);
        openedBondCount++;
    }

    public boolean percolates() {
        return uf.connected(size * size, size * size + 1);
    }

    public int numberOfOpenBonds() {
        return openedBondCount;
    }

    public static void main(String[] args) {

    }

}
