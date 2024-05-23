package com.ieening.datastructure;

public class MyCycleDetection {
    private boolean[] marked;
    private int[] edgeTo;
    private MyStack<Integer> cycle;

    private boolean[] onStack; // onStack[v]：顶点v是否在栈中，有向图使用

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

    public MyCycleDetection(MyDigraph graph) {
        marked = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        onStack = new boolean[graph.V()];

        for (int v = 0; v < graph.V(); v++) {
            if (!marked[v] && cycle == null) {
                dfs(graph, v);
            }
        }
    }

    public MyCycleDetection(MyDigraph graph, MyQueue<Integer> queue) {
        // 顶点的入度
        int[] inDegree = new int[graph.V()];
        for (int v = 0; v < graph.V(); v++) {
            inDegree[v] = graph.inDegree(v);
        }
        // 初始化队列，存入所有入度为零的顶点，入度为零，不可能作为环的的顶点，因为环的顶点至少有一个入度和一个出度形成环状
        queue.clear();
        for (int v = 0; v < graph.V(); v++) {
            if (inDegree[v] == 0)
                queue.enqueue(v);
        }

        while (!queue.isEmpty()) { // 遍历顶点，
            int v = queue.dequeue();
            for (int w : graph.adj(v)) {
                inDegree[w]--; // 减去因为不能作为环的顶点入度，减到零，说明该顶点也不可能作为环的顶点，入队，
                if (inDegree[w] == 0)
                    queue.enqueue(w);
            }
        }
        // there is a directed cycle in subgraph of vertices with inDegree >= 1.
        edgeTo = new int[graph.V()];
        int root = -1;
        for (int v = 0; v < graph.V(); v++) {
            if (inDegree[v] == 0)
                continue;
            else
                root = v;
            for (int w : graph.adj(v)) {
                if (inDegree[w] > 0) {
                    edgeTo[w] = v;
                }
            }
        }

        if (root != -1) {
            // 环中任意一个顶点
            marked = new boolean[graph.V()];
            while (!marked[root]) {
                marked[root] = true;
                root = edgeTo[root];
            }

            // extract cycle
            cycle = new MyResizingArrayStack<>();
            int v = root;
            do {
                cycle.push(v);
                v = edgeTo[v];
            } while (v != root);
            cycle.push(root);
        }
    }

    private void dfs(MyDigraph graph, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (int w : graph.adj(v)) {
            if (cycle != null) // 如果已找到环，那么直接返回
                return;
            else if (!marked[w]) { // 访问到新顶点，递归访问
                edgeTo[w] = v;
                dfs(graph, w);
            } else if (onStack[w]) { // 找到环，追溯有向环
                cycle = new MyResizingArrayStack<>();
                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false; // 访问完 v 的邻接顶点，说明以 v 为起点，没有找到环，说明该顶点在环外，置为false
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
