/**
 https://leetcode.com/problems/longest-substring-without-repeating-characters/solution/
 */
package gordon.study.algorithm.leetcode.string;

import java.util.HashMap;
import java.util.Map;

public class Leet003LongestSubstringWithoutRepeatingCharacters_Solution {

    public static void main(String[] args) {
        Leet003LongestSubstringWithoutRepeatingCharacters_Solution inst = new Leet003LongestSubstringWithoutRepeatingCharacters_Solution();
        System.out.println(inst.lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(inst.lengthOfLongestSubstring("bbbbb")); // 1
        System.out.println(inst.lengthOfLongestSubstring("pwwkew")); // 3
        System.out.println(inst.lengthOfLongestSubstring("abcdefg")); // 7
        System.out.println(inst.lengthOfLongestSubstring("abba")); // 2
    }

    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }
}
