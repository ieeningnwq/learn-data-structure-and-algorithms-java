package com.ieening.doexercises.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Q377CombinationSumIV
 */
public class Q377CombinationSumIV {

    private int ans = 0;

    public int combinationSum4(int[] nums, int target) {
        // 排序方便剪枝
        Arrays.sort(nums);

        Map<Integer, Integer> map = new HashMap<>();

        backtrack(0, nums, target, map);

        return ans;
    }

    private void backtrack(int startIndex, int[] nums, int target, Map<Integer, Integer> map) {
        // 递归终止函数
        if (0 == target) {
            ans++;
            return;
        }
        for (int i = startIndex; i < nums.length && nums[i] <= target; i++) { // 加入剪枝操作 nums[i]<=most
            int targetNext = target - nums[i];
            if (map.containsKey(targetNext)) {
                ans += map.get(targetNext);
            } else {
                int c = ans;
                backtrack(startIndex, nums, targetNext, map);
                map.put(targetNext, ans - c);
            }
        }
    }

    public static void main(String[] args) {
        Q377CombinationSumIV solution = new Q377CombinationSumIV();
        solution.combinationSum4(new int[] { 1, 2, 3 }, 5);
    }
}