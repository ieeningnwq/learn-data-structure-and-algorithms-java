package com.ieening.datastructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyResizingArrayQueue<E> implements MyQueue<E> {
    // MARK:Fields

    /**
     * 队列默认初始容量
     */
    private static final int DEFAULT_CAPACITY = 8;

    /**
     * 共享空队列实例
     */
    private static final Object[] EMPTY_ELEMENT_DATA = {};

    /**
     * 队列存储数据的数组
     */
    private Object[] elementData;

    /**
     * 队列中元素数量
     */
    private int size;

    /**
     * 队列中队首索引位置
     */
    private int head;

    /**
     * 队列中队尾索引位置
     */
    private int tail;

    // MARK:Constructors

    /**
     * 构造一个空队列
     */
    public MyResizingArrayQueue() {
        elementData = new Object[DEFAULT_CAPACITY]; // 加一，因为 tail 需要占用一个空的位置
        head = tail = size = 0;
    }

    /**
     * 构造一个初始容量为 initialCapacity 的队列
     * 
     * @param initialCapacity
     */
    public MyResizingArrayQueue(int initialCapacity) {
        if (initialCapacity > 0) {
            elementData = new Object[initialCapacity];
            head = tail = size = 0;
        } else if (initialCapacity == 0) {
            elementData = EMPTY_ELEMENT_DATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    // MARK:Queue Operations

    @Override
    public void clear() {
        head = tail = size = 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E dequeue() {
        checkIsEmpty();
        E e = (E) elementData[head];
        head = (head + 1) % (elementData.length);
        size--;
        return e;
    }

    @Override
    public boolean enqueue(E element) {
        elementData[tail] = element;
        size++;
        if ((tail = (tail + 1) % elementData.length) == head)
            doubleCapacity();
        return true;
    }

    private void doubleCapacity() {
        assert head == tail;
        int p = head;
        int n = elementData.length;
        int r = n - p; // number of elements to the right of p
        int newCapacity = n << 1;
        if (newCapacity < 0) // 如果操作 int 型最大数值
            throw new IllegalStateException("Sorry, deque too big");
        Object[] a = new Object[newCapacity];
        System.arraycopy(elementData, p, a, 0, r);
        System.arraycopy(elementData, 0, a, r, p);
        elementData = a;
        head = 0;
        tail = n;
    }

    private void checkIsEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue Underflow");
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0 && head == tail;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        checkIsEmpty();
        return (E) elementData[head];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new RingBufferIterator(head, tail);
    }

    private class RingBufferIterator implements Iterator<E> {
        private int head;
        private int tail;

        /**
         * @param head
         * @param tail
         */
        public RingBufferIterator(int head, int tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public boolean hasNext() {
            return head != tail;
        }

        @Override
        public E next() {
            @SuppressWarnings("unchecked")
            E e = (E) elementData[head];
            head = (head + 1) % elementData.length;
            return e;
        }

    }
}
