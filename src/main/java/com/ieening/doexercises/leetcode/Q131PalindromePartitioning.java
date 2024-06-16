package com.ieening.doexercises.leetcode;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Q131PalindromePartitioning {
    public List<List<String>> partition(String s) {
        List<List<String>> ans = new LinkedList<>();
        Deque<String> elements = new LinkedList<>();

        backtrack(0, s.length(), s, elements, ans);

        return ans;
    }

    private void backtrack(int start, int sLength, String s, Deque<String> elements, List<List<String>> ans) {
        // 递归终止条件
        if (start == sLength) {
            LinkedList<String> l = new LinkedList<>(elements);
            Collections.reverse(l);
            ans.add(l);
            return;
        }
        int startNext = start + 1;
        while (startNext <= sLength) {
            boolean palindrome = false;
            // 从 start 开始找到最短回文子串
            while (startNext <= sLength && !(palindrome = isPalindrome(s.substring(start, startNext)))) {
                startNext++;
            }
            if (palindrome) {
                elements.push(s.substring(start, startNext));
                backtrack(startNext, sLength, s, elements, ans);
                // 回溯
                elements.pop();
                startNext++;
            }

        }
    }

    private boolean isPalindrome(String s) {
        int sRight = s.length() - 1;
        int sLeft = 0;
        while (sLeft < sRight && s.charAt(sLeft) == s.charAt(sRight)) {
            sLeft++;
            sRight--;
        }
        return sLeft >= sRight;
    }

    public static void main(String[] args) {
        Q131PalindromePartitioning solution = new Q131PalindromePartitioning();
        System.out.println(solution.partition("aabaa"));
        
    }
}
