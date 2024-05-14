package com.ieening.datastructure;

public class MyCycleDetection {
    private boolean[] marked;
    private int[] edgeTo;
    private MyStack<Integer> cycle;

    public MyCycleDetection(MyUndirectedGraph graph) {
        marked = new boolean[graph.V()];
        edgeTo = new int[graph.V()];

        for (int v = 0; v < graph.V(); v++) {
            if (!marked[v]) {
                dfs(graph, v, v);
            }
        }
    }

    private void dfs(MyUndirectedGraph graph, int vertex, int origin) {
        // 访问顶点
        marked[vertex] = true;
        for (int w : graph.adj(vertex)) {
            if (cycle != null) // 如果环已找到，返回
                return;
            if (!marked[w]) {
                edgeTo[w] = vertex;
                dfs(graph, w, vertex);
            }
            // 检测环是否存在，如果不允许自环和平行边，可以将判断条件由 (w != origin) 修改为 (w != origin &&
            // edgeTo[w]!=vertex && w!=vertex) 即可
            else if (w != origin) { // 如果访问过且不是上一次访问的，说明有环存在
                cycle = new MyResizingArrayStack<>();
                for (int x = vertex; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(vertex);
            }
        }
    }

    /**
     * 如果有环，返回{@code true}
     * 
     * @return 有环返回{@code true}
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * 返回一个环，如果没有则返回{@code null}
     * 
     * @return 返回图中的一个环，如果没有，返回{@code null}
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

}
