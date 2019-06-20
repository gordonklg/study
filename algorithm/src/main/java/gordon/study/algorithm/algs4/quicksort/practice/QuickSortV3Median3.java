package gordon.study.algorithm.algs4.quicksort.practice;

import java.util.Random;

public class QuickSortV3Median3 {

    private static int THRESHOLD = 7;

    public static void sort(int[] a) {
        shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(int[] a, int lo, int hi) {
        if (hi - lo <= THRESHOLD) {
            insertionSort(a, lo, hi);
            return;
        }

        int m = median3(a, lo, lo + (hi - lo) / 2, hi);
        if (m != lo) {
            int temp = a[m];
            a[m] = a[lo];
            a[lo] = temp;
        }

        int p = partition(a, lo, hi);
        sort(a, lo, p - 1);
        sort(a, p + 1, hi);
    }

    private static int median3(int[] a, int p, int q, int r) {
        if (a[p] > a[q]) {
            if (a[q] > a[r]) {
                return q;
            } else {
                return a[r] > a[p] ? p : r;
            }
        } else {
            if (a[r] > a[q]) {
                return q;
            } else {
                return a[r] > a[p] ? r : p;
            }
        }
    }

    private static void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            for (int j = i; j > lo && a[j] < a[j - 1]; j--) {
                int temp = a[j];
                a[j] = a[j - 1];
                a[j - 1] = temp;
            }
        }
    }

    private static int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1, v = a[lo];
        while (true) {
            while (a[++i] < v) {
                if (i == hi) break;
            }
            while (a[--j] > v) {
                // if (j == lo) break;
            }
            if (i >= j) break;
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        a[lo] = a[j]; // 必须和 j 交换！
        a[j] = v;
        return j;
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
