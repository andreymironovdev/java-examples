package com.andreymironov.algorithms;

public class RotatedSortedArrayWithDuplicatesSearchUtils {
    public static int searchPivotIndex(int[] nums) {
        return searchPivotIndex(nums, 0, nums.length - 1);
    }

    public static int searchPivotIndex(int[] nums, int start, int end) {
        if (start < nums.length - 1 && nums[start] > nums[start + 1]) {
            return start;
        }

        int mid = start + (end - start) / 2;

        if (nums[mid] > nums[end]) {
            return searchPivotIndex(nums, mid, end);
        } else if (nums[mid] < nums[start]) {
            return searchPivotIndex(nums, mid, end);
        } else if (nums[mid] == nums[start] && nums[mid] == nums[end]) {
            int left = searchPivotIndex(nums, start, mid - 1);

            if (left == -1) {
                return searchPivotIndex(nums, mid + 1, end);
            } else {
                return left;
            }
        } else {
            return -1;
        }
    }

    public static boolean search(int[] nums, int target) {
        int pivotIndex = searchPivotIndex(nums, 0, nums.length - 1) + 1;

        if (pivotIndex == -1) {
            return -1 != SortedArraySearchUtils.binarySearch(nums, target, 0, nums.length - 1);
        } else {
            int firstIndex = SortedArraySearchUtils.binarySearch(nums, target, 0, pivotIndex - 1);
            if (firstIndex == -1) {
                return -1 != SortedArraySearchUtils.binarySearch(nums, target, pivotIndex, nums.length - 1);
            } else {
                return true;
            }
        }
    }
}
