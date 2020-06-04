/**
 You are climbing a stair case. It takes n steps to reach to the top.

 Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

 Note: Given n will be a positive integer.
 Example 1:
 Input: 2
 Output: 2
 Explanation: There are two ways to climb to the top.
 1. 1 step + 1 step
 2. 2 steps

 Example 2:
 Input: 3
 Output: 3
 Explanation: There are three ways to climb to the top.
 1. 1 step + 1 step + 1 step
 2. 1 step + 2 steps
 3. 2 steps + 1 step
 */
package gordon.study.algorithm.leetcode.dp;

import java.util.Arrays;

public class Leet070ClimbingStairs {

    public static void main(String[] args) {
        Leet070ClimbingStairs inst = new Leet070ClimbingStairs();
        System.out.println(inst.climbStairs(3));
    }

    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1; // 只剩一级台阶，有一种上法
        dp[2] = dp[1] + 1; // 只剩两级台阶，可以先上一级转变为只剩一级台阶的问题，也可以一下上两级台阶
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

}
