package com.andreymironov.leetcode;

import java.util.TreeSet;

public class SlidingWindowUtils {
    /**
     * The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle values.
     * <p>
     * For examples, if arr = [2,3,4], the median is 3.
     * For examples, if arr = [1,2,3,4], the median is (2 + 3) / 2 = 2.5.
     * You are given an integer array nums and an integer k. There is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.
     * <p>
     * Return the median array for each window in the original array. Answers within 10-5 of the actual value will be accepted.
     * Constraints:
     * <p>
     * 1 <= k <= nums.length <= 10^5
     * -2^31 <= nums[i] <= 2^31 - 1
     * Complexity is O(n*k)
     */
    public static double[] medianSlidingWindow(int[] nums, int k) {
        double[] medians = new double[nums.length - k + 1];

        TreeSet<IntegerWithIndex> windowSet = new TreeSet<>();

        for (int i = 0; i < k - 1; i++) {
            windowSet.add(new IntegerWithIndex(nums[i], i));
        }


        for (int i = 0; i < nums.length - k + 1; i++) {
            windowSet.add(new IntegerWithIndex(nums[i + k - 1], i + k - 1));
            double middle = k % 2 == 0
                    ? windowSet.stream().skip((k - 1) / 2).limit(2)
                    .mapToInt(v -> v.value).average().orElse(0)
                    : windowSet.stream().skip(k / 2).map(v -> v.value).findFirst().orElse(0);
            medians[i] = middle;
            windowSet.remove(new IntegerWithIndex(nums[i], i));
        }

        return medians;
    }

    private static class IntegerWithIndex implements Comparable<IntegerWithIndex> {
        int value;
        int index;

        public IntegerWithIndex(int value, int index) {
            this.value = value;
            this.index = index;
        }

        @Override
        public int compareTo(IntegerWithIndex o) {
            return this.value == o.value
                    ? this.index - o.index
                    : this.value > o.value ? 1 : -1;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof IntegerWithIndex another)) {
                return false;
            }

            return this.value == another.value && this.index == another.index;
        }
    }

    /**
     * You are given a string s and an integer k. You can choose any character of the string and change it to any other uppercase English character.
     * You can perform this operation at most k times.
     * Return the length of the longest substring containing the same letter you can get after performing the above operations.
     * Constraints:
     * 1 <= s.length <= 10^5
     * s consists of only uppercase English letters.
     * 0 <= k <= s.length
     */
    public static int characterReplacement(String s, int k) {
        //todo
        return 0;
    }
}
