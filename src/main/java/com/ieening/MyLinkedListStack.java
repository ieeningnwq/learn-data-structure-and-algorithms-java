package com.ieening;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedListStack<E> implements MyStack<E> {
    // MARK:Fields

    /**
     * 头节点引用
     */
    Node<E> head;

    /**
     * 栈中元素数量
     */
    private int size;

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
     * 构造一个空的栈
     */
    public MyLinkedListStack() {
        head = null;
        size = 0;
    }

    // MARK:Stack Operations

    @Override
    public boolean isEmpty() {
        return size == 0 && head == null;
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

    @Override
    public int size() {
        return size;
    }

    @Override
    public void push(E element) {
        Node<E> newNode = new Node<E>(element, head);
        head = newNode;
        size++;
    }

    @Override
    public E pop() {
        checkIsEmpty();
        E element = head.item;
        head = head.next;
        size--;
        return element;
    }

    @Override
    public E peek() {
        checkIsEmpty();
        return head.item;
    }

    private void checkIsEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack Underflow");
        }
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }
}
