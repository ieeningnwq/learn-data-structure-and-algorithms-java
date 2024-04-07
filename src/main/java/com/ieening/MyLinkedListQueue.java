package com.ieening;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedListQueue<E> implements MyQueue<E> {
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
        Node<E> next;

        Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }
    }

    // MARK:Constructors

    /**
     * 构造一个空队列
     */
    public MyLinkedListQueue() {
        head = null;
        size = 0;
    }

    // MARK:Queue Operations

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public E dequeue() {
        checkIsEmpty();
        E e = head.item;
        head = head.next;
        size--;
        return e;
    }

    @Override
    public void enqueue(E element) {
        Node<E> newNode = new Node<>(element, null);
        if (head == null) { // 如果没有元素
            head = tail = newNode;
        } else {
            tail.next = newNode; // 连接上链表
            tail = newNode;
        }
        size++;
    }

    private void checkIsEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue Underflow");
        }
    }

    @Override
    public boolean isEmpty() {
        return head == null && size == 0;
    }

    @Override
    public E peek() {
        checkIsEmpty();
        return head.item;
    }

    @Override
    public int size() {
        return size;
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
