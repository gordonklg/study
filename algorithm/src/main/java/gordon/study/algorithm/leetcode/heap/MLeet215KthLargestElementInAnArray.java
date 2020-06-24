/**
 Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.

 Example 1:
 Input: [3,2,1,5,6,4] and k = 2
 Output: 5

 Example 2:
 Input: [3,2,3,1,2,4,5,5,6] and k = 4
 Output: 4

 Note:
 You may assume k is always valid, 1 ≤ k ≤ array's length.
 */
package gordon.study.algorithm.leetcode.heap;

import java.util.PriorityQueue;

public class MLeet215KthLargestElementInAnArray {

    public static void main(String[] args) {
        MLeet215KthLargestElementInAnArray inst = new MLeet215KthLargestElementInAnArray();
        System.out.println(inst.findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2)); // 5
        System.out.println(inst.findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4)); // 4
    }

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(k + 1);
        for (int i = 0; i < nums.length; i++) {
            pq.offer(nums[i]);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        return pq.peek();
    }

}
