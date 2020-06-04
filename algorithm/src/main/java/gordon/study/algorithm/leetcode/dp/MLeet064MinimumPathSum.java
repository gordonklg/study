/**
 Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum of all numbers along its path.

 Note: You can only move either down or right at any point in time.

 Example:
 Input:
 [
 [1,3,1],
 [1,5,1],
 [4,2,1]
 ]
 Output: 7
 Explanation: Because the path 1→3→1→1→1 minimizes the sum.
 */
package gordon.study.algorithm.leetcode.dp;

public class MLeet064MinimumPathSum {

    public static void main(String[] args) {
        MLeet064MinimumPathSum inst = new MLeet064MinimumPathSum();
        System.out.println(inst.minPathSum(new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}}));
    }

    // 优化成使用一维DP数组
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] dp = new int[n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == m - 1 && j == n - 1) {
                    dp[j] = grid[i][j];
                } else if (i == m - 1) {
                    dp[j] = grid[i][j] + dp[j + 1];
                } else if (j == n - 1) {
                    dp[j] = grid[i][j] + dp[j];
                } else {
                    dp[j] = grid[i][j] + Math.min(dp[j], dp[j + 1]);
                }
            }
        }
        return dp[0];
    }

    public int minPathSumOld(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                dp[i][j] = grid[i][j];
                if (i == m - 1 && j == n - 1) {

                } else if (i == m - 1) {
                    dp[i][j] += dp[i][j + 1];
                } else if (j == n - 1) {
                    dp[i][j] += dp[i + 1][j];
                } else {
                    dp[i][j] += Math.min(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        return dp[0][0];
    }

}
