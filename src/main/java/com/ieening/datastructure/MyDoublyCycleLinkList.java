package com.ieening.datastructure;

import java.util.Iterator;

public class MyDoublyCycleLinkList<E> implements Iterable<E> {
    // MARK:Fields

    private DulNode<E> head; // 链表头结点

    private int size; // 链表结点个数

    static class DulNode<E> {
        private E item;
        private DulNode<E> prior; // 直接前驱指针
        private DulNode<E> next; // 直接后继指针

        DulNode(E item) {
            this(item, null, null);
        }

        DulNode(E item, DulNode<E> prior, DulNode<E> next) {
            this.item = item;
            this.prior = prior;
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

        /**
         * @return the prior
         */
        public DulNode<E> getPrior() {
            return prior;
        }

        /**
         * @param prior the prior to set
         */
        public void setPrior(DulNode<E> prior) {
            this.prior = prior;
        }

        /**
         * @return the next
         */
        public DulNode<E> getNext() {
            return next;
        }

        /**
         * @param next the next to set
         */
        public void setNext(DulNode<E> next) {
            this.next = next;
        }
    }

    // MARK: Constructor

    /**
     * 构建一个空的双向循环链表
     */
    public MyDoublyCycleLinkList() {
        head = new DulNode<>(null);
        head.setNext(head);
        head.setPrior(head);

        size = 0;
    }

    // MARK:Query Operations

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E get(int index) {
        checkElementIndex(index);
        return node(index).getItem();
    }

    private DulNode<E> node(int index) {
        DulNode<E> x = head.next;
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
            for (DulNode<E> x = head.next; x != head; x = x.next) {
                if (x.item == null)
                    return index;
                index++;
            }
        } else {
            for (DulNode<E> x = head.next; x != head; x = x.next) {
                if (o.equals(x.item))
                    return index;
                index++;
            }
        }
        return -1;
    }

    public Object[] toArray() {
        Object[] result = new Object[size];
        int index = 0;
        for (DulNode<E> x = head.getNext(); x != head; x = x.getNext()) {
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
        for (DulNode<E> x = head.getNext(); x != head; x = x.getNext()) {
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
        private DulNode<E> current = head.getNext();

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
        DulNode<E> current = head;
        while ((current.getNext()) != head) {
            current = current.getNext();
        }
        // 新建连接 head 结点，并连接到末端结点
        current.setNext(new DulNode<E>(e, current, head));
        // 修改 size
        size++;
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
        DulNode<E> x = node(index);
        DulNode<E> preX = x.getPrior();
        DulNode<E> newDulNode = new DulNode<>(element, preX, x);
        preX.setNext(newDulNode);
        x.setPrior(newDulNode);
        // 修改 size
        size++;
    }

    public E remove(int index) {
        checkElementIndex(index);
        return unlink(index);
    }

    private E unlink(int index) {
        final DulNode<E> x = node(index);
        DulNode<E> preX = x.getPrior();
        DulNode<E> nextX = x.getNext();
        final E element = x.item;
        // 修改指针使得 x 删除
        preX.setNext(nextX);
        nextX.setPrior(preX);
        // 删除结点 x
        x.setItem(null);
        x.setPrior(null);
        x.setNext(null);
        // 修改 size
        size--;
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
        DulNode<E> x = node(index);
        E oldValue = x.getItem();
        x.setItem(element);
        return oldValue;
    }

    public boolean remove(Object o) {
        int i = 0;
        if (o == null) {
            for (DulNode<E> x = head.next; x != head; x = x.next) {
                if (x.item == null) {
                    unlink(i);
                    return true;
                }
                i++;
            }
        } else {
            for (DulNode<E> x = head.next; x != head; x = x.next) {
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
        DulNode<E> current = head.next;
        while (current != head) {
            current.item = null;
            DulNode<E> currentNext = current.next;
            current.setNext(null);
            current.setPrior(null);
            current = currentNext;
        }
        head.setNext(head);
        head.setPrior(head);

        // 修改 size
        size = 0;
    }

    // MARK:Visible

    public void draw(String filePath) {
        draw(1024, 1024, filePath);
    }

    public void draw(int width, int height, String filePath) {
        // 绘制结点
        int size = size();
        // 绘制 head 结点
        for (DulNode<E> x = head.getNext(); x != head; x = x.getNext()) {

        }
    }
}
