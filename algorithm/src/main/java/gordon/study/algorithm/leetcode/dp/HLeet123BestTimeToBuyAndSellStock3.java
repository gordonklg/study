/**
 Say you have an array for which the ith element is the price of a given stock on day i.

 Design an algorithm to find the maximum profit. You may complete at most two transactions.

 Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).

 Example 1:
 Input: [3,3,5,0,0,3,1,4]
 Output: 6
 Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.

 Example 2:
 Input: [1,2,3,4,5]
 Output: 4
 Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
 Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are
 engaging multiple transactions at the same time. You must sell before buying again.

 Example 3:
 Input: [7,6,4,3,1]
 Output: 0
 Explanation: In this case, no transaction is done, i.e. max profit = 0.
 */
/*
https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/discuss/39608/A-clean-DP-solution-which-generalizes-to-k-transactions
 */
package gordon.study.algorithm.leetcode.dp;

public class HLeet123BestTimeToBuyAndSellStock3 {

    public static void main(String[] args) {
        HLeet123BestTimeToBuyAndSellStock3 inst = new HLeet123BestTimeToBuyAndSellStock3();
        System.out.println(inst.maxProfit(new int[]{3, 3, 5, 0, 0, 3, 1, 4}));
        System.out.println(inst.maxProfit(new int[]{7, 6, 4, 3, 1}));
        System.out.println(inst.maxProfit(new int[0]));
    }

    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int K = 2;
        /*
         dp[k, i] represents the max profit up until prices[i] (Note: NOT ending with prices[i]) using at most k transactions.
         dp[k, i] = max(dp[k, i-1], prices[i] - prices[j] + dp[k-1, j]) { j in range of [0, i-1] }
                  = max(dp[k, i-1], prices[i] + max(dp[k-1, j] - prices[j]))
         dp[0, i] = 0; 0 times transaction makes 0 profit
         dp[k, 0] = 0; if there is only one price data point you can't make any money no matter how many times you can trade
         */
        int[][] dp = new int[K + 1][n];
        for (int k = 1; k <= K; k++) {
            int tmpMax = dp[k - 1][0] - prices[0];
            for (int i = 1; i < n; i++) {
                dp[k][i] = Math.max(dp[k][i - 1], prices[i] + tmpMax);
                tmpMax = Math.max(tmpMax, dp[k - 1][i] - prices[i]);
            }
        }
        return dp[K][n - 1];
    }
}
