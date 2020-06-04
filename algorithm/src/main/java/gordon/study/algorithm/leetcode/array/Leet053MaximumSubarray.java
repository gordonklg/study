/**
 Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.

 Example:
 Input: [-2,1,-3,4,-1,2,1,-5,4],
 Output: 6
 Explanation: [4,-1,2,1] has the largest sum = 6.

 Follow up:
 If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
 */
package gordon.study.algorithm.leetcode.array;

public class Leet053MaximumSubarray {

    public static void main(String[] args) {
        Leet053MaximumSubarray inst = new Leet053MaximumSubarray();
        System.out.println(inst.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4})); // 6
        System.out.println(inst.maxSubArray(new int[]{-2, -3, -1})); // -1
    }

    public int maxSubArray(int[] nums) {
        int result = nums[0];
        int sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (sum < 0) {
                sum = nums[i];
            } else {
                sum += nums[i];
            }
            if (sum > result) {
                result = sum;
            }
        }
        return result;
    }

}
