/**
 You are given two non-empty linked lists representing two non-negative integers.
 The digits are stored in reverse order and each of their nodes contain a single digit.
 Add the two numbers and return it as a linked list.

 You may assume the two numbers do not contain any leading zero, except the number 0 itself.

 Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 Output: 7 -> 0 -> 8
 */
package gordon.study.algorithm.leetcode.linkedlist;

import java.util.HashMap;
import java.util.Map;

public class Leet002AddTwoNumbers {

    public static void main(String[] args) {
        Leet002AddTwoNumbers inst = new Leet002AddTwoNumbers();
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
        ListNode head = new ListNode(0);
        ListNode current = head;
        int carry = 0;
        while (l1 != null) {
            int sum = l1.val + carry;
            l1 = l1.next;
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            carry = sum / 10;
            current.next = new ListNode(sum % 10);
            current = current.next;
        }

        while (l2 != null) {
            int sum = l2.val + carry;
            l2 = l2.next;
            carry = sum / 10;
            current.next = new ListNode(sum % 10);
            current = current.next;
        }

        if (carry == 1) {
            current.next = new ListNode(carry);
        }

        return head.next;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
