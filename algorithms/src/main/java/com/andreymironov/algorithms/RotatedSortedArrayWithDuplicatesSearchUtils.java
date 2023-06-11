package com.andreymironov.algorithms;

public class RotatedSortedArrayWithDuplicatesSearchUtils {
    public static boolean search(int[] nums, int target) {
        int pivotIndex = searchPivotIndex(nums);

        if (pivotIndex == -1) {
            return -1 != BinarySearchUtils.search(nums, target);
        } else {
            return BinarySearchUtils.search(nums, target, 0, pivotIndex) != -1
                    || BinarySearchUtils.search(nums, target, pivotIndex + 1, nums.length - 1) != -1;
        }
    }

    public static int searchPivotIndex(int[] nums) {
        int start = 0, end = nums.length - 1;

        while (start <= end) {
            int middle = start + (end - start) / 2;
            int startValue = nums[start];
            int middleValue = nums[middle];
            int endValue = nums[end];

            if (startValue > middleValue || middleValue < endValue) {
                end = middle - 1;
            } else if (middleValue > endValue || startValue < middleValue) {
                start = middle;
            } else {
                // startValue == middleValue == endValuez

            }
        }

        if (nums[start] > nums[start + 1]) {
            return start;
        } else {
            return -1;
        }
    }

    public static int searchPivotIndexRecursively(int[] nums) {
        return searchPivotIndexRecursively(nums, 0, nums.length - 1);
    }

    public static int searchPivotIndexRecursively(int[] nums, int start, int end) {
        if (start >= end) {
            if (nums[start] > nums[start + 1]) {
                return start;
            } else {
                return -1;
            }
        }

        int middle = start + (end - start) / 2;
        int startValue = nums[start];
        int middleValue = nums[middle];
        int endValue = nums[end];

        if (startValue > middleValue || middleValue < endValue) {
            return searchPivotIndexRecursively(nums, start, middle - 1);
        } else if (middleValue > endValue || startValue < middleValue) {
            return searchPivotIndexRecursively(nums, middle, end);
        } else {
            int left = searchPivotIndexRecursively(nums, start, middle - 1);

            if (left == -1) {
                return searchPivotIndexRecursively(nums, middle, end);
            }
        }

        return -1;
    }
}
