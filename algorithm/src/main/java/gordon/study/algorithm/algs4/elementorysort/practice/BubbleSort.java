package gordon.study.algorithm.algs4.elementorysort.practice;

public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = new int[]{4, 7, 3, 5, 9, 1, 2, 8, 6};

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
