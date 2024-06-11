package com.ieening.doexercises.leetcode;

import com.ieening.doexercises.leetcode.predefine.ListNode;

public class Q206ReverseLinkedList {
    public ListNode reverseList(ListNode head) {
        ListNode[] reverseHead=new ListNode[]{head};
        reverseRecursion(head, reverseHead).next=null;
        return reverseHead[0];
    }

    private ListNode reverseRecursion(ListNode head,ListNode[] reverseHead) {
        // 如何使用递归呢
        if (head.next == null){
            reverseHead[0]=head;
            return head;
        }

        // 拆分
        ListNode node = reverseRecursion(head.next, reverseHead);
        node.next = head;
        return head;
    }

    public static void main(String[] args) {
        Q206ReverseLinkedList solution = new Q206ReverseLinkedList();
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null)))));
        solution.reverseList(head);
    }

}

