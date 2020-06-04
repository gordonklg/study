/**
 A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

 The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid
 (marked 'Finish' in the diagram below).

 Now consider if some obstacles are added to the grids. How many unique paths would there be?

 An obstacle and empty space is marked as 1 and 0 respectively in the grid.

 Note: m and n will be at most 100.

 Example 1:
 Input:
 [
 [0,0,0],
 [0,1,0],
 [0,0,0]
 ]
 Output: 2
 Explanation:
 There is one obstacle in the middle of the 3x3 grid above.
 There are two ways to reach the bottom-right corner:
 1. Right -> Right -> Down -> Down
 2. Down -> Down -> Right -> Right
 */
package gordon.study.algorithm.leetcode.dp;

public class MLeet063UniquePaths2 {

    public static void main(String[] args) {
        MLeet063UniquePaths2 inst = new MLeet063UniquePaths2();
        System.out.println(inst.uniquePathsWithObstacles(new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}}));
        System.out.println(inst.uniquePathsWithObstacles(new int[][]{{1}, {0}})); // 0
    }

    // 优化成使用一维DP数组
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[] dp = new int[n];
        for (int i = n - 1; i >= 0; i--) { // 处理最下一行所有格子路径数量
            if (obstacleGrid[m - 1][i] == 1) {
                break;
            }
            dp[i] = 1;
        }
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (obstacleGrid[i][j] == 1) {
                    dp[j] = 0;
                } else if (j < n - 1) {
                    dp[j] = dp[j] + dp[j + 1];
                }
            }
        }
        return dp[0];
    }

    public int uniquePathsWithObstaclesOld(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        for (int i = n - 1; i >= 0; i--) { // 处理最下一行所有格子路径数量
            if (obstacleGrid[m - 1][i] == 1) {
                break;
            }
            dp[m - 1][i] = 1;
        }
        for (int i = m - 1; i >= 0; i--) { // 处理最右一列所有格子路径数量
            if (obstacleGrid[i][n - 1] == 1) {
                break;
            }
            dp[i][n - 1] = 1;
        }
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                if (obstacleGrid[i][j] != 1) {
                    dp[i][j] = dp[i + 1][j] + dp[i][j + 1];
                }
            }
        }
        return dp[0][0];
    }

}
