/**
 Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets
 in the array which gives the sum of zero.

 Note:
 The solution set must not contain duplicate triplets.

 Example:
 Given array nums = [-1, 0, 1, 2, -1, -4],
 A solution set is:
 [ [-1, 0, 1], [-1, -1, 2] ]
 */
package gordon.study.algorithm.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MLeet015ThreeSum {

    public static void main(String[] args) {
        MLeet015ThreeSum inst = new MLeet015ThreeSum();
        System.out.println(inst.threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println(inst.threeSum(new int[]{0, 0, 0, 0, 0}));
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int j = 0; j < nums.length - 2; j++) { // nums[j] is the first element of the triplets
            if (nums[j] > 0) {
                break;
            }
            // skip duplicate element
            if (j > 0 && nums[j] == nums[j - 1]) {
                continue;
            }
            int target = -nums[j];
            int lp = j + 1;
            int rp = nums.length - 1;
            while (lp < rp) {
                if(nums[lp] > target / 2 || nums[rp] < target / 2) {
                    break;
                }
                if (nums[lp] + nums[rp] == target) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[j]);
                    list.add(nums[lp]);
                    list.add(nums[rp]);
                    result.add(list);
                    do {
                        ++lp;
                    } while (lp < rp && nums[lp] == nums[lp - 1]);
                    --rp;
                } else if (nums[lp] + nums[rp] < target) {
                    ++lp;
                } else {
                    --rp;
                }
            }
        }
        return result;
    }


}
