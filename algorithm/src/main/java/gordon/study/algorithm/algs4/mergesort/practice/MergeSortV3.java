package gordon.study.algorithm.algs4.mergesort.practice;

public class MergeSortV3 {

    private static int THRESHOLD = 7;

    private static void merge(int[] src, int[] dst, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                dst[k] = src[j++];
            } else if (j > hi) {
                dst[k] = src[i++];
            } else if (src[j] < src[i]) {
                dst[k] = src[j++];
            } else {
                dst[k] = src[i++];
            }
        }
    }

    private static void sort(int[] src, int[] dst, int lo, int hi) {
        if (hi - lo <= THRESHOLD) {
            insertionSort(dst, lo, hi);
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(dst, src, lo, mid);
        sort(dst, src,mid + 1, hi);

        if (src[mid] <= src[mid + 1]) {
            System.arraycopy(src, lo, dst, lo, hi -lo + 1);
            return;
        }
        merge(src, dst, lo, mid, hi);
    }


    public static void main(String[] args) {
        int[] arr = new int[]{4, 7, 3, 5, 9, 1, 2, 8, 6};
        int[] aux = new int[arr.length];
        System.arraycopy(arr, 0, aux, 0, arr.length);
        sort(aux, arr,0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
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

}
