package com.ieening.datastructure;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

/**
 * 左倾红黑树，2-3 树版本
 */
public class MyRedBlackBST<K, V> extends MyLinkedBST<K, V> {

    /**
     * 红黑树结点链接颜色
     */
    public static enum TreeNodeLinkColor {
        RED, BLACK;

        public static TreeNodeLinkColor invertColor(TreeNodeLinkColor color) {
            return color == RED ? BLACK : RED;
        }
    }

    /**
     * RedBlackTreeNode
     */
    class RedBlackTreeNode extends MyBinarySearchTreeNode {
        private TreeNodeLinkColor color;

        /**
         * @return the color
         */
        public TreeNodeLinkColor getColor() {
            return color;
        }

        /**
         * @param color the color to set
         */
        public void setColor(TreeNodeLinkColor color) {
            this.color = color;
        }

        RedBlackTreeNode(K key, V value) {
            this(key, value, 1, 0, TreeNodeLinkColor.RED);
        }

        RedBlackTreeNode(K key, V value, int size, int height, TreeNodeLinkColor color) {
            super(key, value, size, height);
            this.color = color;
        }

        @Override
        public RedBlackTreeNode getLeftChild() {
            return (RedBlackTreeNode) super.getLeftChild();
        }

        @Override
        public RedBlackTreeNode getRightChild() {
            return (RedBlackTreeNode) super.getRightChild();
        }
    }

    /**
     * 返回根结点
     */
    @Override
    public RedBlackTreeNode getRoot() {
        return (RedBlackTreeNode) super.getRoot();
    }

    /**
     * 判断结点是否是红结点
     * 
     * @param treeNode 待判断的结点
     * @return 结点与父结点链接的颜色
     */
    private boolean isRed(RedBlackTreeNode treeNode) {
        return treeNode == null ? false : treeNode.getColor() == TreeNodeLinkColor.RED;
    }

    /**
     * 把右倾红链接旋转为左倾
     * 
     * @param treeNode 待旋转结点
     * @return 旋转后子树根结点
     */
    private RedBlackTreeNode rotateLeft(RedBlackTreeNode treeNode) {
        assert treeNode != null && isRed(treeNode.getRightChild());

        RedBlackTreeNode x = treeNode.getRightChild();
        treeNode.setRightChild(x.getLeftChild());
        x.setLeftChild(treeNode);
        // 更新颜色
        x.setColor(treeNode.getColor());
        treeNode.setColor(TreeNodeLinkColor.RED);
        // 更新size
        x.setSize(treeNode.getSize());
        updateSize(treeNode);
        // 更新高度
        x.setHeight(treeNode.getHeight());
        updateHeight(treeNode);

        return x;
    }

    /**
     * 将左倾红链接旋转为右倾
     * 
     * @param treeNode
     * @return
     */
    private RedBlackTreeNode rotateRight(RedBlackTreeNode treeNode) {
        assert treeNode != null && isRed(treeNode.getLeftChild());

        RedBlackTreeNode x = treeNode.getLeftChild();
        treeNode.setLeftChild(x.getRightChild());
        x.setRightChild(treeNode);
        // 更新颜色
        x.setColor(treeNode.getColor());
        treeNode.setColor(TreeNodeLinkColor.RED);
        // 更新size
        x.setSize(treeNode.getSize());
        updateSize(treeNode);
        // 更新高度
        x.setHeight(treeNode.getHeight());
        updateHeight(treeNode);

        return x;
    }

    /**
     * 反转结点以及两个子结点的颜色，<em>treeNode</em>必须和两个子结点颜色相反
     * 
     * @param treeNode 结点
     */
    private void flipColors(RedBlackTreeNode treeNode) {
        // h must have opposite color of its two children
        assert (treeNode != null) && (treeNode.getLeftChild() != null) && (treeNode.getRightChild() != null);
        assert (!isRed(treeNode) && isRed(treeNode.getLeftChild())
                && isRed(treeNode.getRightChild()))
                || (isRed(treeNode) && !isRed(treeNode.getLeftChild())
                        && !isRed(treeNode.getRightChild()));

        treeNode.setColor(TreeNodeLinkColor.invertColor(treeNode.getColor()));
        RedBlackTreeNode leftChild = treeNode.getLeftChild();
        leftChild.setColor(TreeNodeLinkColor.invertColor(leftChild.getColor()));
        RedBlackTreeNode rightChild = treeNode.getRightChild();
        rightChild.setColor(TreeNodeLinkColor.invertColor(rightChild.getColor()));
    }

