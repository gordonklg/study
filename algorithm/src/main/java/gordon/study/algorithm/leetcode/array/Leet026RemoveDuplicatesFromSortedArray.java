/**
 Given a sorted array nums, remove the duplicates in-place such that each element appear only once and return the new length.

 Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.

 Example 1:
 Given nums = [1,1,2],
 Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
 It doesn't matter what you leave beyond the returned length.

 Example 2:
 Given nums = [0,0,1,1,1,2,2,3,3,4],
 Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3, and 4 respectively.
 It doesn't matter what values are set beyond the returned length.
 */
package gordon.study.algorithm.leetcode.array;

public class Leet026RemoveDuplicatesFromSortedArray {

    public static void main(String[] args) {
        Leet026RemoveDuplicatesFromSortedArray inst = new Leet026RemoveDuplicatesFromSortedArray();
        int[] input = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        int len = inst.removeDuplicates(input);
        for (int i = 0; i < len; i++) {
            System.out.println(input[i]);
        }
    }

    public int removeDuplicates(int[] nums) {
        if(nums.length == 0) {
            return 0;
        }
        int pf = 1, pb = 0; // 前后两个位置指示器
        while (pf < nums.length) {
            if(nums[pf] == nums[pb]) {
                pf++;
            } else {
                nums[++pb] = nums[pf++];
            }
        }
        return pb + 1;
    }
}
