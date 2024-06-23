package com.ieening.doexercises.leetcode;

import com.ieening.doexercises.leetcode.predefine.TreeNode;

public class Q226InvertBinaryTree {
    public TreeNode invertTree(TreeNode root) {
        // 递归终止条件
        if (root == null || (root.left == null && root.right == null))
            return root;
        // 递归拆分问题
        TreeNode right = invertTree(root.left);
        TreeNode left = invertTree(root.right);
        // 递归合并问题
        root.right = right;
        root.left = left;
        return root;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4, new TreeNode(2, new TreeNode(1, null, null), new TreeNode(3, null, null)),
                new TreeNode(7, new TreeNode(6, null, null), new TreeNode(9, null, null)));
        Q226InvertBinaryTree solution = new Q226InvertBinaryTree();
        root = solution.invertTree(root);
        System.out.println(root);
    }
}
