/**
 Given a string, find the length of the longest substring without repeating characters.

 Examples:

 Given "abcabcbb", the answer is "abc", which the length is 3.

 Given "bbbbb", the answer is "b", with the length of 1.

 Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 */
package gordon.study.algorithm.leetcode.string;

import gordon.study.algorithm.leetcode.linkedlist.Leet002AddTwoNumbers;

import java.util.HashMap;
import java.util.Map;

public class Leet003LongestSubstringWithoutRepeatingCharacters {

    public static void main(String[] args) {
        Leet003LongestSubstringWithoutRepeatingCharacters inst = new Leet003LongestSubstringWithoutRepeatingCharacters();
        System.out.println(inst.lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(inst.lengthOfLongestSubstring("bbbbb")); // 1
        System.out.println(inst.lengthOfLongestSubstring("pwwkew")); // 3
        System.out.println(inst.lengthOfLongestSubstring("abcdefg")); // 7
        System.out.println(inst.lengthOfLongestSubstring("abba")); // 2
    }

    public int lengthOfLongestSubstring(String s) {
        int maxLen = 0, begin = 0;
        // key: character, value: index of the character in the string.
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                maxLen = Math.max(maxLen, i - begin);
                int pos = map.get(c);
                for (int j = begin; j < pos; j++) {
                    map.remove(s.charAt(j));
                }
                begin = pos + 1;
            }
            map.put(c, i);
        }
        maxLen = Math.max(maxLen, s.length() - begin);
        return maxLen;
    }
}
