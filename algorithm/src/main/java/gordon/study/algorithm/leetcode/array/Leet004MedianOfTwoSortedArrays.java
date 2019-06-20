/**
 There are two sorted arrays nums1 and nums2 of size m and n respectively.

 Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

 Example 1:
 nums1 = [1, 3]
 nums2 = [2]

 The median is 2.0

 Example 2:
 nums1 = [1, 2]
 nums2 = [3, 4]

 The median is (2 + 3)/2 = 2.5
 */
package gordon.study.algorithm.leetcode.array;

public class Leet004MedianOfTwoSortedArrays {

    public static void main(String[] args) {
        Leet004MedianOfTwoSortedArrays inst = new Leet004MedianOfTwoSortedArrays();
//        System.out.println(inst.findMedianSortedArrays(new int[]{1, 2, 8}, new int[]{4, 5, 6}));
//        System.out.println(inst.findMedianSortedArrays(new int[]{1, 2, 7, 8}, new int[]{4, 5, 6}));
//        System.out.println(inst.findMedianSortedArrays(new int[]{1, 2, 8}, new int[]{4, 5, 6, 7}));
//        System.out.println(inst.findMedianSortedArrays(new int[]{1, 2, 7, 8}, new int[]{3, 4, 5, 6}));
//        System.out.println(inst.findMedianSortedArrays(new int[]{1}, new int[]{2}));
//        System.out.println(inst.findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
        System.out.println(inst.findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}));
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int left1 = 0, right1 = nums1.length - 1, left2 = 0, right2 = nums2.length - 1;
        int half, remainder;
        while (true) {
            half = (right1 - left1 + right2 - left2) / 2 + 1;
            remainder =  (right1 - left1 + right2 - left2) % 2;
            // get the boundary of nums1 array. boundary belong to left part.
            int boundary1 = (right1 - left1) / 2 + left1;
            // get the boundary of nums2 array. boundary belong to right part.
            int boundary2 = half - (boundary1 - left1 + 1) + left2;
            int maxLeft = Math.max(nums1[boundary1], boundary2 - 1 >= 0 ? nums2[boundary2 - 1] : Integer.MIN_VALUE);
            int minRight = Math.min(boundary1 + 1 < nums1.length ? nums1[boundary1 + 1] : Integer.MAX_VALUE, nums2[boundary2] );
            if (maxLeft <= minRight) {
                if (remainder == 1) {
                    return minRight;
                } else {
                    return (maxLeft + minRight) / 2.0;
                }
            } else {

            }

        }

    }
}
