package com.ieening.algorithms;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class MyQuickFindUnionFind implements MyUnionFind {
    private int[] id; // id[v]：顶点 v 连通分量的标识
    private int count; // 连通分量的数量

    public MyQuickFindUnionFind(int V) {
        if (V < 0)
            throw new IllegalArgumentException("number of vertices in a Graph must be non-negative");
        count = V;
        id = new int[V];
        for (int v = 0; v < V; v++)
            id[v] = v;
    }

    public MyQuickFindUnionFind(Scanner in) {
        if (in == null)
            throw new IllegalArgumentException("argument is null");
        try {
            int V = in.nextInt();
            if (V < 0)
                throw new IllegalArgumentException("number of vertices in a Graph must be non-negative");
            count = V;
            id = new int[V];
            for (int v = 0; v < V; v++)
                id[v] = v;
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
        validateVertex(p);
        validateVertex(q);
        int pID = id[p];
        int qID = id[q];
        if (pID == qID)
            return;
        for (int v = 0; v < id.length; v++) {
            if (id[v] == pID)
                id[v] = qID;
        }
        count--;
    }

    @Override
    public int find(int p) {
        validateVertex(p);
        return id[p];
    }

    private void validateVertex(int p) {
        int n = id.length;
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
