package com.ieening.datastructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyResizingArrayDeque<E> implements MyDeque<E> {
    // MARK:Fields

    /**
     * 队列默认初始容量
     */
    private static final int DEFAULT_CAPACITY = 8;
    /**
     * 共享空队列实例
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 队列中元素数量
     */
    private int size;

    /**
     * 队列存储数据的数组
     */
    private Object[] elementData;
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
    public MyResizingArrayDeque() {
        elementData = new Object[DEFAULT_CAPACITY]; // 加一，因为 tail 需要占用一个空的位置
        head = tail = size = 0;
    }

    /**
     * 构造一个初始容量为 initialCapacity 的队列
     * 
     * @param initialCapacity
     */
    public MyResizingArrayDeque(int initialCapacity) {
        if (initialCapacity > 0) {
            elementData = new Object[initialCapacity];
            head = tail = size = 0;
        } else if (initialCapacity == 0) {
            elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    // MARK:Deque Operations

    @Override
    public void enqueueFirst(E element) {
        elementData[head = index(head - 1)] = element;
        size++;
        if (head == tail)
            doubleCapacity();
    }

    @Override
    public void enqueueLast(E element) {
        elementData[tail] = element;
        size++;
        if ((tail = index(tail + 1)) == head)
            doubleCapacity();
    }

    private void doubleCapacity() {
        assert head == tail;
        int h = head;
        int n = elementData.length;
        int r = n - h;
        int newCapacity = n << 1;
        if (newCapacity < 0) {
            throw new IllegalStateException("Sorry, deque too big");
        }
        Object[] a = new Object[newCapacity];
        System.arraycopy(elementData, h, a, 0, r);
        System.arraycopy(elementData, 0, a, r, h);
        elementData = a;
        head = 0;
        tail = n;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E dequeueFirst() {
        checkIsEmpty();
        E e = (E) elementData[head];
        head = index(head + 1);
        size--;
        return e;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E dequeueLast() {
        checkIsEmpty();
        E e = (E) elementData[tail = index(tail - 1)];
        size--;
        return e;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peekFirst() {
        checkIsEmpty();
        return (E) elementData[head];
    }

    private void checkIsEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue Underflow");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peekLast() {
        checkIsEmpty();
        return (E) elementData[index(tail - 1)];
    }

    private int index(int i) {
        return (i + elementData.length) % elementData.length;
    }

    // MARK:Queue Operations

    @Override
    public boolean isEmpty() {
        return size == 0 && head == tail;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean enqueue(E element) {
        enqueueLast(element);
        return true;
    }

    @Override
    public E dequeue() {
        return dequeueFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public void clear() {
        head = tail = size = 0;
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
