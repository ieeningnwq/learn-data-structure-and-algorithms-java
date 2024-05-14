package com.ieening.doexercises.leetcode;

public class Q42TrappingRainWater {
    public int myTrap(int[] height) {
        int step = 0, waterDrops = 0;
        for (int i = 0; i < height.length; i++) {
            if (step < height[i])
                step = height[i];
        }
        for (; step > 0; step--) {
            int index = 0, left = -1;
            while (index < height.length) {
                if (height[index] >= step) { // 寻找左侧堤坝
                    left = index++;

                } else if (height[index] < step && left != -1) { // 寻找到低坑
                    // 寻找右侧堤坝
                    while (index < height.length && height[index] < step) {
                        index++;
                    }
                    if (index < height.length) {
                        waterDrops += (index - left - 1);
                    }
                }else{
                    index++;
                }
            }
        }
        return waterDrops;
    }

    public static void main(String[] args) {
        Q42TrappingRainWater tRainWater = new Q42TrappingRainWater();
        int[] height = new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
        System.out.println(tRainWater.myTrap(height));
        height = new int[] { 4, 2, 0, 3, 2, 5 };
        System.out.println(tRainWater.myTrap(height));

    }
}
