package org.demo.core.algos.sort;

import org.demo.core.data.DoubleLinkedList;
import org.demo.core.data.SingleLinkedList;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuickSort {

    private static void sort(int[] arr, int low, int high) {
        if (low < high) {
            int left = low;
            int index = low+1;
            int right = high;
            int pivot = arr[low];
            while (index <= right) {
                if      (arr[index] < pivot) Swap.on(arr, left++, index++);
                else if (arr[index] > pivot) Swap.on(arr, index, right--);
                else              index++;
            }
            sort(arr, low, left - 1);
            sort(arr, right + 1, high);
        }
    }

    private static void dualPivotSort(int[] arr, int low, int high) {

        //compare to the one above
        if (high<=low) return;
        if (arr[high] < arr[low])  Swap.on(arr, low, high);

        int index=low+1;
        int left=low+1;
        int right=high-1;

        while (index<=right){
            if (arr[index] < arr[low]) Swap.on(arr, index++, left++);
            else if (arr[index] > arr[high])Swap.on(arr, index, right--);
            else index++;
        }

        Swap.on(arr, low, --left);
        Swap.on(arr, high, ++right);

        dualPivotSort(arr, low, left-1);
        if (arr[left] < arr[right]) dualPivotSort(arr, left+1, right-1);
        dualPivotSort(arr, right+1, high);
    }
    public static int[] on(int[] arr, int size) {
        dualPivotSort(arr, 0, size - 1);

        return arr;
    }

    public static void sort(SingleLinkedList list) {
    }

    public static void sort(DoubleLinkedList list) {
    }

    public static void main(String[] args) {

        int[] arr = {12, 11, 13, 5, 6, 7};
        int n = arr.length;

        System.out.println("Sorted array is");
        String arrString =
                IntStream.of(on(arr, n)).mapToObj(String::valueOf).collect(Collectors.joining(" "));
        System.out.println("[" + arrString + "]");
    }
}
