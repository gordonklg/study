/**
 Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, find the area of largest rectangle in the histogram.

 Example:
 Input: [2,1,5,6,2,3]
 Output: 10
 */
package gordon.study.algorithm.leetcode.array;

public class HLeet084LargestRectangleInHistogram {

    public static void main(String[] args) {
        HLeet084LargestRectangleInHistogram inst = new HLeet084LargestRectangleInHistogram();
        System.out.println(inst.largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
    }

    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        if (n == 0) {
            return 0;
        }
        int max = 0;
        int[] leftLessPos = new int[n]; // 对当前元素i而言，往左遍历第一个小于该元素值的元素位置，可能为-1
        int[] rightLessPos = new int[n]; // 对当前元素i而言，往右遍历第一个小于该元素值的元素位置，可能为n
        leftLessPos[0] = -1;
        rightLessPos[n - 1] = n;
        for (int i = 1; i < n; i++) {
            int pos = i - 1;
            while (pos >= 0 && heights[pos] >= heights[i]) {
                pos = leftLessPos[pos]; // 算法的精华，保证整体时间复杂度为O(n)
            }
            leftLessPos[i] = pos;
        }
        for (int i = n - 2; i >= 0; i--) {
            int pos = i + 1;
            while (pos < n && heights[pos] >= heights[i]) {
                pos = rightLessPos[pos];
            }
            rightLessPos[i] = pos;
        }
        for (int i = 0; i < n; i++) {
            int width = rightLessPos[i] - leftLessPos[i] - 1;
            max = Math.max(max, width * heights[i]);
        }
        return max;
    }
}
