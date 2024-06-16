package com.ieening.doexercises.leetcode;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Q39CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> ans = new LinkedList<>();
        Deque<Integer> elements = new LinkedList<>();

        backtrack(candidates, 0, target, elements, ans);

        return ans;

    }

    private void backtrack(int[] candidates, int start, int target, Deque<Integer> elements, List<List<Integer>> ans) {
        // 递归终止条件
        if (target == 0) {
            ans.add(new LinkedList<>(elements));
            return;
        }

        for (int i = start; i < candidates.length && candidates[i] <= target; i++) {
            elements.push(candidates[i]);
            backtrack(candidates, i, target - candidates[i], elements, ans);
            elements.pop();
        }
    }

    public static void main(String[] args) {
        Q39CombinationSum solution = new Q39CombinationSum();
        System.out.println(solution.combinationSum(new int[]{2,3,5}, 8));

    }
}
