package com.ieening.datastructure;

import java.util.Iterator;

public class MyDepthFirstSearch {
    private boolean[] marked; // marked[v] 值表示是否有路径 s-v
    private int count; // 和 s 相连顶点个数，包含自身

    private MyList<Integer> traversalVertexes; // 遍历的顶点

    /**
     * 无向图深度优先搜索
     * 
     * @param graph     无向图
     * @param source    起点
     * @param recursive 是否递归实现
     */
    public MyDepthFirstSearch(MyUndirectedGraph graph, int source, boolean recursive) {
        marked = new boolean[graph.V()];
        validateVertex(source);
        traversalVertexes = new MyArrayList<>();
        if (recursive) {
            dfs0(graph, source);
        } else {
            dfs1(graph, source);
        }
    }

    /**
     * 无向图深度优先搜索，迭代实现
     * 
     * @param graph  无向图
     * @param source 起点
     */
    @SuppressWarnings("unchecked")
    private void dfs1(MyUndirectedGraph graph, int source) {
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[graph.V()];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = graph.adj(i).iterator();
        }

        MyResizingArrayStack<Integer> mStack = new MyResizingArrayStack<>();
        marked[source] = true;
        traversalVertexes.add(source);
        mStack.push(source);
        while (!mStack.isEmpty()) {
            int v = mStack.peek();
            if (adj[v].hasNext()) {
                int w = adj[v].next();
                if (!marked(w)) {
                    marked[w] = true;
                    traversalVertexes.add(w);
                    mStack.push(w);
                }
            } else
                mStack.pop();
        }
    }

    /**
     * 无向图深度优先搜索，递归实现
     * 
     * @param graph 无向图
     * @param s     开始结点
     */
    private void dfs0(MyUndirectedGraph graph, int v) {
        count++;
        marked[v] = true;
        traversalVertexes.add(v);
        for (int w : graph.adj(v)) {
            if (!marked[w]) { // 如果没有遍历，访问
                dfs0(graph, w);
            }
        }
    }

    /**
     * 有向图深度优先搜索
     * 
     * @param graph     有向图
     * @param source    起点
     * @param recursive 是否递归实现
     */
    public MyDepthFirstSearch(MyDigraph graph, int source, boolean recursive) {
        marked = new boolean[graph.V()];
        validateVertex(source);
        traversalVertexes = new MyArrayList<>();
        if (recursive) {
            dfs0(graph, source);
        } else {
            dfs1(graph, source);
        }
    }

    /**
     * 有向图深度优先搜索，迭代实现
     * 
     * @param graph
     * @param source
     */
    @SuppressWarnings("unchecked")
    private void dfs1(MyDigraph graph, int source) {
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[graph.V()];
        for (int v = 0; v < adj.length; v++) {
            adj[v] = graph.adj(v).iterator();
        }

        MyStack<Integer> mStack = new MyResizingArrayStack<>();
        marked[source] = true;
        traversalVertexes.add(source);
        mStack.push(source);
        while (!mStack.isEmpty()) {
            int v = mStack.peek();
            if (adj[v].hasNext()) {
                int w = adj[v].next();
                if (!marked(w)) {
                    marked[w] = true;
                    traversalVertexes.add(w);
                    mStack.push(w);
                }
            } else
                mStack.pop();
        }
    }

    /**
     * 有向图深度优先搜索，递归实现
     * 
     * @param graph  有向图
     * @param source 起点
     */
    private void dfs0(MyDigraph graph, int v) {
        count++;
        marked[v] = true;
        traversalVertexes.add(v);
        for (int w : graph.adj(v)) {
            if (!marked(w)) {
                dfs0(graph, w);
            }
        }
    }

    /**
     * 图中，s 和 v 是否连通
     * 
     * @param v 待检查的顶点
     * @return s 和 v 连通，返回 true
     */
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * 图中与 s，连通的顶点数量
     * 
     * @return 连通的顶点数量
     */
    public int count() {
        return count;
    }

    public Object[] traversalVertexes() {
        return traversalVertexes.toArray();
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + "is not between 0 and " + (V - 1));
    }
}
