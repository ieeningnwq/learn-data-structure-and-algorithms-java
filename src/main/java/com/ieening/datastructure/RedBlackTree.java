package com.ieening.datastructure;

public class RedBlackTree<K, V> extends AbstractMyBinarySearchTree<K, V> {

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
}
