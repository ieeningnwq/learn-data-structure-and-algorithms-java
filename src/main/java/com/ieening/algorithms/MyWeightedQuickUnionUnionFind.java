package com.ieening.algorithms;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class MyWeightedQuickUnionUnionFind implements MyUnionFind {
    private int[] parent; // parent[v]：顶点 v 的父顶点
    private int[] size; // size[v]：以 v 为根顶点子树顶点的数量
    private int count; // 连通分量数量

    public MyWeightedQuickUnionUnionFind(int V) {
        if (V < 0)
            throw new IllegalArgumentException("number of vertices in a Graph must be non-negative");
        count = V;
        parent = new int[V];
        size = new int[V];
        for (int v = 0; v < V; v++) {
            parent[v] = v;
            size[v] = 1;
        }
    }

    public MyWeightedQuickUnionUnionFind(Scanner in) {
        if (in == null)
            throw new IllegalArgumentException("argument is null");
        try {
            int V = in.nextInt();
            if (V < 0)
                throw new IllegalArgumentException("number of vertices in a Graph must be non-negative");
            count = V;
            parent = new int[V];
            size = new int[V];
            for (int v = 0; v < V; v++) {
                parent[v] = v;
                size[v] = 1;
            }
            while (in.hasNextInt()) {
                int p = in.nextInt();
                int q = in.nextInt();
                union(p, q);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }

    @Override
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ)
            return;
        else {
            if (size[rootP] < size[rootQ]) {
                parent[rootP] = rootQ;
                size[rootQ] += size[rootP];
            } else {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
            }
            count--;
        }
    }

    @Override
    public int find(int p) {
        validateVertex(p);
        while (p != parent[p])
            p = parent[p];
        return p;
    }

    private void validateVertex(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public int count() {
        return count;
    }

}
