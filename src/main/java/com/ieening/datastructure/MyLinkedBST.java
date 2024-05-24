package com.ieening.datastructure;

import java.util.Objects;

public class MyLinkedBST<K, V> extends AbstractMyBinarySearchTree<K, V> {
    // MARK:Fields

    class MyBinarySearchTreeNode implements MyBinarySearchTree.TreeNode<K, V> {
        private K key; // 排序的键
        private V value; // 存储的值
        private MyBinarySearchTree.TreeNode<K, V> left, right; // 左右子树
        private int size;
        private int height;

        MyBinarySearchTreeNode(K key, V value) {
            this(key, value, 1, 0);
        }

        MyBinarySearchTreeNode(K key, V value, int size, int height) {
            this.key = key;
            this.value = value;
            this.size = size;
            this.height = height;
        }

        @Override
        public void setKey(K key) {
            this.key = key;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public TreeNode<K, V> getLeftChild() {
            return left;
        }

        @Override
        public void setLeftChild(
                TreeNode<K, V> treeNode) {
            left = treeNode;
        }

        @Override
        public TreeNode<K, V> getRightChild() {
            return right;
        }

        @Override
        public void setRightChild(
                MyBinarySearchTree.TreeNode<K, V> treeNode) {
            right = treeNode;
        }

        @Override
        public int getSize() {
            return size;
        }

        @Override
        public void setSize(int size) {
            this.size = size;
        }

        @Override
        public int getHeight() {
            return height;
        }

        @Override
        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj instanceof MyBinarySearchTree.TreeNode) {
                MyBinarySearchTree.TreeNode<?, ?> other = (MyBinarySearchTree.TreeNode<?, ?>) obj;
                if (size == other.getSize() && height == other.getHeight() && Objects.equals(key, other.getKey())
                        && Objects.equals(value, other.getValue())) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = size * height;
            result = prime * result + ((key == null) ? 0 : key.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

    }

    // MARK:Modification Operations

    @Override
    MyBinarySearchTreeNode createTreeNode(K key, V value) {
        return new MyBinarySearchTreeNode(key, value);
    }

    // MARK:View

    /**
     * 中序遍历非递归
     */
    @Override
    public Iterable<K> middleOrder() {
        return middleOrder(getRoot());
    }

    private Iterable<K> middleOrder(TreeNode<K, V> treeNode) {
        MyQueue<K> keys = new MyResizingArrayQueue<>();
        MyStack<TreeNode<K, V>> stack = new MyResizingArrayStack<>();

        while (true) {
            while (treeNode != null) {
                stack.push(treeNode);
                treeNode = treeNode.getLeftChild();
            }
            if (stack.isEmpty())
                break;
            treeNode = stack.pop();
            keys.enqueue(treeNode.getKey());
            treeNode = treeNode.getRightChild();
        }
        return keys;
    }

    /**
     * 后序遍历非递归
     */
    @Override
    public Iterable<K> postOrder() {
        return postOrder(getRoot());
    }

    private Iterable<K> postOrder(TreeNode<K, V> treeNode) {
        MyQueue<K> keys = new MyResizingArrayQueue<>();
        MyStack<TreeNode<K, V>> stack = new MyResizingArrayStack<>();
        TreeNode<K, V> lastVisit = null;

        while (true) {
            while (treeNode != null) {
                stack.push(treeNode);
                treeNode = treeNode.getLeftChild();
            }
            if (stack.isEmpty())
                break;
            treeNode = stack.peek();
            if (treeNode.getRightChild() == null || treeNode.getRightChild() == lastVisit) {
                lastVisit = stack.pop();
                keys.enqueue(lastVisit.getKey());
                treeNode = null;
            } else {
                treeNode = treeNode.getRightChild();
            }
        }
        return keys;
    }

    /**
     * 前序遍历非递归
     */
    @Override
    public Iterable<K> preOrder() {
        return preOrder(getRoot());
    }

    private Iterable<K> preOrder(TreeNode<K, V> treeNode) {
        MyQueue<K> keys = new MyResizingArrayQueue<>();
        MyStack<TreeNode<K, V>> stack = new MyResizingArrayStack<>();

        while (treeNode != null) {
            keys.enqueue(treeNode.getKey());
            if (treeNode.getRightChild() != null) {
                stack.push(treeNode.getRightChild());
            }
            if (treeNode.getLeftChild() != null) {
                stack.push(treeNode.getLeftChild());
            }
            treeNode = stack.isEmpty() ? null : stack.pop();
        }
        return keys;
    }

    /**
     * 深度优先非递归
     */
    @Override
    public Iterable<K> depthFirstOrder() {
        return depthFirstOrder(getRoot());
    }

    private Iterable<K> depthFirstOrder(TreeNode<K, V> treeNode) {
        MyStack<TreeNode<K, V>> nodeStack = new MyLinkedListStack<>();
        MyList<K> keys = new MyLinkedList<>();

        while (treeNode != null) {
            keys.add(treeNode.getKey());
            if (treeNode.getRightChild() != null)
                nodeStack.push(treeNode.getRightChild());

            if (treeNode.getLeftChild() != null)
                nodeStack.push(treeNode.getLeftChild());

            treeNode = nodeStack.isEmpty() ? null : nodeStack.pop();
        }
        return keys;
    }
}
