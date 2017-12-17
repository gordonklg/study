package gordon.study.algorithm.leetcode.divide;

public class Leet53MaximumSubarray {

    public static void main(String[] args) {
        Leet53MaximumSubarray inst = new Leet53MaximumSubarray();
        System.out.println(inst.maxSubArray(new int[] { -1, -2, -3, 3, -2, 9, -7, -4, 2, -1, 4, -1, -2, 5 }));
    }

    int maxSubArray(int[] nums) {
        int curMax = nums[0];
        int rightSideMax = nums[0];
        for (int i = 1; i < nums.length; i++) {
            rightSideMax = Math.max(nums[i], rightSideMax + nums[i]);
            curMax = Math.max(curMax, rightSideMax);
        }
        return curMax;
    }
}
