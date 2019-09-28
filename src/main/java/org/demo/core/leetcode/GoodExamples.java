package org.demo.core.leetcode;

import java.util.Arrays;
import java.util.HashMap;


public class GoodExamples {

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

    public int numSubarraysWithSum(int[] A, int S) {
        int psum = 0, res = 0;
        int[] lastSumCount = new int[A.length + 1];

        lastSumCount[0] = 1;
        for (int i : A) {
            psum += i;
            if (psum >= S)
                res += lastSumCount[psum - S]; //revisiting last "sum index"
            lastSumCount[psum]++;
        }
        return res;
    }

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
