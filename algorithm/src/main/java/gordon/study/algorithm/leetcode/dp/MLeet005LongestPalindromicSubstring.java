/**
 Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

 Example 1:
 Input: "babad"
 Output: "bab"
 Note: "aba" is also a valid answer.

 Example 2:
 Input: "cbbd"
 Output: "bb"
 */
package gordon.study.algorithm.leetcode.dp;

public class MLeet005LongestPalindromicSubstring {

    public static void main(String[] args) {
        MLeet005LongestPalindromicSubstring inst = new MLeet005LongestPalindromicSubstring();
        System.out.println(inst.longestPalindrome("babad"));
        System.out.println(inst.longestPalindrome("cbbd"));
        System.out.println(inst.longestPalindrome(""));
    }

    // 优化成使用一维DP数组
    public String longestPalindrome(String s) {
        int n = s.length();
        if (n < 2) {
            return s;
        }
        int maxLength = 0;
        int begin = 0;
        int end = 0;
        boolean[] dp = new boolean[n];
        for (int i = n - 2; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) { // 必须改为从右向左遍历
                if (i + 1 < j - 1 && !dp[j - 1]) {
                    dp[j] = false;
                } else {
                    boolean b = s.charAt(i) == s.charAt(j);
                    dp[j] = b;
                    if (b && j - i > maxLength) {
                        maxLength = j - i;
                        begin = i;
                        end = j;
                    }
                }
            }
        }
        return s.substring(begin, end + 1);
    }

    public String longestPalindromeOld(String s) {
        int n = s.length();
        if (n < 2) {
            return s;
        }
        int maxLength = 0;
        int begin = 0;
        int end = 0;
        boolean[][] dp = new boolean[n][n]; // dp[i][j]表示s.substring(i, j+1)是否是回文字符串
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = true;
            for (int j = i + 1; j < n; j++) { // s.substring(i, j)是回文字符串要求s.substring(i+1, j-1)是回文字符串且i,j位置字符相同
                if (i + 1 < j - 1 && !dp[i + 1][j - 1]) {
                    dp[i][j] = false;
                } else {
                    boolean b = s.charAt(i) == s.charAt(j);
                    dp[i][j] = b;
                    if (b && j - i > maxLength) {
                        maxLength = j - i;
                        begin = i;
                        end = j;
                    }
                }
            }
        }
        return s.substring(begin, end + 1);
    }

}
