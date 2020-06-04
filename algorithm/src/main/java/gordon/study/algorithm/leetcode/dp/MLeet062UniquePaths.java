/**
 A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

 The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner
 of the grid (marked 'Finish' in the diagram below).

 How many possible unique paths are there?

 Example 1:
 Input: m = 3, n = 2
 Output: 3
 Explanation:
 From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
 1. Right -> Right -> Down
 2. Right -> Down -> Right
 3. Down -> Right -> Right

 Example 2:
 Input: m = 7, n = 3
 Output: 28

 Constraints:
 1 <= m, n <= 100
 It's guaranteed that the answer will be less than or equal to 2 * 10 ^ 9.
 */
package gordon.study.algorithm.leetcode.dp;

public class MLeet062UniquePaths {

    public static void main(String[] args) {
        MLeet062UniquePaths inst = new MLeet062UniquePaths();
        System.out.println(inst.uniquePaths(7, 3));
        System.out.println(inst.uniquePaths(1, 1));
    }

    // 优化成使用一维DP数组
    public int uniquePaths(int m, int n) {
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) { // 最下一行所有格子路径数量为1
            dp[i] = 1;
        }
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                dp[j] = dp[j] + dp[j + 1];
            }
        }
        return dp[0];
    }


    public int uniquePathsOld(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < n; i++) { // 最下一行所有格子路径数量为1
            dp[m - 1][i] = 1;
        }
        for (int i = 0; i < m; i++) { // 最右一列所有格子路径数量为1
            dp[i][n - 1] = 1;
        }
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                dp[i][j] = dp[i + 1][j] + dp[i][j + 1];
            }
        }
        return dp[0][0];
    }

}
