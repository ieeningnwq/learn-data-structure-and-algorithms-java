package com.ieening.datastructure;

public interface MyQueue<E> extends Iterable<E> {

    /**
     * 判断队列是否为空
     * 
     * @return 如果为空，返回<tt>true</tt>，否则返回<tt>false</tt>
     */
    boolean isEmpty();

    /**
     * 返回队列中存储元素个数
     * 
     * @return 队列中元素数量
     */
    int size();

    /**
     * 元素入队列
     * 
     * @param item the item to add
     */
    boolean enqueue(E element);

    /**
     * 元素出队
     * 
     * @return 出队的元素
     * @throws java.util.NoSuchElementException 如果队列为空
     */
    E dequeue();

    /**
     * 查看队首元素
     * 
     * @return 队首元素
     * @throws java.util.NoSuchElementException 如果队列为空
     */
    E peek();

    /**
     * 清除所有元素
     */
    void clear();
}
