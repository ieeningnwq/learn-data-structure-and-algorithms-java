package com.ieening;

import java.util.Iterator;

public interface MyList<E> {
    // MARK:Query Operations

    /**
     * 返回列表元素个数，如果列表元素个数大于 <tt>Integer.MAX_VALUE</tt>，返回 <tt>Integer.MAX_VALUE</tt>
     * 
     * @return 列表元素个数
     */
    int size();

    /**
     * 判断列表是否为空
     *
     * @return 如果为空，返回 <tt>true</tt>，否则返回 <tt>false</tt>
     */
    boolean isEmpty();

    /**
     * 判断列表是否包含指定元素
     *
     * @param o 待判断元素
     * @return 包含返回<tt>true</tt>
     * @throws ClassCastException   如果指定的元素与列表类型参数不能相互转换，抛出 ClassCastException
     * @throws NullPointerException 如果为 null 且列表不允许 null 时，抛出 NullPointerException
     */
    boolean contains(Object o);

    /**
     * 返回迭代器
     *
     * @return 迭代器
     */
    Iterator<E> iterator();

    /**
     * 返回按照顺序排列的数组
     *
     * @return 数组
     */
    Object[] toArray();

    /**
     * 返回类型为类型参数的数组
     *
     * @param a 存储数据的数组
     * @return 数组
     * @throws ArrayStoreException  如果数组 a 的运行时类型不是列表元素运行时类型的超类时抛出
     *                              ArrayStoreException
     * @throws NullPointerException 如果数组 a 是 null
     */
    <T> T[] toArray(T[] a);

    // MARK:Modification Operations

    /**
     * 在列表末尾添加元素
     *
     * @param e 待添加的元素
     * @return 添加成功返回<tt>true</tt>
     * @throws UnsupportedOperationException 如果列表不支持添加，比如不可变列表，那么抛出
     *                                       UnsupportedOperationException
     * @throws ClassCastException            如果列表类型参数不是待添加元素的超类，抛出
     *                                       ClassCastException
     * @throws NullPointerException          如果列表不支持 null，元素且待添加的元素为 null，抛出
     *                                       NullPointerException
     * @throws IllegalArgumentException      如果因为待添加元素的原因致使添加操作失败，抛出
     *                                       IllegalArgumentException 异常
     */
    boolean add(E e);

    /**
     * 移除第一次出现指定的元素，如果不包含目标元素，那么列表不发生改变
     *
     * @param o 待移除元素
     * @return 如果包含待删除元素，返回<tt>true</tt>
     * @throws ClassCastException            如果列表类型参数不是待移除元素的超类，抛出
     *                                       <tt>ClassCastException</tt>
     * @throws NullPointerException          如果列表不支持 null，元素且待移除的元素为 null，抛出
     *                                       <tt>NullPointerException</tt>
     * @throws UnsupportedOperationException 如果列表不支持移除操作，抛出
     *                                       <tt>UnsupportedOperationException</tt>
     *                                       异常
     */
    boolean remove(Object o);

    // MARK:Bulk Modification Operations

    /**
     * 如果指定列表的元素在该列表都包含，返回 <tt>true</tt>
     *
     * @param c 待检查列表
     * @return 如果包含，返回 <tt>true</tt>
     * @throws ClassCastException   如果指定列表元素与列表元素类型不兼容，抛出
     *                              <tt>ClassCastException</tt>
     * @throws NullPointerException 如果列表不支持 null，元素且待检查列表包含 null，抛出
     *                              <tt>NullPointerException</tt>
     */
    boolean containsAll(MyList<?> c);

    /**
     * 添加参数列表所有元素到该列表中
     *
     * @param c 待添加的列表
     * @return 添加成功，返回<tt>true</tt>
     * @throws UnsupportedOperationException 如果列表不支持 <tt>addAll</tt> 操作，抛出
     *                                       <tt>UnsupportedOperationException</tt>
     * @throws ClassCastException            如果指定列表元素与列表元素类型不兼容，抛出
     *                                       <tt>ClassCastException</tt>
     * @throws NullPointerException          如果列表不支持 null，元素且待检查列表包含 null，抛出
     *                                       <tt>NullPointerException</tt>
     * @throws IllegalArgumentException      如果因为待添加列表因为导致操作失败，抛出
     *                                       <tt>IllegalArgumentException</tt>
     * @see #add(Object)
     */
    boolean addAll(MyList<? extends E> c);

