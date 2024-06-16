package com.ieening.doexercises.leetcode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Q77Combinations {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans = new LinkedList<>();
        Integer[] elements = new Integer[k];

        backtrack(elements, 1, n, 0, ans);

        return ans;

    }

    private void backtrack(Integer[] elements, int num, int n, int k, List<List<Integer>> ans) {
        // 递归终止条件
        if (k == elements.length) {
            ans.add(Arrays.asList(Arrays.copyOf(elements, elements.length))); // 存放结果
            return;
        }

        for (int i = num; i <= n; i++) {
            elements[k++] = i;
            backtrack(elements, i + 1, n, k, ans);
            k--; // 回溯，撤销处理结果
        }

    }

    public static void main(String[] args) {
        Q77Combinations solution = new Q77Combinations();
        System.out.println(solution.combine(4, 2));
    }
}
