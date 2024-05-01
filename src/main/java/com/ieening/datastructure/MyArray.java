package com.ieening.datastructure;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class MyArray<E> implements Iterable<E> {
    // MARK:Fields
    /**
     * The array into which the elements of the MyArray are stored.
     * The capacity of the MyArray is the length of this array buffer. Any
     * empty MyArray with elementData == EMPTY_ELEMENTDATA.
     */
    private final Object[] elementData;
    /**
     * The size of the ArrayList (the number of elements it contains).
     */
    private int size;
    /**
     * modify times counter
     */
    protected transient int modCount = 0;

    /**
     * Shared empty array instance used for empty instances.
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 默认容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    // MARK: Constructers
    /**
     * 通过给定数组初始化 MyArray
     * 
     * @param elementData
     */
    public MyArray(E[] elementData) {
        this.elementData = Arrays.copyOf(elementData, elementData.length);
        size = elementData.length;
    }

    /**
     * 初始化一个容量为 capacity 的数组，size 默认为零
     * 
     * @param capacity 数组容量
     */
    public MyArray(int capacity) {
        if (capacity > 0) {
            this.elementData = new Object[capacity];
        } else if (capacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    capacity);
        }
    }

    /**
     * Constructs an empty MyArray.
     */
    public MyArray() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    // MARK:查找和获取

    /**
     * 数组数据 size
     * 
     * @return the size
     */
    public int getSize() {
        return size;
    }

    public E get(int index) {
        rangeCheck(index);
        return elementData(index);
    }

    /**
     * 随机获取一个元素
     * 
     * @return
     */
    public E randomAccess() {
        if (isEmpty()) {
            throw new RuntimeException("MyArray is empty");
        }
        int randomIndex = ThreadLocalRandom.current().nextInt(size);
        return elementData(randomIndex);

    }

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (elementData[i] == null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--)
                if (elementData[i] == null)
                    return i;
        } else {
            for (int i = size - 1; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * 检查索引 index 是否越界
     * 
     * @param index 索引
     */
    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    public boolean isFull() {
        return size + 1 == elementData.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // MARK:添加、删除、设置

    public void add(E element, int index) {
        rangeCheckForAdd(index);
        if (isFull()) {
            throw new RuntimeException("array is full, forbid adding");
        }
        System.arraycopy(elementData, index, elementData, index + 1,
                size - index);
        elementData[index] = element;
        size++;
        modCount++;
    }

    public void add(E element) {
        int index = size;
        add(element, index);
    }

    public E remove(int index) {
        rangeCheck(index);

        modCount++;

        E oldValue = elementData(index);

        int numMoved = --size - index;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index,
                    numMoved);
        return oldValue;
    }

    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    remove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    remove(index);
                    return true;
                }
        }
        return false;
    }

    public E set(int index, E element) {
        rangeCheck(index);

        E oldValue = elementData(index);
        elementData[index] = element;
        modCount++;
        return oldValue;

    }

    // MARK:迭代操作

    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        int cursor; // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        Itr() {
        }

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = MyArray.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                MyArray.this.remove(lastRet);
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
            final int size = MyArray.this.size;
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = MyArray.this.elementData;
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

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    // MARK:Object 方法
    @Override
    public String toString() {
        return "MyArray [elementData=" + Arrays.toString(Arrays.copyOf(elementData, size)) + ", size=" + size + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(elementData);
        result = prime * result + size;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("rawtypes")
        MyArray other = (MyArray) obj;
        if (size != other.size)
            return false;
        if (!Arrays.deepEquals(Arrays.copyOf(elementData, size), Arrays.copyOf(other.elementData, other.size)))
            return false;

        return true;
    }

    // MARK:与数组转换
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

}
