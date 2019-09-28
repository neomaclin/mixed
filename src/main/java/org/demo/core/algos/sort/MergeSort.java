package org.demo.core.algos.sort;

import org.demo.core.data.DoubleLinkedList;
import org.demo.core.data.SingleLinkedList;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MergeSort {

    private static void merge(int[] arr, int low, int mid, int high, int[] tmp) {
        int left = low, right = mid + 1, length = 0;
        while (left <= mid && right <= high) {
            if (arr[left] <= arr[right]) tmp[length++] = arr[left++];
            else tmp[length++] = arr[right++];
        }
        //copy sorted
        while (left <= mid) tmp[length++] = arr[left++];
        while (right <= high) tmp[length++] = arr[right++];
        System.arraycopy(tmp, 0, arr, low, length);
    }

    static private void mergeSort(int[] arr, int low, int high, int[] tmp) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(arr, low, mid, tmp);
            mergeSort(arr, mid + 1, high, tmp);
            merge(arr, low, mid, high, tmp);
        }
    }

    // Time: O(n*log(n)), Space: O(n)
    static private int[] sortRecursive(int[] arr) {
        if (arr == null || arr.length == 0) return arr;
        int[] tmp = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, tmp);
        return arr;
    }

    // Time: O(n*log(n)), Space: O(n)
    static private int[] sortIterative(int[] arr) {
        if (arr == null || arr.length == 0) return arr;
        int n = arr.length;
        int[] tmp = new int[n];
        for (int len = 1; len < n; len = 2 * len) {
            for (int low = 0; low < n; low += 2 * len) {
                int mid = Math.min(low + len - 1, n - 1);
                int high = Math.min(low + 2 * len - 1, n - 1);
                merge(arr, low, mid, high, tmp);
            }
        }
        return arr;
    }

    public static int[] on(int[] arr, int size) {
        return sortRecursive(arr);
    }


    private static SingleLinkedList.Node merge(SingleLinkedList.Node l1, SingleLinkedList.Node l2) {
        SingleLinkedList.Node newNode = new SingleLinkedList.Node(-1), current = newNode;

        while (l1 != null && l2 != null) {
            if (l1.value < l2.value) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }

        if (l1 != null) current.next = l1;
        if (l2 != null) current.next = l2;
        return newNode.next;
    }

    private static SingleLinkedList.Node midOf(SingleLinkedList.Node list) {
        SingleLinkedList.Node temp = new SingleLinkedList.Node(0);
        temp.next = list;
        SingleLinkedList.Node slow = temp;
        SingleLinkedList.Node fast = temp;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private static SingleLinkedList.Node on(SingleLinkedList.Node list) {
        if (list == null || list.next == null) return list;
        SingleLinkedList.Node mid = midOf(list);
        SingleLinkedList.Node next = mid.next;
        mid.next = null;
        return merge(on(list), on(next));
    }

    public static void sort(DoubleLinkedList list) {
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
}
