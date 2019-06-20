/**
 * Dutch national flag. Given an array of n buckets, each containing a red, white, or blue pebble, sort them by color. The allowed operations are:
 *
 * swap(i, j): swap the pebble in bucket i with the pebble in bucket j.
 * color(i): determine the color of the pebble in bucket i.
 *
 * The performance requirements are as follows:
 * At most n calls to color().
 * At most n calls to swap().
 * Constant extra space.
 */
package gordon.study.algorithm.algs4.elementorysort.coursera;

import edu.princeton.cs.algs4.StdRandom;

public class IQ3_DutchNationalFlag {

    private static final int NUM = 100;

    private static int[] buckets;

    private static int counterColor;

    private static int counterSwap;

    static {
        buckets = new int[NUM];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = StdRandom.uniform(3);
        }
    }

    public static void main(String[] args) {
        int lt = 0;
        int gt = NUM - 1;
        int i = 0;
        int j = 0;
        while (i <= gt) {
            if (buckets[i] == 0) {
                if (i > lt) {
                    swap(i, lt);
                }
                i++;
                lt++;
            } else if (buckets[i] == 1) {
                i++;
            } else {
                swap(i, gt--);
            }
        }

        for (int k = 0; k < buckets.length; k++) {
            System.out.print(buckets[k]);
        }
    }

    private static int color(int i) {
        counterColor++;
        if (counterColor > NUM) {
            System.err.println("Too many calls on 'COLOR'");
        }
        return buckets[i];
    }

    private static void swap(int i, int j) {
        int temp = buckets[i];
        buckets[i] = buckets[j];
        buckets[j] = temp;
        counterSwap++;
        if (counterSwap > NUM) {
            System.err.println("Too many calls on 'SWAP'");
        }
    }
}
