/**
 You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed. All houses at this
 place are arranged in a circle. That means the first house is the neighbor of the last one. Meanwhile, adjacent houses have security system
 connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

 Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob
 tonight without alerting the police.

 Example 1:
 Input: [2,3,2]
 Output: 3
 Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2),
 because they are adjacent houses.

 Example 2:
 Input: [1,2,3,1]
 Output: 4
 Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 Total amount you can rob = 1 + 3 = 4.
 */
package gordon.study.algorithm.leetcode;

public class MLeet213HouseRobber2 {

    public static void main(String[] args) {
        MLeet213HouseRobber2 inst = new MLeet213HouseRobber2();
        System.out.println(inst.rob(new int[]{2, 3, 2})); // 3
        System.out.println(inst.rob(new int[]{4, 1, 2, 7, 5, 3, 1})); // 14
        System.out.println(inst.rob(new int[]{1, 1, 1, 2})); // 3
        System.out.println(inst.rob(new int[]{1, 1, 3, 6, 7, 10, 7, 1, 8, 5, 9, 1, 4, 4, 3})); // 41
    }

    public int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int maxpp = 0;
        boolean hasFirstpp = false;
        int maxp = nums[0];
        boolean hasFirstp = true;
        for (int i = 1; i < nums.length - 1; i++) {
            int sum = maxpp + nums[i];
            if (sum > maxp) {
                maxpp = sum;
            } else {
                if (maxpp == maxp) {
                    hasFirstpp &= hasFirstp;
                } else {
                    hasFirstpp = hasFirstp;
                }
                maxpp = maxp;
            }
            int tmp = maxp;
            maxp = maxpp;
            maxpp = tmp;
            boolean b = hasFirstp;
            hasFirstp = hasFirstpp;
            hasFirstpp = b;
        }
        return Math.max(hasFirstpp ? maxpp : (maxpp + nums[nums.length - 1]), maxp);
    }

}
