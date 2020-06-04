/**
 Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.

 The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.

 Example:
 Input: [0,1,0,2,1,0,1,3,2,1,2,1]
 Output: 6
 */
package gordon.study.algorithm.leetcode.dp;

public class HLeet042TrappingRainWater {

    public static void main(String[] args) {
        HLeet042TrappingRainWater inst = new HLeet042TrappingRainWater();
        System.out.println(inst.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1})); // 6
        System.out.println(inst.trap(new int[]{5, 4, 4, 2, 1, 3, 4, 6, 1, 7})); // 17
    }

    /*
    暴力算法：对每个数组元素，找到其左边最大值（包含该元素），找到其右边最大值（包含该元素），这两个值中的最小值与该元素值的差就是该格能存的水量。
    显然，时间复杂度O(n*n)
    DP就是将连续子数组的最大值储存起来，减少重复计算量。时间复杂度O(n)，空间复杂度O(n)
     */
    public int trap(int[] height) {
        int n = height.length;
        if (n < 2) {
            return 0;
        }
        int[] dplmax = new int[n];
        int[] dprmax = new int[n];
        dplmax[0] = height[0];
        dprmax[n - 1] = height[n - 1];
        for (int i = 1; i < n - 1; i++) {
            dplmax[i] = Math.max(dplmax[i - 1], height[i]);
        }
        for (int i = n - 2; i > 0; i--) {
            dprmax[i] = Math.max(dprmax[i + 1], height[i]);
        }
        int sum = 0;
        for (int i = 1; i < n - 1; i++) {
            sum += Math.min(dplmax[i], dprmax[i]) - height[i];
        }
        return sum;
    }
}
