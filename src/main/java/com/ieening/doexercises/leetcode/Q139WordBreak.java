package com.ieening.doexercises.leetcode;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Q139WordBreak {
    public boolean wordBreak(String s, List<String> wordDict) {
        Deque<Integer> stack = new LinkedList<>();
        Set<String> wordDictSet = new HashSet<>(wordDict);
        stack.push(0);
        boolean result = true;
        int i = 1;
        while (!stack.isEmpty() && result) {
            int start = stack.peek();
            boolean flag = true;
            while (flag && i <= s.length()) {
                if (wordDictSet.contains(s.substring(start, i))) {
                    stack.push(i);
                    flag = false;
                } else
                    i++;
            }
            if (flag)
                i = stack.pop() + 1;
            else if (i == s.length())
                result = false;
        }
        return !result;
    }

    public static void main(String[] args) {
        Q139WordBreak solution = new Q139WordBreak();
        System.out.println(solution.wordBreak("catsandog", Arrays.asList(new String[] { "cats", "dog", "sand", "and", "cat" })));
    }
}
