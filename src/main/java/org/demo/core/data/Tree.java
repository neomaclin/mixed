package org.demo.core.data;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Tree<Key extends Comparable<Key>, Value> {
    //  //  private ConcurrentHashMap ccMap;
    //  //  private HashMap hmp;
    private Node root;
    private enum Color {Red, Black}
    public class Node{
        private Key key;
        private Value value;
        private Color color;
        private long nodeCount;
        private Node left;
        private Node right;
        Node(Key key, Value value, long nodeCount, Color color) {
            this.color= color;
            this.key = key;
            this.value = value;
            this.nodeCount = nodeCount;
        }
        public boolean isRed(){
            return this.color == Color.Red;
        }
        public boolean isBlack(){
            return !isRed();
        }
//        public void setLeft(Node node){
//            this.left = node;
//        }
//        public void setRight(Node node){
//            this.right = node;
//        }

    }
    private boolean isRed(Node node){
        return node != null && node.isRed();
    }

    private Node redNode(Key key, Value value, long nodeCount)  {
          return  new Node(key,value,nodeCount, Color.Red);
    }
    private Node blackNode(Key key, Value value, long nodeCount)  {
        return  new Node(key,value,nodeCount, Color.Black);
    }
    private Node rotateRight(Node subtree){
        Node flipping = subtree.left;
        subtree.left = flipping.right;
        flipping.right = subtree;
        flipping.color = subtree.color;
        subtree.nodeCount = flipping.nodeCount;
        flipping.nodeCount = 1 + subtree.left.nodeCount + subtree.right.nodeCount;
        return flipping;
    }

    private Node rotateLeft(Node subtree){
        Node flipping = subtree.right;
        subtree.right = flipping.left;
        flipping.left = subtree;
        flipping.color = subtree.color;
        subtree.nodeCount = flipping.nodeCount;
        flipping.nodeCount = 1 + subtree.left.nodeCount + subtree.right.nodeCount;
        return flipping;
    }

    public void put(Key key, Value value) {
        root = insert(root, key,value);
        root.color = Color.Black;
    }

    private void flipColors(Node node){
        node.color = Color.Red;
        node.left.color =  node.right.color = Color.Black;
    }

    private Node insert(Node node, Key key, Value value){
        if(node == null) return redNode(key,value,0);
        else {
            //update nodes
            int cmp = key.compareTo(node.key);
            if (cmp < 0) node.left  = insert(node.left,  key, value);
            else if (cmp > 0) node.right = insert(node.right, key, value);
            else node.value = value;

            //rotate
            if (isRed(node.right) && !isRed(node.left))    node = rotateLeft(node);
            if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
            if (isRed(node.left) && isRed(node.right))     flipColors(node);

            node.nodeCount = node.left.nodeCount + node.right.nodeCount + 1;
            return  node;
        }
    }
//    public void delete(Key key){}
//    public  Value get(Key key)
//        private Tree rotateLeft(
//
//        )
//            private Tree rotateRight


}
