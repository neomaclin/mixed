package org.demo.core.algos.sort;

class Swap {

  static void on(int[] arr, int a, int b) {
    int temp = arr[a];
    arr[a] = arr[b];
    arr[b] = temp;
  }
}
