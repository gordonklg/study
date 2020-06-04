/**
 You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint
 stopping you from robbing each of them is that adjacent houses have security system connected and it will automatically contact the police
 if two adjacent houses were broken into on the same night.

 Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob
 tonight without alerting the police.

 Example 1:
 Input: nums = [1,2,3,1]
 Output: 4
 Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 Total amount you can rob = 1 + 3 = 4.

 Example 2:
 Input: nums = [2,7,9,3,1]
 Output: 12
 Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
 Total amount you can rob = 2 + 9 + 1 = 12.

 Constraints:
 0 <= nums.length <= 100
 0 <= nums[i] <= 400
 */
package gordon.study.algorithm.leetcode.dp;

public class Leet198HouseRobber {

    public static void main(String[] args) {
        Leet198HouseRobber inst = new Leet198HouseRobber();
        System.out.println(inst.rob(new int[]{2, 7, 9, 3, 1})); // 12
        System.out.println(inst.rob(new int[]{4, 1, 2, 7, 5, 3, 1})); // 14
        System.out.println(inst.rob(new int[]{1, 1, 1, 2})); // 3
        System.out.println(inst.rob(new int[]{1, 1, 3, 6, 7, 10, 7, 1, 8, 5, 9, 1, 4, 4, 3})); // 42
    }

    public int rob(int[] nums) { // not a dp solution?
        if (nums.length == 0) {
            return 0;
        }
        int maxpp = 0;
        int maxp = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int currMax = Math.max(maxpp + nums[i], maxp);
            maxpp = maxp;
            maxp = currMax;
        }
        return maxp;
    }

}
