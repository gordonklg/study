package gordon.study.algorithm.algs4.elementorysort.practice;

public class InsertionSort {
    public static void main(String[] args) {
        int[] arr = new int[]{4, 7, 3, 5, 9, 1, 2, 8, 6};

        for (int i = 1; i < arr.length; i++) { // try to insert arr[i] to previous sorted sub-array.
            int pos = i; // insertion point
            for (; pos > 0 && arr[i] < arr[pos - 1]; pos--) { // find the insertion point
            }

            int temp = arr[i];
            for (int k = i; k > pos; k--) {
                arr[k] = arr[k - 1];
            }
            arr[pos] = temp;
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
