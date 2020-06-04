/**
 Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

 Note:

 The number of elements initialized in nums1 and nums2 are m and n respectively.
 You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.

 Example:
 Input:
 nums1 = [1,2,3,0,0,0], m = 3
 nums2 = [2,5,6],       n = 3
 Output: [1,2,2,3,5,6]
 */
package gordon.study.algorithm.leetcode.array;

import java.util.Arrays;

public class Leet088MergeSortedArray {

    public static void main(String[] args) {
        Leet088MergeSortedArray inst = new Leet088MergeSortedArray();
        int[] a = new int[]{1, 2, 3, 0, 0, 0};
        inst.merge(a, 3, new int[]{2, 5, 6}, 3);
        Arrays.stream(a).forEach((i) -> System.out.print(i + " "));
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m + n - 1;
        --m;
        --n;
        while (m >= 0 && n >= 0) {
            if (nums1[m] > nums2[n]) {
                nums1[i--] = nums1[m--];
            } else {
                nums1[i--] = nums2[n--];
            }
        }
        while (n >= 0) { // 只有在nums2还有剩余元素时，才需要将nums2剩余元素移到nums1
            nums1[n] = nums2[n];
            --n;
        }
    }

}
