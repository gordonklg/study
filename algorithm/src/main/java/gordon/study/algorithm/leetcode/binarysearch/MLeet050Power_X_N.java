/**
 Implement pow(x, n), which calculates x raised to the power n (x^n).

 Example 1:
 Input: 2.00000, 10
 Output: 1024.00000

 Example 2:
 Input: 2.10000, 3
 Output: 9.26100

 Example 3:
 Input: 2.00000, -2
 Output: 0.25000
 Explanation: 2-2 = 1/22 = 1/4 = 0.25

 Note:
 -100.0 < x < 100.0
 n is a 32-bit signed integer, within the range [−2^31, 2^31 − 1]
 */
package gordon.study.algorithm.leetcode.binarysearch;

public class MLeet050Power_X_N {

    public static void main(String[] args) {
        MLeet050Power_X_N inst = new MLeet050Power_X_N();
        System.out.println(inst.myPow(2.00000, 20));
        System.out.println(inst.myPow(2.00000, Integer.MAX_VALUE));
        System.out.println(inst.myPow(1.00000001, Integer.MAX_VALUE));
        System.out.println(inst.myPow(2.00000, 0));
        System.out.println(inst.myPow(2.00000, -20));
        System.out.println(inst.myPow(2.00000, Integer.MIN_VALUE));
        System.out.println(inst.myPow(1.00000001, Integer.MIN_VALUE));
        System.out.println(inst.myPow(1.00000001, Integer.MIN_VALUE + 1));
    }

    public double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n == Integer.MIN_VALUE) {
            n = n / 2; // MIN_VALUE is even
            x = x * x;
        }
        if (n < 0) {
            n = -n;
            x = 1 / x;
        }

        if (n % 2 == 0) {
            return myPow(x * x, n / 2);
        } else {
            return x * myPow(x * x, n / 2);
        }
    }
}
