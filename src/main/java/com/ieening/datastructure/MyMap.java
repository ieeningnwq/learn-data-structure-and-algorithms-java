package com.ieening.datastructure;

public interface MyMap<K, V> {
    // MARK:Query Operations

    /**
     * 返回哈希表中键值对的数量，如果哈希表元素的数量超过<tt>Integer.MAX_VALUE</tt>，返回<tt>Integer.MAX_VALUE</tt>.
     *
     * @return 哈希表中键值对数量
     */
    int size();

    /**
     * 如果哈希表为空，返回<tt>true</tt>，否则返回<tt>false</tt>.
     *
     * @return 哈希表为空，返回<tt>true</tt>
     */
    boolean isEmpty();

    /**
     * 如果哈希表包含指定键的映射则返回<tt>true</tt>
     *
     * @param key 待测试是否在哈希表中的键名
     * @return 如果哈希表包含指定键的映射，返回<tt>true</tt>
     * @throws ClassCastException   如果键的类型和哈希表键的类型参数不匹配
     * @throws NullPointerException 如果指定键位为 {@code null}，并且哈希表不允许 {@code null} 值
     */
    boolean containsKey(Object key);

    /**
     * 哈希表中如果一个或多个映射包含目标值，则返回<tt>true</tt>.
     *
     * @param value 哈希表中判断是否存在的值
     * @return 如果哈希表中有一个或多个键对应的值是给定的值，返回<tt>true</tt>
     * @throws ClassCastException   如果给定值的类型和哈希表中值的类型参数不匹配
     * @throws NullPointerException 如果给定的值为 {@code null}，且哈希表中不允许 {@code null} 值
     */
    boolean containsValue(Object value);

    /**
     * 如果键存在，返回哈希表中键对应的值，否则返回 null
     *
     * @param key 待返回值对应的键
     * @return 如果键存在，返回哈希表中键对应的值，否则返回 {@code null}
     * @throws ClassCastException   如果键的类型和哈希表键的类型参数不匹配
     * @throws NullPointerException 如果指定键位为 {@code null}，并且哈希表不允许 {@code null} 值
     */
    V get(Object key);

    /**
     * 如果哈希表有指定键，那么返回键对应的值，否则返回默认值{@code defaultValue}
     *
     * @param key          查询的键
     * @param defaultValue 如果键不存在，返回的默认值
     * @return 指定键对应的值，否则返回给定的默认值{@code defaultValue}
     * @throws ClassCastException   如果键的类型与哈希表键类型参数不匹配
     * @throws NullPointerException 如果指定键位为 {@code null}，并且哈希表不允许 {@code null} 值
     */
    default V getOrDefault(Object key, V defaultValue) {
        V v;
        return (((v = get(key)) != null) || containsKey(key)) ? v : defaultValue;
    }

    // MARK:Modification Operations

    /**
     * 将键值对存入哈希表中，如果表中已包含键，那么新值将会替换旧值
     *
     * @param key   待存入哈希表中的键
     * @param value 带存入哈希表中的值
     * @return 如果哈希表已有对应的键，那么返回旧值，否则返回 <tt>null</tt>
     * @throws UnsupportedOperationException 如果哈希表不支持<tt>put</tt>操作
     * @throws ClassCastException            如果键或者值的类型与哈希表键或者值类型参数不匹配
     * @throws NullPointerException          如果指定键位为 {@code null}，并且哈希表不允许
     *                                       {@code null} 值
     * @throws IllegalArgumentException      如果因为键或者值的原因导致操作失败
     */
    V put(K key, V value);

    /**
     * 根据键移除键值对，返回移除的值，如果不包含键，那么返回 <tt>null</tt>
     *
     * @param key 待从哈希表中删除键值对的键
     * @return 如果哈希表中包含指定键值对，返回值，否则返回 <tt>null</tt>
     * @throws UnsupportedOperationException 如果哈希表不支持<tt>remove</tt>操作
     * @throws ClassCastException            如果键的类型与哈希表键或者值类型参数不匹配
     * @throws NullPointerException          如果指定键位为 {@code null}，并且哈希表不允许
     *                                       {@code null} 值
     */
    V remove(Object key);

    // MARK:Bulk Operations

    /**
     * 批量将键值对存入哈希表中
     *
     * @param m 待存储的哈希表
     * @throws UnsupportedOperationException 如果哈希表不支持 putAll</tt> 操作
     * @throws ClassCastException            如果键的类型与哈希表键或者值类型参数不匹配
     * @throws NullPointerException          如果指定键位为 {@code null}，并且哈希表不允许
     *                                       {@code null} 值
     * @throws IllegalArgumentException      如果因为待存储的哈希表键或者值的原因导致操作失败
     */
    void putAll(MyMap<? extends K, ? extends V> m);

    /**
     * 移除哈希表中所有的键值对，执行完操作后，哈希表处于空的状态
     *
     * @throws UnsupportedOperationException 如果哈希表不支持 <tt>clear</tt>
     */
    void clear();

    // MARK:Views

    /**
     * 返回哈希表中所有键的视图，注意哈希表构成键的视图，所以任何在视图上的修改都应该在该视图表现
     *
     * @return 返回哈希表键的集合视图
     */
    MySet<K> keySet();

    /**
     * 返回哈希表所有键的列表视图
     *
     * @return 哈希表中值列表视图
     */
    MyList<V> values();

    /**
     * 返回哈希表中键值对的视图
     *
     * @return a set view of the mappings contained in this map
     */
    MySet<MyMap.Entry<K, V>> entrySet();

    /**
     * 哈希表的条目（键值对）类
     */
    interface Entry<K, V> {
        /**
         * 返回键值对对应的键
         *
         * @return 键值对对应的键
         * @throws IllegalStateException 如果键值对被哈希表删除了
         */
        K getKey();

        /**
         * 返回键值对中对应的值
         *
         * @return 键值对对应的值
         * @throws IllegalStateException 如果键值对被哈希表删除了
         */
        V getValue();

        /**
         * 哈希表 {@code equals} 方法
         *
         * @param o 待比较的另一个键值对对象
         * @return 如果两个键值对相等返回 <tt>true</tt>
         */
        boolean equals(Object o);

        /**
         * 键值对 {@code hashCOde} 方法
         *
         * @return 键值对的哈希值
         */
        int hashCode();
    }

    // MARK:Comparison and hashing

    /**
     * 哈希表 {@code equals} 方法
     *
     * @param o 待比较的另一个 map 对象
     * @return 如果两个哈希表相等返回 <tt>true</tt>
     */
    boolean equals(Object o);

    /**
     * 哈希表 {@code hashCOde} 方法
     *
     * @return 哈希表的哈希值
     */
    int hashCode();
}
