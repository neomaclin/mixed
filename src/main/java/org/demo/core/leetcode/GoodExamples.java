package org.demo.core.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class GoodExamples {

    public List<List<Integer>> threeNumSumToZeroOn2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);

        for (int k = nums.length-1; k >= 2; --k) {
            if (nums[k] < 0) break;
            int target = -nums[k], i = 0, j = k-1;
            while (i < j) {
                if (nums[i] + nums[j] == target) {
                    result.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    while (i < j && nums[i+1] == nums[i]) ++i;
                    while (i < j && nums[j-1] == nums[j]) --j;
                    ++i; --j;
                } else if (nums[i] + nums[j] < target) {
                    ++i;
                } else {
                    --j;
                }
            }
            while (k >= 2 && nums[k-1] == nums[k]) --k;
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
        HashMap<Integer, Integer> map = new HashMap <> ();
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
