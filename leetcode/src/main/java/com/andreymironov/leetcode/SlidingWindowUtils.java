package com.andreymironov.leetcode;

import com.andreymironov.leetcode.domain.slidingwindow.ValueWithIndex;

import java.util.Arrays;
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
        int n = nums.length;
        double[] medians = new double[n - k + 1];

        if (k == 1) {
            for (int i = 0; i < n; i++) {
                medians[i] = nums[i];
            }

            return medians;
        }

        ValueWithIndex[] values = new ValueWithIndex[n];
        TreeSet<ValueWithIndex> minSet = new TreeSet<>();
        TreeSet<ValueWithIndex> maxSet = new TreeSet<>();

        // prefill minSet and maxSet
        int maxMinSetSize = k % 2 == 0 ? k / 2 : (k + 1) / 2;
        for (int i = 0; i < k; i++) {
            ValueWithIndex currentValue = new ValueWithIndex(nums[i], i);
            values[i] = currentValue;

            if (i < maxMinSetSize) {
                minSet.add(currentValue);
            } else {
                if (currentValue.compareTo(minSet.last()) > 0) {
                    maxSet.add(currentValue);
                } else {
                    maxSet.add(minSet.pollLast());
                    minSet.add(currentValue);
                }
            }
        }

        for (int i = 0; i < n - k + 1; i++) {
            double middle = k % 2 == 0
                    ? 0.5 * minSet.last().value() + 0.5 * maxSet.first().value()
                    : minSet.last().value();
            medians[i] = middle;

            if (i < n - k) {
                //remove left value and insert right value
                ValueWithIndex leftValue = values[i];
                ValueWithIndex rightValue = new ValueWithIndex(nums[i + k], i + k);
                values[i + k] = rightValue;

                if (leftValue.compareTo(minSet.last()) > 0) {
                    maxSet.remove(leftValue);
                    if (rightValue.compareTo(minSet.last()) > 0) {
                        maxSet.add(rightValue);
                    } else {
                        maxSet.add(minSet.pollLast());
                        minSet.add(rightValue);
                    }
                } else {
                    minSet.remove(leftValue);
                    if (rightValue.compareTo(maxSet.first()) < 0) {
                        minSet.add(rightValue);
                    } else {
                        minSet.add(maxSet.pollFirst());
                        maxSet.add(rightValue);
                    }
                }
            }
        }

        return medians;
    }

    /**
     * You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.
     * Return the max sliding window.
     * Constraints:
     * 1 <= nums.length <= 10^5
     * -10^4 <= nums[i] <= 10^4
     * 1 <= k <= nums.length
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] max = new int[n - k + 1];
        ValueWithIndex[] values = new ValueWithIndex[n];
        TreeSet<ValueWithIndex> windowSet = new TreeSet<>();

        for (int i = 0; i < k; i++) {
            ValueWithIndex currentValue = new ValueWithIndex(nums[i], i);
            values[i] = currentValue;
            windowSet.add(currentValue);
        }

        for (int i = 0; i < n - k + 1; i++) {
            max[i] = windowSet.last().value();

            if (i < n - k) {
                windowSet.remove(values[i]);
                ValueWithIndex rightValue = new ValueWithIndex(nums[i + k], i + k);
                values[i + k] = rightValue;
                windowSet.add(rightValue);
            }
        }

        return max;
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
        int result = 0;

        int[] frequencies = new int[26];
        int maxFrequency = 0;

        int start = 0;

        for (int end = 0; end < s.length(); end++) {
            System.out.println(String.format("%s, %s, %s, %s", start, end, maxFrequency, Arrays.toString(frequencies)));
            int endCharIndex = s.charAt(end) - 'A';
            frequencies[endCharIndex] += 1;
            maxFrequency = Math.max(maxFrequency, frequencies[endCharIndex]);

            int windowLength = end + 1 - start;
            if (windowLength - maxFrequency > k) {
                int startCharIndex = s.charAt(start) - 'A';
                frequencies[startCharIndex]--;
                start++;
            }

            result = end + 1 - start;
        }

        return result;
    }
}
