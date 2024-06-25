package com.ieening.doexercises.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Q51NQueens {
    public List<List<String>> solveNQueens(int n) {
        List<int[]> elements = new ArrayList<>(n);
        List<List<String>> ans = new LinkedList<>();

        backtrack(n, elements, ans);

        return ans;
    }

    private void backtrack(int n, List<int[]> elements, List<List<String>> ans) {
        if (elements.size() == n) {
            List<String> e = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                char[] element = new char[n];
                Arrays.fill(element, '.');
                element[elements.get(i)[1]] = 'Q';
                e.add(new String(element));
            }
            ans.add(e);
            return;
        }
        int y = 0;
        while (y < n) {
            int[] pos = getValidPos(elements, y, n);
            if (pos == null) {
                return;
            } else {
                y = pos[1] + 1;
                elements.add(pos);
                backtrack(n, elements,ans);
                elements.remove(elements.size() - 1);
            }
        }

    }

    private int[] getValidPos(List<int[]> elements, int startY, int n) {
        int x = elements.size(), y = -1;
        for (int i = startY; i < n; i++) {
            int m = 0;
            for (int[] element : elements) {
                if (i == element[1] || Math.abs(x - element[0]) == Math.abs(i - element[1]))
                    break;
                else
                    m++;
            }
            if (m == elements.size()) {
                y = i;
                break;
            }
        }
        if (y == -1) {
            return null;
        }
        return new int[] { x, y };
    }

    public static void main(String[] args) {
        Q51NQueens solution = new Q51NQueens();
        System.out.println(solution.solveNQueens(4));
    }
}
