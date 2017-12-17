package gordon.study.algorithm.leetcode.divide;

public class Leet169MajorityElement {

    public static void main(String[] args) {
        Leet169MajorityElement inst = new Leet169MajorityElement();
        System.out.println(inst.majorityElement(new int[] { 1, 1, 1, 1, 2, 3, 4, 5, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7 }));
        System.out.println(inst.majorityElement(new int[] { 3, 3, 4 }));
        System.out.println(inst.majorityElement(new int[] { 12, 52, 12, 70, 12, 61, 12, 12, 50, 72, 82, 12, 11, 25, 28, 43, 40, 12, 12, 53,
                12, 12, 98, 12, 12, 92, 81, 2, 12, 15, 40, 12, 12, 9, 12, 31, 12, 12, 12, 12, 77, 12, 12, 50, 12, 12, 12, 93, 41, 92, 12,
                12, 12, 12, 12, 12, 12, 12, 12, 37, 48, 14, 12, 70, 82, 12, 80, 12, 12, 12, 12, 56, 30, 12, 8, 12, 50, 12, 20, 12, 21, 97,
                12, 42, 12, 10, 12, 38, 73, 15, 9, 11, 79, 12, 12, 28, 51, 12, 15, 12 }));
    }

    int majorityElement(int[] nums) {
        int val = 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (count == 0) {
                val = nums[i];
                ++count;
            } else {
                if (nums[i] == val) {
                    ++count;
                } else {
                    --count;
                }
            }
        }
        return val;
    }
}
