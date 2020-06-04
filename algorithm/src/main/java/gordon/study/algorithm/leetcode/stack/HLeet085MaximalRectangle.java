/**
 Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.

 Example:
 Input:
 [
 ["1","0","1","0","0"],
 ["1","0","1","1","1"],
 ["1","1","1","1","1"],
 ["1","0","0","1","0"]
 ]
 Output: 6
 */
package gordon.study.algorithm.leetcode.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class HLeet085MaximalRectangle {

    public static void main(String[] args) {
        HLeet085MaximalRectangle inst = new HLeet085MaximalRectangle();
        System.out.println(inst.maximalRectangle(new char[][]{{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}}));
    }

    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        if (m == 0) {
            return 0;
        }
        int n = matrix[0].length;
        int[] height = new int[n + 1]; // trick! height[n]===0
        int max = 0;
        for (int i = 0; i < m; i++) { // 对每一行，转化为084LargestRectangleInHistogram
            Deque<Integer> stack = new ArrayDeque<>();
            for (int j = 0; j <= n; j++) {
                if (j < n) {
                    if (matrix[i][j] == '1') {
                        ++height[j];
                    } else {
                        height[j] = 0;
                    }
                }
                while (!stack.isEmpty() && height[j] < height[stack.peek()]) {
                    int topPos = stack.pop();
                    int width = stack.isEmpty() ? j : j - stack.peek() - 1;
                    max = Math.max(max, height[topPos] * width);
                }
                stack.push(j);
            }
        }
        return max;
    }
}
