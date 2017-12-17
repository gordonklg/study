package gordon.study.algorithm.algs4.unionfind.webexercise;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation3D {

    private WeightedQuickUnionUF uf;

    private WeightedQuickUnionUF backwash;

    private int size;

    private boolean[][][] isSiteOpen;

    private int openedSiteCount;

    public Percolation3D(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.size = n;
        this.uf = new WeightedQuickUnionUF(n * n * n + 2);
        this.backwash = new WeightedQuickUnionUF(n * n * n + 1);
        this.isSiteOpen = new boolean[n][n][n];
    }

    public void open(int layer, int row, int col) {
        if ((layer < 1) || (layer > size) || (row < 1) || (row > size) || (col < 1) || (col > size)) {
            throw new IllegalArgumentException();
        }

        if (isOpen(layer, row, col)) {
            return;
        }

        isSiteOpen[layer - 1][row - 1][col - 1] = true;
        openedSiteCount++;

        int self = (layer - 1) * size * size + (row - 1) * size + col;
        if (layer == 1) {
            uf.union(0, self);
            backwash.union(0, self);
        }
        if (layer == size) {
            uf.union(size * size * size + 1, self);
        }

        if ((layer != 1) && isOpen(layer - 1, row, col)) {
            uf.union(self, self - size * size);
            backwash.union(self, self - size * size);
        }
        if ((layer != size) && isOpen(layer + 1, row, col)) {
            uf.union(self, self + size * size);
            backwash.union(self, self + size * size);
        }
        if ((row != 1) && isOpen(layer, row - 1, col)) {
            uf.union(self, self - size);
            backwash.union(self, self - size);
        }
        if ((row != size) && isOpen(layer, row + 1, col)) {
            uf.union(self, self + size);
            backwash.union(self, self + size);
        }
        if ((col != 1) && isOpen(layer, row, col - 1)) {
            uf.union(self, self - 1);
            backwash.union(self, self - 1);
        }
        if ((col != size) && isOpen(layer, row, col + 1)) {
            uf.union(self, self + 1);
            backwash.union(self, self + 1);
        }
    }

    public boolean isOpen(int layer, int row, int col) {
        if ((layer < 1) || (layer > size) || (row < 1) || (row > size) || (col < 1) || (col > size)) {
            throw new IllegalArgumentException();
        }

        return isSiteOpen[layer - 1][row - 1][col - 1];
    }

    public boolean isFull(int layer, int row, int col) {
        if ((layer < 1) || (layer > size) || (row < 1) || (row > size) || (col < 1) || (col > size)) {
            throw new IllegalArgumentException();
        }

        return backwash.connected(0, (layer - 1) * size * size + (row - 1) * size + col);
    }

    public int numberOfOpenSites() {
        return openedSiteCount;
    }

    public boolean percolates() {
        return uf.connected(0, size * size * size + 1);
    }

    public static void main(String[] args) {

    }

}
