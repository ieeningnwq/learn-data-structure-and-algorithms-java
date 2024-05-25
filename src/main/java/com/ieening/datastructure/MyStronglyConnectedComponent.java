package com.ieening.datastructure;

import java.util.Arrays;

public class MyStronglyConnectedComponent {
    public static enum AlgorithmType {
        Kosaraju, Tarjan, Gabow
    }

    private final boolean[] marked; // marked[v] = 顶点 v 是否已被访问
    private final int[] id; // id[v] = 顶点 v 所在强连通分量 id
    private int count; // 强连通分量数量

    public MyStronglyConnectedComponent(MyDigraph graph, AlgorithmType algorithmType) {
        marked = new boolean[graph.V()];
        id = new int[graph.V()];
        if (AlgorithmType.Kosaraju == algorithmType) {
            kosarajuSCC(graph);
        } else if (AlgorithmType.Tarjan == algorithmType) {
            tarjanSCC(graph);
        } else if (AlgorithmType.Gabow == algorithmType) {
            gabowSCC(graph);
        }
    }

    private void gabowSCC(MyDigraph graph) {
        int[] preOrder = new int[graph.V()];
        int[] pre = new int[] { 0 };// 记录当前访问顺序
        MyStack<Integer> path = new MyLinkedListStack<>();
        MyStack<Integer> root = new MyLinkedListStack<>();

        for (int v = 0; v < graph.V(); v++) {
            id[v] = -1;
        }

        for (int v = 0; v < graph.V(); v++) {
            if (!marked[v])
                dfsGabow(graph, v, preOrder, pre, path, root);
        }
    }

    private void dfsGabow(MyDigraph graph, int v, int[] preOrder, int[] pre, MyStack<Integer> path,
            MyStack<Integer> root) {
        marked[v] = true;
        preOrder[v] = pre[0];
        pre[0] += 1;
        path.push(v);
        root.push(v);

        for (Integer w : graph.adj(v)) {
            if (!marked[w]) {
                dfsGabow(graph, w, preOrder, pre, path, root);
            } else if (id[w] == -1) {
                while (preOrder[root.peek()] > preOrder[w]) {
                    root.pop();
                }
            }
        }
        // 找到包含顶点 v 的强连通分量
        if (root.peek() == v) {
            root.pop();
            int w;
            do {
                w = path.pop();
                id[w] = count;
            } while (w != v);
            count++;
        }
    }

    private void tarjanSCC(MyDigraph graph) {
        int[] low = new int[graph.V()]; // low[v]：v 或 v 的子树能够追溯到的最早的栈中节点的次序号。
        int[] dfn = new int[graph.V()]; // 时间戳
        boolean[] onStack = new boolean[graph.V()]; // onStack[v]：结点 v 在栈中
        MyStack<Integer> stack = new MyLinkedListStack<>();
        int[] timeStamp = new int[] { 0 };
        for (int v = 0; v < graph.V(); v++) {
            if (!marked[v]) {
                dfsTarjan(graph, v, dfn, low, timeStamp, onStack, stack);
            }
        }
    }

    private void dfsTarjan(MyDigraph graph, int v, int[] dfn, int[] low, int[] timeStamp, boolean[] onStack,
            MyStack<Integer> stack) {
        marked[v] = true;
        dfn[v] = timeStamp[0];
        low[v] = timeStamp[0];
        timeStamp[0] += 1;
        stack.push(v); // 进栈
        onStack[v] = true;

        for (Integer w : graph.adj(v)) {
            if (!marked[w]) {// 由结点 v 通过树边访问到未访问的结点 w
                dfsTarjan(graph, w, dfn, low, timeStamp, onStack, stack);
                low[v] = Math.min(low[v], low[w]);
            } else if (onStack[w]) // 横叉边或者反祖边情况
                low[v] = Math.min(low[v], dfn[w]);
        }
        if (dfn[v] == low[v]) { // 强连通分量的根
            int w;
            do {
                w = stack.pop();
                onStack[w] = false; // 结点出栈
                id[w] = count;
            } while (w != v);
            count++;
        }
    }

    private void kosarajuSCC(MyDigraph graph) {
        // 反向图的逆后序
        Iterable<Integer> reverserPost = reverserPostOrder(graph.reverse());
        // 重新初始化 marked，为下一次深度优先遍历做准备
        Arrays.fill(marked, false);
        // 以反向图逆后序深度优先遍历有向图
        for (Integer v : reverserPost) {
            if (!marked[v]) {
                dfsKosaraju(graph, v);
                count++;
            }
        }
    }

    private void dfsKosaraju(MyDigraph graph, Integer v) {
        marked[v] = true;
        id[v] = count;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                dfsKosaraju(graph, w);
            }
        }
    }

    private Iterable<Integer> reverserPostOrder(MyDigraph graph) {
        MyStack<Integer> order = new MyLinkedListStack<>();

        for (int v = 0; v < graph.V(); v++) {
            if (!marked[v]) {
                dfsReversePostOrder(graph, v, order);
            }
        }
        return order;
    }

    private void dfsReversePostOrder(MyDigraph graph, int v, MyStack<Integer> order) {
        marked[v] = true;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                dfsReversePostOrder(graph, w, order);
            }
        }
        order.push(v);
    }

    public boolean stronglyConnected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id[v] == id[w];
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public int count() {
        return count;
    }

    public int id(int v) {
        validateVertex(v);
        return id[v];
    }
}
