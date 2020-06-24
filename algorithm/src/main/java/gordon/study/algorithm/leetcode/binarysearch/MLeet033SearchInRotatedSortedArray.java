/**
 Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
 You are given a target value to search. If found in the array return its index, otherwise return -1.
 You may assume no duplicate exists in the array.
 Your algorithm's runtime complexity must be in the order of O(log n).

 Example 1:
 Input: nums = [4,5,6,7,0,1,2], target = 0
 Output: 4

 Example 2:
 Input: nums = [4,5,6,7,0,1,2], target = 3
 Output: -1
 */
package gordon.study.algorithm.leetcode.binarysearch;

public class MLeet033SearchInRotatedSortedArray {

    public static void main(String[] args) {
        MLeet033SearchInRotatedSortedArray inst = new MLeet033SearchInRotatedSortedArray();
        System.out.println(inst.search(new int[]{3, 4, 5, 6, 7, 8, 9, 0, 1, 2}, 5));
        System.out.println(inst.search(new int[]{3, 4, 5, 6, 7, 8, 9, 0, 1, 2}, 0));
        System.out.println(inst.search(new int[]{3, 4, 5, 6, 7, 8, 9, 0, 1, 2}, 10));
    }

    public int search(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) {
            return -1;
        }
        int first = nums[0];
        if (target == first) {
            return 0;
        }
        int lo = 0, hi = n, mid;
        while (lo < hi) {
            mid = (lo + hi) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (target > first) { // 只可能在左半部分
                if (nums[mid] > target || nums[mid] < first) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            } else { // 只可能在右半部分
                if (nums[mid] < target || nums[mid] > first) {
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }
        }
        return -1;
    }
}
