package com.ieening.datastructure;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;

public class MyLinkedList<E> implements MyList<E> {
    // MARK:Fields

    // 列表大小
    transient int size = 0;

    /**
     * 头节点引用
     */
    transient Node<E> head;

    /**
     * 尾节点引用
     */
    transient Node<E> tail;

    // 单向链表节点定义
    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.item = element;
            this.next = next;
        }
    }

    /**
     * 列表被修改次数，主要用于迭代时列表是否发生修改
     */
    protected transient int modCount = 0;

    // MARK:Constructors

    /**
     * 构造空列表
     */
    public MyLinkedList() {
    }

    /**
     * 从列表中构造一个链表列表
     *
     * @param c 构造链表列表源列表
     * @throws NullPointerException 如果列表为空
     */
    public MyLinkedList(MyList<? extends E> c) {
        this();
        addAll(c);
    }

    // MARK:Positional Access Operations
    @Override
    public E get(int index) {
        checkPositionIndex(index);
        return node(index).item;
    }

    private Node<E> node(int index) {
        Node<E> x = head;
        for (int i = 0; i < index; i++) {
            x = x.next;
        }
        return x;
    }

    @Override
    public E set(int index, E element) {
        checkElementIndex(index);
        Node<E> x = node(index);
        E oldValue = x.item;
        x.item = element;
        return oldValue;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    @Override
    public void add(int index, E element) {
        checkPositionIndex(index);

        if (index == size) {
            linkTail(element);
        } else {
            linkBefore(element, index);
        }

    }

    private void linkBefore(E element, int index) {
        Node<E> newNode = new Node<>(element, null);
        if (index == 0) { // 没有前置
            final Node<E> h = head; // 保存头节点
            newNode.next = h;// 接上链表
            head = newNode;
        } else { // 有前置
            Node<E> preX = node(index - 1);
            Node<E> x = preX.next;
            preX.next = newNode;
            newNode.next = x;
        }
        size++;
        modCount++;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Node<E> x = head; x != null; x = x.next) {
                if (x.item == null)
                    return index;
                index++;
            }
        } else {
            for (Node<E> x = head; x != null; x = x.next) {
                if (o.equals(x.item))
                    return index;
                index++;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        final Object[] l = toArray();
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (l[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(l[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public E remove(int index) {
        checkElementIndex(index);
        return unlink(index);
    }

    private E unlink(int index) {
        final Node<E> x = node(index);
        final E element = x.item;
        if (head == tail && index == 0 && size == 1) { // 仅有一个节点
            head = tail = null;
        } else if (index == 0) { // 多个节点，删除头节点
            head = head.next;
        } else if (index == size - 1) { // 多个节点，删除尾节点
            tail = node(index - 1);
            tail.next = null;
        } else { // 多个节点非首节点非尾节点
            Node<E> preX = node(index - 1);
            preX.next = x.next;
        }
        size--;
        modCount++;
        return element;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    // MARK:Modification Operations

    @Override
    public boolean add(E e) {
        linkTail(e);
        return true;

    }

    private void linkTail(E e) {
        final Node<E> t = tail; // 保存尾节点位置
        final Node<E> newNode = new Node<>(e, null); // 新建节点
        tail = newNode; // 尾节点更新位置
        if (t == null) { // 如果链表没有元素
            head = newNode; // 初始化头节点
        } else
            t.next = newNode; // 新节点与链表连接
        size++;
        modCount++;
    }

    @Override
    public boolean remove(Object o) {
        int i = 0;
        if (o == null) {
            for (Node<E> x = head; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(i);
                    return true;
                }
                i++;
            }
        } else {
            for (Node<E> x = head; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(i);
                    return true;
                }
                i++;
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
        Node<E> current = head; // 当前节点
        int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            checkForComodification();

            E e = current.item;
            current = current.next;
            return e;
        }

        private void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int index = 0;
        for (Node<E> x = head; x != null; x = x.next) {
            result[index++] = x.item;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int index = 0;
        Object[] result = a;
        for (Node<E> x = head; x != null; x = x.next) {
            result[index++] = x.item;
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    // MARK:Bulk Modification Operations

    @Override
    public boolean addAll(MyList<? extends E> l) {
        checkPositionIndex(size);

        Object[] a = l.toArray();
        int numNew = a.length;
        if (numNew == 0) {
            return false;
        }
        Node<E> pred = tail; // 前驱和后继节点

        for (Object o : a) {
            @SuppressWarnings("unchecked")
            E e = (E) o;
            Node<E> newNode = new Node<>(e, null);
            if (pred == null) { // 如果前驱为 null，即头节点 head 为 null
                head = newNode;
            } else {
                pred.next = newNode; // 将 newNode 接入链表中
            }
            pred = newNode;
        }
        tail = pred; // 指定尾节点
        size += numNew;
        modCount++;
        return true;
    }

    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Unimplemented method 'checkPositionIndex'");

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
        boolean modified = false;
        int i = 0;
        for (Node<E> x = head; x != null; x = x.next) {
            if (c.contains(x.item)) {
                remove(i--); // 减一个，索引少一个
                modified = true;
            }
            i++;
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
        MyLinkedList other = (MyLinkedList) obj;
        if (size != other.size())
            return false;
        if (!Arrays.deepEquals(toArray(), other.toArray()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(toArray());
        result = prime * result + size;
        return result;
    }

    @Override
    public String toString() {
        return "MyLinkedList [" + Arrays.toString(toArray()) + ", size=" + size + "]";
    }

}
