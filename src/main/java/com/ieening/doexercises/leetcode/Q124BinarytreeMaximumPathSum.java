package com.ieening.doexercises.leetcode;

import java.util.ArrayList;
import java.util.List;

import com.ieening.doexercises.leetcode.predefine.TreeNode;

public class Q124BinarytreeMaximumPathSum {
    public TreeNode maxPathSum(TreeNode root, List<TreeNode> leftList, List<TreeNode> rightList, int leftIndex,
            int rightIndex) {
        // 递归后序遍历
        // 访问左节点
        if (root.left != null) {
            leftList.add(root.left);
            maxPathSum(root.left, leftList, new ArrayList<>(), leftIndex + 1, 0);
        }
        // 访问右节点
        if (root.right != null) {
            rightList.add(root.right);
            maxPathSum(root.right, new ArrayList<>(), rightList, 0, rightIndex + 1);
        }
        System.out.println(root.toString() + " leftList:" + leftList.toString() + "_" + leftIndex + " rightList:"
                + rightList.toString() + " " + rightIndex);
        return root;
    }

    public static void main(String[] args) {
        Q124BinarytreeMaximumPathSum solution = new Q124BinarytreeMaximumPathSum();
        TreeNode root = new TreeNode(-10, new TreeNode(9, null, null),
                new TreeNode(20, new TreeNode(15, null, null), new TreeNode(7, null, null)));
        // solution.treeNodeList.add(solution.maxPathSum(root));
        // System.out.println(solution.treeNodeList);
        solution.maxPathSum(root, new ArrayList<>(), new ArrayList<>(), 0, 0);
    }
}
