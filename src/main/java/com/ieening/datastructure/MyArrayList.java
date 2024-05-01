package com.ieening.datastructure;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * MyArrayList
 */
public class MyArrayList<E> implements MyList<E> {
    // MARK:Fields

    /**
     * 列表默认初始容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 共享空列表实例
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 对于默认长度的空列表使用的空列表实例，在第一次添加元素时会扩张
     */
    private static final Object[] DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA = {};

    /**
     * 列表存储数据的数组
     */
    Object[] elementData;

    /**
     * MyArrayList 大小
     */
    private int size;

    /**
     * 列表被修改次数，主要用于迭代时列表是否发生修改
     */
    protected transient int modCount = 0;

    // MARK:Constructors

    /**
     * 构造一个容量为<tt>initialCapacity</tt>的空列表
     * 
     * @param initialCapacity
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    /**
     * 构造一个初始容量为<tt>10</tt>的空列表
     * Constructs an empty list with an initial capacity of ten.
     */
    public MyArrayList() {
        this.elementData = DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
    }

    /**
     * 从一个 MyList 中构建新的 MyArrayList
     * 
     * @param l
     */
    public MyArrayList(MyList<? extends E> l) {
        elementData = l.toArray();
        if ((size = elementData.length) != 0) {
            if (elementData.getClass() != Object[].class) {
                elementData = Arrays.copyOf(elementData, size, Object[].class);
            } else {
                this.elementData = EMPTY_ELEMENTDATA;
            }
        }
    }

    // MARK:Positional Access Operations
    @Override
    public E get(int index) {
        rangeCheck(index);
        return elementData(index);
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);

        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);

        ensureCapacityInternal(size + 1); // Increments modCount!!
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++) {
                if (elementData[index] == null)
                    return index;
            }
        } else {
            for (int index = 0; index < size; index++) {
                if (o.equals(elementData[index]))
                    return index;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int index = size - 1; index >= 0; index--) {
                if (elementData[index] == null)
                    return index;
            }
        } else {
            for (int index = size - 1; index >= 0; index--) {
                if (o.equals(elementData[index]))
                    return index;
            }
        }
        return -1;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index); // 检查索引是否越界

        modCount++;
        E oldValue = elementData(index);

        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null; // 清除移动后的最后一位

        return oldValue;
    }

    @SuppressWarnings("unchecked")
    private E elementData(int index) {
        return (E) elementData[index];
    }

    private void rangeCheck(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    // MARK:Modification Operations

    @Override
    public boolean add(E e) {
        ensureCapacityInternal(size + 1); // Increments modCount!!
        elementData[size++] = e;
        return true;
    }

    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++; // 修改次数加一

        // 如果容量不够
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    /**
     * 为列表增加容量，注意没有考虑数组大小溢出整型最大的情况
     * 
     * @param minCapacity
     */
    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1); // 扩大至 1.5 倍
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private int calculateCapacity(Object[] elementData, int minCapacity) {
        if (elementData == DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++) {
                if (elementData[index] == null) {
                    remove(index); // 这里有一步是多余的，就是检查索引越界
                    return true;
                }
            }
        } else {
            for (int index = 0; index < size; index++) {
                if (o.equals(elementData[index])) {
                    remove(index);
                    return true;
                }
            }
        }
        return false;
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
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        int cursor; // 下个返回元素的索引
        int lastRet = -1; // 最后返回元素索引，-1 表示没有元素
        int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public E next() {
            checkForComodification();
            int i = cursor;
            if (i > size) {
                throw new NoSuchElementException();
            }
            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData(lastRet = i);
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final int size = MyArrayList.this.size;
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                consumer.accept((E) elementData[i++]);
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        private void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elementData, size);
        }
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    // MARK:Bulk Modification Operations
    @Override
    public boolean addAll(MyList<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(numNew + size);
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;

    }

    @Override
    public void clear() {
        modCount++;
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean containsAll(MyList<?> c) {
        for (Object e : c)
            if (!contains(e))
                return false;
        return true;
    }

    @Override
    public boolean removeAll(MyList<?> c) {
        Objects.requireNonNull(c);

        final Object[] elementData = this.elementData;
        int r = 0, w = 0;
        boolean modified = false;
        try {
            for (; r < size; r++) {
                if (!c.contains(elementData[r])) {
                    elementData[w++] = elementData[r];
                }
            }
        } finally {
            if (r != size) { // 中途失败，把后面没有运行的移到前面
                System.arraycopy(elementData, r,
                        elementData, w,
                        size - r);
            }
            if (w != size) {
                for (int i = w; i < size; i++) {
                    elementData[i] = null;
                }
                modCount += size - w;
                size = w;
                modified = true;
            }
        }
        return modified;
    }

    // MARK:Comparison and hashing

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("rawtypes")
        MyArrayList other = (MyArrayList) obj;
        if (size != other.size)
            return false;
        if (!Arrays.deepEquals(Arrays.copyOf(elementData, size), Arrays.copyOf(other.elementData, other.size)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(Arrays.copyOf(elementData, size));
        result = prime * result + size;
        return result;
    }

    @Override
    public String toString() {
        return "MyArrayList [elementData=" + Arrays.toString(Arrays.copyOf(elementData, size)) + ", size=" + size + "]";
    }

}