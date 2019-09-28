package org.demo.core.data;

public class SingleLinkedList {

//  private int size = 0;
//  Node head;

    static public StringBuilder print(Node list) {
        StringBuilder link = new StringBuilder();
        Node head = list;
        while (head != null) {
            link.append(head.value);
            head = head.next;
            if (head != null) link.append("->");
        }
        return link;
    }

    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }


    }

    public static SingleLinkedList.Node from(int[] arr) {
        if (arr == null) return null;
        SingleLinkedList.Node result = null;
        for (int i = arr.length - 1; i >= 0; i--) {
            SingleLinkedList.Node temp = new Node(arr[i]);
            temp.next = result;
            result = temp;
        }
        return result;
    }
}
