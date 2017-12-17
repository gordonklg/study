package gordon.study.algorithm.algs4.unionfind.coursera;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int trials;

    private double mean;

    private double stddev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.trials = trials;

        double[] trialsArr = new double[trials];
        int total = n * n;
        int[] notOpen = new int[total];
        for (int i = 0; i < trials; i++) {
            for (int index = 0; index < notOpen.length; index++) {
                notOpen[index] = index;
            }

            Percolation perc = new Percolation(n);
            int notOpenCnt = total;
            while (!perc.percolates()) {
                int rand = StdRandom.uniform(notOpenCnt--);
                int x = notOpen[rand] / n;
                int y = notOpen[rand] - x * n;
                perc.open(x + 1, y + 1);

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
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.printf("%-24s", "mean");
        StdOut.printf("= %.16f\n", stats.mean());
        StdOut.printf("%-24s", "stddev");
        StdOut.printf("= %.18f\n", stats.stddev());
        StdOut.printf("%-24s", "95% confidence interval");
        StdOut.printf("= %.16f, %.16f\n", stats.confidenceLo(),
                stats.confidenceHi());
    }
}
