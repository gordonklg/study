/**
 Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be
 segmented into a space-separated sequence of one or more dictionary words.

 Note:
 The same word in the dictionary may be reused multiple times in the segmentation.
 You may assume the dictionary does not contain duplicate words.

 Example 1:
 Input: s = "leetcode", wordDict = ["leet", "code"]
 Output: true
 Explanation: Return true because "leetcode" can be segmented as "leet code".

 Example 2:
 Input: s = "applepenapple", wordDict = ["apple", "pen"]
 Output: true
 Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 Note that you are allowed to reuse a dictionary word.

 Example 3:
 Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 Output: false
 */
package gordon.study.algorithm.leetcode.dp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MLeet139WordBreak {

    public static void main(String[] args) {
        MLeet139WordBreak inst = new MLeet139WordBreak();
        System.out.println(inst.wordBreak("applepenapple", Arrays.asList(new String[]{"apple", "pen"})));
        System.out.println(inst.wordBreak("catsandog", Arrays.asList(new String[]{"cats", "dog", "sand", "and", "cat"})));
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        Set<String> dict = new HashSet<>(wordDict);
        boolean[] dp = new boolean[n + 1]; // dp[i] 表示 s.substring(i) 是否可以字典表示
        dp[n] = true; // trick for simplify inner loop
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (dp[j + 1] && dict.contains(s.substring(i, j + 1))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[0];
    }

}
