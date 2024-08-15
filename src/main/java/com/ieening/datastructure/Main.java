package com.ieening.datastructure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ServiceLoader;
import java.util.stream.Stream;
import java.util.LinkedList;

public class Main {
    /**
     * InnerMain
     */
    public class InnerMain {
    
        
    }
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    // 交换元素
    private static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[j] ^ arr[i];
        arr[i] = arr[i] ^ arr[j];
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
        arr = new int[]{5,4,3,2,1};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
        arr = new int[]{5,6,3,2,0,8,6};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

}