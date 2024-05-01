package com.ieening.datastructure;

public class RedBlackBST<K, V> extends AbstractMyBinarySearchTree<K, V> {

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
}
