package com.ieening.datastructure;

public class MyConnectedComponents {
    private boolean[] marked; // marked[v]：顶点 v 是不是被标记
    private int[] id; // id[v]：包含顶点 v 的连通分量 id
    private int[] size; // size[id] = 给定连通分量顶点的个数
    private int count; // 连通分量的数量

    /**
     * 计算无向图连通分量
     * 
     * @param graph 无向图
     */
    public MyConnectedComponents(MyUndirectedGraph graph) {
        marked = new boolean[graph.V()];
        id = new int[graph.V()];
        size = new int[graph.V()];

        for (int v = 0; v < graph.V(); v++) {
            if (!marked[v]) {
                dfs(graph, v);
                count++;
            }
        }
    }

    private void dfs(MyUndirectedGraph graph, int v) {
        // 访问顶点
        marked[v] = true; // 标识已被访问
        id[v] = count; // 顶点 w 所在连通分量 id
        size[count]++; // 所在连通分量加 1
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                dfs(graph, w);
            }
        }
    }

    /**
     * 顶点 v 所在连通分量 id
     * 
     * @param v 顶点
     * @return 顶点所在连通分量 id
     */
    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + "is not between 0 and " + (V - 1));
    }

    /**
     * 返回顶点 v 所在连通分量顶点个数
     * 
     * @param v 顶点
     * @return 顶点 v 所在连通分量顶点个数
     */
    public int size(int v) {
        validateVertex(v);
        return size[id[v]];
    }

    /**
     * 连通分量个数
     * 
     * @return
     */
    public int count() {
        return count;
    }

    /**
     * 顶点 v 和 顶点 w 是否连通
     * 
     * @param v 顶点 v
     * @param w 顶点 w
     * @return
     */
    public boolean connected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id[v] == id[w];
    }

}
