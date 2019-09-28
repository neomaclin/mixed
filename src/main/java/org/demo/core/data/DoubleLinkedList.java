package org.demo.core.data;

public class DoubleLinkedList {
  Node head;
  Node tail;

  public class Node {
    int value;
    Node prev;
    Node next;

    Node(int value) {
      this.value = value;
    }
  }
}
