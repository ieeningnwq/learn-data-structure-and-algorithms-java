package com.ieening.algorithms;

/**
 * MyUnionFind
 */
public interface MyUnionFind {
    /**
     * 如果顶点 p 和 顶点 q 不相连，那么增加一条连接
     * 
     * @param p 顶点
     * @param q 顶点
     */
    void union(int p, int q);

    /**
     * 顶点 p 所在分量的标识符
     * 
     * @param p 顶点
     * @return 分量标识符
     */
    int find(int p);

    /**
     * 如果 p 和 q 在同一个分量中，返回 true
     * 
     * @param p 顶点
     * @param q 顶点
     * @return 是否在同一个分量中
     */
    boolean connected(int p, int q);

    /**
     * 连通分量数量
     * 
     * @return 连通分量数量
     */
    int count();
}
