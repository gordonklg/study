/**
 Given an array nums of n integers and an integer target, are there elements a, b, c, and d in nums such that a + b + c + d = target?
 Find all unique quadruplets in the array which gives the sum of target.

 Note:
 The solution set must not contain duplicate quadruplets.

 Example:
 Given array nums = [1, 0, -1, 0, -2, 2], and target = 0.
 A solution set is:
 [ [-1,  0, 0, 1], [-2, -1, 1, 2], [-2,  0, 0, 2]]
 */
package gordon.study.algorithm.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MLeet018FourSum {

    public static void main(String[] args) {
        MLeet018FourSum inst = new MLeet018FourSum();
        System.out.println(inst.fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0));
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int j = 0; j < nums.length - 3; j++) { // nums[j] is the first element of the quadruplets
            // skip duplicate element
            if (j > 0 && nums[j] == nums[j - 1]) {
                continue;
            }
            int expect3Sum = target - nums[j];
            for (int i = j + 1; i < nums.length - 2; i++) { // nums[i] is the second element of the quadruplets
                // skip duplicate element
                if (i > j + 1 && nums[i] == nums[i - 1]) {
                    continue;
                }
                int expect2Sum = expect3Sum - nums[i];
                int lp = i + 1;
                int rp = nums.length - 1;
                while (lp < rp) {
                    int sum = nums[lp] + nums[rp];
                    if (sum == expect2Sum) {
                        List<Integer> list = new ArrayList<>();
                        list.add(nums[j]);
                        list.add(nums[i]);
                        list.add(nums[lp]);
                        list.add(nums[rp]);
                        result.add(list);
                        do {
                            ++lp;
                        } while (lp < rp && nums[lp] == nums[lp - 1]);
                        --rp;
                    } else if (sum < expect2Sum) {
                        ++lp;
                    } else {
                        --rp;
                    }
                }
            }
        }
        return result;
    }

}
