package gordon.study.algorithm.algs4.unionfind.webexercise;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class Percolation3DStats {

    private int trials;

    private double mean;

    private double stddev;

    public Percolation3DStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.trials = trials;

        double[] trialsArr = new double[trials];
        int total = n * n * n;
        int[] notOpen = new int[total];
        for (int i = 0; i < trials; i++) {
            for (int index = 0; index < notOpen.length; index++) {
                notOpen[index] = index;
            }

            Percolation3D perc = new Percolation3D(n);
            int notOpenCnt = total;
            while (!perc.percolates()) {
                int rand = StdRandom.uniform(notOpenCnt--);
                int layer = notOpen[rand] / (n * n);
                int row = (notOpen[rand] - layer * n * n) / n;
                int col = notOpen[rand] - layer * n * n - row * n;
                perc.open(layer + 1, row + 1, col + 1);

                notOpen[rand] = notOpen[notOpenCnt];
            }
            trialsArr[i] = (double) perc.numberOfOpenSites() / total;
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
        Percolation3DStats stats = new Percolation3DStats(n, trials);
        StdOut.printf("%-24s", "mean");
        StdOut.printf("= %.16f\n", stats.mean());
        StdOut.printf("%-24s", "stddev");
        StdOut.printf("= %.18f\n", stats.stddev());
        StdOut.printf("%-24s", "95% confidence interval");
        StdOut.printf("= %.16f, %.16f\n", stats.confidenceLo(), stats.confidenceHi());
    }
}
