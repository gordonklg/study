/**
 Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.

 The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.

 Example:
 Input: [0,1,0,2,1,0,1,3,2,1,2,1]
 Output: 6
 */
package gordon.study.algorithm.leetcode.stack;

import java.util.ArrayDeque;
import java.util.Deque;

// https://leetcode.com/problems/trapping-rain-water/solution/
public class HLeet042TrappingRainWater {

    public static void main(String[] args) {
        HLeet042TrappingRainWater inst = new HLeet042TrappingRainWater();
        System.out.println(inst.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1})); // 6
        System.out.println(inst.trap(new int[]{5, 4, 4, 2, 1, 3, 4, 6, 1, 7})); // 17
    }

    public int trap(int[] height) {
        int sum = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int bottomIndex = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                int width = i - stack.peek() - 1;
                int ht = Math.min(height[i], height[stack.peek()]) - height[bottomIndex];
                sum += width * ht;
            }
            stack.push(i); // 将下标入栈
        }
        return sum;
    }
}
