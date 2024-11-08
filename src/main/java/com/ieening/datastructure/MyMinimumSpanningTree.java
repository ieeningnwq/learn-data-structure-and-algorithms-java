package com.ieening.datastructure;

import java.util.Arrays;

import com.ieening.algorithms.MyWeightedQuickUnionWithPathCompressionUnionFind;
import com.ieening.datastructure.MyEdgeWeightedGraph.MyEdge;

public class MyMinimumSpanningTree {
    public static enum AlgorithmType {
        Prim, LazyPrim, Kruskal, Boruvka
    }

    /**
     * 即时 Prim 算法中优先队列维护的横切边，其中有不在树中顶点以及该顶点到树的最小权重值
     */
    private static class VertexToMinimumSpanningTreeLeastWeight
            implements Comparable<VertexToMinimumSpanningTreeLeastWeight> {

        private final int vertex;
        private double weight;

        /**
         * @param vertex
         */
        public VertexToMinimumSpanningTreeLeastWeight(int vertex, double weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        /**
         * @return the vertex
         */
        public int getVertex() {
            return vertex;
        }

        @Override
        public int compareTo(VertexToMinimumSpanningTreeLeastWeight other) {
            return Double.compare(weight, other.weight);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            VertexToMinimumSpanningTreeLeastWeight other = (VertexToMinimumSpanningTreeLeastWeight) obj;
            if (vertex != other.vertex)
                return false;
            return true;
        }

    }

    private double weight; // 最小生成树总权重
    private Iterable<MyEdgeWeightedGraph.MyEdge> mst; // 最小生成树边

    public MyMinimumSpanningTree(MyEdgeWeightedGraph graph, AlgorithmType algorithmType) {
        if (AlgorithmType.LazyPrim == algorithmType) {
            MyQueue<MyEdgeWeightedGraph.MyEdge> mst = new MyLinkedListQueue<>();
            MyPriorityQueue<MyEdgeWeightedGraph.MyEdge> pq = new MyPriorityQueue<>(); // 横切边
            boolean[] marked = new boolean[graph.V()]; // marked[v]：顶点 v 在最小生成树中
            for (int v = 0; v < graph.V(); v++) {
                if (!marked[v])
                    lazyPrim(graph, v, mst, pq, marked);
            }
            this.mst = mst;
        } else if (AlgorithmType.Prim == algorithmType) {
            MyEdge[] edgeTo = new MyEdge[graph.V()]; // edgeTo[v]：由树中顶点到非树中顶点 v 权重最小边
            double[] distTo = new double[graph.V()]; // distTo[v]：由树中顶点到非树中顶点 v 权重最小边的权重
            boolean[] marked = new boolean[graph.V()]; // marked[v]：顶点 v 在最小生成树中
            MyPriorityQueue<VertexToMinimumSpanningTreeLeastWeight> pq = new MyPriorityQueue<>(); // 横切边
            for (int v = 0; v < graph.V(); v++)
                distTo[v] = Double.POSITIVE_INFINITY;
            for (int v = 0; v < graph.V(); v++) {
                if (!marked[v])
                    prim(graph, v, pq, edgeTo, distTo, marked);
            }
            MyQueue<MyEdgeWeightedGraph.MyEdge> mst = new MyLinkedListQueue<>();
            for (int v = 0; v < edgeTo.length; v++) {
                MyEdge edge = edgeTo[v];
                if (edge != null)
                    mst.enqueue(edge);
            }
            this.mst = mst;
            for (MyEdge edge : this.mst) {
                weight += edge.weight();
            }
        } else if (AlgorithmType.Kruskal == algorithmType) {
            this.mst = kruskal(graph);
        } else if (AlgorithmType.Boruvka == algorithmType) {
            this.mst = boruvka(graph);
        }
    }

    private MyQueue<MyEdge> kruskal(MyEdgeWeightedGraph graph) {
        MyEdge[] edges = new MyEdge[graph.E()]; // 按照权重顺序排序的加权无向边
        int t = 0;
        for (MyEdge edge : graph.edges())
            edges[t++] = edge;
        Arrays.sort(edges);

        MyQueue<MyEdge> mst = new MyLinkedListQueue<>();

        // 贪心算法
        MyWeightedQuickUnionWithPathCompressionUnionFind uf = new MyWeightedQuickUnionWithPathCompressionUnionFind(
                graph.V());
        for (int i = 0; i < graph.E() && mst.size() < graph.V() - 1; i++) {
            MyEdge edge = edges[i];
            int v = edge.either();
            int w = edge.other(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
                mst.enqueue(edge);
                weight += edge.weight();
            }
        }
        return mst;
    }

    private MyList<MyEdge> boruvka(MyEdgeWeightedGraph graph) {
        MyList<MyEdge> mst = new MyLinkedList<>();

        MyWeightedQuickUnionWithPathCompressionUnionFind uf = new MyWeightedQuickUnionWithPathCompressionUnionFind(
                graph.V());
        // 循环最多 lgV 次或者已找到 V-1 条边
        for (int t = 1; t < graph.V() && mst.size() < graph.V() - 1; t = t + t) {
            MyEdge[] closest = new MyEdge[graph.V()];
            for (MyEdge edge : graph.edges()) {
                int v = edge.either(), w = edge.other(v);
                int i = uf.find(v), j = uf.find(w);
                if (i == j)
                    continue;
                if (closest[i] == null || edge.compareTo(closest[i]) < 0)
                    closest[i] = edge;
                if (closest[j] == null || edge.compareTo(closest[j]) < 0)
                    closest[j] = edge;
            }

            for (int i = 0; i < graph.V(); i++) {
                MyEdge edge = closest[i];
                if (edge != null) {
                    int v = edge.either(), w = edge.other(v);
                    if (!uf.connected(v, w)) {
                        mst.add(edge);
                        weight += edge.weight();
                        uf.union(v, w);
                    }
                }
            }
        }
        return mst;
    }

    private void prim(MyEdgeWeightedGraph graph, int s, MyPriorityQueue<VertexToMinimumSpanningTreeLeastWeight> pq,
            MyEdge[] edgeTo,
            double[] distTo, boolean[] marked) {
        distTo[s] = 0;
        pq.enqueue(new VertexToMinimumSpanningTreeLeastWeight(s, distTo[s]));
        while (!pq.isEmpty()) {
            VertexToMinimumSpanningTreeLeastWeight vLeastWeight = pq.dequeue();
            scan(graph, vLeastWeight, pq, edgeTo, distTo, marked);
        }
    }

    /**
     * 即时 Prim 算法实现，标记顶点 v，并将和 v 连接且另一端点未被标记权重最小的顶点和权重值保存至优先队列中
     * 
     * @param graph
     * @param vLeastWeight
     * @param pq
     * @param edgeTo
     * @param distTo
     * @param marked
     */
    private void scan(MyEdgeWeightedGraph graph, VertexToMinimumSpanningTreeLeastWeight vLeastWeight,
            MyPriorityQueue<VertexToMinimumSpanningTreeLeastWeight> pq, MyEdge[] edgeTo, double[] distTo,
            boolean[] marked) {
        marked[vLeastWeight.getVertex()] = true;
        for (MyEdge edge : graph.adj(vLeastWeight.getVertex())) {
            int w = edge.other(vLeastWeight.getVertex());
            if (marked[w])
                continue;

            if (edge.weight() < distTo[w]) {
                distTo[w] = edge.weight();
                edgeTo[w] = edge;
                VertexToMinimumSpanningTreeLeastWeight wLeastWeight = new VertexToMinimumSpanningTreeLeastWeight(w,
                        distTo[w]);
                if (pq.contains(wLeastWeight))
                    pq.remove(wLeastWeight);
                pq.enqueue(wLeastWeight);
            }
        }
    }

    /**
     * 最小生成树延时实现
     * 
     * @param graph
     * @param s
     */
    private void lazyPrim(MyEdgeWeightedGraph graph, int s, MyQueue<MyEdgeWeightedGraph.MyEdge> mst,
            MyPriorityQueue<MyEdgeWeightedGraph.MyEdge> pq, boolean[] marked) {
        scan(graph, s, pq, marked);
        while (!pq.isEmpty()) {
            MyEdge e = pq.dequeue(); // 拿到优先级队列中权重最小的边
            int v = e.either(), w = e.other(v);// 边的两个顶点
            if (marked[v] && marked[w])
                continue; // 如果顶点都已加入，说明是失效边，跳过
            mst.enqueue(e); // 将边加入最小生成树中
            weight += e.weight();// 更新最小生成树权重
            if (!marked[v]) // 将顶点 v
                scan(graph, v, pq, marked);
            if (!marked[w]) // 或 w 加入最小生成树中
                scan(graph, w, pq, marked);
        }
    }

    /**
     * 延时 Prim 实现，标记顶点 v，并将所有和 v 连接且另一端点未被标记的边加入优先级队列
     * 
     * @param graph 加权无向图
     * @param v     顶点
     */
    private void scan(MyEdgeWeightedGraph graph, int v, MyPriorityQueue<MyEdgeWeightedGraph.MyEdge> pq,
            boolean[] marked) {
        assert !marked[v];
        marked[v] = true;
        for (MyEdgeWeightedGraph.MyEdge e : graph.adj(v)) {
            if (!marked[e.other(v)])
                pq.enqueue(e);
        }
    }

    public Iterable<MyEdgeWeightedGraph.MyEdge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }
}
