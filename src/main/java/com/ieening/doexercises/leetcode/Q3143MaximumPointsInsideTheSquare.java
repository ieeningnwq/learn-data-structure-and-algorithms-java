package com.ieening.doexercises.leetcode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Q3143MaximumPointsInsideTheSquare {
    public int maxPointsInsideSquare(int[][] points, String s) {
        int length = -1;
        Map<Character, Integer> chars = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            char c = s.charAt(i);
            int charL = Math.max(Math.abs(points[i][0]), Math.abs(points[i][1]));
            if (length == -1) {
                if (chars.containsKey(c)) {
                    int l = chars.get(c);
                    int maxer, miner;
                    if (charL > l) {
                        maxer = charL;
                        miner = l;
                    } else {
                        maxer = l;
                        miner = charL;
                    }
                    // 初始化边长
                    length = maxer - 1;
                    // 更新哈希表
                    chars.put(c, miner);
                    // 更新 chars
                    Iterator<Character> it = chars.keySet().iterator();
                    while (it.hasNext()) {
                        Character next = it.next();
                        if (chars.get(next) > length)
                            it.remove();
                    }
                } else {
                    chars.put(c, charL);
                }
            } else {
                if (charL <= length) {
                    if (chars.containsKey(c)) {
                        int l = chars.get(c);
                        int maxer, miner;
                        if (charL > l) {
                            maxer = charL;
                            miner = l;
                        } else {
                            maxer = l;
                            miner = charL;
                        }
                        // 初始化边长
                        length = maxer - 1;
                        // 更新哈希表
                        chars.put(c, miner);
                        // 更新 chars
                        Iterator<Character> it = chars.keySet().iterator();
                        while (it.hasNext()) {
                            Character next = it.next();
                            if (chars.get(next) > length)
                                it.remove();
                        }
                    } else {
                        chars.put(c, charL);
                    }
                }
            }
        }
        return chars.size();
    }
}
