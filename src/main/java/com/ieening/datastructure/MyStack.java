package com.ieening.datastructure;

public interface MyStack<E> extends Iterable<E> {

    /**
     * 判断栈是否为空
     * 
     * @return 如果为空，返回<tt>true</tt>，否则返回<tt>false</tt>
     */
    boolean isEmpty();

    /**
     * 返回栈存储元素个数
     * 
     * @return 栈中元素数量
     */
    int size();

    /**
     * 元素入栈
     * 
     * @param item the item to add
     */
    void push(E element);

    /**
     * 元素出栈
     * 
     * @return 出栈的元素
     * @throws java.util.NoSuchElementException 如果栈为空
     */
    E pop();

    /**
     * 查看栈顶元素
     * 
     * @return 栈顶元素
     * @throws java.util.NoSuchElementException 如果栈为空
     */
    E peek();

    /**
     * 清除所有元素
     */
    void clear();
}
