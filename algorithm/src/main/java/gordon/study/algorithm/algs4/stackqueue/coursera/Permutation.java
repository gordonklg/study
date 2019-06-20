package gordon.study.algorithm.algs4.stackqueue.coursera;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        int n = 0;
        if (args.length >= 1) {
            n = Integer.parseInt(args[0]);
        }

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String x = StdIn.readString();
            randomizedQueue.enqueue(x);
        }

        for (int i = 0; i < n; ++i) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}
