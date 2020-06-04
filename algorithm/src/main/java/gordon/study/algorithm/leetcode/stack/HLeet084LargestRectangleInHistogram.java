/**
 Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.

 Example:
 Input: [2,1,5,6,2,3]
 Output: 10
 */
package gordon.study.algorithm.leetcode.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class HLeet084LargestRectangleInHistogram {

    public static void main(String[] args) {
        HLeet084LargestRectangleInHistogram inst = new HLeet084LargestRectangleInHistogram();
        System.out.println(inst.largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3})); // 10
        System.out.println(inst.largestRectangleArea(new int[]{2, 2, 5, 6, 2, 3, 2})); // 14
        System.out.println(inst.largestRectangleArea(new int[]{5, 4, 1, 2})); // 8
    }

    public int largestRectangleArea(int[] heights) {
        int max = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[i] < heights[stack.peek()]) {
                int topIndex = stack.pop();
                int leftPos = stack.isEmpty() ? -1 : stack.peek();
                max = Math.max(max, (i - leftPos - 1) * heights[topIndex]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int pos = stack.pop();
            int leftPos = stack.isEmpty() ? -1 : stack.peek();
            max = Math.max(max, (heights.length - leftPos - 1) * heights[pos]);
        }
        return max;
    }
}
