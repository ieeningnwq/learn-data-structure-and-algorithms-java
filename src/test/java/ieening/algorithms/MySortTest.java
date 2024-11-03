package ieening.algorithms;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.ieening.algorithms.MySort;

public class MySortTest {
    @Test
    public void testBubbleSortLengthZero() {
        int[] arr = new int[0];
        MySort.bubbleSort(arr);
        assertArrayEquals(new int[0], arr);
    }

    @Test
    public void testBubbleSortLengthNorm() {
        int[] arr = new int[] { 10, 7, 1, 5, 6, 2, 1, 0, -3 };
        int[] arrCopy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arrCopy);
        MySort.bubbleSort(arr);
        assertArrayEquals(arrCopy, arr);
    }

    @Test
    public void testSelectionSortLengthZero() {
        int[] arr = new int[0];
        MySort.selectionSort(arr);
        assertArrayEquals(new int[0], arr);
    }

    @Test
    public void testSelectionSortLengthNorm() {
        int[] arr = new int[] { 10, 7, 1, 5, 6, 2, 1, 0, -3 };
        int[] arrCopy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arrCopy);
        MySort.selectionSort(arr);
        assertArrayEquals(arrCopy, arr);
    }

    @Test
    public void testInsertSortLengthZero() {
        int[] arr = new int[0];
        MySort.insertSort(arr);
        assertArrayEquals(new int[0], arr);
    }

    @Test
    public void testInsertSortLengthNorm() {
        int[] arr = new int[] { 10, 7, 1, 5, 6, 2, 1, 0, -3 };
        int[] arrCopy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arrCopy);
        MySort.insertSort(arr);
        assertArrayEquals(arrCopy, arr);
    }

    @Test
    public void testShellSortLengthZero() {
        int[] arr = new int[0];
        MySort.shellSort(arr);
        assertArrayEquals(new int[0], arr);
    }

    @Test
    public void testShellSortLengthNorm() {
        int[] arr = new int[] { 10, 7, 1, 5, 6, 2, 1, 0, -3 };
        int[] arrCopy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arrCopy);
        MySort.shellSort(arr);
        assertArrayEquals(arrCopy, arr);
    }

    @Test
    public void testMergeSortLengthZero() {
        int[] arr = new int[0];
        MySort.mergeSort(arr);
        assertArrayEquals(new int[0], arr);
    }

    @Test
    public void testMergeSortLengthNorm() {
        int[] arr = new int[] { 10, 7, 1, 5, 6, 2, 1, 0, -3 };
        int[] arrCopy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arrCopy);
        MySort.mergeSort(arr);
        assertArrayEquals(arrCopy, arr);
    }

    @Test
    public void testQuickSortLengthZero() {
        int[] arr = new int[0];
        MySort.quickSort(arr);
        assertArrayEquals(new int[0], arr);
    }

    @Test
    public void testQuickSortLengthNorm() {
        int[] arr = new int[] { 10, 7, 1, 5, 6, 2, 1, 0, -3 };
        int[] arrCopy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arrCopy);
        MySort.quickSort(arr);
        assertArrayEquals(arrCopy, arr);
    }

    @Test
    public void testHeapSortLengthZero() {
        int[] arr = new int[0];
        MySort.heapSort(arr);
        assertArrayEquals(new int[0], arr);
    }

    @Test
    public void testHeapSortLengthNorm() {
        int[] arr = new int[] { 10, 7, 1, 5, 6, 2, 1, 0, -3 };
        int[] arrCopy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arrCopy);
        MySort.heapSort(arr);
        assertArrayEquals(arrCopy, arr);
    }
}
