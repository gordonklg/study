/**
 Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add spaces in s to
 construct a sentence where each word is a valid dictionary word. Return all such possible sentences.

 Note:
 The same word in the dictionary may be reused multiple times in the segmentation.
 You may assume the dictionary does not contain duplicate words.

 Example 1:
 Input:
 s = "catsanddog"
 wordDict = ["cat", "cats", "and", "sand", "dog"]
 Output:
 [
 "cats and dog",
 "cat sand dog"
 ]

 Example 2:
 Input:
 s = "pineapplepenapple"
 wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
 Output:
 [
 "pine apple pen apple",
 "pineapple pen apple",
 "pine applepen apple"
 ]
 Explanation: Note that you are allowed to reuse a dictionary word.

 Example 3:
 Input:
 s = "catsandog"
 wordDict = ["cats", "dog", "sand", "and", "cat"]
 Output:
 []
 */
package gordon.study.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HLeet140WordBreak2 {

    public static void main(String[] args) {
        HLeet140WordBreak2 inst = new HLeet140WordBreak2();
//        System.out.println(inst.wordBreak("pineapplepenapple", Arrays.asList(new String[]{"apple", "pen", "applepen", "pine", "pineapple"})));
//        System.out.println(inst.wordBreak("catsanddog", Arrays.asList(new String[]{"cat", "cats", "and", "sand", "dog"})));
//        System.out.println(inst.wordBreak("catsandog", Arrays.asList(new String[]{"cat", "cats", "and", "sand", "dog"})));
        System.out.println(inst.wordBreak("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                Arrays.asList(new String[]{"a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa", "aaaaaaaa", "aaaaaaaaa", "aaaaaaaaaa"})));



    }

    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> result = new ArrayList<>();
        int n = s.length();
        Set<String> dict = new HashSet<>();
        int maxWordLen = 0; // 优化目的。字典中最长单词长度
        for (String word : wordDict) {
            maxWordLen = Math.max(maxWordLen, word.length());
            dict.add(word);
        }
        boolean dp[] = new boolean[n + 1]; // dp[i] 表示前i个字符是否能被字典分解
        dp[0] = true; // trick
        Map<Integer, List<String>> prevs = new HashMap<>();// 表示前i个字符被分解的方式
        for (int i = 1; i <= n; i++) {
            List<String> now = new ArrayList<>();
            for (int j = Math.max(0, i - maxWordLen); j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    if (j == 0) {
                        now.add(s.substring(j, i));
                    } else {
                        for (String segment : prevs.get(j)) {
                            now.add(segment + " " + s.substring(j, i));
                        }
                    }
                }
            }
//            if (dp[i]) {
            prevs.put(i, now);
            System.out.println(i + "," +now);
//            }
        }
        return prevs.get(n);
    }
}
