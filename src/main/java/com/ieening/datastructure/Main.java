package com.ieening.datastructure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        // PriorityQueue
        // Arrays.copyOf(null, 0)

        // HashMap<String, String> foo = new HashMap<String, String>();
        // foo.equals(foo);
        // HashMap

        // PriorityQueue

        // String
        // ArrayList
        // ArrayDeque
        // Double
        // HashSet
        // Queue
        int[] a = new int[]{1,2};
    }

}

class Solution {
    private static class Node {
        /** * 节点值 */
        public int value;
        /** * 左节点 */
        public Node left;
        /** * 右节点 */
        public Node right;

        public Node(int value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    public static void dfs(Node treeNode) {
        if (treeNode == null) {
            return;
        }
        // 遍历节点
        // process(treeNode);
        // 遍历左节点
        dfs(treeNode.left);
        // 遍历右节点
        dfs(treeNode.right);
    }
}
