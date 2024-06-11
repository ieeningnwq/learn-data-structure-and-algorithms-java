package com.ieening.doexercises.leetcode;

/**
 * Q215KthLargestElementInAnArray
 */
public class Q215KthLargestElementInAnArray {

    public int findKthLargest(int[] nums, int k) {
        quickSelect(nums, 0, nums.length - 1, nums.length - k);
        return nums[nums.length - k];
    }

    private void quickSelect(int[] nums, int left, int right, int k) {
        // 递归终止条件
        if (left >= right)
            return;
        // 拆分问题
        // 快速排序中确认基点
        int pIndex = partition(nums, left, right, k);
        if (pIndex == k)
            return;
        else if (pIndex < k)
            quickSelect(nums, pIndex + 1, right, k);
        else
            quickSelect(nums, left, pIndex - 1, k);
    }

    private int partition(int[] nums, int left, int right, int k) {
        int randomIndex = left + (int) (Math.random() * (right - left + 1));
        swap(nums, left, randomIndex);
        int pivot = nums[left];
        int lt = left + 1;
        int rt = right;
        while (true) {
            while (lt <= right && nums[lt] <= pivot)
                lt++;
            while (rt > left && nums[rt] > pivot)
                rt--;
            if (lt >= rt)
                break;
            // 交换
            swap(nums, lt, rt);
            lt++;
            rt--;
        }
        swap(nums, left, rt);
        return rt;
    }

    private void swap(int[] nums, int index_first, int index_second) {
        int temp = nums[index_first];
        nums[index_first] = nums[index_second];
        nums[index_second] = temp;
    }

    public static void main(String[] args) {
        Q215KthLargestElementInAnArray solution = new Q215KthLargestElementInAnArray();
        System.out.println(solution.findKthLargest(new int[] { 3, 2, 1, 5, 6, 4 }, 2));
    }
}