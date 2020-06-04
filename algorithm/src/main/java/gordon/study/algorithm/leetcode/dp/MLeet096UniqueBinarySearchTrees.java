/**
 Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?

 Example:
 Input: 3
 Output: 5
 Explanation:
 Given n = 3, there are a total of 5 unique BST's:

 1         3     3      2      1
 ~\       /     /      / \      \
 ~~3     2     1      1   3      2
 ~/     /       \                 \
 2     1         2                 3
 */
/*
 C(3)=5，C(4)=?
 左子树3节点-根-右子树0节点：用4做根节点，可能结构=C(3)
 左子树2节点-根-右子树1节点：用3做根节点，1,2在左子树，可能结构为C(2)，3在右子树，可能结构为C(1)，总可能结构为C(2)*C(1)
 左子树1节点-根-右子树2节点：用2做根节点，总可能结构为C(1)*C(2)
 左子树0节点-根-右子树3节点：用1做根节点，可能结构=C(3)
 */
package gordon.study.algorithm.leetcode.dp;

public class MLeet096UniqueBinarySearchTrees {

    public static void main(String[] args) {
        MLeet096UniqueBinarySearchTrees inst = new MLeet096UniqueBinarySearchTrees();
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
