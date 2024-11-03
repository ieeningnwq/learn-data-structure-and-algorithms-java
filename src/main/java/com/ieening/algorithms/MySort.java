package com.ieening.algorithms;

public class MySort {
    // 冒泡排序
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) { // 比较的轮数
            for (int j = 0; j < arr.length - 1 - i; j++) { // 该轮需要比较的次数
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    // 选择排序
    public static void selectionSort(int[] arr) {
        if (arr.length < 2) // 0 或 1 个数组元素个数
            return;
        for (int i = 0; i < arr.length - 1; i++) { // 当前 N-1 个元素排好后，最后一个元素无需执行，故 i < arr.length - 1
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) { // 找到本轮执行中最小的元素，将最小值下标赋值给 minIdx
                if (arr[j] < arr[minIdx])
                    minIdx = j;
            }
            swap(arr, i, minIdx);
        }
        return;
    }

    // 插入排序
    public static void insertSort(int[] arr) {
        if (arr.length < 2)
            return;
        // n - 1 轮次执行
        for (int i = 1; i < arr.length; i++) {
            // 通过二分查找得到插入位置
            int target = arr[i];
            int pos = binarySearch(arr, 0, i - 1, target);
            for (int j = i; j > pos; j--) { // 移动
                arr[j] = arr[j - 1];
            }
            arr[pos] = target; // 插入
        }
        return;
    }

    // 希尔排序：采用 Shell 增量 N / 2^k
    public static void shellSort(int[] arr) {
        if (arr.length < 2)
            return;
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) { // gap 初始为 n/2，缩小gap直到1
            for (int start = 0; start < gap; start++) { // 步长增量是gap，当前增量下需要对gap组序列进行简单插入排序
                for (int i = start + gap; i < n; i += gap) { // 此for及下一个for对当前增量序列执行简单插入排序
                    int target = arr[i], j = i - gap;
                    for (; j >= 0; j -= gap) {
                        if (target < arr[j])
                            arr[j + gap] = arr[j];
                        else
                            break;
                    }
                    arr[j + gap] = target;
                }
            }
        }
        return;
    }

    // 交换元素
    private static void swap(int[] arr, int i, int j) {
        if (i == j)
            return;
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[j] ^ arr[i];
        arr[i] = arr[i] ^ arr[j];
    }

    // 二分搜索
    private static int binarySearch(int[] arr, int l, int r, int target) {
        while (l <= r) {
            int c = l + (r - l) / 2;
            if (arr[c] <= target)
                l = c + 1;
            else
                r = c - 1;
        }
        return l;
    }

    // 归并排序，非原地
    public static void mergeSort(int[] arr) {
        if (arr.length < 2)
            return;
        int[] tmpArr = new int[arr.length];
        mergeSort(arr, tmpArr, 0, arr.length - 1);
        return;
    }

    // mergeSort 递归方法
    private static void mergeSort(int[] arr, int[] tmpArr, int l, int r) {
        if (l < r) {
            int c = l + (r - l) / 2;
            mergeSort(arr, tmpArr, l, c);
            mergeSort(arr, tmpArr, c + 1, r);
            merge(arr, tmpArr, l, c, r);
        }
    }

    // 非原地合并方法
    private static void merge(int[] arr, int[] tmpArr, int l, int c, int r) {
        int lh = l, rh = c + 1, h = l; // lh: left head, rh: right head, h: tmpArr head
        while (lh <= c && rh <= r) {
            tmpArr[h++] = arr[lh] <= arr[rh] ? arr[lh++] : arr[rh++];
        }
        while (lh <= c)
            tmpArr[h++] = arr[lh++]; // 左半边还有剩余，加入 tmpArr 末尾
        while (rh <= r)
            tmpArr[h++] = arr[rh++]; // 右半边还有剩余，加入 tmpArr 末尾
        for (; l <= r; l++)
            arr[l] = tmpArr[l]; // 将 tmpArr 拷回 arr 中
    }

    // 单轴快排：首位为轴
    public static void quickSort(int[] arr) {
        if (arr.length < 2)
            return;
        quickSort(arr, 0, arr.length - 1);
        return;
    }

    // 单轴快排递归方法：首位为轴
    private static void quickSort(int[] arr, int l, int r) {
        if (l < r) { // 若left == right，表示此时 arr 只有一个元素，即为基准情形，完成递归
            int p = partition(arr, l, r); // 完成 p 排序
            quickSort(arr, l, p - 1);
            quickSort(arr, p + 1, r);
        }
    }

    // partition 方法
    private static int partition(int[] arr, int l, int r) {
        int j = l + 1;
        for (int i = j; i <= r; i++) {
            if (arr[i] < arr[l]) {
                swap(arr, i, j); // 交换后的 arr[j] 为当前最后一个小于主轴元素的元素
                j++;
            }
        }
        swap(arr, l, j - 1); // 主轴元素归位
        return j - 1;
    }

    // 堆排序
    public static void heapSort(int[] arr) {
        if (arr.length < 2)
            return;
        heapify(arr, arr.length - 1); // 构建大顶堆
        for (int i = arr.length - 1; i > 0; i--) { // i > 0 即可，无需写成 i >= 0，当 N-1 个元素排序时，最后一个元素也已排序
            swap(arr, 0, i); // 交换堆顶和当前未排序部分最后一个元素
            siftDown(arr, 0, i - 1); // i - 1 是未排序部分最后一个元素下标，传入此参数确保下滤不会超过此范围
        }
        return;
    }

    // 堆化方法
    private static void heapify(int[] arr, int r) {
        for (int hole = (r - 1) / 2; hole >= 0; hole--) { // (r - 1) / 2 为最后一个叶子节点的父节点下标
            siftDown(arr, hole, r);
        }
    }

    // 下滤方法
    private static void siftDown(int[] arr, int hole, int r) {
        int target = arr[hole], child = hole * 2 + 1; // target 是要下滤的结点的值
        while (child <= r) { // child 最大为 r
            if (child < r && arr[child + 1] > arr[child])
                child++; // #1
            if (arr[child] > target) { // 若 child 大于 target
                arr[hole] = arr[child]; // 则 arr[child] 上移到下标 hole 处
                hole = child; // hole 更新为 child (下滤)
                child = hole * 2 + 1; // 更新 child ，也可以写成 child = child * 2 + 1
            } else
                break; // 若 arr[child] <= target ，说明下标 hole 处已经满足堆序，退出 while
        }
        arr[hole] = target; // 将 target 填 入hole 中
    }
}
