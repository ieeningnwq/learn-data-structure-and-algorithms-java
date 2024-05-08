package com.ieening.datastructure;

import java.util.Iterator;
import java.util.Objects;

public class MyHashSet<E> implements MySet<E> {
    // MARK:Fields

    // 维护一个 MyHashMap 实现 MyHashSet
    private MyHashMap<E, Object> map;
    // 维护的 MyHashMap 中键值对的值
    private static final Object SET_VALUE = new Object();

    // MARK:Constructors

    /**
     * 构建一个空的 MyHashSet
     */
    public MyHashSet() {
        map = new MyHashMap<>();
    }

    /**
     * 构建一个空的集合，传入的容量和负载因子，构造一个空的哈希表
     *
     * @param initialCapacity 哈希表的初始容量
     * @param loadFactor      哈希表的负载因子
     * @throws IllegalArgumentException 如果初始容量为负否则负载因子非正
     */
    public MyHashSet(int initialCapacity, float loadFactor) {
        map = new MyHashMap<>(initialCapacity, loadFactor);
    }

    /**
     * 构建一个空的集合，背后的哈希表指定了初始容量，负载因子采用默认值（0.75）
     *
     * @param initialCapacity 哈希表的初始容量
     * @throws IllegalArgumentException 如果初始容量为负
     */
    public MyHashSet(int initialCapacity) {
        map = new MyHashMap<>(initialCapacity);
    }

    // MARK:Query Operations

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public Object[] toArray() {
        return map.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return map.keySet().toArray(a);
    }

    // MARK:Modification Operations

    @Override
    public boolean add(E e) {
        return map.put(e, SET_VALUE) == null;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) == SET_VALUE;
    }

    // MARK:Bulk Operations

    @Override
    public boolean containsAll(MySet<?> mySet) {
        for (Object e : mySet)
            if (!contains(e))
                return false;

        return true;
    }

    @Override
    public boolean addAll(MySet<? extends E> mySet) {
        boolean modified = false;
        for (E e : mySet)
            if (add(e))
                modified = true;
        return modified;
    }

    @Override
    public boolean removeAll(MySet<?> mySet) {
        Objects.requireNonNull(mySet);
        boolean modified = false;

        if (size() > mySet.size()) {
            for (Iterator<?> i = mySet.iterator(); i.hasNext();)
                modified |= remove(i.next());

        } else {
            for (Iterator<?> i = iterator(); i.hasNext();) {
                if (mySet.contains(i.next())) {
                    i.remove();
                    modified = true;
                }
            }
        }

        return modified;
    }

    @Override
    public boolean retainAll(MySet<?> mySet) {
        Objects.requireNonNull(mySet);
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!mySet.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        map.clear();
    }

    // MARK:Comparison and hashing

    @Override
    public int hashCode() {
        int h = 0;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            h += Objects.hashCode(it.next());
        }
        return h;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof MySet)) {
            return false;
        }
        MySet<?> mySet = (MySet<?>) o;

        if (mySet.size() != size()) {
            return false;
        }
        return containsAll(mySet);
    }
}
