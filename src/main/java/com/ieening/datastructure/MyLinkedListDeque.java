package com.ieening.datastructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedListDeque<E> implements MyDeque<E> {
    // MARK:Fields

    /**
     * 队列中元素数量
     */
    private int size;

    /**
     * 队列中队首节点
     */
    private Node<E> head;

    /**
     * 队列中队尾索引位置
     */
    private Node<E> tail;

    // 单向链表节点定义
    private static class Node<E> {
        E item;
        Node<E> prev; // 前驱节点
        Node<E> next; // 后继节点

        Node(E item, Node<E> prev, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    // MARK:Constructors
    /**
     * 构造一个空队列
     */
    public MyLinkedListDeque() {
        head = null;
        size = 0;
    }

    // MARK:Deque Operations

    @Override
    public void enqueueFirst(E element) {
        Node<E> newNode = new Node<>(element, null, null); // 新建节点
        if (isEmpty()) // 如果没有元素
            head = tail = newNode;
        else {
            head.prev = newNode;
            newNode.next = head; // 连接上链表
            head = newNode;
        }
        size++;
    }

    @Override
    public void enqueueLast(E element) {
        Node<E> newNode = new Node<>(element, null, null); // 新建节点
        if (isEmpty()) { // 如果没有元素
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail; // 连接上链表
            tail = newNode;
        }
        size++;
    }

    @Override
    public E dequeueFirst() {
        checkIsEmpty();
        E e = head.item;
        if (head.next != null) {
            head = head.next;
            head.prev = null;
        } else
            head = tail = null;
        size--;
        return e;
    }

    @Override
    public E dequeueLast() {
        checkIsEmpty();
        E e = tail.item;
        if (tail.prev != null) {
            tail = tail.prev;
            tail.next = null;
        } else
            head = tail = null;
        size--;
        return e;

    }

    @Override
    public E peekFirst() {
        checkIsEmpty();
        return head.item;
    }

    private void checkIsEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue Underflow");
        }
    }

    @Override
    public E peekLast() {
        checkIsEmpty();
        return tail.item;
    }

    // MARK:Queue Operations

    @Override
    public boolean isEmpty() {
        return head == null && tail == null && size == 0;
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
        head = tail = null;
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedIterator(head);

    }

    private class LinkedIterator implements Iterator<E> {
        private Node<E> current;

        /**
         * @param current
         */
        public LinkedIterator(Node<E> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E e = current.item;
            current = current.next;
            return e;
        }
    }

}