    @Override
    public void put(K key, V value) {
        super.put(key, value);
        getRoot().setColor(TreeNodeLinkColor.BLACK);
    }

    @Override
    TreeNode<K, V> afterNodePut(TreeNode<K, V> treeNode) {
        // 修正右倾斜链接
        RedBlackTreeNode node = (RedBlackTreeNode) treeNode;
        if (isRed(node.getRightChild()) && !isRed(node.getLeftChild()))
            node = rotateLeft(node);
        if (isRed(node.getLeftChild())
                && isRed(node.getLeftChild().getLeftChild()))
            node = rotateRight(node);
        if (isRed(node.getLeftChild()) && isRed(node.getRightChild()))
            flipColors(node);
        // 更新 size 和 height
        return super.afterNodePut(node);
    }

    @Override
    RedBlackTreeNode createTreeNode(K key, V value) {
        return new RedBlackTreeNode(key, value);
    }

    @Override
    public void deleteMin() {
        checkIsEmpty();

        // 如果根的左右子树链接都是黑色，那么将根结点链接颜色置为红色
        if (!isRed(getRoot().getLeftChild()) && !isRed(getRoot().getRightChild()))
            getRoot().setColor(TreeNodeLinkColor.RED);

        setRoot(deleteMin(getRoot()));
        if (!isEmpty())
            getRoot().setColor(TreeNodeLinkColor.BLACK);
    }

    @Override
    TreeNode<K, V> deleteMin(TreeNode<K, V> treeNode) {
        if (treeNode.getLeftChild() == null)
            return null;

        RedBlackTreeNode node = (RedBlackTreeNode) treeNode;

        if (!isRed(node.getLeftChild())
                && !isRed(node.getLeftChild().getLeftChild()))
            node = moveRedLeft(node);
        node.setLeftChild(deleteMin(node.getLeftChild()));
        return afterNodeDelete(node);
    }

    private RedBlackTreeNode moveRedLeft(RedBlackTreeNode treeNode) {
        // 结点为红色，treeNode.left 和 treeNode.left.left 都是黑色
        assert treeNode != null;
        assert isRed(treeNode) && !isRed(treeNode.getLeftChild())
                && !isRed(treeNode.getLeftChild().getLeftChild());
        // 将 treeNode.left 或者 treeNode.left的子结点中的一个变红

        flipColors(treeNode);
        if (isRed(treeNode.getRightChild().getLeftChild())) {
            treeNode.setRightChild(rotateRight(treeNode.getRightChild()));
            treeNode = rotateLeft(treeNode);
            flipColors(treeNode);
        }

        return treeNode;
    }

    @Override
    public void deleteMax() {
        checkIsEmpty();

        // 如果根的左右子树链接都是黑色，那么将根结点链接颜色置为红色
        if (!isRed(getRoot().getLeftChild()) && !isRed(getRoot().getRightChild()))
            getRoot().setColor(TreeNodeLinkColor.RED);

        setRoot(deleteMax(getRoot()));
        if (!isEmpty())
            getRoot().setColor(TreeNodeLinkColor.BLACK);
    }

    @Override
    TreeNode<K, V> deleteMax(TreeNode<K, V> treeNode) {
        RedBlackTreeNode node = (RedBlackTreeNode) treeNode;
        // 通过右旋操作，将左红链接转为右红链接
        if (isRed(node.getLeftChild()))
            node = rotateRight(node);

        if (node.getRightChild() == null) {
            return null;
        }
        if (!isRed(node.getRightChild())
                && !isRed(node.getRightChild().getLeftChild()))
            node = moveRedRight(node);
        node.setRightChild(deleteMax(node.getRightChild()));

        return afterNodeDelete(node);
    }

    private RedBlackTreeNode moveRedRight(RedBlackTreeNode treeNode) {
        // 当前结点为红，且右节点为红，右节点左结点为红
        assert treeNode != null && isRed(treeNode) && !isRed(treeNode.getRightChild())
                && !isRed(treeNode.getRightChild().getLeftChild());

        flipColors(treeNode);
        if (isRed(treeNode.getLeftChild().getLeftChild())) {
            treeNode = rotateRight(treeNode);
            flipColors(treeNode);
        }
        return treeNode;
    }

