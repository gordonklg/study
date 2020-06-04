/**
 A message containing letters from A-Z is being encoded to numbers using the following mapping:

 'A' -> 1
 'B' -> 2
 ...
 'Z' -> 26
 Given a non-empty string containing only digits, determine the total number of ways to decode it.

 Example 1:
 Input: "12"
 Output: 2
 Explanation: It could be decoded as "AB" (1 2) or "L" (12).

 Example 2:
 Input: "226"
 Output: 3
 Explanation: It could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
 */
package gordon.study.algorithm.leetcode.dp;

public class MLeet091DecodeWays {

    public static void main(String[] args) {
        MLeet091DecodeWays inst = new MLeet091DecodeWays();
        System.out.println(inst.numDecodings("226")); // 3
        System.out.println(inst.numDecodings("110212712520")); // 9
        System.out.println(inst.numDecodings("0")); // 0
        System.out.println(inst.numDecodings("")); // 0
        System.out.println(inst.numDecodings("707")); // 0
    }

    public int numDecodings(String s) {
        int n = s.length();
        if (n < 1) {
            return 0;
        }
        int[] dp = new int[n + 1]; // 包含前n个字符的字串有多少种解码方案
        dp[0] = 1; // 神奇数字，无法在注释中解释
        dp[1] = 1; // 单个字符显然只有一个编码方案
        if (s.charAt(0) == '0') { // 防止输入 "0" 无法解码
            return 0;
        }
        for (int i = 1; i < n; i++) { // 转化为上台阶问题
            char c = s.charAt(i);
            char pc = s.charAt(i - 1);
            if (c == '0') { // 如果当前字符是0，则必然要和前一个字符组成10/20，因此可能解码方案数与排除10/20的前面字符串一致。
                if (pc != '1' && pc != '2') { // 防止无法解码，例如707
                    return 0;
                }
                dp[i + 1] = dp[i - 1];
            } else if (pc == '1' || (pc == '2' && c < '7')) {
                dp[i + 1] = dp[i - 1] + dp[i];
            } else {
                dp[i + 1] = dp[i];
            }
        }
        return dp[n];
    }

}
