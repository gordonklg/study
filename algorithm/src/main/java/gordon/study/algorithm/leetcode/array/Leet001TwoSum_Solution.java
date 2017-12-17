/**
 https://leetcode.com/problems/two-sum/solution/
 */
package gordon.study.algorithm.leetcode.array;

import java.util.HashMap;
import java.util.Map;

public class Leet001TwoSum_Solution {

    public static void main(String[] args) {
        Leet001TwoSum_Solution inst = new Leet001TwoSum_Solution();
        int[] result = inst.twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println(result[0] + "," + result[1]);
        result = inst.twoSum(new int[]{5, 3, 7, 15}, 10);
        System.out.println(result[0] + "," + result[1]);
        result = inst.twoSum(new int[]{2, 5, 5, 9}, 10);
        System.out.println(result[0] + "," + result[1]);
    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }
}

// only one loop, that's pretty cool!