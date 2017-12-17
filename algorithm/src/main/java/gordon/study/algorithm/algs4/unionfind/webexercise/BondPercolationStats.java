package gordon.study.algorithm.algs4.unionfind.webexercise;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class BondPercolationStats {

    private int trials;

    private double mean;

    private double stddev;

    public BondPercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.trials = trials;

        double[] trialsArr = new double[trials];
        int total = n * (n - 1) * 2;
        int[] notOpen = new int[total];
        for (int i = 0; i < trials; i++) {
            for (int index = 0; index < notOpen.length; index++) {
                notOpen[index] = index;
            }

            BondPercolation perc = new BondPercolation(n);
            int notOpenCnt = total;
            while (!perc.percolates()) {
                int rand = StdRandom.uniform(notOpenCnt--);
                int bond = notOpen[rand];
                if (bond < n * n - n) { // means vertical bond, connect the given node and the node beneath it.
                    perc.union(bond, bond + n);
                } else { // means horizontal bond.
                    bond -= n * n - n;
                    int compensation = bond / (n - 1);
                    perc.union(bond + compensation, bond + compensation + 1);
                }
                notOpen[rand] = notOpen[notOpenCnt];
            }
            trialsArr[i] = (double) perc.numberOfOpenBonds() / total;
        }

        mean = StdStats.mean(trialsArr);
        stddev = StdStats.stddev(trialsArr);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean - 1.96 * stddev / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean + 1.96 * stddev / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        BondPercolationStats stats = new BondPercolationStats(n, trials);
        StdOut.printf("%-24s", "mean");
        StdOut.printf("= %.16f\n", stats.mean());
        StdOut.printf("%-24s", "stddev");
        StdOut.printf("= %.18f\n", stats.stddev());
        StdOut.printf("%-24s", "95% confidence interval");
        StdOut.printf("= %.16f, %.16f\n", stats.confidenceLo(), stats.confidenceHi());
    }
}
