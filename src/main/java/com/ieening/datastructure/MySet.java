package com.ieening.datastructure;

import java.util.Iterator;

public interface MySet<E> extends Iterable<E> {
    // MARK:Query Operations

    /**
     * 返回集合元素个数
     *
     * @return 集合内元素的个数
     */
    int size();

    /**
     * 检查集合是否为空
     *
     * @return 如果数组为空，返回<tt>true</tt>
     */
    boolean isEmpty();

    /**
     * 如果集合包含指定元素，那么返回 <tt>true</tt>
     *
     * @param o 检查的元素
     * @return 如果集合包含指定的元素，返回<tt>true</tt>
     * @throws ClassCastException   如果指定的元素类型和集合元素类型参数不匹配
     * @throws NullPointerException 如果指定的元素为
     *                              {@code null}，且集合不允许{@code null}值
     */
    boolean contains(Object o);

    /**
     * 返回迭代集合所有元素的迭代器
     *
     * @return 可以迭代集合所有元素的 <tt>Iterator</tt>
     */
    Iterator<E> iterator();

    /**
     * 返回保存集合所有元素的{@code Object}数组
     *
     * @return 返回保存集合所有元素的数组
     */
    Object[] toArray();

    /**
     * 转换为数组，数组元素为集合类型参数
     *
     * @param <T> 待保存集合元素的数组运行时类型
     * @param a   待保存集合元素的数组
     * @return 保存集合所有元素的数组
     * @throws ArrayStoreException  如果运行时类型不是集合元素类型的父类型
     * @throws NullPointerException 如果参数数组为 {@code null}
     */
    <T> T[] toArray(T[] a);

    // MARK:Modification Operations

    /**
     * 集合添加元素
     *
     * @param e 集合待添加的元素
     * @return 调用该方法，如果集合成功添加元素，返回<tt>true</tt>
     * @throws UnsupportedOperationException 如果集合不支持<tt>add</tt>操作
     * @throws ClassCastException            如果指定的元素类型和集合元素类型参数不匹配
     * @throws NullPointerException          如果指定的元素为
     *                                       {@code null}，且集合不允许{@code null}值
     * @throws IllegalArgumentException      如果因为待添加的元素原因导致操作失败
     */
    boolean add(E e);

    /**
     * 移除指定元素
     *
     * @param o 从集合中待移除的元素
     * @return 如果成功移除，返回<tt>true</tt>
     * @throws ClassCastException            如果指定的元素类型和集合元素类型参数不匹配
     * @throws NullPointerException          如果指定的元素为
     *                                       {@code null}，且集合不允许{@code null}值
     * @throws UnsupportedOperationException 如果集合不支持<tt>remove</tt>操作
     */
    boolean remove(Object o);

    // MARK:Bulk Operations

    /**
     * 检查给定集合内的所有元素是否都在集合内，如果是返回<tt>true</tt>
     *
     * @param c 待检查的集合
     * @return 如果待检查的集合元素都在集合内，返回<tt>true</tt>
     * @throws ClassCastException   如果给定集合元素类型与集合元素类型不匹配
     * @throws NullPointerException 如果给定集合为 {@code null} 并且结合不支持
     *                              {@code null}
     */
    boolean containsAll(MySet<?> c);

    /**
     * 集合添加给定集合内的所有元素
     *
     * @param c 待添加的集合
     * @return 如果集合元素在调用完该方法后发生改变后，返回<tt>true</tt>
     * @throws UnsupportedOperationException 如果集合不支持<tt>addAll</tt>操作
     * @throws ClassCastException            如果给定集合元素类型与集合元素类型不匹配
     * @throws NullPointerException          如果给定集合为 {@code null} 并且结合不支持
     *                                       {@code null}
     * @throws IllegalArgumentException      如果因为待添加集合内的元素原因导致操作失败
     */
    boolean addAll(MySet<? extends E> c);

    /**
     * 集合移除给定集合内的所有元素
     *
     * @param c 待移除的集合
     * @return 如果集合元素在调用完该方法后发生改变后，返回<tt>true</tt>
     * @throws UnsupportedOperationException 如果集合不支持<tt>removeAll</tt>操作
     * @throws ClassCastException            如果给定集合元素类型与集合元素类型不匹配
     * @throws NullPointerException          如果给定集合为 {@code null} 并且结合不支持
     *                                       {@code null}
     */
    boolean removeAll(MySet<?> c);

    /**
     * 移除集合内所有元素，方法执行完后，集合为空
     *
     * @throws UnsupportedOperationException 如果集合不支持<tt>clear</tt>操作
     */
    void clear();

    // MARK:Comparison and hashing

    /**
     * 集合 {@code equals} 方法
     *
     * @param o object to be compared for equality with this collection
     * @return <tt>true</tt> if the specified object is equal to this
     *         collection
     */
    boolean equals(Object o);

    /**
     * 返回集合哈希值
     *
     * @return 集合哈希值
     */
    int hashCode();

}
