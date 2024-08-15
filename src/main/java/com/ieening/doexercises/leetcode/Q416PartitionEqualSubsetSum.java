package com.ieening.doexercises.leetcode;

import java.util.HashMap;
import java.util.Map;

public class Q416PartitionEqualSubsetSum {
    boolean idvidedSuccess = false;
    Map<String, Boolean> notes = new HashMap<>();

    public boolean canPartition(int[] nums) {
        int s = 0;
        for (int num : nums) {
            s += num;
        }
        if ((s & 1) == 1)
            return false;
        else
            s /= 2;
        backtrack(s, 0, nums);
        return idvidedSuccess;
    }

    private void backtrack(int target, int start, int[] nums) {
        // 递归终止条件
        if (idvidedSuccess)
            return;
        else if (target == 0) {
            idvidedSuccess = true;
            return;
        }

        // 拆分问题
        for (int pos = start; pos < nums.length; pos++) {
            if (target - nums[pos] >= 0) {
                target -= nums[pos];
                String key = target + "," + start;
                if (notes.containsKey(key))
                    continue;
                backtrack(target, pos + 1, nums);
                notes.put(key, idvidedSuccess);
                target += nums[pos];
            }
        }
    }

    public static void main(String[] args) {
        Q416PartitionEqualSubsetSum solution = new Q416PartitionEqualSubsetSum();
        int[] nums = new int[] { 1,
                1,
                1, 1, 1, 1, 1, 1,
                97, 95 };
        System.out.println(solution.canPartition(nums));
    }
}
