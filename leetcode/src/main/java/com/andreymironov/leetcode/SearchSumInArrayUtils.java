package com.andreymironov.leetcode;

import java.util.*;

public class SearchSumInArrayUtils {

    public static int[] searchIndexesOf2Sum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            Integer anotherIndex = map.get(target - nums[i]);
            if (anotherIndex != null) {
                return new int[]{i, anotherIndex};
            } else {
                map.put(nums[i], i);
            }
        }

        return null;
    }

    public static List<List<Integer>> search4Sums(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();

        int n = nums.length;

        if (n < 4) {
            return result;
        }

        Arrays.sort(nums);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int a = nums[i], b = nums[j];
                long sum = (long) target - a - b;
                int start = j + 1, end = n - 1;
                while (start < end) {
                    int c = nums[start], d = nums[end];

                    long partialSum = c + d;
                    if (partialSum == sum) {
                        result.add(List.of(a, b, c, d));

                        //skip duplicates for start
                        do {
                            start++;
                        } while (start < end && nums[start] == nums[start - 1]);

                        //skip duplicates for end
                        do {
                            end--;
                        } while (start < end && nums[end] == nums[end + 1]);
                    } else if (partialSum < sum) {
                        start++;
                    } else {
                        // c + d > sum
                        end--;
                    }
                }

                //skip duplicates for j
                while (j < n - 1 && nums[j] == nums[j + 1]) {
                    j++;
                }
            }

            //skip duplicates for i
            while (i < n -1 && nums[i] == nums[i + 1]) {
                i++;
            }
        }

        return result;
    }
}
