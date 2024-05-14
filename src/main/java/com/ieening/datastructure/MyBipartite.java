package com.ieening.datastructure;

public class MyBipartite {
    private boolean isBipartite; // 二分图？
    private boolean[] color; // color[v]：二分图的一边
    private boolean[] marked; // marked[v]：是否访问过
    private int[] edgeTo; // edgeTo[v]：抵达 v 的最后一条路径
    private Iterable<Integer> cycle; // 奇数边的环

    public MyBipartite(MyUndirectedGraph graph, boolean isDfs) {
        isBipartite = true;
        color = new boolean[graph.V()];
        marked = new boolean[graph.V()];
        edgeTo = new int[graph.V()];

        for (int v = 0; v < graph.V() && isBipartite; v++) {
            if (!marked[v]) {
                if (isDfs)
                    dfs(graph, v);
                else
                    bfs(graph, v);
            }
        }
    }

    private void bfs(MyUndirectedGraph graph, int s) {
        MyQueue<Integer> queue = new MyResizingArrayQueue<>();
        color[s] = false;
        marked[s] = true;
        queue.enqueue(s);
        while (!queue.isEmpty()) {
            Integer v = queue.dequeue();
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    color[w] = !color[v];
                    queue.enqueue(w);
                } else if (color[w] == color[v]) {
                    isBipartite = false;
                    MyQueue<Integer> bfsCycle = new MyResizingArrayQueue<>();
                    MyStack<Integer> stack = new MyResizingArrayStack<>();
                    int x = v, y = w;
                    while (x != y) {
                        stack.push(x);
                        bfsCycle.enqueue(y);
                        x = edgeTo[x];
                        y = edgeTo[y];
                    }
                    stack.push(x);
                    while (!stack.isEmpty()) {
                        bfsCycle.enqueue(stack.pop());

                    }
                    bfsCycle.enqueue(w);

                    cycle = bfsCycle;
                    return;
                }
            }
        }
    }

    private void dfs(MyUndirectedGraph graph, int v) {
        marked[v] = true; // 访问顶点
        for (int w : graph.adj(v)) {
            if (cycle != null)
                return;
            if (!marked[w]) {
                edgeTo[w] = v;
                color[w] = !color[v];
                dfs(graph, w);
            }
            // 检查是否是奇数边环
            else if (color[w] == color[v]) {
                isBipartite = false;
                MyStack<Integer> dfsCycle = new MyResizingArrayStack<>();
                dfsCycle.push(w);
                for (int x = v; x != w; x = edgeTo[x])
                    dfsCycle.push(x);
                dfsCycle.push(w);
                cycle = dfsCycle;
            }
        }
    }

    /**
     * @return the isBipartite
     */
    public boolean isBipartite() {
        return isBipartite;
    }

    /**
     * 返回顶点属于二分图中的哪一部分
     * 
     * @param v 待检查顶点
     * @return 顶点属于二分图的哪一部分
     * @throws UnsupportedOperationException 图不是二分图
     */
    public boolean color(int v) {
        validateVertex(v);
        if (!isBipartite) {
            throw new UnsupportedOperationException("graph is not bipartite");
        }
        return color[v];
    }

    /**
     * 如果图是二分图，返回{@code null}，否则返回奇数边环
     * 
     * @return 奇数边环
     */
    public Iterable<Integer> oddCycle() {
        return cycle;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= marked.length)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (marked.length - 1));
    }
}
