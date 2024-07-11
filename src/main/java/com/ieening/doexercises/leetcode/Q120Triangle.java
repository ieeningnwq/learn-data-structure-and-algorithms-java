package com.ieening.doexercises.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q120Triangle {
    public int minimumTotal(List<List<Integer>> triangle) {
        int l = triangle.size();
        int[] s = new int[l];
        s[0] = triangle.get(0).get(0);
        int pre = s[0];
        for (int x = 1; x < l; x++) {
            for (int y = 0; y <= x; y++) {
                if (y == 0) {
                    pre = s[y];
                    s[y] = s[y] + triangle.get(x).get(y);
                } else if (y == x) {
                    s[y] = pre + triangle.get(x).get(y);

                } else {
                    int r = Math.min(pre, s[y]) + triangle.get(x).get(y);
                    pre = s[y];
                    s[y] = r;
                }
            }
        }
        pre = s[0];
        for (int i = 1; i < l; i++) {
            if (pre > s[i])
                pre = s[i];
        }
        return pre;
    }

    public static void main(String[] args) {
        Q120Triangle solution = new Q120Triangle();
        List<List<Integer>> triangle = new ArrayList<>();
        triangle.add(new ArrayList<>(Arrays.asList(2)));
        triangle.add(new ArrayList<>(Arrays.asList(3, 4)));
        triangle.add(new ArrayList<>(Arrays.asList(6, 5, 7)));
        triangle.add(new ArrayList<>(Arrays.asList(4, 1, 8, 3)));
        System.out.println(solution.minimumTotal(triangle));
    }
}
