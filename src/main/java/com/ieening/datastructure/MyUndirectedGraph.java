package com.ieening.datastructure;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;

public class MyUndirectedGraph {
    private static final String NEWLINE = System.lineSeparator();
    private final int V; // 顶点数目
    private int E; // 边数目
    private MyList<Integer>[] adj; // 邻接表

    /**
     * 初始化一个顶点个数为 {@code V} 边个数为 0 的空图
     *
     * @param V 顶点的个数
     * @throws IllegalArgumentException 如果顶点个数 {@code V < 0}
     */
    @SuppressWarnings("unchecked")
    public MyUndirectedGraph(int V) {
        if (V < 0)
            throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = V;
        this.E = 0;
        adj = (MyList<Integer>[]) new MyList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new MyArrayList<Integer>();
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
    public MyUndirectedGraph(Scanner in) {
        if (in == null)
            throw new IllegalArgumentException("argument is null");
        try {
            this.V = in.nextInt();
            if (V < 0)
                throw new IllegalArgumentException("number of vertices in a Graph must be non-negative");
            adj = (MyList<Integer>[]) new MyList[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new MyArrayList<Integer>();
            }
            int E = in.nextInt();
            if (E < 0)
                throw new IllegalArgumentException("number of edges in a Graph must be non-negative");
            for (int i = 0; i < E; i++) {
                int v = in.nextInt();
                int w = in.nextInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }

    /**
     * 深度拷贝图
     *
     * @param G 待拷贝的图
     * @throws IllegalArgumentException 如果 {@code G} 是 {@code null}
     */
    @SuppressWarnings("unchecked")
    public MyUndirectedGraph(MyUndirectedGraph G) {
        this.V = G.V();
        this.E = G.E();
        if (V < 0)
            throw new IllegalArgumentException("Number of vertices must be non-negative");

        // update adjacency lists
        adj = (MyList<Integer>[]) new MyList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new MyArrayList<Integer>();
        }

        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            MyStack<Integer> reverse = new MyResizingArrayStack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

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

    // 除非 {@code 0 <= v < V}，否则抛出 IllegalArgumentException 异常
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    /**
     * 向图中添加无向边
     *
     * @param v 边中一个顶点
     * @param w 边中另一个顶点
     * @throws IllegalArgumentException 如果 {@code 0 <= v < V} 以及
     *                                  {@code 0 <= w < V}，否则抛出异常
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(w);
        adj[w].add(v);
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
     * 返回顶点 {@code v} 的度
     *
     * @param v 顶点
     * @return 顶点 {@code v} 的度
     * @throws IllegalArgumentException 不满足 {@code 0 <= v < V}
     */
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    // MARK:Visible

    public void draw(String filePath) {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph(this.getClass().getSimpleName());
        graph.setAttribute("ui.stylesheet", "url(file:src/main/resources/asserts/graph.css)");
        graph.setStrict(false);

        // 加入结点
        for (int v = 0; v < V; v++) {
            graph.addNode(String.valueOf(v)).setAttribute("ui.label", String.valueOf(v));
            ;
        }
        // 加入边
        for (int v = 0; v < V; v++) {
            for (int w : adj[v]) {
                graph.addEdge(v + "-" + w, v, w);
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
