package org.demo.core.leetcode;

import java.util.*;


public class GoodExamples {
    public interface NestedInteger {

             // @return true if this NestedInteger holds a single integer, rather than a nested list.
             public boolean isInteger();

             // @return the single integer that this NestedInteger holds, if it holds a single integer
              // Return null if this NestedInteger holds a nested list
              public Integer getInteger();

              public List<NestedInteger> getList();
  }

//
//    public int[] rearrangeBarcodes(int[] barcodes) {
//        Map<Integer, Integer> cnt = new TreeMap<>();
//        for (int i : barcodes) cnt.put(i, cnt.getOrDefault(i, 0) + 1);
//         List<Map.Entry<Integer, Integer>> list = new ArrayList<>(cnt.entrySet());
//        Collections.sort(list, Map.Entry.<Integer, Integer>comparingByValue().reversed());
//
//        int l = barcodes.length, i = 0;
//        int[] res = new int[l];
//        for (Map.Entry<Integer, Integer> e : list){
//            int time = e.getValue();
//            while (time-- > 0){
//                res[i] = e.getKey();
//                i += 2;
//                if (i >= barcodes.length) i = 1;
//            }
//        }
//        return res;
//    }
    public class NestedIterator implements Iterator<Integer> {
        Queue<Integer> queue = new LinkedList<>();
        Iterator<Integer> it = null;
        private void enqueue(NestedInteger nested) {
            if (nested.isInteger()) {
                queue.add(nested.getInteger());
            } else {
                nested.getList().forEach(this::enqueue);

            }


        }
        public NestedIterator(List<NestedInteger> nestedList) {
            nestedList.forEach(this::enqueue);
            it = queue.iterator();
        }

        @Override
        public Integer next() {
            return it.next();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }
    }
    public class TrieNode {
        public TrieNode[] children = new TrieNode[26];
        public boolean isWord;
    }

    private TrieNode root = new TrieNode();

    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) {
                node.children[c - 'a'] = new TrieNode();
            }
            node = node.children[c - 'a'];
        }
        node.isWord = true;
    }

    public boolean search(String word) {
        return match(word.toCharArray(), 0, root);
    }

    private boolean match(char[] chs, int k, TrieNode node) {
        if (k == chs.length) return node.isWord;
        if (chs[k] != '.') {
            return node.children[chs[k] - 'a'] != null && match(chs, k + 1, node.children[chs[k] - 'a']);
        } else {
            for (int i = 0; i < node.children.length; i++) {
                if (node.children[i] != null) {
                    if (match(chs, k + 1, node.children[i])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<TreeNode> allPossibleFBT(int N) {

        if (N <= 0) {
            return new ArrayList<>();
        }

        List<TreeNode>[] dp = new List[N + 1];
        for (int i = 0; i <= N; i++) {
            dp[i] = new ArrayList<>();
        }
        dp[1].add(new TreeNode(0));

        for (int numNode = 1; numNode <= N; numNode += 2) {
            for (int leftNode = 1; leftNode < numNode; leftNode += 2) {
                for (TreeNode left : dp[leftNode]) {
                    for (TreeNode right : dp[numNode - 1 - leftNode]) {
                        TreeNode root = new TreeNode(0);
                        root.left = left;
                        root.right = right;
                        dp[numNode].add(root);
                    }
                }
            }
        }
        return dp[N];
    }


    public TreeNode build(int count) {
        if (count < 0) return null;
        else if (count == 1) return new TreeNode(0);
        else {
            TreeNode node = new TreeNode(0);
            node.left = new TreeNode(0);
            node.right = new TreeNode(0);
            return node;
        }
    }

    public TreeNode deepCopy(TreeNode node) {
        if (node == null) return null;
        else {
            TreeNode newNode = new TreeNode(0);
            newNode.left = deepCopy(node.left);
            newNode.right = deepCopy(node.right);
            return newNode;
        }
    }

    public List<List<Integer>> threeNumSumToZeroOn2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);

        for (int k = nums.length - 1; k >= 2; --k) {
            if (nums[k] < 0) break;
            int target = -nums[k], i = 0, j = k - 1;
            while (i < j) {
                if (nums[i] + nums[j] == target) {
                    result.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    while (i < j && nums[i + 1] == nums[i]) ++i;
                    while (i < j && nums[j - 1] == nums[j]) --j;
                    ++i;
                    --j;
                } else if (nums[i] + nums[j] < target) {
                    ++i;
                } else {
                    --j;
                }
            }
            while (k >= 2 && nums[k - 1] == nums[k]) --k;
        }

        return result;
    }

    public int maxScoreSightseeingPair(int[] A) {
        int maxScore = 0, distance = 0;
        int lastBestValue = A[distance];
        while (++distance < A.length) {
            int currSightValue = A[distance];
            maxScore = Math.max(maxScore, lastBestValue + currSightValue - distance);
            lastBestValue = Math.max(lastBestValue, currSightValue + distance);

        }
        return maxScore;
    }


    public int subarraySum(int[] nums, int k) {
        int count = 0, sum = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k))
                count += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

//    public int numSubarraysWithSum(int[] A, int S) {
//        int psum = 0, res = 0;
//        int[] lastSumCount = new int[A.length + 1];
//
//        lastSumCount[0] = 1;
//        for (int i : A) {
//            psum += i;
//            if (psum >= S)
//                res += lastSumCount[psum - S]; //revisiting last "sum index"
//            lastSumCount[psum]++;
//        }
//        return res;
//    }

    public int[] asteroidCollision(int[] asteroids) {
        if (asteroids == null || asteroids.length == 0) return asteroids;
        int prev = 0;
        int curr = 1;
        int n = asteroids.length;
        while (curr < n) {
            if (prev == -1 || asteroids[curr] > 0 || asteroids[prev] < 0) {
                asteroids[++prev] = asteroids[curr++];
            } else {
                int fromLeft = asteroids[prev];
                int fromRight = asteroids[curr];
                if (fromLeft + fromRight == 0) {
                    // explore on impact if the same mass
                    prev--; // mark one out of the array from left
                    curr++; // mark one out of the array from right
                } else if (Math.abs(fromLeft) > Math.abs(fromRight)) {
                    curr++;
                } else {
                    prev--;
                }
            }

        }

        return Arrays.copyOf(asteroids, prev + 1);

    }
}
