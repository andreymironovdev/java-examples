package com.andreymironov.algorithms;

public class SortedArraySearchUtils {
    /**
     * @param nums - sorted array
     * @param target - item to search
     * @return index of target or -1 if array doesn't contain target
     */
    public static int binarySearch(int[] nums, int target) {
        return binarySearch(nums, target, 0, nums.length - 1);
    }

    public static int binarySearch(int[] nums, int target, int start, int end) {
        while (start <= end) {
            int current = start + (end - start) / 2;
            if (nums[current] < target) {
                start = current + 1;
            } else if (nums[current] > target) {
                end = current - 1;
            } else {
                return current;
            }
        }

        return -1;
    }
}
