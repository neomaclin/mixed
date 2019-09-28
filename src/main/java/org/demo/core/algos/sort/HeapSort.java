package org.demo.core.algos.sort;

import org.demo.core.data.DoubleLinkedList;
import org.demo.core.data.SingleLinkedList;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HeapSort {

  private static void heapify(int[] arr, int n, int i) {
    int largest = i; // Initialize largest as root
    int left = 2 * i + 1; // left = 2*i + 1
    int right = 2 * i + 2; // right = 2*i + 2

    // If left child is larger than root
    if (left < n && arr[left] > arr[largest]) largest = left;

    // If right child is larger than largest so far
    if (right < n && arr[right] > arr[largest]) largest = right;

    // If largest is not root
    if (largest != i) {
      Swap.on(arr, i, largest);
      // Recursively heapify the affected sub-tree
      heapify(arr, n, largest);
    }
  }

  public static void main(String[] args) {

    int[] arr = {12, 11, 13, 5, 6, 7};
    int n = arr.length;

    System.out.println("Sorted array is");
    String arrString =
        IntStream.of(on(arr, n)).mapToObj(String::valueOf).collect(Collectors.joining(" "));
    System.out.println("[" + arrString + "]");
  }

  public static int[] on(int[] arr, int n) {

    for (int i = n / 2 - 1; i >= 0; i--) heapify(arr, n, i);

    // One by one extract an element from heap
    for (int i = n - 1; i >= 0; i--) {
      Swap.on(arr, 0, i);
      // call max heapify on the reduced heap
      heapify(arr, i, 0);
    }

    return arr;
  }

  public static void on(SingleLinkedList list) {}

  public static void on(DoubleLinkedList list) {}
}
