package gordon.study.algorithm.leetcode;

public class Leet674LongestContinuousIncreasingSubsequence {

    public static void main(String[] args) {
        Leet674LongestContinuousIncreasingSubsequence inst = new Leet674LongestContinuousIncreasingSubsequence();
        System.out.println(inst.findLengthOfLCIS(new int[] { 1 }));
        System.out.println(inst.findLengthOfLCIS(new int[] { 1, 2 }));
        System.out.println(inst.findLengthOfLCIS(new int[] { 1, 1, 1 }));
        System.out.println(inst.findLengthOfLCIS(new int[] { 1, 2, 1, 2, 3 }));
        System.out.println(inst.findLengthOfLCIS(new int[] { 1, 2, 3, 1, 2 }));
        System.out.println(inst.findLengthOfLCIS(new int[] { 1, 2, 3, 4, 3, 4, 5, 6, 7, 3, 2, 6 }));
        System.out.println(inst.findLengthOfLCIS(new int[] { Integer.MIN_VALUE, 0, Integer.MAX_VALUE }));
        System.out.println(inst.findLengthOfLCIS(new int[] { 1, 2, Integer.MIN_VALUE, 0, Integer.MAX_VALUE }));
    }

    int findLengthOfLCIS(int[] nums) {
        int maxLen = 0;
        int curMaxLen = 0;
        int last = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > last || (i == 0 && nums[i] == Integer.MIN_VALUE)) {
                ++curMaxLen;
            } else {
                if (curMaxLen > maxLen) {
                    maxLen = curMaxLen;
                }
                curMaxLen = 1;
            }
            last = nums[i];
        }
        if (curMaxLen > maxLen) {
            maxLen = curMaxLen;
        }
        return maxLen;
    }
}
