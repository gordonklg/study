/**
 Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.

 For example, given the following triangle

 [
 [2],
 [3,4],
 [6,5,7],
 [4,1,8,3]
 ]
 The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
 */
package gordon.study.algorithm.leetcode.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MLeet120Triangle {

    public static void main(String[] args) {
        MLeet120Triangle inst = new MLeet120Triangle();
        List<List<Integer>> input = new ArrayList<>();
        input.add(Arrays.asList(2));
        input.add(Arrays.asList(3, 4));
        input.add(Arrays.asList(6, 5, 7));
        input.add(Arrays.asList(4, 1, 8, 3));
        System.out.println(inst.minimumTotal(input)); // 11
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        Integer[] dp = triangle.get(triangle.size() - 1).toArray(new Integer[triangle.size() - 1]);
        for (int i = triangle.size() - 2; i >= 0; i--) {
            List<Integer> line = triangle.get(i);
            for (int j = 0; j < line.size(); j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + line.get(j);
            }
        }
        return dp[0];
    }

}
