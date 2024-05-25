package com.ieening.datastructure;

import java.util.Iterator;

public class MySingleCycleLinkedList<E> implements Iterable<E> {
    // MARK:Fields

    /**
     * 头结点
     */
    private Node<E> head;

    /**
     * 定义结点
     */
    static class Node<E> {
        private Node<E> next;
        private E item;

        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }

        Node() {
            this(null, null);
        }

        /**
         * @return the next
         */
        public Node<E> getNext() {
            return next;
        }

        /**
         * @param next the next to set
         */
        public void setNext(Node<E> next) {
            this.next = next;
        }

        /**
         * @return the item
         */
        public E getItem() {
            return item;
        }

        /**
         * @param item the item to set
         */
        public void setItem(E item) {
            this.item = item;
        }

    }

    // MARK:Constructor

    /**
     * 创建一个空的单循环链表
     */
    public MySingleCycleLinkedList() {
        head = new Node<>();
        head.setNext(head);
    }

    // MARK:Query Operations

    public int size() {
        int size = 0;
        Node<E> node = head;
        while ((node = node.getNext()) != head)
            size++;

        return size;
    }

    public boolean isEmpty() {
        return head == head.next;
    }

    public E get(int index) {
        checkElementIndex(index);
        return node(index).getItem();

    }

    private Node<E> node(int index) {
        Node<E> x = head.next;
        for (int i = 0; i < index; i++) {
            x = x.next;
        }
        return x;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size();
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size();
    }

    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Node<E> x = head.next; x != head; x = x.next) {
                if (x.item == null)
                    return index;
                index++;
            }
        } else {
            for (Node<E> x = head.next; x != head; x = x.next) {
                if (o.equals(x.item))
                    return index;
                index++;
            }
        }
        return -1;
    }

    public Object[] toArray() {
        Object[] result = new Object[size()];
        int index = 0;
        for (Node<E> x = head.getNext(); x != head; x = x.getNext()) {
            result[index++] = x.getItem();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        int size = size();
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int index = 0;
        Object[] result = a;
        for (Node<E> x = head.getNext(); x != head; x = x.getNext()) {
            result[index++] = x.getItem();
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public Iterator<E> iterator() {
        return new CycleLinkedListIterator();
    }

    private class CycleLinkedListIterator implements Iterator<E> {
        private Node<E> current = head.getNext();

        @Override
        public boolean hasNext() {
            return current != head;
        }

        @Override
        public E next() {
            E e = current.getItem();
            current = current.getNext();
            return e;
        }

    }

    // MARK:Modify Operations

    public boolean add(E e) {
        linkTail(e);
        return true;
    }

    private void linkTail(E e) {
        // 找到末端结点
        Node<E> current = head;
        while ((current.getNext()) != head) {
            current = current.getNext();
        }
        // 新建连接 head 结点，并连接到末端结点
        current.setNext(new Node<E>(e, head));
    }

    public void add(int index, E element) {
        checkPositionIndex(index);

        if (index == this.size()) {
            linkTail(element);
        } else {
            linkBefore(element, index);
        }

    }

    private void linkBefore(E element, int index) {
        Node<E> newNode = new Node<>(element, null);
        if (index == 0) { // 没有前置
            final Node<E> h = head.next; // 保存头节点
            newNode.next = h;// 接上链表
            head.next = newNode;
        } else { // 有前置
            Node<E> preX = node(index - 1);
            Node<E> x = preX.next;
            preX.next = newNode;
            newNode.next = x;
        }
    }

    public E remove(int index) {
        checkElementIndex(index);
        return unlink(index);
    }

    private E unlink(int index) {
        final Node<E> x = node(index);
        final E element = x.item;
        if (head.getNext().getNext() == head && index == 0 && size() == 1) { // 仅有一个节点
            head.setNext(head);
        } else if (index == 0) { // 多个节点，删除头节点
            head.setNext(x.getNext());
        } else { // 多个节点非首节点
            Node<E> preX = node(index - 1);
            preX.setNext(x.getNext());
        }
        return element;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size();
    }

    public E set(int index, E element) {
        checkElementIndex(index);
        Node<E> x = node(index);
        E oldValue = x.getItem();
        x.setItem(element);
        return oldValue;
    }

    public boolean remove(Object o) {
        int i = 0;
        if (o == null) {
            for (Node<E> x = head.next; x != head; x = x.next) {
                if (x.item == null) {
                    unlink(i);
                    return true;
                }
                i++;
            }
        } else {
            for (Node<E> x = head.next; x != head; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(i);
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    // MARK:Bulk Operations
    public void clear() {
        Node<E> current = head.next;
        while (current != head) {
            current.setItem(null);
            Node<E> currentNext = current.next;
            current.setNext(null);
            current = currentNext;
        }
        head.setNext(head);
    }

    // MARK:Visible

    public void draw(String filePath) {
        draw(1024, 1024, filePath);
    }

    public void draw(int width, int height, String filePath) {

    }
}
