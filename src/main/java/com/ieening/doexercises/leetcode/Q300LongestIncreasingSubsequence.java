package com.ieening.doexercises.leetcode;

/**
 * Q300LongestIncreasingSubsequence
 */
public class Q300LongestIncreasingSubsequence {

    public int lengthOfLIS(int[] nums) {
        int ans = 1;
        int[] dp = new int[nums.length];
        dp[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            int t = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j] && t <= dp[j])
                    t = dp[j] + 1;
            }
            dp[i] = t;
            if (t > ans)
                ans = t;
        }
        return ans;
    }

    public static void main(String[] args) {
        Q300LongestIncreasingSubsequence solution = new Q300LongestIncreasingSubsequence();
        System.out.println(solution.lengthOfLIS(new int[] { 10, 9, 2, 5, 3, 7, 101, 18 }));
    }
}