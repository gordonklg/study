/**
 Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

 Example:
 Input:
 1 0 1 0 0
 1 0 1 1 1
 1 1 1 1 1
 1 0 0 1 0
 Output: 4
 */
package gordon.study.algorithm.leetcode.dp;

// https://leetcode.com/problems/maximal-square/solution/
public class MLeet221MaximalSquare_Solution {

    public static void main(String[] args) {
        MLeet221MaximalSquare_Solution inst = new MLeet221MaximalSquare_Solution();
        System.out.println(inst.maximalSquare(new char[][]{{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}}));
    }

    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        if (m == 0) {
            return 0;
        }
        int n = matrix[0].length;
        int[] dp = new int[n + 1]; // 优化dp数组维度
        int max = 0;
        int prev = 0; // prev 用于暂存原 dp[i-1][j-1] 的值
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int temp = dp[j]; // dp[j]是原 dp[i-1][j] 的值
                if (matrix[i - 1][j - 1] == '1') {
                    dp[j] = Math.min(Math.min(dp[j - 1], dp[j]), prev) + 1; // dp[j-1]是原 dp[i][j-1] 的值
                    max = Math.max(dp[j], max);
                } else {
                    dp[j] = 0;
                }
                prev = temp; // 由于dp[0]===0，处理新行时，尽管prev指向元素不对，但不会影响dp[1]的重新赋值（不是0就是1），因此OK。
            }
        }
        return max * max;
    }

}
