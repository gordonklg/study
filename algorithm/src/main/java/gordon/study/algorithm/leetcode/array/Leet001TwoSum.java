/**
 Given an array of integers, return indices of the two numbers such that they add up to a specific target.

 You may assume that each input would have exactly one solution, and you may not use the same element twice.

 Example:
 Given nums = [2, 7, 11, 15], target = 9,
 Because nums[0] + nums[1] = 2 + 7 = 9,
 return [0, 1].
 */
package gordon.study.algorithm.leetcode.array;

import java.util.HashMap;
import java.util.Map;

public class Leet001TwoSum {

    public static void main(String[] args) {
        Leet001TwoSum inst = new Leet001TwoSum();
        int[] result = inst.twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println(result[0] + "," + result[1]);
        result = inst.twoSum(new int[]{5, 3, 7, 15}, 10);
        System.out.println(result[0] + "," + result[1]);
        result = inst.twoSum(new int[]{2, 5, 5, 9}, 10);
        System.out.println(result[0] + "," + result[1]);
    }

    public int[] twoSum(int[] nums, int target) {
        // key: int value, value: array index.
        // According to the assumption, one int value will appear at most twice.
        // If the int value is duplicated, the array index would be the latter one.
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        // For each int value, try to find the complement value in the map.
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                return new int[]{i, map.get(complement)};
            }
        }
        return null;
    }
}

// COOL! My code is nearly the same as the solution approach 2.