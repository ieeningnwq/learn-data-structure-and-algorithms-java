package com.ieening.datastructure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Stream;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class KnapsackProblem {
    public int groupingKnapsack(List<List<Integer>> w, List<List<Integer>> v, int W) {
        int[] dp = new int[W + 1];
        for (int i = 0; i < w.size(); i++) {
            for (int j = W; j >= 0; j--) {
                for (int k = 0; k < w.get(i).size(); k++) {
                    if (j>=w.get(i).get(k)) {
                        dp[j]=Math.max(dp[j], dp[j-w.get(i).get(k)]+v.get(i).get(k));
                    }
                }
            }
        }
        return dp[W];
    }
}