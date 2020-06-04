/**
 Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.

 If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).

 The replacement must be in-place and use only constant extra memory.

 Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.

 1,2,3 → 1,3,2
 3,2,1 → 1,2,3
 1,1,5 → 1,5,1
 */
package gordon.study.algorithm.leetcode.array;

import java.util.Arrays;

public class MLeet031NextPermutation {

    public static void main(String[] args) {
        MLeet031NextPermutation inst = new MLeet031NextPermutation();
        int[] a = new int[]{3, 5, 7, 4, 8, 8, 6, 6, 4, 2};
        inst.nextPermutation(a);
        Arrays.stream(a).forEach(i -> System.out.print(i + " ")); // 3 5 7 6 2 4 4 6 8 8
        System.out.println();
        int[] b = new int[]{1, 2, 3};
        inst.nextPermutation(b);
        Arrays.stream(b).forEach(i -> System.out.print(i + " ")); // 1 3 2
        System.out.println();
        int[] c = new int[]{3, 2, 1};
        inst.nextPermutation(c);
        Arrays.stream(c).forEach(i -> System.out.print(i + " ")); // 1 2 3
    }

    public void nextPermutation(int[] nums) {
        int p = nums.length - 2;
        while (p >= 0 && nums[p] >= nums[p + 1]) { // reverse find the first decline element's position
            --p;
        }
        if (p >= 0) { // in case of [3, 2, 1], p is -1
            int q = nums.length - 1;
            while (nums[q] <= nums[p]) { // reverse find the element a little bigger than nums[p]
                --q;
            }
            // swap p and q
            int temp = nums[p];
            nums[p] = nums[q];
            nums[q] = temp;
        }
        // reverse the subarray after p
        int lp = p + 1;
        int rp = nums.length - 1;
        while (lp < rp) {
            int temp = nums[lp];
            nums[lp] = nums[rp];
            nums[rp] = temp;
            ++lp;
            --rp;
        }
    }

}
