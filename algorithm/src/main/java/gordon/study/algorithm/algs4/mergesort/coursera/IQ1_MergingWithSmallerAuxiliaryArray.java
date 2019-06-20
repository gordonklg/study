/**
 * Merging with smaller auxiliary array. Suppose that the subarray a[0] to a[n−1] is sorted and the subarray
 * a[n] to a[2∗n−1] is sorted. How can you merge the two subarrays so that a[0] to a[2∗n−1] is sorted using
 * an auxiliary array of length n (instead of 2n)?
 */
package gordon.study.algorithm.algs4.mergesort.coursera;

import edu.princeton.cs.algs4.StdRandom;

public class IQ1_MergingWithSmallerAuxiliaryArray {

    private static final int ARR_LEN = 99;

    private static int[] aux = new int[ARR_LEN / 2 + ARR_LEN % 2];

    private static void merge(int[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= mid; k++) {
            aux[k - lo] = a[k];
        }

        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                break;
            } else if (j > hi) {
                a[k] = aux[i++ - lo];
            } else if (a[j] < aux[i - lo]) {
                a[k] = a[j++];
            } else {
                a[k] = aux[i++ - lo];
            }
        }
    }

    private static void sort(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo) / 2; // this can ensure left part is ge then right part at most one entry.
        sort(a, lo, mid);
        sort(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }


    public static void main(String[] args) {
        int[] arr = new int[ARR_LEN];
        for (int i = 0; i < ARR_LEN - 1; i++) {
            arr[i] = StdRandom.uniform(1000);
        }
        sort(arr, 0, ARR_LEN - 1);
        for (int i = 0; i < ARR_LEN; i++) {
            System.out.println(arr[i]);
        }
    }
}
