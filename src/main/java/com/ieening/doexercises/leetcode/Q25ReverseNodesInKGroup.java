package com.ieening.doexercises.leetcode;

/**
 * Definition for singly-linked list.
 */
class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

/**
 * Q25ReverseNodesInKGroup
 */
public class Q25ReverseNodesInKGroup {

    public ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1)
            return head;
        ListNode dummyNode = new ListNode(0, head), preCurrent = dummyNode, leftNode, current, currentNext, leftHead;
        int left = 1, right = left + k - 1;
        while (left <= right && head != null) {
            head = head.next;
            left++;
            if (left - 1 == right) { // 可以翻转
                // 更新结点值
                leftNode = preCurrent;
                preCurrent = leftNode.next;
                current = preCurrent.next;
                for (int i = 1; i < k; i++) { // k=2，翻转一次
                    leftHead = leftNode.next;
                    currentNext = current.next;

                    leftNode.next = current;
                    current.next = leftHead;
                    preCurrent.next = currentNext;

                    // 更新 current
                    current = currentNext;
                }
                // 更新 left 和 right
                left = right + 1;
                right = left + k - 1;
            }
        }
        return dummyNode.next;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null)))));
        Q25ReverseNodesInKGroup rGroup = new Q25ReverseNodesInKGroup();
        head = rGroup.reverseKGroup(head, 3);
        for (; head != null; head = head.next) {
            System.out.print(head.val + "-");
        }
    }
}
