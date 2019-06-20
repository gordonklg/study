package gordon.study.algorithm.algs4.elementorysort.practice;

public class ShellSort {
    public static void main(String[] args) {
        int[] arr = new int[]{4, 7, 3, 5, 9, 1, 2, 8, 6};

        int h = 1;
        while (h < arr.length / 3) {
            h = h * 3 + 1;
        }

        while (h >= 1) {
            for (int i = h; i < arr.length; i++) { // try to insert arr[i] to previous sorted sub-array.
                int pos = i; // insertion point
                for (; pos >= h && arr[i] < arr[pos - h]; pos -= h) { // find the insertion point
                }
                int temp = arr[i];
                for (int k = i; k > pos; k-=h) {
                    arr[k] = arr[k - h];
                }
                arr[pos] = temp;
            }
            h /= 3;
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
