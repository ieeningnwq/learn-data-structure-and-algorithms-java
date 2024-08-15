package com.ieening.doexercises.leetcode;

import java.util.Deque;
import java.util.LinkedList;

public class Q32LongestValidParentheses {
    public int longestValidParentheses(String s) {
        if (s.length() <= 0)
            return 0;
        int[] dp = new int[s.length()];
        int ans = 0;
        Deque<Integer> stack = new LinkedList<>();
        if (s.charAt(0) == '(')
            stack.push(0);
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if ('(' == c) {
                stack.push(i);
            } else if (stack.isEmpty()) {
                stack.clear();
            } else {
                int last = stack.pop();
                if (last - 1 >= 0 && s.charAt(last-1)==')') {
                    dp[i] = dp[last - 1] + 2;
                } else
                    dp[i] = dp[i - 1] + 2;
                if (ans < dp[i])
                    ans = dp[i];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Q32LongestValidParentheses solution = new Q32LongestValidParentheses();
        System.out.println(solution.longestValidParentheses("()(()()"));
    }
}
