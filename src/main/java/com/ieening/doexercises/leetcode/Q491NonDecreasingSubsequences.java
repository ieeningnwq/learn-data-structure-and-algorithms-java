package com.ieening.doexercises.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Q491NonDecreasingSubsequences {
    List<Integer> temp = new ArrayList<Integer>();
    List<List<Integer>> ans = new ArrayList<List<Integer>>();

    public List<List<Integer>> findSubsequences(int[] nums) {
        dfs(0, Integer.MIN_VALUE, nums);
        return ans;
    }

    public void dfs(int cur, int last, int[] nums) {
        if (cur == nums.length) {
            if (temp.size() >= 2) {
                ans.add(new ArrayList<Integer>(temp));
            }
            return;
        }
        
        if (nums[cur] >= last) {
            temp.add(nums[cur]);
            dfs(cur + 1, nums[cur], nums);
            temp.remove(temp.size() - 1);
        }
        if (nums[cur] != last)
            dfs(cur + 1, last, nums);

    }

    public static void main(String[] args) {
        Q491NonDecreasingSubsequences solution = new Q491NonDecreasingSubsequences();
        System.out.println(solution.findSubsequences(new int[] { 4, 6, 7, 7, 7 }));
    }
}
