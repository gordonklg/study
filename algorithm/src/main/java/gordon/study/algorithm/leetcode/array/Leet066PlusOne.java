/**
 Given a non-empty array of digits representing a non-negative integer, plus one to the integer.

 The digits are stored such that the most significant digit is at the head of the list, and each element in the array contain a single digit.

 You may assume the integer does not contain any leading zero, except the number 0 itself.

 Example 1:
 Input: [1,2,3]
 Output: [1,2,4]
 Explanation: The array represents the integer 123.

 Example 2:
 Input: [4,3,2,1]
 Output: [4,3,2,2]
 Explanation: The array represents the integer 4321.
 */
package gordon.study.algorithm.leetcode.array;

import java.util.Arrays;

public class Leet066PlusOne {

    public static void main(String[] args) {
        Leet066PlusOne inst = new Leet066PlusOne();
        Arrays.stream(inst.plusOne(new int[]{4, 3, 2, 9})).forEach((i) -> System.out.print(i + " "));
        System.out.println();
        Arrays.stream(inst.plusOne(new int[]{9, 9, 9})).forEach((i) -> System.out.print(i + " "));
        System.out.println();
        Arrays.stream(inst.plusOne(new int[]{9, 0, 9})).forEach((i) -> System.out.print(i + " "));
    }

    public int[] plusOne(int[] digits) {
        int n = digits.length;
        digits[n - 1]++;
        for (int i = n - 1; i > 0; i--) {
            if (digits[i] > 9) {
                digits[i] = 0;
                digits[i - 1]++;
            }
        }
        if(digits[0] > 9) {
            int[] result = new int[n + 1];
            result[0] = 1;
            result[1] = 0;
            System.arraycopy(digits, 1, result, 2, n - 1);
            return result;
        }

        return digits;
    }

}
