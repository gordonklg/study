/**
 Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.

 Example:
 Input: 3
 Output:
 [
 [1,null,3,2],
 [3,2,null,1],
 [3,1,null,null,2],
 [2,1,3],
 [1,null,2,null,3]
 ]
 Explanation:
 The above output corresponds to the 5 unique BST's shown below:
 1         3     3      2      1
 ~\       /     /      / \      \
 ~~3     2     1      1   3      2
 ~/     /       \                 \
 2     1         2                 3
 */
package gordon.study.algorithm.leetcode;

public class MLeet095UniqueBinarySearchTrees2 {

    public static void main(String[] args) {
        MLeet095UniqueBinarySearchTrees2 inst = new MLeet095UniqueBinarySearchTrees2();
        System.out.println(inst.numTrees(3));
        System.out.println(inst.numTrees(5));
    }

    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] += dp[j] * dp[i - j - 1];
            }
        }
        return dp[n];
    }

}
