/**
 Say you have an array for which the i-th element is the price of a given stock on day i.

 Design an algorithm to find the maximum profit. You may complete at most k transactions.

 Note:
 You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).

 Example 1:
 Input: [2,4,1], k = 2
 Output: 2
 Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.

 Example 2:
 Input: [3,2,6,5,0,3], k = 2
 Output: 7
 Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4.
 Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 */
package gordon.study.algorithm.leetcode.dp;

public class HLeet188BestTimeToBuyAndSellStock4 {

    public static void main(String[] args) {
        HLeet188BestTimeToBuyAndSellStock4 inst = new HLeet188BestTimeToBuyAndSellStock4();
        System.out.println(inst.maxProfit(2, new int[]{3, 2, 6, 5, 0, 3}));
        System.out.println(inst.maxProfit(3, new int[]{1, 3, 2, 4, 1, 2, 4, 2, 6})); // 10
        System.out.println(inst.maxProfit(6, new int[]{1, 3, 2, 4, 1, 2, 4, 2, 6})); // 11
    }

    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (n < 2) {
            return 0;
        }
        if (k > n / 2) { // in case of Time Limit Exceeded
            int sum = 0;
            for (int i = 1; i < n; i++) {
                int profit = prices[i] - prices[i - 1];
                if (profit > 0) {
                    sum += profit;
                }
            }
            return sum;
        }
        int[] dp = new int[n];
        int[] dp2 = new int[n]; // in case of Memory Limit Exceeded
        for (int j = 1; j <= k; j++) {
            int tmpMax = -prices[0];
            for (int i = 1; i < n; i++) {
                dp2[i] = Math.max(dp2[i - 1], prices[i] + tmpMax);
                tmpMax = Math.max(tmpMax, dp[i] - prices[i]);
            }
            int[] tmp = dp;
            dp = dp2;
            dp2 = tmp;
        }
        return dp[n - 1];
    }

}
