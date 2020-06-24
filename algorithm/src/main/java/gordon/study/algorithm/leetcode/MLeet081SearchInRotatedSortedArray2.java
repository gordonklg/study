/**
 Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 (i.e., [0,0,1,2,2,5,6] might become [2,5,6,0,0,1,2]).
 You are given a target value to search. If found in the array return true, otherwise return false.

 Example 1:
 Input: nums = [2,5,6,0,0,1,2], target = 0
 Output: true

 Example 2:
 Input: nums = [2,5,6,0,0,1,2], target = 3
 Output: false

 Follow up:
 This is a follow up problem to Search in Rotated Sorted Array, where nums may contain duplicates.
 Would this affect the run-time complexity? How and why?
 */
package gordon.study.algorithm.leetcode;

public class MLeet081SearchInRotatedSortedArray2 {

    public static void main(String[] args) {
        MLeet081SearchInRotatedSortedArray2 inst = new MLeet081SearchInRotatedSortedArray2();
        System.out.println(inst.search(new int[]{3, 4, 5, 6, 7, 8, 9, 0, 1, 2}, 5));
        System.out.println(inst.search(new int[]{3, 4, 5, 6, 7, 8, 9, 0, 1, 2}, 0));
        System.out.println(inst.search(new int[]{3, 4, 5, 6, 7, 8, 9, 0, 1, 2}, 10));
        System.out.println(inst.search(new int[]{1, 3, 1, 1}, 3));
    }

    public boolean search(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) {
            return false;
        }
        int first = nums[0];
        if (target == first) {
            return true;
        }
        int lo = 0, hi = n, mid;
        while (lo < hi) {
            mid = (lo + hi) / 2;
            if (nums[mid] == target) {
                return true;
            } else if (nums[mid] == first) {
                lo++;
            } else if (nums[mid] > first) { // mid还在左半部分
                if (target > first || nums[mid] < target) {
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
        return false;
    }
}
