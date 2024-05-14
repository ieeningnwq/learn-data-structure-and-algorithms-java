package com.ieening.doexercises.leetcode;

/**
 * 删除有序数组中的重复项 Ⅱ
 */
public class Q80RemoveDuplicatesFromSortedArrayII {
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length < 3)
            return nums == null ? 0 : nums.length;
        int slowIndex = 0, quickIndex = 1, duplicateTime = 1;
        while (quickIndex < nums.length) {
            if (nums[slowIndex] == nums[quickIndex]) {
                if (duplicateTime < 2) {
                    nums[++slowIndex] = nums[quickIndex];// 出现第二次
                    duplicateTime++;
                }
            } else {
                duplicateTime = 1;
                nums[++slowIndex] = nums[quickIndex];
            }
            quickIndex++;
        }
        return slowIndex + 1;
    }

    public int removeDuplicatesTwoPinter(int[] nums) {
        if (nums == null || nums.length < 3)
            return nums == null ? 0 : nums.length;
        int slowIndex = 2, quickIndex = 2;
        while (quickIndex < nums.length) {
            if (nums[slowIndex - 2] != nums[quickIndex])
                nums[slowIndex++] = nums[quickIndex];
            quickIndex++;
        }
        return slowIndex;
    }
}
