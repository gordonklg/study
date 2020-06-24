/**
 Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.

 Example 1:
 Input: [3,2,1,5,6,4] and k = 2
 Output: 5

 Example 2:
 Input: [3,2,3,1,2,4,5,5,6] and k = 4
 Output: 4

 Note:
 You may assume k is always valid, 1 ≤ k ≤ array's length.
 */
package gordon.study.algorithm.leetcode.heap;

import java.util.Random;

public class MLeet215KthLargestElementInAnArray_SelectionAlgorithm {

    public static void main(String[] args) {
        MLeet215KthLargestElementInAnArray_SelectionAlgorithm inst = new MLeet215KthLargestElementInAnArray_SelectionAlgorithm();
        System.out.println(inst.findKthLargest(new int[]{1, 2, 3, 4, 5, 6}, 1)); // 6
        System.out.println(inst.findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2)); // 5
        System.out.println(inst.findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4)); // 4
    }

    // https://en.wikipedia.org/wiki/Quickselect
    // https://en.wikipedia.org/wiki/Quicksort#Hoare_partition_scheme 不能直接用，这个算法并没有把pivot换到返回的下标处
    public int findKthLargest(int[] nums, int k) {
        shuffle(nums);
        k = nums.length - k;
        int lo = 0;
        int hi = nums.length - 1;
        while (lo < hi) {
            int border = partition(nums, lo, hi);
            if (border < k) {
                lo = border + 1;
            } else if (border > k) {
                hi = border - 1;
            } else {
                break;
            }
        }
        return nums[k];
    }

    private int partition(int[] nums, int lo, int hi) {
        int pivot = nums[lo];
        int i = lo + 1;
        int j = hi;
        while (true) {
            while (i < hi && nums[i] < pivot) {
                i++;
            }
            while (j > lo && nums[j] > pivot) {
                j--;
            }
            if (i >= j) {
                swap(nums, lo, j);
                return j;
            }
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private void swap(int[] nums, int p, int q) {
        int tmp = nums[p];
        nums[p] = nums[q];
        nums[q] = tmp;
    }

    private void shuffle(int[] nums) {
        Random r = new Random();
        for (int i = nums.length; i > 1; i--) {
            swap(nums, i - 1, r.nextInt(i));
        }
    }

}
