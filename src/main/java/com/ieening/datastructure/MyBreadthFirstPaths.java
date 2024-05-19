package com.ieening.datastructure;

public class MyBreadthFirstPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked; // marked[v]：s-v 路径是否存在
    private int[] edgeTo; // edgeTo[v]：最短路径 s-v，前一条边
    private int[] distTo; // distTo[v]：s-v 最短路径边的数量

    public MyBreadthFirstPaths(MyUndirectedGraph graph, int source) {
        marked = new boolean[graph.V()];
        distTo = new int[graph.V()];
        edgeTo = new int[graph.V()];
        validateVertex(source);
        bfs(graph, source);

    }

    public MyBreadthFirstPaths(MyUndirectedGraph graph, Iterable<Integer> sources) {
        marked = new boolean[graph.V()];
        distTo = new int[graph.V()];
        edgeTo = new int[graph.V()];
        validateVertices(sources);
        bfs(graph, sources);

    }

    public MyBreadthFirstPaths(MyDigraph graph, int source) {
        marked = new boolean[graph.V()];
        distTo = new int[graph.V()];
        edgeTo = new int[graph.V()];
        validateVertex(source);
        bfs(graph, source);

    }

    private void bfs(MyDigraph graph, int source) {
        MyQueue<Integer> queue = new MyResizingArrayQueue<>();
        for (int v = 0; v < graph.V(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[source] = 0;
        marked[source] = true;

        queue.enqueue(source);

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (Integer w : graph.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    queue.enqueue(w);
                }
            }
        }
    }

    public MyBreadthFirstPaths(MyDigraph graph, Iterable<Integer> sources) {
        marked = new boolean[graph.V()];
        distTo = new int[graph.V()];
        edgeTo = new int[graph.V()];
        validateVertices(sources);
        bfs(graph, sources);

    }

    private void bfs(MyDigraph graph, Iterable<Integer> sources) {
        MyQueue<Integer> queue = new MyResizingArrayQueue<>();
        for (int v = 0; v < graph.V(); v++) {
            distTo[v] = INFINITY;
        }
        for (Integer s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            queue.enqueue(s);
        }
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
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
        for (int v = 0; v < graph.V(); v++) {
            distTo[v] = INFINITY;
        }
        for (Integer s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            queue.enqueue(s);
        }
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    queue.enqueue(w);
                }
            }
        }
    }

    private void bfs(MyUndirectedGraph graph, int source) {
        MyQueue<Integer> queue = new MyResizingArrayQueue<>();
        for (int v = 0; v < graph.V(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[source] = 0;
        marked[source] = true;

        queue.enqueue(source);

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (Integer w : graph.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    queue.enqueue(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v))
            return null;
        MyStack<Integer> path = new MyResizingArrayStack<>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x])
            path.push(x);

        path.push(x);
        return path;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + "is not between 0 and " + (V - 1));
    }
}
