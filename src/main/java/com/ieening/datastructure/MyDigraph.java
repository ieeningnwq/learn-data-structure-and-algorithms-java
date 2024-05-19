package com.ieening.datastructure;

import java.io.IOException;
import java.util.Scanner;

import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;

public class MyDigraph {
    // MARK:Fields

    private static final String NEWLINE = System.lineSeparator();
    private final int V; // 有向图中顶点数量
    private int E;// 有向图中边的数量
    private MyList<Integer>[] adj; // 邻接表
    private int[] inDegree; // inDegree[v]：顶点 v 的入度

    // MARK:Constructor

    /**
     * 构建一个顶点个数为V的空有向图
     * 
     * @param V 顶点个数
     * @throws IllegalArgumentException V为负数
     */
    @SuppressWarnings("unchecked")
    public MyDigraph(int V) {
        if (V < 0)
            throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = V;
        this.E = 0;
        adj = (MyList<Integer>[]) new MyList[V];
        inDegree = new int[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new MyArrayList<>();
        }
    }

    /**
     * 从指定的输入流中读取一个图
     * 首行是顶点的个数
     * 第二行是边的数量
     * 接下来的每行表示一条具体的边，由两个结点表示，节点之间以空格相隔
     * 
     * @param in 输入流
     * @throws IllegalArgumentException 如果输入流 {@code in} 为 {@code null}
     * @throws IllegalArgumentException 如果边中结点编号超出范围
     * @throws IllegalArgumentException 如果边的数量或者结点的数量为负数
     * @throws IllegalArgumentException 如果输入流数据不符合数据格式规范
     */
    @SuppressWarnings("unchecked")
    public MyDigraph(Scanner in) {
        if (in == null)
            throw new IllegalArgumentException("input stream is null");
        try {
            V = in.nextInt();
            if (V < 0)
                throw new IllegalArgumentException(
                        "Number of vertices must be non-negative, " + V + " is less than zero");
            adj = (MyList<Integer>[]) new MyList[V];
            inDegree = new int[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new MyArrayList<>();
            }
            int E = in.nextInt();
            if (E < 0) {
                throw new IllegalArgumentException(
                        "Number of edges must be non-negative, " + E + " is less than zero");
            }
            for (int i = 0; i < E; i++) {
                int v = in.nextInt();
                int w = in.nextInt();
                addEdge(v, w);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }

    /**
     * 验证顶点是否合法，如果 {@code 0 <= v || v > V} 则非法
     * 
     * @param v 顶点 v
     * @throws IllegalArgumentException 如果 {@code 0 <= v || v > V}
     */
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    // MARK:Modify Operations

    /**
     * 向有向图中添加有向边
     * 
     * @param v 有向边起点
     * @param w 有向边终点
     * @throws IllegalArgumentException 如果 {@code 0 <= v || v >= V} 以及
     *                                  {@code 0 <= w || w >= V} 抛出异常
     */
    private void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        adj[v].add(w);
        inDegree[w]++;
        E++;
    }

    // MARK:Query Operations

    /**
     * 返回图顶点的数量
     *
     * @return 图顶点的数量
     */
    public int V() {
        return V;
    }

    /**
     * 返回图边的数量
     *
     * @return 图边的数量
     */
    public int E() {
        return E;
    }

    /**
     * 返回顶点 {@code v} 所有的邻接点
     *
     * @param v 顶点
     * @return 顶点 {@code v} 的所有邻接点
     * @throws IllegalArgumentException 不满足 {@code 0 <= v < V}
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * 返回顶点 {@code v} 的入度
     * 
     * @param v 顶点
     * @return 顶点 {@code v} 的入度
     * @throws IllegalArgumentException 不满足 {@code 0 <= v < V}
     */
    public int inDegree(int v) {
        validateVertex(v);
        return inDegree[v];
    }

    /**
     * 返回顶点 {@code v} 的出度
     * 
     * @param v 顶点
     * @return 顶点 {@code v} 的入度
     * @throws IllegalArgumentException 不满足 {@code 0 <= v < V}
     */
    public int outDegree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * 返回有向图的反向图
     * 
     * @return
     */
    public MyDigraph reverse() {
        MyDigraph reverse = new MyDigraph(V);
        for (int v = 0; v < V; v++) {
            for (Integer w : adj[v]) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    // MARK:Visible

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges" + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj[v]) {
                s.append(String.format("%d ", w));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public void draw(String filePath) {
        System.setProperty("org.graphstream.ui", "swing");
        MultiGraph graph = new MultiGraph(this.getClass().getSimpleName());
        graph.setAttribute("ui.stylesheet", "url(file:src/main/resources/assets/graph.css)");
        for (int v = 0; v < V; v++) {
            // 加入顶点
            graph.addNode(String.valueOf(v)).setAttribute("ui.label", String.valueOf(v));
        }
        for (int v = 0; v < V; v++) {
            // 加入边
            for (int w : adj[v]) {
                graph.addEdge(v + "-" + w, v, w, true);
            }
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
