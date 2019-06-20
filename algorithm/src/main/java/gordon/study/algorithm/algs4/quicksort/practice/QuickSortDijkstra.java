package gordon.study.algorithm.algs4.quicksort.practice;

import java.util.Random;

public class QuickSortDijkstra {

    public static void sort(int[] a) {
        shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int lt = lo, gt = hi, v = a[lo], i = lo + 1;
        while (i <= gt) {
            if (a[i] < v) {
                swap(a, i++, lt++);
            } else if (a[i] > v) {
                swap(a, i, gt--);
            } else {
                i++;
            }
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static void shuffle(int[] a) {
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < a.length; i++) {
            int p = r.nextInt(a.length);
            int temp = a[p];
            a[p] = a[i];
            a[i] = temp;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{4, 7, 3, 5, 9, 1, 2, 8, 6};
        sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
