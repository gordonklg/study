/**
 https://leetcode.com/problems/add-two-numbers/solution/
 */
package gordon.study.algorithm.leetcode.linkedlist;

public class Leet002AddTwoNumbers_Solution {

    public static void main(String[] args) {
        Leet002AddTwoNumbers_Solution inst = new Leet002AddTwoNumbers_Solution();
        ListNode input1 = createListNode("243");
        ListNode input2 = createListNode("562");
        ListNode result = inst.addTwoNumbers(input1, input2);
        printListNode(result); // 706

        input1 = createListNode("802");
        input2 = createListNode("297");
        result = inst.addTwoNumbers(input1, input2);
        printListNode(result); // 0001

        input1 = createListNode("9991");
        input2 = createListNode("1");
        result = inst.addTwoNumbers(input1, input2);
        printListNode(result); // 0002

        input1 = createListNode("01");
        input2 = createListNode("999");
        result = inst.addTwoNumbers(input1, input2);
        printListNode(result); // 9001
    }

    private static ListNode createListNode(String value) {
        ListNode head = new ListNode(0);
        ListNode current = head;
        for (int i = 0; i < value.length(); i++) {
            int digit = Integer.parseInt(value.charAt(i) + "");
            current.next = new ListNode(digit);
            current = current.next;
        }
        return head.next;
    }

    private static void printListNode(ListNode node) {
        while (node != null) {
            System.out.print(node.val);
            node = node.next;
        }
        System.out.println();
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
