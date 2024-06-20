package com.ieening.doexercises.leetcode;

import java.util.LinkedList;
import java.util.List;

public class Q15ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        // 结果列表
        List<List<Integer>> result = new LinkedList<>();
        boolean addZero=false;
        List<Integer> greaterThanZero = new LinkedList<>();
        for (int num : nums) {
            if (num>=0) {
                if (!addZero) {
                    greaterThanZero.add(num);
                    addZero=true;
                }
            }
        }
        Character.isWhitespace(0);
        return result;
    }

    public static void main(String[] args) {
        new Q15ThreeSum().threeSum(new int[] { -1,0,1,2,-1,-4,-2,-3,3,0,4 });
    }
}
