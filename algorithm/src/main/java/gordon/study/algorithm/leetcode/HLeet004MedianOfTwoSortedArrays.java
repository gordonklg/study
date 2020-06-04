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
package gordon.study.algorithm.leetcode;

public class HLeet004MedianOfTwoSortedArrays {

    public static void main(String[] args) {
        HLeet004MedianOfTwoSortedArrays inst = new HLeet004MedianOfTwoSortedArrays();
//        System.out.println(inst.findMedianSortedArrays(new int[]{1, 2, 8}, new int[]{4, 5, 6}));
//        System.out.println(inst.findMedianSortedArrays(new int[]{1, 2, 7, 8}, new int[]{4, 5, 6}));
//        System.out.println(inst.findMedianSortedArrays(new int[]{1, 2, 8}, new int[]{4, 5, 6, 7}));
//        System.out.println(inst.findMedianSortedArrays(new int[]{1, 2, 7, 8}, new int[]{3, 4, 5, 6}));
//        System.out.println(inst.findMedianSortedArrays(new int[]{1}, new int[]{2}));
//        System.out.println(inst.findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));
        System.out.println(inst.findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}));
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        boolean odd = ((m + n) % 2 == 1);
        int l1 = 0, r1 = m - 1, l2 = 0, r2 = n - 1;
        int d1 = (m + 1) / 2;
        int d2 = (m + n + 1) / 2 - d1; // 确保左半区元素数量与右半区相等或只多一个

        while (true) {
            if (nums1[d1 - 1] <= nums2[d2]) { // 数组一左半区最大值小于数组二右半区最小值
                if (nums2[d2 - 1] <= nums1[d1]) {  // 数组二左半区最大值小于数组一右半区最小值
                    // 已经完成左右切分
                    if (odd) {
                        return Math.max(nums1[d1 - 1], nums2[d2 - 1]);
                    } else {
                        return (Math.max(nums1[d1 - 1], nums2[d2 - 1]) + Math.max(nums1[d1], nums2[d2])) / 2.0;
                    }
                } else { // 需要d1右移、d2左移
                    // 如果d1已经在最右边
                    if(d1 == (n - 1)) {

                    }
                }

            }

        }


    }
}
