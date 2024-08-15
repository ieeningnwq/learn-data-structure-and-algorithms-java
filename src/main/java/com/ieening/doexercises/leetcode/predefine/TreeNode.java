package com.ieening.doexercises.leetcode.predefine;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return String.format("TreeNode[val=%d, left=%s, right=%s]", val,
                left == null ? "null" : Integer.toString(left.val),
                right == null ? "null" : Integer.toString(right.val));
    }

}
