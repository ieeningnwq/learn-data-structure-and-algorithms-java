package com.ieening.doexercises.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Q90SubsetsII {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> ans = new LinkedList<>();
        List<Integer> elements = new ArrayList<>();
        List<int[]> nonRepeatCount = new ArrayList<>(nums.length);

        // 将相同数据合在一起
        Arrays.sort(nums);
        for (int num : nums) {
            int size = nonRepeatCount.size();
            if (size == 0 || nonRepeatCount.get(size - 1)[0] != num) {
                nonRepeatCount.add(new int[] { num, 1 });
            } else {
                nonRepeatCount.get(size - 1)[1]++;
            }
        }

        backtrack(0, nonRepeatCount, elements, ans);

        return ans;
    }

    private void backtrack(int start, List<int[]> nonRepeatCount, List<Integer> elements, List<List<Integer>> ans) {
        if (start == nonRepeatCount.size()) {
            ans.add(new LinkedList<>(elements));
            return;
        }
        backtrack(start + 1, nonRepeatCount, elements, ans);
        for (int i = 1; i <= nonRepeatCount.get(start)[1]; i++) {
            elements.add(nonRepeatCount.get(start)[0]);
            backtrack(start + 1, nonRepeatCount, elements, ans);
        }
        for (int i = 1; i <= nonRepeatCount.get(start)[1]; i++) {
            elements.remove(elements.size() - 1);
        }
    }

    public static void main(String[] args) {
        Q90SubsetsII solution = new Q90SubsetsII();
        System.out.println(solution.subsetsWithDup(new int[] { 4, 4, 4, 1, 4 }));
    }
}
