package com.ieening.datastructure;

public interface MyBinarySearchTree<K, V> {
    // MARK:Tree Node Interface
    /**
     * 二叉搜索树中结点接口定义
     */
    interface TreeNode<K, V> {
        /**
         * 获取树结点键值
         * 
         * @return 树结点键值
         */
        K getKey();

        /**
         * 设置树结点键值
         * 
         * @param key 修改后的键
         */
        void setKey(K key);

        /**
         * 获取树结点值
         * 
         * @return 树结点值
         */
        V getValue();

        /**
         * 修改树结点值
         * 
         * @param value 修改值
         * @throws IllegalArgumentException 如果键 {@code value} 为 {@code null}
         */
        void setValue(V value);

        /**
         * 获取当前结点左孩子引用
         * 
         * @return 当前结点左孩子引用
         */
        TreeNode<K, V> getLeftChild();

        /**
         * 修改当前结点左孩子
         * 
         * @param treeNode 待修改树结点
         */
        void setLeftChild(TreeNode<K, V> treeNode);

        /**
         * 获取当前结点右孩子引用
         * 
         * @return 当前结点右孩子引用
         */
        TreeNode<K, V> getRightChild();

        /**
         * 修改当前结点右孩子引用
         * 
         * @param treeNode 待修改树结点
         * @return 当前结点已被修改右孩子引用
         */
        void setRightChild(TreeNode<K, V> treeNode);

        /**
         * 当前结点为树时，树的大小
         * 
         * @return 树大小
         */
        int getSize();

        /**
         * 设置当前树的大小
         */
        void setSize(int size);

        /**
         * 以当前结点为根，树的深度
         * 
         * @return 树深度
         */
        int getHeight();

        /**
         * 设置以当前结点为树的深度
         */
        void setHeight(int height);

        /**
         * 树结点 {@code equals} 方法
         *
         * @param o 待比较的另一个树结点对象
         * @return 如果两个树结点相等返回 <tt>true</tt>
         */
        boolean equals(Object o);

        /**
         * 树结点 {@code hashCOde} 方法
         *
         * @return 树结点的哈希值
         */
        int hashCode();
    }

    // MARK:Query Operations
    /**
     * 返回根结点
     * 
     * @return 根结点
     */
    TreeNode<K, V> getRoot();

    /**
     * 设置根结点
     */
    void setRoot(TreeNode<K, V> treeNode);

    /**
     * 树存储数据量
     * 
     * @return 树的大小
     */
    int size();

    /**
     * 是否为空树
     * 
     * @return 是否为空树
     */
    boolean isEmpty();

    /**
     * 如果树中包含指定键的结点则返回 <tt>true</tt>
     * 
     * @param key 待检查键值
     * @return 如果哈希表包含指定键的映射，返回 <tt>true</tt>
     * @throws IllegalArgumentException 如果键 {@code key} 为 {@code null}
     */
    boolean contains(K key);

    /**
     * 如果结点存在，那么返回结点值，否则返回 {@code null}
     * 
     * @param key 待返回值对应键
     * @return 如果结点存在，那么返回结点值，否则返回 {@code null}
     * @throws IllegalArgumentException 如果键 {@code key} 为 {@code null}
     */
    V get(K key);

    /**
     * 返回 BST 中最小键
     * 
     * @return 最小键
     */
    K min();

    /**
     * 返回 BST 最大键
     * 
     * @return 最大键
     */
    K max();

    /**
     * 返回小于或者等于指定键的最大键
     * 
     * @param key 指定的键
     * @return BST 中小于或等于键值的最大键
     * @throws NoSuchElementException   如果 BST 中没有符合条件的键值
     * @throws IllegalArgumentException 如果键 {@code key} 为 {@code null}
     */
    K floor(K key);

    /**
     * 返回大于或等于指定键的最小键
     * 
     * @param key 指定键
     * @return BST 中大于或者等于指定键的最小键
     * @throws NoSuchElementException   如果 BST 中没有符合条件的键值
     * @throws IllegalArgumentException 如果键 {@code key} 为 {@code null}
     */
    K ceiling(K key);

    /**
     * 返回排名为 <em>rank</em>+1 的键
     * 
     * @param rank 等级
     * @return 排名为 <em>rank</em>+1 的键
     * @throws IllegalArgumentException 如果 {@code rank} 值不在 0 和 <em>n</em>–1 之间
     */
    K select(int rank);

    /**
     * 返回小于指定键的个数
     * 
     * @param key 指定键
     * @return 小于指定键的个数
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    int rank(K key);

    /**
     * 返回树的高度
     * 
     * @return 树的高度
     */
    int height();

    // MARK:Modification Operations

    /**
     * 插入指定的键值对到树中。如果树中已存在对应键，那么使用新值代替旧值，如果没有则添加到树中，如果值为 {@code null} 那么删除键值对
     * 
     * @param key   键
     * @param value 值
     * @throws IllegalArgumentException 如果键 {@code key} 为 {@code null}
     */
    void put(K key, V value);

    /**
     * 从二叉树中删除最小键以及关联的值
     */
    void deleteMin();

    /**
     * 从二叉树中删除最大键以及关联的值
     */
    void deleteMax();

    /**
     * 删除指定键对应的树结点，并返回对应的值。如果 {@code key} 不存在，返回 {@code null}
     * 
     * @param key 指定键
     * @return 返回对应的键，如果 {@code key} 不存在，返回 {@code null}
     * @throws IllegalArgumentException 如果 {@code key} 为 {@code null}
     */
    void delete(K key);

    // MARK:View
    /**
     * 按升序返回 BST 中所有键键
     * 
     * @return BST 中所有键
     */
    Iterable<K> keys();

    /**
     * 按升序返回给定范围所有键
     * 
     * @param lo 范围左边界
     * @param hi 范围右边界
     * @return 按升序在 {@code lo} 和 {@code hi} 范围之间所有键
     * @throws IllegalArgumentException 如果 {@code lo} 或者 {@code hi} 为{@code null}
     */
    Iterable<K> keys(K lo, K hi);

    /**
     * BST 前序遍历
     * 
     * @return 按照前序遍历得到所有的键
     */
    Iterable<K> preOrder();

    /**
     * BST 中序遍历
     * 
     * @return 按照中华序遍历得到所有的键
     */
    Iterable<K> middleOrder();

    /**
     * BST 后序遍历
     * 
     * @return 按照后序遍历得到所有的键
     */
    Iterable<K> postOrder();

    /**
     * BST 层遍历
     * 
     * @return 按照层序遍历得到所有的键
     */
    Iterable<K> levelOrder();

    // MARK:Comparison and hashing

    /**
     * 二叉搜索树 {@code equals} 方法
     *
     * @param o 待比较的另一个 BST 对象
     * @return 如果两个 BST 相等返回 <tt>true</tt>
     */
    boolean equals(Object o);

    /**
     * BST {@code hashCOde} 方法
     *
     * @return BST 的哈希值
     */
    int hashCode();
}
