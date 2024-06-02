package com.ieening.datastructure;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;

public class MyEdgeWeightedDigraph {
    // MARK:Fields

    private static final String NEWLINE = System.lineSeparator();

    public static class MyDirectedEdge {
        private final int v;
        private final int w;
        private final double weight;

        /**
         * 构建加权有向边
         * 
         * @param v      边出发顶点
         * @param w      边目标顶点
         * @param weight 边权重
         * @throws IllegalArgumentException v<0
         * @throws IllegalArgumentException w<0
         * @throws IllegalArgumentException weight 不是数字
         */
        public MyDirectedEdge(int v, int w, double weight) {
            if (v < 0)
                throw new IllegalArgumentException("Vertex names must be non-negative integers");
            if (w < 0)
                throw new IllegalArgumentException("Vertex names must be non-negative integers");
            if (Double.isNaN(weight))
                throw new IllegalArgumentException("Weight is NaN");

            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        public double weight() {
            return weight;
        }

        public int from() {
            return v;
        }

        public int to() {
            return w;
        }

        public String toString() {
            return v + "->" + w + " " + String.format("%5.2f", weight);
        }
    }

    private final int V; // 有向图中顶点的数量
    private int E; // 有向图中边的数量
    private final MyList<MyDirectedEdge>[] adj; // adj[v]：顶点 v 的邻接链表
    private final int[] inDegree; // inDegree[v]：顶点 v 的入度

    // MARK: Constructors

    /**
     * 构建顶点个数为 V 的空图
     * 
     * @param V 顶点个数
     * @throws IllegalArgumentException V<0
     */
    @SuppressWarnings("unchecked")
    public MyEdgeWeightedDigraph(int V) {
        if (V < 0)
            throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");
        this.V = V;
        this.E = 0;
        this.inDegree = new int[V];
        adj = (MyList<MyDirectedEdge>[]) new MyList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new MyLinkedList<>();
    }

    /**
     * 根据输入流，建立一幅加权有向图
     * 
     * @param in 输入流
     * @throws IllegalArgumentException 如果输入流为 {@code null}
     * @throws IllegalArgumentException 如果顶点或者边的数量为负
     * @throws IllegalArgumentException 如果边的顶点不在规定范围
     * @throws IllegalArgumentException 如果输入流没有按照规范
     */
    @SuppressWarnings("unchecked")
    public MyEdgeWeightedDigraph(Scanner in) {
        if (in == null)
            throw new IllegalArgumentException("input stream argument is null");
        try {
            V = in.nextInt();
            if (V < 0)
                throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");
            inDegree = new int[V];
            adj = (MyList<MyDirectedEdge>[]) new MyList[V];
            for (int v = 0; v < V; v++)
                adj[v] = new MyLinkedList<>();
            int E = in.nextInt();
            if (E < 0)
                throw new IllegalArgumentException("Number of edges must be non-negative");
            for (int i = 0; i < E; i++) {
                int v = in.nextInt();
                int w = in.nextInt();
                validateVertex(v);
                validateVertex(w);
                double weight = in.nextDouble();
                addEdge(new MyDirectedEdge(v, w, weight));
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in MyEdgeWeightedDigraph constructor", e);
        }
    }

    /**
     * 深度拷贝一幅加权有向图
     * 
     * @param graph 待复制图
     */
    public MyEdgeWeightedDigraph(MyEdgeWeightedDigraph graph) {
        this(graph.V());
        E = graph.E();
        for (int v = 0; v < V; v++)
            inDegree[v] = graph.inDegree(v);
        for (int v = 0; v < V; v++) {
            // 保证和 graph 中边的顺序一致
            MyStack<MyDirectedEdge> reverse = new MyLinkedListStack<>();
            for (MyDirectedEdge edge : graph.adj[v])
                reverse.push(edge);
            for (MyDirectedEdge edge : reverse)
                adj[v].add(edge);
        }
    }

    public int inDegree(int v) {
        validateVertex(v);
        return inDegree[v];
    }

    public int outDegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public int E() {
        return E;
    }

    public int V() {
        return V;
    }

    public void addEdge(MyDirectedEdge edge) {
        int v = edge.from();
        int w = edge.to();
        validateVertex(v);
        validateVertex(w);
        adj[v].add(edge);
        inDegree[w]++;
        E++;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public Iterable<MyDirectedEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public Iterable<MyDirectedEdge> edges() {
        MyList<MyDirectedEdge> list = new MyLinkedList<>();
        for (int v = 0; v < V; v++) {
            for (MyDirectedEdge edge : adj[v])
                list.add(edge);
        }
        return list;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (MyDirectedEdge edge : adj[v])
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
        for (MyDirectedEdge edge : edges()) {
            int v = edge.from();
            int w = edge.to();
            graph.addEdge(edge.toString(), v, w, true).setAttribute("ui.label", String.valueOf(edge.weight()));
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
