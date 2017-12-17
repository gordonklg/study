package gordon.study.algorithm.algs4.unionfind.coursera;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF uf;

    private WeightedQuickUnionUF backwash;

    private int size;

    private boolean[][] isSiteOpen;

    private int openedSiteCount;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.size = n;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.backwash = new WeightedQuickUnionUF(n * n + 1);
        this.isSiteOpen = new boolean[n][n];
    }

    public void open(int row, int col) {
        if ((row < 1) || (row > size) || (col < 1) || (col > size)) {
            throw new IllegalArgumentException();
        }

        if (isOpen(row, col)) {
            return;
        }

        isSiteOpen[row - 1][col - 1] = true;
        openedSiteCount++;

        int self = (row - 1) * size + col;
        if (row == 1) {
            uf.union(0, self);
            backwash.union(0, self);
        }
        if (row == size) {
            uf.union(size * size + 1, self);
        }

        if ((row != 1) && isOpen(row - 1, col)) {
            uf.union(self, self - size);
            backwash.union(self, self - size);
        }
        if ((row != size) && isOpen(row + 1, col)) {
            uf.union(self, self + size);
            backwash.union(self, self + size);
        }
        if ((col != 1) && isOpen(row, col - 1)) {
            uf.union(self, self - 1);
            backwash.union(self, self - 1);
        }
        if ((col != size) && isOpen(row, col + 1)) {
            uf.union(self, self + 1);
            backwash.union(self, self + 1);
        }
    }

    public boolean isOpen(int row, int col) {
        if ((row < 1) || (row > size) || (col < 1) || (col > size)) {
            throw new IllegalArgumentException();
        }

        return isSiteOpen[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        if ((row < 1) || (row > size) || (col < 1) || (col > size)) {
            throw new IllegalArgumentException();
        }

        return backwash.connected(0, (row - 1) * size + col);
    }

    public int numberOfOpenSites() {
        return openedSiteCount;
    }

    public boolean percolates() {
        return uf.connected(0, size * size + 1);
    }

    public static void main(String[] args) {

    }

}
