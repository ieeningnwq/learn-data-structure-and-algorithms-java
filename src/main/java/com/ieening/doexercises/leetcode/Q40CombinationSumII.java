package com.ieening.doexercises.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Q40CombinationSumII {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new LinkedList<>();
        Deque<Integer[]> elements = new LinkedList<>();

        List<int[]> candidatesFrequency = new ArrayList<>();
        Arrays.sort(candidates);
        for (int candidate : candidates) {
            int size = candidatesFrequency.size();
            if (candidatesFrequency.isEmpty() || candidatesFrequency.get(size - 1)[0] != candidate) {
                candidatesFrequency.add(new int[] { candidate, 1 });
            } else {
                candidatesFrequency.get(size - 1)[1]++;
            }
        }

        backtrack(0, target, elements, candidatesFrequency, ans);

        return ans;
    }

    private void backtrack(int start, int target, Deque<Integer[]> elements,
            List<int[]> candidatesFrequency,
            List<List<Integer>> ans) {
        // 终止条件
        if (target == 0) {
            List<Integer> l = new LinkedList<>();
            for (Integer[] element : elements) {
                for (int i = 0; i < element[1]; i++) {
                    l.add(element[0]);
                }
            }
            ans.add(l);
            return;
        }
        if (start == candidatesFrequency.size()
                || candidatesFrequency.get(start)[0] > target) // 剪枝
            return;

        int most = Math.min(candidatesFrequency.get(start)[1], target / candidatesFrequency.get(start)[0]);
        for (int i = 0; i <= most; i++) {
            elements.push(new Integer[] { candidatesFrequency.get(start)[0], i });
            backtrack(start + 1, target - i * candidatesFrequency.get(start)[0], elements, candidatesFrequency, ans);
            elements.pop();
        }
    }

    public static void main(String[] args) {
        Q40CombinationSumII solution = new Q40CombinationSumII();
        System.out.println(solution.combinationSum2(new int[] { 2, 5, 2, 1, 2 }, 5));
    }
}
