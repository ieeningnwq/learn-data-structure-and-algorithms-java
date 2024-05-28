package com.ieening.datastructure;

import java.io.IOException;
import java.util.Scanner;

import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;

public class MyEdgeWeightedGraph {
    // MARK:Fields
    private static final String NEWLINE = System.lineSeparator();

    public static class Edge implements Comparable<Edge> {
        private final int v;
        private final int w;
        private final double weight;

        /**
         * 构建无向权重边
         * 
         * @param v      边的一个顶点
         * @param w      边的另一个顶点
         * @param weight 边的权重
         */
        public Edge(int v, int w, double weight) {
            if (v < 0 || w < 0)
                throw new IllegalArgumentException("vertex index must be a non-negative number");
            if (Double.isNaN(weight))
                throw new IllegalArgumentException("edge weight is NaN");

            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        /**
         * 返回边的权重
         * 
         * @return 边的权重值
         */
        public double weight() {
            return weight;
        }

        /**
         * 返回边的其中一个顶点
         * 
         * @return 边的顶点
         */
        public int either() {
            return v;
        }

        /**
         * 返回与顶点{@code vertex}对应的另一顶点
         * 
         * @param vertex 边的其中一个顶点
         * @return 边的另一个顶点
         * @throws IllegalArgumentException {@code vertex}不是边的顶点
         */
        public int other(int vertex) {
            if (vertex == v)
                return w;
            else if (vertex == w)
                return v;
            else
                throw new IllegalArgumentException("illegal endpoint");
        }

        @Override
        public String toString() {
            return String.format("%d-%d %.5f", v, w, weight);
        }

        @Override
        public int compareTo(Edge that) {
            return Double.compare(weight, that.weight);
        }
    }

    private final int V; // 无向权重图的顶点数量
    private int E; // 图边的数量
    private final MyList<Edge>[] adj; // 邻接表

    // MARK:Constructors

    /**
     * 根据顶点个数{@code V}构建空无向权重图
     * 
     * @param V 顶点个数
     * @throws IllegalArgumentException 如果{@code V}为负数
     */
    @SuppressWarnings("unchecked")
    public MyEdgeWeightedGraph(int V) {
        if (V < 0)
            throw new IllegalArgumentException("number of vertexes must be non-negative");
        this.V = V;
        this.E = 0;
        adj = (MyList<Edge>[]) new MyList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new MyArrayList<>();

    }

    /**
     * 由输入流创建图
     * 
     * @param in 输入流
     * @throws IllegalArgumentException 如果输入流 {@code in} 为 {@code null}
     * @throws IllegalArgumentException 如果边中结点编号超出范围
     * @throws IllegalArgumentException 如果边的数量或者结点的数量为负数
     * @throws IllegalArgumentException 如果输入流数据不符合数据格式规范
     */
    @SuppressWarnings("unchecked")
    public MyEdgeWeightedGraph(Scanner in) {
        if (in == null)
            throw new IllegalArgumentException("input stream is null");
        try {
            V = in.nextInt();
            if (V < 0)
                throw new IllegalArgumentException(
                        "number of vertices must be non-negative, " + V + " is less than zero");
            adj = (MyList<Edge>[]) new MyList[V];
            for (int v = 0; v < V; v++)
                adj[v] = new MyArrayList<>();
            int E = in.nextInt();
            if (E < 0)
                throw new IllegalArgumentException("number of edges must be non-negative, " + E + " is less than zero");
            for (int e = 0; e < E; e++) {
                int v = in.nextInt();
                validateVertex(v);
                int w = in.nextInt();
                validateVertex(w);
                double weight = in.nextDouble();
                addEdge(new Edge(v, w, weight));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "invalid input format in " + this.getClass().getSimpleName() + " constructor", e);
        }
    }

    private void addEdge(Edge edge) {
        int v = edge.either();
        int w = edge.other(v);
        validateVertex(v);
        validateVertex(w);
        adj[v].add(edge);
        adj[w].add(edge);
        E++;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public Iterable<Edge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * 获取图的所有边
     * 
     * @return 图所有边
     */
    public Iterable<Edge> edges() {
        MyList<Edge> edgeList = new MyArrayList<>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge edge : adj[v]) {
                if (edge.other(v) > v) {
                    edgeList.add(edge);
                } else if (edge.other(v) == v) {
                    // 对于自环，只加一次
                    if (selfLoops == 0) {
                        edgeList.add(edge);
                        selfLoops++;
                    }
                }
            }
        }
        return edgeList;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge edge : adj[v]) 
                s.append(edge + " ; ");
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public void draw(String filePath) {
        System.setProperty("org.graphstream.ui", "swing");
        MultiGraph graph = new MultiGraph(this.getClass().getSimpleName());
        graph.setAttribute("ui.stylesheet", "url(file:src/main/resources/assets/graph.css)");
        // 加入顶点
        for (int v = 0; v < V; v++) {
            graph.addNode(String.valueOf(v)).setAttribute("ui.label", String.valueOf(v));
        }
        // 加入边
        for (Edge edge : edges()) {
            int v = edge.either();
            int w = edge.other(v);
            graph.addEdge(edge.toString(), v, w).setAttribute("ui.label", String.valueOf(edge.weight()));
        }
        try {
            FileSinkImages fileSinkImages = FileSinkImages.createDefault();
            fileSinkImages.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
            fileSinkImages.setAutofit(true);
            graph.write(fileSinkImages, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
