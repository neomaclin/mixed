package org.demo.core.algos.sort;

import org.demo.core.data.DoubleLinkedList;
import org.demo.core.data.SingleLinkedList;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InsertionSort {
    public static int[] on(int[] arr, int size) {
        if (arr == null || size == 0) return new int[]{};
        for (int i = 1; i < size; i++) {
            int curr = arr[i];
            int head = i - 1;
            while (head >= 0 && arr[head] > curr) {
                arr[head + 1] = arr[head--];
            }
            arr[head + 1] = curr;
        }
        return arr;
    }

    public static SingleLinkedList.Node on(SingleLinkedList.Node list) {
        SingleLinkedList.Node sorted = null;
        SingleLinkedList.Node index = list;
        SingleLinkedList.Node next, moving;
        while (index != null) {
            next = index.next;
            moving = index;
            if (sorted == null || sorted.value >= moving.value) {
                moving.next = sorted;
                sorted = moving;
            } else {
                moving = sorted;
                /* Locate the node before the point of insertion */
                while (moving.next != null && index.value > moving.next.value) {
                    moving = moving.next;
                }
                index.next = moving.next;
                moving.next = index;
            }
            index = next;
        }
        return sorted;
    }

    public static void main(String[] args) {

        int[] arr = {12, 11, 13, 5, 6, 7};
        int n = arr.length;
        SingleLinkedList.Node list = SingleLinkedList.from(arr);
        System.out.println("Sorted array is");
        String arrString =
                IntStream.of(on(arr, n)).mapToObj(String::valueOf).collect(Collectors.joining(" "));
        System.out.println("[" + arrString + "]");

        System.out.println("Sorted Single linked list is");
        System.out.println(SingleLinkedList.print(on(list)).toString());
    }

    public static void on(DoubleLinkedList list) {
    }
}
