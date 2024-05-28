package com.ieening.datastructure;

import com.ieening.datastructure.MyEdgeWeightedGraph.Edge;

public class MyMinimumSpanningTree {
    public static enum AlgorithmType {
        Prim, LazyPrim, Kruskal, Boruvka
    }

    private double weight; // 最小生成树总权重
    private MyQueue<MyEdgeWeightedGraph.Edge> mst; // 最小生成树边
    private boolean[] marked; // marked[v]：顶点 v 在最小生成树中
    private MyPriorityQueue<MyEdgeWeightedGraph.Edge> pq; // 横切边

    public MyMinimumSpanningTree(MyEdgeWeightedGraph graph, AlgorithmType algorithmType) {
        mst = new MyLinkedListQueue<>();
        pq = new MyPriorityQueue<>();
        marked = new boolean[graph.V()];

        for (int v = 0; v < graph.V(); v++) {
            if (!marked[v])
                lazyPrim(graph, v);
        }
    }

    /**
     * 最小生成树延时实现
     * 
     * @param graph
     * @param s
     */
    private void lazyPrim(MyEdgeWeightedGraph graph, int s) {
        scan(graph, s);
        while (!pq.isEmpty()) {
            Edge e = pq.dequeue(); // 拿到优先级队列中权重最小的边
            int v = e.either(), w = e.other(v);// 边的两个顶点
            if (marked[v] && marked[w])
                continue; // 如果顶点都已加入，说明是失效边，跳过
            mst.enqueue(e); // 将边加入最小生成树中
            weight += e.weight();// 更新最小生成树权重
            if (!marked[v]) // 将顶点 v
                scan(graph, v);
            if (!marked[w]) // 或 w 加入最小生成树中
                scan(graph, w);
        }
    }

    /**
     * 标记顶点 v，并将所有和 v 连接且另一端点未被标记的边加入优先级队列
     * 
     * @param graph 加权无向图
     * @param v     顶点
     */
    private void scan(MyEdgeWeightedGraph graph, int v) {
        assert !marked[v];
        marked[v] = true;
        for (MyEdgeWeightedGraph.Edge e : graph.adj(v)) {
            if (!marked[e.other(v)])
                pq.enqueue(e);
        }
    }

    public Iterable<MyEdgeWeightedGraph.Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }
}
