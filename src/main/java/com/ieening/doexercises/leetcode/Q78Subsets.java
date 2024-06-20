package com.ieening.doexercises.leetcode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Q78Subsets
 */
public class Q78Subsets {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new LinkedList<>();
        Deque<Integer> elements = new LinkedList<>();

        backtrack(nums, 0, elements, ans);

        return ans;
    }

    private void backtrack(int[] nums, int start, Deque<Integer> elements, List<List<Integer>> ans) {
        ans.add(new LinkedList<>(elements));
        for (int i = start; i < nums.length; i++) {
            elements.push(nums[i]);
            backtrack(nums, i + 1, elements, ans);
            elements.pop();
        }
    }

    public static void main(String[] args) {
        Q78Subsets solution = new Q78Subsets();
        System.out.println(solution.subsets(new int[] { 1, 2, 3 }));
    }
}