/**
 Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

 You may assume no duplicates in the array.

 Example 1:
 Input: [1,3,5,6], 5
 Output: 2

 Example 2:
 Input: [1,3,5,6], 2
 Output: 1

 Example 3:
 Input: [1,3,5,6], 7
 Output: 4

 Example 4:
 Input: [1,3,5,6], 0
 Output: 0
 */
package gordon.study.algorithm.leetcode.array;

public class Leet035SearchInsertPosition {

    public static void main(String[] args) {
        Leet035SearchInsertPosition inst = new Leet035SearchInsertPosition();
        System.out.println(inst.searchInsert(new int[]{1, 3, 5, 6}, 5)); // 2
        System.out.println(inst.searchInsert(new int[]{1, 3, 5, 6}, 2)); // 1
        System.out.println(inst.searchInsert(new int[]{1, 3, 5, 6}, 7)); // 4
        System.out.println(inst.searchInsert(new int[]{1, 3, 5, 6}, 0)); // 0

        int[] input = new int[100];
        for (int i = 0; i < input.length; i++) {
            input[i] = i * 2;
        }
        System.out.println(inst.searchInsert(input, 52)); // 26
        System.out.println(inst.searchInsert(input, 13)); // 7
        System.out.println(inst.searchInsert(input, 198)); // 99
        System.out.println(inst.searchInsert(input, 222)); // 100
        System.out.println(inst.searchInsert(input, 0)); // 0
        System.out.println(inst.searchInsert(input, -2)); // 0
    }

    public int searchInsert(int[] nums, int target) {
        if (nums.length < 50) {
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] >= target) {
                    return i;
                }
            }
            return nums.length;
        } else {
            int lb = 0; // 左边界
            int rb = nums.length - 1; // 右边界
            int pos = 0;
            while (lb < rb - 4) { // 留一段范围用来对比检索，提高性能
                pos = (lb + rb) / 2; // binary search
                if (nums[pos] == target) {
                    return pos;
                } else if (nums[pos] > target) {
                    rb = pos - 1;
                } else {
                    lb = pos + 1;
                }
            }
            for (int i = lb; i <= rb; i++) {
                if (nums[i] >= target) {
                    return i;
                }
            }
            return rb + 1;
        }
    }
}
