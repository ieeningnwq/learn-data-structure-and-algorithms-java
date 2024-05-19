package com.ieening.datastructure;

public class MyBreadthFirstSearch {
    private boolean[] marked; // marked[v]：s-v 路径是否存在
    private int count; // 和 s 相连顶点个数，包含自身
    private MyList<Integer> traversalVertexes; // 遍历的顶点

    /**
     * 单起点无向图广度优先搜索
     * 
     * @param graph  无向图
     * @param source 起点
     */
    public MyBreadthFirstSearch(MyUndirectedGraph graph, int source) {
        marked = new boolean[graph.V()];
        validateVertex(source);
        traversalVertexes = new MyArrayList<>();
        bfs(graph, source);
    }

    /**
     * 多起点无向图广度优先搜索
     * 
     * @param graph   无向图
     * @param sources 起点
     */
    public MyBreadthFirstSearch(MyUndirectedGraph graph, Iterable<Integer> sources) {
        marked = new boolean[graph.V()];
        validateVertices(sources);
        traversalVertexes = new MyArrayList<>();
        bfs(graph, sources);
    }

    /**
     * 单起点有向图广度优先搜索
     * 
     * @param graph  无向图
     * @param source 起点
     */
    public MyBreadthFirstSearch(MyDigraph graph, int source) {
        marked = new boolean[graph.V()];
        validateVertex(source);
        traversalVertexes = new MyArrayList<>();
        bfs(graph, source);
    }

    private void bfs(MyDigraph graph, int source) {
        MyQueue<Integer> queue = new MyResizingArrayQueue<>();
        marked[source] = true;
        count++;
        traversalVertexes.add(source);
        queue.enqueue(source);

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (Integer w : graph.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    count++;
                    traversalVertexes.add(w);
                    queue.enqueue(w);
                }
            }
        }
    }

    /**
     * 多起点有向图广度优先搜索
     * 
     * @param graph   无向图
     * @param sources 起点
     */
    public MyBreadthFirstSearch(MyDigraph graph, Iterable<Integer> sources) {
        marked = new boolean[graph.V()];
        validateVertices(sources);
        traversalVertexes = new MyArrayList<>();
        bfs(graph, sources);
    }

    private void bfs(MyDigraph graph, Iterable<Integer> sources) {
        MyQueue<Integer> queue = new MyResizingArrayQueue<>();

        for (Integer s : sources) {
            marked[s] = true;
            count++;
            traversalVertexes.add(s);
            queue.enqueue(s);
        }
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    count++;
                    traversalVertexes.add(w);
                    queue.enqueue(w);
                }
            }
        }
    }

    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null)
            throw new IllegalArgumentException("argument is null");
        int vertexCount = 0;
        for (Integer vertex : vertices) {
            vertexCount++;
            if (vertex == null) {
                throw new IllegalArgumentException("vertex is null");
            }
            validateVertex(vertex);
        }
        if (vertexCount == 0) {
            throw new IllegalArgumentException("zero vertices");
        }
    }

    private void bfs(MyUndirectedGraph graph, Iterable<Integer> sources) {
        MyQueue<Integer> queue = new MyResizingArrayQueue<>();

        for (Integer s : sources) {
            marked[s] = true;
            count++;
            traversalVertexes.add(s);
            queue.enqueue(s);
        }
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    count++;
                    traversalVertexes.add(w);
                    queue.enqueue(w);
                }
            }
        }
    }

    private void bfs(MyUndirectedGraph graph, int source) {
        MyQueue<Integer> queue = new MyResizingArrayQueue<>();
        marked[source] = true;
        count++;
        traversalVertexes.add(source);
        queue.enqueue(source);

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (Integer w : graph.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    count++;
                    traversalVertexes.add(w);
                    queue.enqueue(w);
                }
            }
        }
    }

    /**
     * 图中，s 和 v 是否连通
     * 
     * @param v 待检查的顶点
     * @return s 和 v 连通，返回 true
     */
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * 图中与 s，连通的顶点数量
     * 
     * @return 连通的顶点数量
     */
    public int count() {
        return count;
    }

    public Object[] traversalVertexes() {
        return traversalVertexes.toArray();
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + "is not between 0 and " + (V - 1));
    }
}
