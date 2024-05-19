package com.ieening.datastructure;

public class MyDepthFirstPaths {
    private boolean[] marked; // marked[v]：s-v 路径是否存在
    private int[] edgeTo; // edgeTo[v]：s-v 路径中最后一条边

    private final int source; // 起始顶点

    /**
     * 无向图深度优先搜索路径问题
     * 
     * @param graph  无向图
     * @param source 起点
     */
    public MyDepthFirstPaths(MyUndirectedGraph graph, int source) {
        marked = new boolean[graph.V()];
        validateVertex(source);
        this.source = source;
        edgeTo = new int[graph.V()];
        dfs(graph, source);
    }

    /**
     * 无向图深度优先搜索
     * 
     * @param graph 无向图
     * @param v     顶点
     */
    private void dfs(MyUndirectedGraph graph, int v) {
        marked[v] = true;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(graph, w);
            }
        }
    }

    /**
     * 有向图深度优先搜索路径问题
     * 
     * @param graph  无向图
     * @param source 起点
     */
    public MyDepthFirstPaths(MyDigraph graph, int source) {
        marked = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        validateVertex(source);
        this.source = source;
        dfs(graph, source);
    }

    /**
     * 有向图深度优先搜索
     * 
     * @param graph
     * @param v
     */
    private void dfs(MyDigraph graph, int v) {
        marked[v] = true;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(graph, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + "is not between 0 and " + (V - 1));
    }

    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v))
            return null;
        MyStack<Integer> mStack = new MyResizingArrayStack<>();
        for (int x = v; x != source; x = edgeTo[x]) {
            mStack.push(x);
        }
        mStack.push(source);
        return mStack;
    }
}
