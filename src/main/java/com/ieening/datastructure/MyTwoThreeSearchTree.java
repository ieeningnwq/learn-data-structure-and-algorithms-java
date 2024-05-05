package com.ieening.datastructure;

/**
 * 2-3 搜索树
 */
public class MyTwoThreeSearchTree<K, V> {
    /**
     * TwoThreeSearchTreeNodeType 2-3 结点类型
     */
    public static enum TwoThreeSearchTreeNodeType {
        TWO, THREE, LEAF;
    }

    // 定义一个空结点用于辨识是2-结点还是3-结点
    private TwoThreeSearchTreeNode NULL_NODE = new TwoThreeSearchTreeNode(null, null, 0, 0);

    class TwoThreeSearchTreeNode {
        private K key;
        private V value;
        private int size;
        private int height;
        private TwoThreeSearchTreeNode left; // 左链接
        private TwoThreeSearchTreeNode middle = NULL_NODE; // 中链接
        private TwoThreeSearchTreeNode right; // 右链接

        /**
         * @param key    键
         * @param value  值
         * @param size   以该结点为根节点2-3树大小
         * @param height 该结点高度
         */
        TwoThreeSearchTreeNode(K key, V value, int size, int height) {
            this.key = key;
            this.value = value;
            this.size = size;
            this.height = height;
        }

        TwoThreeSearchTreeNode(K key, V value) {
            this(key, value, 1, 0);
        }

        /**
         * @return the key
         */
        public K getKey() {
            return key;
        }

        /**
         * @param key the key to set
         */
        public void setKey(K key) {
            this.key = key;
        }

        /**
         * @return the value
         */
        public V getValue() {
            return value;
        }

        /**
         * @param value the value to set
         */
        public void setValue(V value) {
            this.value = value;
        }

        /**
         * @return the size
         */
        public int getSize() {
            return size;
        }

        /**
         * @param size the size to set
         */
        public void setSize(int size) {
            this.size = size;
        }

        /**
         * @return the height
         */
        public int getHeight() {
            return height;
        }

        /**
         * @param height the height to set
         */
        public void setHeight(int height) {
            this.height = height;
        }

        /**
         * @return the left
         */
        public TwoThreeSearchTreeNode getLeft() {
            return left;
        }

        /**
         * @param left the left to set
         */
        public void setLeft(TwoThreeSearchTreeNode left) {
            this.left = left;
        }

        /**
         * @return the middle
         */
        public TwoThreeSearchTreeNode getMiddle() {
            return middle;
        }

        /**
         * @param middle the middle to set
         */
        public void setMiddle(TwoThreeSearchTreeNode middle) {
            this.middle = middle;
        }

        /**
         * @return the right
         */
        public TwoThreeSearchTreeNode getRight() {
            return right;
        }

        /**
         * @param right the right to set
         */
        public void setRight(TwoThreeSearchTreeNode right) {
            this.right = right;
        }

    }

    public TwoThreeSearchTreeNodeType getNodeType(TwoThreeSearchTreeNode treeNode) {
        return treeNode == null ? TwoThreeSearchTreeNodeType.LEAF
                : (treeNode.getMiddle() == NULL_NODE ? TwoThreeSearchTreeNodeType.TWO
                        : TwoThreeSearchTreeNodeType.THREE);
    }

    public boolean isLeafNode(TwoThreeSearchTreeNode treeNode) {
        return TwoThreeSearchTreeNodeType.LEAF == getNodeType(treeNode);
    }

    public boolean isTwoNode(TwoThreeSearchTreeNode treeNode) {
        return TwoThreeSearchTreeNodeType.TWO == getNodeType(treeNode);
    }

    public boolean isThreeNode(TwoThreeSearchTreeNode treeNode) {
        return TwoThreeSearchTreeNodeType.THREE == getNodeType(treeNode);
    }
}