    /**
     * 从列表中移除参数列表的所有参数
     *
     * @param c 代移除的列表
     * @return 移除成功，返回<tt>true</tt>
     * @throws UnsupportedOperationException 如果列表不支持移除操作，抛出
     *                                       <tt>UnsupportedOperationException</tt>
     * @throws ClassCastException            如果指定列表元素与列表元素类型不兼容，抛出
     *                                       <tt>ClassCastException</tt>
     * @throws NullPointerException          如果列表不支持 null，元素且待检查列表包含 null，抛出
     *                                       <tt>NullPointerException</tt>
     */
    boolean removeAll(MyList<?> c);

    /**
     * 移除所有列表元素
     *
     * @throws UnsupportedOperationException 如果列表不支持
     */
    void clear();

    // MARK:Comparison and hashing

    /**
     * 两个列表比较
     *
     * @param o 比较列表
     * @return 相等返回<tt>true</tt>
     */
    boolean equals(Object o);

    /**
     * 计算列表哈希值
     *
     * @return 列表的哈希值
     */
    int hashCode();

    // MARK:Positional Access Operations

    /**
     * 返回列表索引为 index 值
     *
     * @param index 索引值
     * @return 列表元素
     * @throws IndexOutOfBoundsException 如果索引越界（<tt>index &lt; 0 || index &gt;= size()</tt>）
     */
    E get(int index);

    /**
     * 设置索引为 <tt>index</tt> 的值
     *
     * @param index   索引值
     * @param element 待设置值
     * @return 列表索引位置先前值
     * @throws UnsupportedOperationException 如果列表不支持<tt>set</tt>操作
     * @throws ClassCastException            如果元素与列表元素类型不兼容
     * @throws NullPointerException          如果指定的元素为
     *                                       <tt>null</tt>，且列表不支持<tt>null</tt>处理
     * @throws IllegalArgumentException      如果因为元素导致操作失败
     * @throws IndexOutOfBoundsException     如果索引越界（<tt>index &lt; 0 || index &gt;= size()</tt>）
     */
    E set(int index, E element);

    /**
     * 在指定索引处插入指定元素，把插入位置右边的元素全部右移一位
     *
     * @param index   待插入索引
     * @param element 待插入元素
     * @throws UnsupportedOperationException 如果列表不支持 <tt>add</tt> 操作
     * @throws ClassCastException            如果待插入元素类型与类型参数不兼容
     * @throws NullPointerException          如果待插入元素是<tt>null</tt>，且列表不支持<tt>null</tt>元素
     * @throws IllegalArgumentException      因为待插入元素失败
     * @throws IndexOutOfBoundsException     索引越界（<tt>index &lt; 0 || index &gt; size()</tt>）
     */
    void add(int index, E element);

    /**
     * 按照索引移除指定元素，移除元素右边的数据往左移动，返回已移除元素值
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *                                       is not supported by this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    E remove(int index);

    // Search Operations

    /**
     * 查找第一次出现指定元素的索引，如果没有，返回<tt>-1</tt>
     *
     * @param o 待查找元素
     * @return 查找索引结果
     * @throws ClassCastException   如果带查找元素类型与列表不兼容
     * @throws NullPointerException 如果待查找元素为<tt>null</tt>，且列表不支持<tt>null</tt>
     */
    int indexOf(Object o);

    /**
     * 从后查找第一次出现指定元素的索引，如果没有，返回<tt>-1</tt>
     *
     * @param o 待查找元素
     * @return 从后查找第一次出现指定元素的索引
     * @throws ClassCastException   如果带查找元素类型与列表不兼容
     * @throws NullPointerException 如果待查找元素为<tt>null</tt>，且列表不支持<tt>null</tt>
     */
    int lastIndexOf(Object o);

    // MARK:View

    /**
     * 返回子列表，索引在 [fromIndex, toIndex) 之间，如果 fromIndex==toIndex，返回空列表
     *
     * @param fromIndex 子列表较低索引值
     * @param toIndex   子列表较高索引值
     * @return 列表视图
     * @throws IndexOutOfBoundsException 非法索引时（<tt>fromIndex &lt; 0 || toIndex &gt; size || fromIndex &gt; toIndex</tt>），抛出
     *                                   <tt>IndexOutOfBoundsException</tt>
     */
    MyList<E> subList(int fromIndex, int toIndex);
}
