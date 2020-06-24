/**
 Given a non-negative integer num represented as a string, remove k digits from the number so that the new number is the smallest possible.

 Note:
 The length of num is less than 10002 and will be ≥ k.
 The given num does not contain any leading zero.

 Example 1:
 Input: num = "1432219", k = 3
 Output: "1219"
 Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.

 Example 2:
 Input: num = "10200", k = 1
 Output: "200"
 Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.

 Example 3:
 Input: num = "10", k = 2
 Output: "0"
 Explanation: Remove all the digits from the number and it is left with nothing which is 0.
 */
package gordon.study.algorithm.leetcode.greedy;

import java.util.ArrayDeque;
import java.util.Deque;

public class MLeet402RemoveKDigits {

    public static void main(String[] args) {
        MLeet402RemoveKDigits inst = new MLeet402RemoveKDigits();
        System.out.println(inst.removeKdigits("164275397", 3)); // 125397
        System.out.println(inst.removeKdigits("10200", 1)); // 200
        System.out.println(inst.removeKdigits("10", 2)); // 0
        System.out.println(inst.removeKdigits("9", 1)); // 0
        System.out.println(inst.removeKdigits("1111111", 3)); // 1111
        System.out.println(inst.removeKdigits("14453", 1)); // 1443
    }

    public String removeKdigits(String num, int k) {
        char[] chars = num.toCharArray();
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < chars.length; i++) {
            while (k > 0 && !stack.isEmpty() && chars[i] < stack.peek()) {
                stack.pop();
                --k;
            }
            stack.push(chars[i]);
        }
        while (k-- > 0) {
            stack.pop();
        }
        StringBuilder sb = new StringBuilder();
        boolean skipZero = true;
        while (!stack.isEmpty()) {
            char c = stack.removeLast();
            if (skipZero && c == '0') {
                continue;
            }
            sb.append(c);
            skipZero = false;
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}
