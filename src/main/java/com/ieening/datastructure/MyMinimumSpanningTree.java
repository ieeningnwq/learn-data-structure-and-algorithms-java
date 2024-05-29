package com.ieening.datastructure;

import com.ieening.datastructure.MyEdgeWeightedGraph.Edge;

public class MyMinimumSpanningTree {
    public static enum AlgorithmType {
        Prim, LazyPrim, Kruskal, Boruvka
    }

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
    private Iterable<MyEdgeWeightedGraph.Edge> mst; // 最小生成树边

    public MyMinimumSpanningTree(MyEdgeWeightedGraph graph, AlgorithmType algorithmType) {
        if (AlgorithmType.LazyPrim == algorithmType) {
            MyQueue<MyEdgeWeightedGraph.Edge> mst = new MyLinkedListQueue<>();
            MyPriorityQueue<MyEdgeWeightedGraph.Edge> pq = new MyPriorityQueue<>(); // 横切边
            boolean[] marked = new boolean[graph.V()]; // marked[v]：顶点 v 在最小生成树中
            for (int v = 0; v < graph.V(); v++) {
                if (!marked[v])
                    lazyPrim(graph, v, mst, pq, marked);
            }
            this.mst = mst;
        } else if (AlgorithmType.Prim == algorithmType) {
            Edge[] edgeTo = new Edge[graph.V()]; // edgeTo[v]：由树中顶点到非树中顶点 v 权重最小边
            double[] distTo = new double[graph.V()]; // distTo[v]：由树中顶点到非树中顶点 v 权重最小边的权重
            boolean[] marked = new boolean[graph.V()]; // marked[v]：顶点 v 在最小生成树中
            MyPriorityQueue<VertexToMinimumSpanningTreeLeastWeight> pq = new MyPriorityQueue<>(); // 横切边
            for (int v = 0; v < graph.V(); v++)
                distTo[v] = Double.POSITIVE_INFINITY;
            for (int v = 0; v < graph.V(); v++) {
                if (!marked[v])
                    prim(graph, v, pq, edgeTo, distTo, marked);
            }
            MyQueue<MyEdgeWeightedGraph.Edge> mst = new MyLinkedListQueue<>();
            for (int v = 0; v < edgeTo.length; v++) {
                Edge edge = edgeTo[v];
                if (edge != null)
                    mst.enqueue(edge);
            }
            this.mst = mst;
            for (Edge edge : this.mst) {
                weight += edge.weight();
            }
        }

    }

    private void prim(MyEdgeWeightedGraph graph, int s, MyPriorityQueue<VertexToMinimumSpanningTreeLeastWeight> pq,
            Edge[] edgeTo,
            double[] distTo, boolean[] marked) {
        distTo[s] = 0;
        pq.enqueue(new VertexToMinimumSpanningTreeLeastWeight(s, distTo[s]));
        while (!pq.isEmpty()) {
            VertexToMinimumSpanningTreeLeastWeight vLeastWeight = pq.dequeue();
            scan(graph, vLeastWeight, pq, edgeTo, distTo, marked);
        }
    }

    private void scan(MyEdgeWeightedGraph graph, VertexToMinimumSpanningTreeLeastWeight vLeastWeight,
            MyPriorityQueue<VertexToMinimumSpanningTreeLeastWeight> pq, Edge[] edgeTo, double[] distTo,
            boolean[] marked) {
        marked[vLeastWeight.getVertex()] = true;
        for (Edge edge : graph.adj(vLeastWeight.getVertex())) {
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
    private void lazyPrim(MyEdgeWeightedGraph graph, int s, MyQueue<MyEdgeWeightedGraph.Edge> mst,
            MyPriorityQueue<MyEdgeWeightedGraph.Edge> pq, boolean[] marked) {
        scan(graph, s, pq, marked);
        while (!pq.isEmpty()) {
            Edge e = pq.dequeue(); // 拿到优先级队列中权重最小的边
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
     * 标记顶点 v，并将所有和 v 连接且另一端点未被标记的边加入优先级队列
     * 
     * @param graph 加权无向图
     * @param v     顶点
     */
    private void scan(MyEdgeWeightedGraph graph, int v, MyPriorityQueue<MyEdgeWeightedGraph.Edge> pq,
            boolean[] marked) {
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
