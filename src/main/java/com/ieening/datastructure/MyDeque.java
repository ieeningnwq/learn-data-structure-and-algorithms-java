package com.ieening.datastructure;

public interface MyDeque<E> extends MyQueue<E> {
    /**
     * 队首入队
     * 
     * @param element
     */
    void enqueueFirst(E element);

    /**
     * 队尾入队
     * 
     * @param element
     */
    void enqueueLast(E element);

    /**
     * 队首出队
     * 
     * @throws java.util.NoSuchElementException 如果队列为空
     * @return 出队元素
     */
    E dequeueFirst();

    /**
     * 队尾出队
     * 
     * @return 出队元素
     * @throws java.util.NoSuchElementException 如果队列为空
     */
    E dequeueLast();

    /**
     * 队首查看
     * 
     * @return 队首查看的元素
     * @throws java.util.NoSuchElementException 如果队列为空
     */
    E peekFirst();

    /**
     * 队尾查看
     * 
     * @return 队尾查看的元素
     * @throws java.util.NoSuchElementException 如果队列为空
     */
    E peekLast();
}
