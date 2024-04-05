package com.ieening;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    /* 随机访问元素 */
    static int randomAccess(int[] nums) {
        // 在区间 [0, nums.length) 中随机抽取一个数字
        int randomIndex = ThreadLocalRandom.current().nextInt(0, nums.length);
        // 获取并返回随机元素
        int randomNum = nums[randomIndex];
        return randomNum;
    }

    public static void main(String[] args) {
        // ArrayDeque
        ArrayList<Integer> a = new ArrayList<>();
        a.add(12);
        a.equals(a);
        a.hashCode();
        System.out.println(randomAccess(new int[] { 1, 2, 3, 4, 5 }));

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
