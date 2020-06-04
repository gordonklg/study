/**
 Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai). n vertical lines
 are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines, which together with x-axis
 forms a container, such that the container contains the most water.

 Note: You may not slant the container and n is at least 2.

 Example:
 Input: [1,8,6,2,5,4,8,3,7]
 Output: 49
 */
package gordon.study.algorithm.leetcode.array;

public class MLeet011ContainerWithMostWater {

    public static void main(String[] args) {
        MLeet011ContainerWithMostWater inst = new MLeet011ContainerWithMostWater();
        System.out.println(inst.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7})); // 49
        System.out.println(inst.maxArea(new int[]{6, 2, 1, 25, 3, 25, 2, 3, 7})); // 50
    }

    public int maxArea(int[] height) {
        int lp = 0, rp = height.length - 1, max = 0;
        while (lp < rp) {
            if (height[lp] < height[rp]) {
                max = Math.max(height[lp] * (rp - lp), max);
                ++lp;
            } else {
                max = Math.max(height[rp] * (rp - lp), max);
                --rp;
            }
        }
        return max;
    }


}
