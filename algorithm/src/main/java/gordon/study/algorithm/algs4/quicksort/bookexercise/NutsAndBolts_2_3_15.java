package gordon.study.algorithm.algs4.quicksort.bookexercise;

public class NutsAndBolts_2_3_15 {

    public static void main(String[] args) {
        int[] nuts = new int[]{2, 4, 6, 7, 3, 9, 8, 1, 5};
        int[] bolts = new int[]{5, 8, 2, 3, 6, 1, 7, 9, 4};

        // skip shuffle

        match(nuts, bolts, 0, nuts.length - 1);

        for (int i = 0; i < nuts.length; i++) {
            System.out.println(nuts[i] + ":" + bolts[i]);
        }
    }

    private static void match(int[] a, int[] b, int lo, int hi) {
        if (lo >= hi) return;
        int size = a[lo]; // pick up a nut
        int p = partition(b, lo, hi, size); // use the nut to partition the bolts
        partition(a, lo, hi, b[p]); // then use the corresponding bolt to partition the nuts. In fact, b[p] == size
        match(a, b, lo, p - 1);
        match(a, b, p + 1, hi);
    }

    private static int partition(int[] a, int lo, int hi, int size) {
        int lt = lo, gt = hi, i = lo;
        while (i < gt) {
            if (a[i] < size) {
                lt++;
                i++;
            } else if (a[i] > size) {
                swap(a, i, gt--);
            } else {
                swap(a, lo, i++);
                lt++;
            }
        }

        if (a[gt] == size) {
            return gt;
        } else if (a[gt] < size) {
            swap(a, lo, gt);
            return gt;
        } else {
            swap(a, lo, --gt);
            return gt;
        }
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
