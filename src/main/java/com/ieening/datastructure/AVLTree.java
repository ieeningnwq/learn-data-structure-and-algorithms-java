package com.ieening.datastructure;

public class AVLTree<K, V> extends AbstractMyBinarySearchTree<K, V> {

    /**
     * 获取结点平衡因子
     * 
     * @param treeNode 待检查结点
     * @return 结点平衡因子
     */
    private int balanceFactor(TreeNode<K, V> treeNode) {
        // 空节点平衡因子为 0
        if (treeNode == null)
            return 0;
        // 节点平衡因子 = 左子树高度 - 右子树高度
        return height(treeNode.getLeftChild()) - height(treeNode.getRightChild());
    }

    /**
     * 右旋操作
     * 
     * @param treeNode 失衡结点
     * @return 旋转后子树的根结点
     */
    private TreeNode<K, V> rightRotate(TreeNode<K, V> treeNode) {
        TreeNode<K, V> child = treeNode.getLeftChild();
        TreeNode<K, V> grandChild = child.getRightChild();
        child.setRightChild(treeNode);
        treeNode.setLeftChild(grandChild);
        // 更新结点高度
        updateHeight(treeNode);
        updateHeight(child);
        // 更新结点 size
        updateSize(treeNode);
        updateSize(child);

        return child;
    }

    /**
     * 左旋操作
     * 
     * @param treeNode 失衡结点
     * @return 旋转后子树的根结点
     */
    private TreeNode<K, V> leftRotate(TreeNode<K, V> treeNode) {
        TreeNode<K, V> child = (TreeNode<K, V>) treeNode.getRightChild();
        TreeNode<K, V> grandChild = (TreeNode<K, V>) child.getLeftChild();
        child.setLeftChild(treeNode);
        treeNode.setRightChild(grandChild);
        // 更新结点高度
        updateHeight(treeNode);
        updateHeight(child);
        // 更新结点 size
        updateSize(treeNode);
        updateSize(child);

        return child;
    }

    /**
     * 先右旋后左旋
     * 
     * @param treeNode 失衡结点
     * @return 旋转后子树根结点
     */
    private TreeNode<K, V> rightLeftRotate(TreeNode<K, V> treeNode) {
        treeNode.setRightChild(rightRotate((TreeNode<K, V>) treeNode.getRightChild()));
        return leftRotate(treeNode);
    }

    /**
     * 先左旋后右旋操作
     * 
     * @param treeNode 失衡结点
     * @return 旋转后子树根结点
     */
    private TreeNode<K, V> leftRightRotate(TreeNode<K, V> treeNode) {
        treeNode.setLeftChild(leftRotate(treeNode.getLeftChild()));
        return rightRotate(treeNode);
    }

    private TreeNode<K, V> rotate(TreeNode<K, V> treeNode) {
        int bf = balanceFactor(treeNode); // 获取 treeNode 结点平衡因子
        if (bf > 1) { // 左偏树
            if (balanceFactor(treeNode.getLeftChild()) >= 0)
                return rightRotate(treeNode);
            else
                return leftRightRotate(treeNode);
        }
        if (bf < -1) {// 右偏树
            if (balanceFactor(treeNode.getRightChild()) <= 0)
                return leftRotate(treeNode);
            else
                return rightLeftRotate(treeNode);

        }
        return treeNode;// 平衡树，无需旋转操作，直接返回
    }

    @Override
    TreeNode<K, V> afterNodePut(TreeNode<K, V> treeNode) {
        treeNode = super.afterNodePut(treeNode);
        return rotate(treeNode);
    }

    @Override
    TreeNode<K, V> afterNodeDelete(TreeNode<K, V> treeNode) {
        treeNode = super.afterNodeDelete(treeNode);
        return rotate(treeNode);
    }

}
