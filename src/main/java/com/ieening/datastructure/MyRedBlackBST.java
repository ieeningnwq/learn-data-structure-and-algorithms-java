package com.ieening.datastructure;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class MyRedBlackBST<K, V> extends MyLinkedBST<K, V> {

    private static final boolean RED = true; // 红色链接
    private static final boolean BLACK = false; // 黑色链接

    /**
     * RedBlackTreeNode
     */
    class RedBlackTreeNode extends MyBinarySearchTreeNode {
        private boolean color;

        /**
         * @return the color
         */
        public boolean getColor() {
            return color;
        }

        /**
         * @param color the color to set
         */
        public void setColor(boolean color) {
            this.color = color;
        }

        RedBlackTreeNode(K key, V value) {
            this(key, value, 1, 0, RED);
        }

        RedBlackTreeNode(K key, V value, int size, int height, boolean color) {
            super(key, value, size, height);
            this.color = color;
        }
    }

    /**
     * 判断结点是否是红结点
     * 
     * @param treeNode 待判断的结点
     * @return 结点与父结点链接的颜色
     */
    private boolean isRed(RedBlackTreeNode treeNode) {
        return treeNode == null ? BLACK : treeNode.getColor() == RED;
    }

    private RedBlackTreeNode rotateLeft(RedBlackTreeNode treeNode) {
        RedBlackTreeNode x = (RedBlackTreeNode) treeNode.getRightChild();
        treeNode.setRightChild(x.getLeftChild());
        x.setLeftChild(treeNode);
        // 更新颜色
        x.setColor(treeNode.getColor());
        treeNode.setColor(RED);
        // 更新size
        x.setSize(treeNode.getSize());
        updateSize(treeNode);
        // 更新高度
        x.setHeight(treeNode.getHeight());
        updateHeight(treeNode);

        return x;
    }

    private RedBlackTreeNode rotateRight(RedBlackTreeNode treeNode) {
        RedBlackTreeNode x = (RedBlackTreeNode) treeNode.getLeftChild();
        treeNode.setLeftChild(x.getRightChild());
        x.setRightChild(treeNode);
        // 更新颜色
        x.setColor(treeNode.getColor());
        treeNode.setColor(RED);
        // 更新size
        x.setSize(treeNode.getSize());
        updateSize(treeNode);
        // 更新高度
        x.setHeight(treeNode.getHeight());
        updateHeight(treeNode);

        return x;
    }

    private void flipColors(RedBlackTreeNode treeNode) {
        treeNode.setColor(RED);
        ((RedBlackTreeNode) treeNode.getLeftChild()).setColor(BLACK);
        ((RedBlackTreeNode) treeNode.getRightChild()).setColor(BLACK);
    }

    @Override
    public void put(K key, V value) {
        super.put(key, value);
        ((RedBlackTreeNode) root).setColor(BLACK);
    }

    @Override
    TreeNode<K, V> afterNodePut(TreeNode<K, V> treeNode) {
        // 修正右倾斜链接
        RedBlackTreeNode node = (RedBlackTreeNode) treeNode;
        if (isRed((RedBlackTreeNode) node.getRightChild()) && !isRed((RedBlackTreeNode) node.getLeftChild()))
            node = rotateLeft(node);
        if (isRed((RedBlackTreeNode) node.getLeftChild())
                && isRed((RedBlackTreeNode) ((RedBlackTreeNode) node.getLeftChild()).getLeftChild()))
            node = rotateRight(node);
        if (isRed((RedBlackTreeNode) node.getLeftChild()) && isRed((RedBlackTreeNode) node.getRightChild()))
            flipColors(node);
        // 更新 size 和 height
        node = (MyRedBlackBST<K, V>.RedBlackTreeNode) super.afterNodePut(node);

        return node;
    }

    @Override
    TreeNode<K, V> createTreeNode(K key, V value) {
        return new RedBlackTreeNode(key, value);
    }

    @Override
    void drawLines(TreeNode<K, V> treeNode, MyMap<TreeNode<K, V>, double[]> coordinatesMap, Graphics2D graphics,
            BufferedImage bufferedImage) {
        if (treeNode == null) {
            return;
        }

        drawLines(treeNode.getLeftChild(), coordinatesMap, graphics, bufferedImage);

        if (treeNode.getLeftChild() != null) {
            graphics.setColor(isRed((RedBlackTreeNode) treeNode.getLeftChild()) ? Color.RED : Color.BLACK);
            graphics.setStroke(new BasicStroke(isRed((RedBlackTreeNode) treeNode.getLeftChild()) ? 3 : 1));
            graphics.draw(new Line2D.Double(bufferedImage.getWidth() * coordinatesMap.get(treeNode)[0],
                    bufferedImage.getHeight() * coordinatesMap.get(treeNode)[1],
                    bufferedImage.getWidth() * coordinatesMap.get(treeNode.getLeftChild())[0],
                    bufferedImage.getHeight() * coordinatesMap.get(treeNode.getLeftChild())[1]));
        }
        if (treeNode.getRightChild() != null) {
            graphics.setColor(isRed((RedBlackTreeNode) treeNode.getRightChild()) ? Color.RED : Color.BLACK);
            graphics.setStroke(new BasicStroke(isRed((RedBlackTreeNode) treeNode.getRightChild()) ? 3 : 1));
            graphics.draw(new Line2D.Double(bufferedImage.getWidth() * coordinatesMap.get(treeNode)[0],
                    bufferedImage.getHeight() * coordinatesMap.get(treeNode)[1],
                    bufferedImage.getWidth() * coordinatesMap.get(treeNode.getRightChild())[0],
                    bufferedImage.getHeight() * coordinatesMap.get(treeNode.getRightChild())[1]));
        }

        drawLines(treeNode.getRightChild(), coordinatesMap, graphics, bufferedImage);
    }

}
