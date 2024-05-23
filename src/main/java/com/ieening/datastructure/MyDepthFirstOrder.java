package com.ieening.datastructure;

public class MyDepthFirstOrder {
    private boolean[] marked; // marked[v]：在搜索的过程中，顶点 v 是否已经被标记
    private int[] pre; // pre[v]：顶点 v 在前序排序中编号
    private int[] post; // post[v]：顶点 v 在后续排序中编号
    private MyQueue<Integer> preOrder; // 前序顶点
    private MyQueue<Integer> postOrder; // 后序顶点
    private int preCounter; // 前序顶点计数器
    private int postCounter; // 后续顶点计数器

    public MyDepthFirstOrder(MyDigraph graph) {
        pre = new int[graph.V()];
        post = new int[graph.V()];
        preOrder = new MyLinkedListQueue<>();
        postOrder = new MyLinkedListQueue<>();
        marked = new boolean[graph.V()];
        // 深度优先搜索
        for (int v = 0; v < graph.V(); v++) {
            if (!marked[v])
                dfs(graph, v);
        }
    }

    private void dfs(MyDigraph graph, int v) {
        marked[v] = true;
        pre[v] = preCounter++;
        preOrder.enqueue(v); // 前序
        for (int w : graph.adj(v)) {
            if (!marked[w])
                dfs(graph, w);
        }
        postOrder.enqueue(v); // 后序
        post[v] = postCounter++;
    }

    /**
     * 返回顶点 v 前序序号
     * 
     * @param v 顶点
     * @return 前序序号
     * @throws IllegalArgumentException 如果{@code 0<=v<V}
     */
    public int pre(int v) {
        validateVertex(v);
        return pre[v];
    }

    /**
     * 返回顶点 v 后序序号
     * 
     * @param v 顶点
     * @return 后序序号
     * @throws IllegalArgumentException 如果{@code 0<=v<V}
     */
    public int post(int v) {
        validateVertex(v);
        return post[v];
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + "is not between 0 and " + (V - 1));
    }

    /**
     * 深度搜索前序顶点
     * 
     * @return 深度搜索前序顶点
     */
    public Iterable<Integer> pre() {
        return preOrder;
    }

    /**
     * 深度搜索后续顶点
     * 
     * @return 深度搜索后续顶点
     */
    public Iterable<Integer> post() {
        return postOrder;
    }

    /**
     * 逆后序顶点
     * 
     * @return 返回逆后序顶点
     */
    public Iterable<Integer> reversePost() {
        MyStack<Integer> reverse = new MyLinkedListStack<>();
        for (Integer v : postOrder) {
            reverse.push(v);
        }
        return reverse;
    }
}
