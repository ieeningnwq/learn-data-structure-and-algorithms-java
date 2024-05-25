package com.ieening.datastructure;

public class MyTopological {
    private Iterable<Integer> order; // 拓扑顺序
    private int[] ranks; // rank[v]：拓扑排序中顶点 v 排名

    public MyTopological(MyDigraph graph, boolean isRecursive) {
        ranks = new int[graph.V()];
        if (isRecursive)
            topologicalRecursive(graph);
        else
            topologicalNonRecursive(graph);
    }

    private void topologicalNonRecursive(MyDigraph graph) {
        int[] inDegree = new int[graph.V()]; // 剩余顶点的入度
        for (int v = 0; v < graph.V(); v++) {
            inDegree[v] = graph.inDegree(v);
        }
        // 初始化操作
        MyQueue<Integer> order = new MyLinkedListQueue<>();
        int count = 0;

        // 所有度为零的顶点都入队列
        MyQueue<Integer> queue = new MyLinkedListQueue<>();
        for (int v = 0; v < graph.V(); v++) {
            if (inDegree[v] == 0)
                queue.enqueue(v);
        }

        while (!queue.isEmpty()) {
            Integer v = queue.dequeue();
            order.enqueue(v);
            ranks[v] = count++;
            for (Integer w : graph.adj(v)) {
                inDegree[w]--;
                if (inDegree[w] == 0) {
                    queue.enqueue(w);
                }
            }
        }
        // 存在有向环
        if (count != graph.V()) {
            order = null;
        }
        this.order = order;
    }

    private void topologicalRecursive(MyDigraph graph) {
        if (!isDirectedAcyclicGraph(graph)) {
            int[] count = new int[] { graph.V() - 1 };
            MyStack<Integer> order = new MyLinkedListStack<>();
            boolean[] marked = new boolean[graph.V()];

            for (int v = 0; v < graph.V(); v++) {
                if (!marked[v]) {
                    dfs(graph, v, marked, order, count);
                }
            }
            this.order = order;
        }

    }

    private void dfs(MyDigraph graph, int v, boolean[] marked, MyStack<Integer> order, int[] count) {
        marked[v] = true;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                dfs(graph, w, marked, order, count);
            }
        }
        order.push(v);
        ranks[v] = count[0];
        count[0] -= 1;
    }

    private boolean isDirectedAcyclicGraph(MyDigraph graph) {
        boolean[] marked = new boolean[graph.V()];
        boolean[] onStack = new boolean[graph.V()];
        boolean[] cycle = new boolean[] { false };
        for (int v = 0; v < graph.V(); v++) {
            if (!marked[v] && !cycle[0]) {
                dfs(graph, v, marked, onStack, cycle);
            }
        }
        return cycle[0];
    }

    private void dfs(MyDigraph graph, int v, boolean[] marked, boolean[] onStack, boolean[] cycle) {
        marked[v] = true;
        onStack[v] = true;
        for (int w : graph.adj(v)) {
            if (cycle[0]) // 已经有环，退出
                return;
            else if (!marked[w]) {
                dfs(graph, w, marked, onStack, cycle);
            } else if (onStack[w]) {// 找到环
                cycle[0] = true;
            }
        }
        onStack[v] = false;
    }

    public boolean hasOrder() {
        return order != null;
    }

    public Iterable<Integer> order() {
        return order;
    }

    public int rank(int v) {
        validateVertex(v);
        return hasOrder() ? ranks[v] : -1;
    }

    private void validateVertex(int v) {
        int V = ranks.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

}
