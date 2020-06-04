/**
 Given an array nums and a value val, remove all instances of that value in-place and return the new length.

 Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.

 The order of elements can be changed. It doesn't matter what you leave beyond the new length.

 Example 1:
 Given nums = [3,2,2,3], val = 3,
 Your function should return length = 2, with the first two elements of nums being 2.
 It doesn't matter what you leave beyond the returned length.

 Example 2:
 Given nums = [0,1,2,2,3,0,4,2], val = 2,
 Your function should return length = 5, with the first five elements of nums containing 0, 1, 3, 0, and 4.
 Note that the order of those five elements can be arbitrary.
 It doesn't matter what values are set beyond the returned length.
 */
package gordon.study.algorithm.leetcode.array;

public class Leet027RemoveElement {

    public static void main(String[] args) {
        Leet027RemoveElement inst = new Leet027RemoveElement();
        int[] input = new int[]{0, 1, 2, 2, 3, 0, 4, 2};
        int len = inst.removeElement2(input, 2);
        for (int i = 0; i < len; i++) {
            System.out.println(input[i]);
        }
    }

    public int removeElement(int[] nums, int val) {
        int pf = 0, pb = -1; // 前后两个位置指示器
        while (pf < nums.length) {
            if (nums[pf] != val) {
                nums[++pb] = nums[pf++];
            } else {
                pf++;
            }
        }
        return pb + 1;
    }

    // 减少不必要的赋值操作，从最右边取元素填充（待删元素较少）
    public int removeElement2(int[] nums, int val) {
        int pl = 0, pr = nums.length - 1;
        while (pl <= pr) {
            if (nums[pl] == val) {
                nums[pl] = nums[pr--];
            } else {
                pl++;
            }
        }
        return pr + 1;
    }
}
