/**
 * Counting inversions. An inversion in an array a[] is a pair of entries a[i] and a[j] such that i<j but a[i]>a[j].
 * Given an array, design a linearithmic algorithm to count the number of inversions.
 */
package gordon.study.algorithm.algs4.mergesort.coursera;

public class IQ2_CountingInversions {

    private static int[] aux = new int[100];

    private static int count;

    private static void merge(int[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (aux[j] < aux[i]) {
                a[k] = aux[j];
                count += (j - k);
                j++;
            } else {
                a[k] = aux[i++];
            }
        }
    }

    private static void sort(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, lo, mid);
        sort(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{4, 7, 3, 5, 9, 1, 2, 8, 6};
        sort(arr, 0, arr.length - 1);
        System.out.println(count);
    }
}
