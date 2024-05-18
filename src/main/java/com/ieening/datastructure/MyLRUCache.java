package com.ieening.datastructure;

public class MyLRUCache<K, V> {
    // MARK:Fields

    // 默认容量
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    // 容器元素个数
    private int size;
    // 容器容量
    private final int capacity;
    // 哈希表
    private final MyHashMap<K, DoubleNode<K, V>> map = new MyHashMap<>();
    // 链表头结点
    private final DoubleNode<K, V> head = new DoubleNode<K, V>(null, null, null, null);
    // 链表尾结点
    private final DoubleNode<K, V> tail = new DoubleNode<K, V>(null, null, null, null);

    /**
     * 双向链表结点定义
     */
    private static class DoubleNode<K, V> {
        K key;
        V value;
        DoubleNode<K, V> prior;
        DoubleNode<K, V> next;

        DoubleNode(K key, V value, DoubleNode<K, V> prior, DoubleNode<K, V> next) {
            this.key = key;
            this.value = value;
            this.prior = prior;
            this.next = next;
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
         * @return the prior
         */
        public DoubleNode<K, V> getPrior() {
            return prior;
        }

        /**
         * @param prior the prior to set
         */
        public void setPrior(DoubleNode<K, V> prior) {
            this.prior = prior;
        }

        /**
         * @return the next
         */
        public DoubleNode<K, V> getNext() {
            return next;
        }

        /**
         * @param next the next to set
         */
        public void setNext(DoubleNode<K, V> next) {
            this.next = next;
        }
    }

    // MARK:Constructor

    /**
     * 构造空的 LRU 缓存，容量为默认容量
     */
    public MyLRUCache() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 构造容量为 {@code capacity} 的 LRU 缓存
     * 
     * @param capacity 指定容量
     */
    public MyLRUCache(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Illegal capacity: " +
                    capacity);
        this.capacity = capacity;
        // 头尾相连
        head.setNext(tail);
        tail.setPrior(head);
    }

    public V get(K key) {
        if (map.containsKey(key)) {
            DoubleNode<K, V> node = map.get(key);
            V v = node.getValue();
            moveToHead(node);
            return v;
        }
        return null;
    }

    /**
     * 将键值对放入 LRUCache 中，如果 MyLRUCache 包含 key，那么更新即可，否则需要判断 size==capacity，
     * 
     * @param key   待添加键值
     * @param value 待添加值
     */
    public void put(K key, V value) {
        if (map.containsKey(key)) {
            final DoubleNode<K, V> node = map.get(key);
            node.setValue(value); // 更新值
            moveToHead(node); // 移到链表首
        } else {
            if (size == capacity) {
                removeTail();
            }
            addToHead(key, value);
        }
    }

    private void removeTail() {
        // 删除链表尾
        final DoubleNode<K, V> deleteNode = tail.getPrior();
        removeNode(deleteNode);
        // 哈希表删除最近最少使用的结点
        map.remove(deleteNode.getKey());
        // 更新 size
        size--;
        // 清除删除结点信息
        deleteNode.setValue(null);
        deleteNode.setPrior(null);
        deleteNode.setNext(null);
        deleteNode.setKey(null);
        
    }

    private void addToHead(K key, V value) {
        // 在链表首添加
        DoubleNode<K, V> newNode = new DoubleNode<>(key, value, head, head.getNext());
        newNode.getNext().setPrior(newNode);
        head.setNext(newNode);

        // 哈希表添加
        map.put(key, newNode);
        // 更新 size
        size++;
    }

    private void moveToHead(DoubleNode<K, V> node) {
        // 先删除结点
        removeNode(node);
        // 将结点移到链表首
        node.setNext(head.getNext());
        head.getNext().setPrior(node);
        head.setNext(node);
        node.setPrior(head);
    }

    private void removeNode(DoubleNode<K, V> node) {
        // 删除结点
        node.getPrior().setNext(node.getNext());
        node.getNext().setPrior(node.getPrior());
    }
}
