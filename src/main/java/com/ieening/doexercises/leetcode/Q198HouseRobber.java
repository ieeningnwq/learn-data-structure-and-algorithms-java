package com.ieening.doexercises.leetcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q198HouseRobber {
    private int profit = 0;
    public int rob(int[] nums) {
        int[] stealHouse = new int[nums.length];
        backtrack(0, stealHouse, nums);
        return profit;
    }

    private void backtrack(int start, int[] stealHouse, int[] nums) {
        // 递归终止条件
        if (start == nums.length) {
            int t = 0;
            for (int i = 0; i < nums.length; i++) {
                if (stealHouse[i] == 1)
                    t += nums[i];
            }
            if (profit < t)
                profit = t;
            return;
        }

        // 分解问题
        int pos = start;
        while (pos < nums.length) {
            // 偷盗
            if (pos - 1 >= 0 && stealHouse[pos - 1] == 1 && pos + 1 < nums.length)
                pos = pos + 1;
            if (pos == 0 || (pos < nums.length && stealHouse[pos - 1] == 0))
                stealHouse[pos] = 1;
            backtrack(pos + 1, stealHouse, nums);
            // 回溯，不偷
            stealHouse[pos] = 0;
            // 查看下一个
            pos += 1;
        }
    }

    public static void main(String[] args) {
        Q198HouseRobber solution = new Q198HouseRobber();
        System.out.println(solution.rob(new int[] { 2, 7, 9, 3, 1 }));
    }
}