    @Override
    public void delete(K key) {
        checkKeyNotNull(key);
        if (!contains(key))
            return;

        if (!isRed(getRoot().getLeftChild()) && !isRed(getRoot().getRightChild()))
            getRoot().setColor(TreeNodeLinkColor.RED);

        setRoot(delete(getRoot(), key));

        if (!isEmpty())
            getRoot().setColor(TreeNodeLinkColor.BLACK);
    }

    @Override
    TreeNode<K, V> delete(TreeNode<K, V> treeNode, K key) {
        RedBlackTreeNode node = (RedBlackTreeNode) treeNode;
        if (compare(key, node.getKey()) < 0) {
            if (!isRed(node.getLeftChild()) && !isRed(node.getLeftChild().getLeftChild()))
                node = moveRedLeft(node);
            node.setLeftChild(delete(node.getLeftChild(), key));
        } else {
            if (isRed(node.getLeftChild())) {
                node = rotateRight(node);
            }
            if (compare(key, node.getKey()) == 0 && node.getRightChild() == null) {
                return null;
            }
            if (!isRed(node.getRightChild()) && !isRed(node.getRightChild().getLeftChild())) {
                node = moveRedRight(node);
            }
            if (compare(key, node.getKey()) == 0) {
                RedBlackTreeNode x = (RedBlackTreeNode) min(node.getRightChild());
                node.setKey(x.getKey());
                node.setValue(x.getValue());
                node.setRightChild(deleteMin(node.getRightChild()));
            } else {
                node.setRightChild(delete(node.getRightChild(), key));
            }
        }
        return afterNodeDelete(node);
    }

    @Override
    TreeNode<K, V> afterNodeDelete(TreeNode<K, V> treeNode) {
        // 修正右倾斜链接
        RedBlackTreeNode node = (RedBlackTreeNode) treeNode;
        if (isRed(node.getRightChild()) && !isRed(node.getLeftChild()))
            node = rotateLeft(node);
        if (isRed(node.getLeftChild())
                && isRed(node.getLeftChild().getLeftChild()))
            node = rotateRight(node);
        if (isRed(node.getLeftChild()) && isRed(node.getRightChild()))
            flipColors(node);
        // 更新 size 和 height
        return super.afterNodeDelete(node);
    }

    @Override
    void drawLines(TreeNode<K, V> treeNode, MyMap<TreeNode<K, V>, double[]> coordinatesMap, Graphics2D graphics,
            BufferedImage bufferedImage) {
        if (treeNode == null) {
            return;
        }
        // 绘制左结点
        drawLines(treeNode.getLeftChild(), coordinatesMap, graphics, bufferedImage);
        // 绘制当前结点
        RedBlackTreeNode node = (RedBlackTreeNode) treeNode;
        if (node.getLeftChild() != null) {
            graphics.setColor(isRed(node.getLeftChild()) ? Color.RED : Color.BLACK);
            graphics.setStroke(new BasicStroke(isRed(node.getLeftChild()) ? 3 : 1));
            graphics.draw(new Line2D.Double(bufferedImage.getWidth() * coordinatesMap.get(node)[0],
                    bufferedImage.getHeight() * coordinatesMap.get(node)[1],
                    bufferedImage.getWidth() * coordinatesMap.get(node.getLeftChild())[0],
                    bufferedImage.getHeight() * coordinatesMap.get(node.getLeftChild())[1]));
        }
        if (node.getRightChild() != null) {
            graphics.setColor(isRed(node.getRightChild()) ? Color.RED : Color.BLACK);
            graphics.setStroke(new BasicStroke(isRed(node.getRightChild()) ? 3 : 1));
            graphics.draw(new Line2D.Double(bufferedImage.getWidth() * coordinatesMap.get(node)[0],
                    bufferedImage.getHeight() * coordinatesMap.get(node)[1],
                    bufferedImage.getWidth() * coordinatesMap.get(node.getRightChild())[0],
                    bufferedImage.getHeight() * coordinatesMap.get(node.getRightChild())[1]));
        }
        // 绘制右结点
        drawLines(treeNode.getRightChild(), coordinatesMap, graphics, bufferedImage);
    }
}
