package gordon.study.algorithm.algs4.heap.practice;

import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class MultiwayMerge {

    public static void main(String[] args) {
        MultiwayMerge inst = new MultiwayMerge();
        int[][] a = new int[10][10];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                a[i][j] = StdRandom.uniform(100);
            }
            Arrays.sort(a[i]);
        }

        int[] result = inst.merge(a);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }
    }

    private int[] merge(int[][] a) {
        int[] result = new int[a.length * a[0].length];
        int pos = 0;
        int[] count = new int[a.length];

        IndexMinHeap heap = new IndexMinHeap();
        for (int i = 0; i < a.length; i++) {
            heap.insert(i, a[i][count[i]++]);
        }

        while (!heap.isEmpty()) {
            result[pos++] = heap.getMinValue();
            int i = heap.popMin();
            if (count[i] < a[i].length) {
                heap.insert(i, a[i][count[i]++]);
            }
        }

        return result;
    }
}
