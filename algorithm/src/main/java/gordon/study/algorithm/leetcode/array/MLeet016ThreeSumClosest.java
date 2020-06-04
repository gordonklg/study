/**
 Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest to target.
 Return the sum of the three integers. You may assume that each input would have exactly one solution.

 Example 1:
 Input: nums = [-1,2,1,-4], target = 1
 Output: 2
 Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).

 Constraints:
 3 <= nums.length <= 10^3
 -10^3 <= nums[i] <= 10^3
 -10^4 <= target <= 10^4
 */
package gordon.study.algorithm.leetcode.array;

import java.util.Arrays;

public class MLeet016ThreeSumClosest {

    public static void main(String[] args) {
        MLeet016ThreeSumClosest inst = new MLeet016ThreeSumClosest();
        System.out.println(inst.threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
        System.out.println(inst.threeSumClosest(new int[]{0, 2, 1, -3}, 1));
    }

    public int threeSumClosest(int[] nums, int target) {
        int result = 0;
        int minDelta = Integer.MAX_VALUE;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) { // nums[i] is the first element of the triplets
            int lp = i + 1;
            int rp = nums.length - 1;
            int nt = target - nums[i]; // new target of the two sum
            while (lp < rp) {
                int sum = nums[lp] + nums[rp];
                int delta = Math.abs(sum - nt);
                if (delta < minDelta) {
                    minDelta = delta;
                    result = nums[i] + sum;
                }
                if (delta == 0) {
                    return result;
                } else if (sum < nt) {
                    ++lp;
                } else {
                    --rp;
                }
            }
        }
        return result;
    }


}
