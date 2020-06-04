/**
 Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.

 The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.

 Example:
 Input: [0,1,0,2,1,0,1,3,2,1,2,1]
 Output: 6
 */
package gordon.study.algorithm.leetcode.array;

public class HLeet042TrappingRainWater {

    public static void main(String[] args) {
        HLeet042TrappingRainWater inst = new HLeet042TrappingRainWater();
        System.out.println(inst.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1})); // 6
        System.out.println(inst.trap(new int[]{5, 4, 4, 2, 1, 3, 4, 6, 1, 7})); // 17
        System.out.println(inst.trap(new int[]{})); // 0
    }

    public int trap(int[] height) {
        int sum = 0;
        int lp = 0, rp = height.length - 1;
        int lmax = 0, rmax = 0;
        while (lp < rp) {
            if (height[lp] < height[rp]) {
                if (height[lp] > lmax) {
                    lmax = height[lp];
                } else {
                    sum += (lmax - height[lp]);
                }
                ++lp;
            } else {
                if (height[rp] > rmax) {
                    rmax = height[rp];
                } else {
                    sum += (rmax - height[rp]);
                }
                --rp;
            }
        }
        return sum;
    }
}
