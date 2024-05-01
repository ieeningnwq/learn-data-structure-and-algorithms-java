package com.ieening.datastructure;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyResizingArrayStack<E> implements MyStack<E> {
    // MARK:Fields

    /**
     * 栈默认初始容量
     */
    private static final int DEFAULT_CAPACITY = 8;

    /**
     * 共享空栈实例
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 栈存储数据的数组
     */
    private Object[] elementData;

    /**
     * 栈中元素数量
     */
    private int size;

    // MARK:Constructors

    /**
     * 构造一个空的栈
     */
    public MyResizingArrayStack() {
        elementData = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * 构造一个初始容量为 initialCapacity 的栈
     * 
     * @param initialCapacity
     */
    public MyResizingArrayStack(int initialCapacity) {
        if (initialCapacity > 0) {
            elementData = new Object[initialCapacity];
            size = 0;
        } else if (initialCapacity == 0) {
            elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    // MARK:Stack Operations

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        checkIsEmpty();
        return (E) elementData[size - 1];
    }

    private void checkIsEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack Underflow");
        }
    }

    @Override
    public E pop() {
        checkIsEmpty();
        @SuppressWarnings("unchecked")
        E e = (E) elementData[--size];
        ensureCapacity(); // 有必要缩减
        return e;
    }

    @Override
    public void push(E element) {
        ensureCapacity();
        elementData[size++] = element;
    }

    private void ensureCapacity() {
        if (elementData.length == size) {
            int oldCapacity = elementData.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1); // 扩大至 1.5 倍
            elementData = Arrays.copyOf(elementData, newCapacity);
        } else if (elementData.length >> 1 > DEFAULT_CAPACITY && size < elementData.length >> 2) {
            int newCapacity = elementData.length >> 1; // 缩小至 0.5 倍
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new ReverseArrayIterator();
    }

    // a array iterator, in reverse order
    private class ReverseArrayIterator implements Iterator<E> {
        private int i;

        public ReverseArrayIterator() {
            i = size - 1;
        }

        public boolean hasNext() {
            return i >= 0;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return (E) elementData[i--];
        }
    }
}
