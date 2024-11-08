package com.ieening.datastructure;

import java.util.Iterator;
import java.util.Objects;

public class MyHashMap<K, V> implements MyMap<K, V> {
    // MARK:Fields
    /**
     * 默认数组初始容量
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * 默认的负载因子
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 哈希表键值对类（节点）
     */
    static class Node<K, V> implements MyMap.Entry<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.value = value;

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
        public String toString() {
            return key + "=" + value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((key == null) ? 0 : key.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj instanceof MyMap.Entry) {
                MyMap.Entry<?, ?> other = (MyMap.Entry<?, ?>) obj;
                if (key.equals(other.getKey()) && value.equals(other.getValue())) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 哈希表数组
     */
    Node<K, V>[] table;
    /**
     * 键值对缓存
     */
    MySet<MyMap.Entry<K, V>> entrySet;

    /**
     * 哈希表大小
     */
    int size;

    /**
     * 扩容阈值，值为：capacity * load factor
     */
    int threshold;

    /**
     * 负载因子
     */
    final float loadFactor;

    /**
     * 键集合
     */
    MySet<K> keySet;

    /**
     * 值列表
     */
    MyList<V> values;

    // MARK:Constructors

    /**
     * 根据容量和负载因子，构造一个空的哈希表
     *
     * @param initialCapacity 初始容量
     * @param loadFactor      负载因子
     * @throws IllegalArgumentException 如果初始容量为负否则负载因子非正
     */
    public MyHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = (int) (initialCapacity * loadFactor);
    }

    /**
     * 根据容量构造一个空哈希表
     *
     * @param initialCapacity 初始容量
     * @throws IllegalArgumentException 如果初始容量为负
     */
    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 构造一个空表，初始容量（16）和负载因子都为默认值（0.75）
     */
    public MyHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    // MARK:Query Operations

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return getNode(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        Node<K, V>[] tab;
        if ((tab = table) != null && size > 0) {
            for (int i = 0; i < tab.length; i++) {
                for (Node<K, V> e = table[i]; e != null; e = e.next) {
                    if (value == e.value || (value != null && value.equals(e.value))) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    @Override
    public V get(Object key) {
        Node<K, V> e;
        return (e = getNode(key)) == null ? null : e.value;
    }

    private Node<K, V> getNode(Object key) {
        if (table != null && table.length > 0) {
            @SuppressWarnings("unchecked")
            K kKey = (K) key;
            Node<K, V> positionNode = seekPositionNodeByKey(kKey);
            if (positionNode != null && !(positionNode.key == key || positionNode.key.equals(key))) {
                positionNode = null;
            }
            return positionNode;
        }
        return null;

    }

    private int indexOf(Object key) {
        return (Objects.hashCode(key) & 0x7fffffff) % table.length;
    }

    // MARK:Modification Operations

    @Override
    public V put(K key, V value) {
        int index;
        if (table == null || table.length == 0) { // 检查 table 是否初始化
            resize();
        }
        if (table[index = indexOf(key)] == null) {
            table[index] = new Node<>(key, value, null);
        } else {
            Node<K, V> positionNode = seekPositionNodeByKey(key);
            if (positionNode.key == key || (key != null && key.equals(positionNode.key))) {
                positionNode.value = value;
                return positionNode.value;
            }
            positionNode.next = new Node<>(key, value, null);
        }
        if (++size > threshold) {
            resize();
        }
        return null;
    }

    private Node<K, V> seekPositionNodeByKey(K key) {
        Node<K, V> positionNode;
        if ((positionNode = table[indexOf(key)]) != null) {
            while (positionNode.next != null) { // newTable first 最后一位
                if (positionNode.key == key || (key != null && key.equals(positionNode.key))) {
                    return positionNode;
                }
                positionNode = positionNode.next;
            }
        }
        return positionNode;
    }

    /**
     * 为 table 初始化或者扩容数组
     * 
     * @return new table
     */
    private Node<K, V>[] resize() {
        Node<K, V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int newCap, newThr = 0;

        if (oldCap > 0 && oldCap >= DEFAULT_INITIAL_CAPACITY) {
            newCap = oldCap << 1; // 扩容两倍
            newThr = (int) (newCap * loadFactor);
        } else {
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        threshold = newThr;
        @SuppressWarnings("unchecked")
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int i = 0; i < oldCap; i++) {
                Node<K, V> e;
                if ((e = oldTab[i]) != null) {
                    oldTab[i] = null;
                    Node<K, V> next;
                    do {
                        next = e.next;
                        Node<K, V> positionNode = seekPositionNodeByKey(e.key);
                        if (positionNode == null) { // newTable first 无值
                            newTab[indexOf(e.key)] = e;
                            e.next = null;
                        } else { // newTable first 有值
                            positionNode.next = e;
                            e.next = null;
                        }
                    } while ((e = next) != null);
                }
            }
        }
        return newTab;
    }

    @Override
    public V remove(Object key) {
        Node<K, V> e;
        return (e = removeNode(key)) == null ? null : e.value;

    }

    private Node<K, V> removeNode(Object key) {
        Node<K, V>[] tab;
        Node<K, V> p;
        int index;
        if ((tab = table) != null && tab.length > 0 && (p = tab[index = indexOf(key)]) != null) {
            Node<K, V> node = null, e;
            if (p.key == key || (key != null && key.equals(p.key))) { // 检查首节点
                node = p;
            } else if ((e = p.next) != null) {
                do {
                    if (e.key == key || (key != null && key.equals(e.key))) {
                        node = e;
                        break;
                    }
                    p = e;
                } while ((e = e.next) != null);
            }
            if (node != null) {
                if (node == p)
                    table[index] = node.next;
                else
                    p.next = node.next;
                size--;
                return node;
            }
        }
        return null;
    }

    @Override
    public void putAll(MyMap<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        Node<K, V>[] tab;
        if ((tab = table) != null && size > 0) {
            size = 0;
            for (int i = 0; i < tab.length; ++i)
                tab[i] = null;
        }
    }

    // MARK:Views

    @Override
    public MySet<K> keySet() {
        MySet<K> ks = keySet;
        if (ks == null) {
            ks = new KeySet();
            keySet = ks;
        }
        return ks;
    }

    abstract class HashIterator {
        Node<K, V> next;
        Node<K, V> current;
        int index;

        HashIterator() {
            Node<K, V>[] t = table;
            current = next = null;
            index = 0;
            if (t != null && size > 0) {
                do { // 找到第一个 Node
                } while (index < t.length && ((next = t[index++]) == null));
            }
        }

        public boolean hasNext() {
            return next != null;
        }

        Node<K, V> nextNode() {
            Node<K, V>[] t;
            Node<K, V> e = next;
            if ((next = (current = e).next) == null && (t = table) != null) {
                do { // 找到第一个 Node
                } while (index < t.length && ((next = t[index++]) == null));
            }
            return e;
        }

        public final void remove() {
            Node<K, V> p = current;
            if (p == null) {
                throw new IllegalStateException();
            }
            current = null;
            K key = p.key;
            removeNode(key);
        }
    }

    class KeyIterator extends HashIterator implements Iterator<K> {
        @Override
        public K next() {
            return nextNode().getKey();
        }

    }

    final class KeySet implements MySet<K> {

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            return size() == 0;
        }

        @Override
        public boolean contains(Object o) {
            return containsKey(o);
        }

        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override
        public Object[] toArray() {
            Object[] r = new Object[size()];
            Iterator<K> it = iterator();
            for (int i = 0; i < r.length; i++) {
                r[i] = it.next();
            }
            return r;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] a) {
            int size = size();
            T[] r = a.length >= size ? a
                    : (T[]) java.lang.reflect.Array
                            .newInstance(a.getClass().getComponentType(), size);
            Iterator<K> it = iterator();

            for (int i = 0; i < r.length; i++) {
                if (!it.hasNext()) {
                    r[i] = null;
                } else {
                    r[i] = (T) it.next();
                }
            }
            return r;
        }

        @Override
        public boolean add(K e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            return removeNode(o) != null;
        }

        @Override
        public boolean containsAll(MySet<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(MySet<? extends K> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(MySet<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(MySet<?> mySet) {
            throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
        }

    }

    @Override
    public MyList<V> values() {
        MyList<V> vs = values;
        if (vs == null) {
            vs = new Values();
            values = vs;
        }
        return vs;
    }

    class Values implements MyList<V> {

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public boolean contains(Object o) {
            return containsValue(o);
        }

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override
        public Object[] toArray() {
            Object[] r = new Object[size()];
            Iterator<V> it = iterator();
            for (int i = 0; i < r.length; i++) {
                r[i] = it.next();
            }
            return r;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] a) {
            int size = size();
            T[] r = a.length >= size ? a
                    : (T[]) java.lang.reflect.Array
                            .newInstance(a.getClass().getComponentType(), size);
            Iterator<V> it = iterator();

            for (int i = 0; i < r.length; i++) {
                if (!it.hasNext()) {
                    r[i] = null;
                } else {
                    r[i] = (T) it.next();
                }
            }
            return r;
        }

        @Override
        public boolean add(V e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(MyList<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(MyList<? extends V> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(MyList<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public V get(int index) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V set(int index, V element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int index, V element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V remove(int index) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int lastIndexOf(Object o) {
            throw new UnsupportedOperationException();
        }
    }

    class ValueIterator extends HashIterator implements Iterator<V> {

        @Override
        public V next() {
            return nextNode().getValue();
        }

    }

    @Override
    public MySet<MyMap.Entry<K, V>> entrySet() {
        MySet<MyMap.Entry<K, V>> es;
        return (es = entrySet) == null ? (entrySet = new EntrySet()) : es;
    }

    class EntrySet implements MySet<MyMap.Entry<K, V>> {

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof MyMap.Entry))
                return false;
            MyMap.Entry<?, ?> e = (MyMap.Entry<?, ?>) o;
            Object key = e.getKey();
            Node<K, V> candidate = getNode(key);
            return candidate != null && candidate.equals(e);

        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public Object[] toArray() {
            Object[] r = new Object[size()];
            Iterator<MyMap.Entry<K, V>> it = iterator();
            for (int i = 0; i < r.length; i++) {
                r[i] = it.next();
            }
            return r;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] a) {
            int size = size();
            T[] r = a.length >= size ? a
                    : (T[]) java.lang.reflect.Array
                            .newInstance(a.getClass().getComponentType(), size);
            Iterator<MyMap.Entry<K, V>> it = iterator();

            for (int i = 0; i < r.length; i++) {
                if (!it.hasNext()) {
                    r[i] = null;
                } else {
                    r[i] = (T) it.next();
                }
            }
            return r;
        }

        @Override
        public boolean add(Entry<K, V> e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(MySet<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(MySet<? extends Entry<K, V>> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(MySet<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(MySet<?> mySet) {
            throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
        }
    }

    class EntryIterator extends HashIterator implements Iterator<MyMap.Entry<K, V>> {

        @Override
        public Entry<K, V> next() {
            return nextNode();
        }
    }

    // MARK:Comparison and hashing

    @Override
    public int hashCode() {
        int h = 0;
        Iterator<Entry<K, V>> i = entrySet().iterator();
        while (i.hasNext())
            h += i.next().hashCode();
        return h;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof MyMap))
            return false;
        MyMap<?, ?> m = (MyMap<?, ?>) obj;
        if (m.size() != size())
            return false;

        try {
            Iterator<Entry<K, V>> i = entrySet().iterator();
            while (i.hasNext()) {
                Entry<K, V> e = i.next();
                K key = e.getKey();
                V value = e.getValue();
                if (value == null) {
                    if (!(m.get(key) == null && m.containsKey(key)))
                        return false;
                } else {
                    if (!value.equals(m.get(key)))
                        return false;
                }
            }
        } catch (ClassCastException unused) {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }
        return true;
    }
}
