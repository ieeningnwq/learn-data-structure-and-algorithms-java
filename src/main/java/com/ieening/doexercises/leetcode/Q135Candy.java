package com.ieening.doexercises.leetcode;

public class Q135Candy {
    public int candy(int[] ratings) {
        // 确认糖果最少的孩子
        int minCandy = 0, minIndex = 0;
        for (int i = 0; i < ratings.length; i++) {
            int currentCandy = 1;
            int j = i + 1;
            while (j < ratings.length && ratings[j] > ratings[j + 1]) { // 递减
                currentCandy++;
            }
            j = i + 1;
            while (j < ratings.length && ratings[j] < ratings[j + 1]) { // 递增
                currentCandy++;
            }
            if (currentCandy > minCandy) {
                minCandy = currentCandy;
                minIndex = i;
            }
        }
        int totalCandy = 1, preCandy = 1;
        for (int i = minIndex + 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                preCandy = preCandy + 1;// 右面孩子评分比左面高
            } else {
                preCandy = 1;
            }
            totalCandy += preCandy;
        }
        for (int i = minIndex - 1; i >= 0; i--) {
            if (ratings[i] > ratings[i - 1]) {
                preCandy = preCandy + 1;// 右面孩子评分比左面高
            } else {
                preCandy = 1;
            }
            totalCandy += preCandy;
        }

        return totalCandy;
    }
}
