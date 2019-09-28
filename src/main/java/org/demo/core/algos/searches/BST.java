package org.demo.core.algos.searches;

import java.util.*;

public class BST {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    //BFS
    public List<Integer> rightSideView(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        List<Integer> result = new ArrayList<>();
        Stack<Integer> level = new Stack<>();
        while (!queue.isEmpty()) {
            int l = queue.size();
            level.clear();
            for (int i = 0; i < l; i++) {
                TreeNode node = queue.poll();
                if (node != null) {
                    level.push(node.val);
                    queue.add(node.left);
                    queue.add(node.right);
                }
            }
            if (!level.isEmpty()) {
                result.add(level.pop());
            }
        }
        return result;
    }

    //DFS
    public int depth(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        Stack<Integer> value = new Stack<>();
        List<Integer> values = new LinkedList<>();
        value.push(1);
        int depth = 0;
        while(!stack.isEmpty()) {
            TreeNode node = stack.pop();
            int temp = value.pop();
            depth = Math.max(temp, depth);
            if(node.left != null) {
                stack.push(node.left);
                value.push(temp+1);
            }
            if(node.right != null) {
                stack.push(node.right);
                value.push(temp+1);
            }
            values.add(node.val);
        }
        return depth;



    }
}
