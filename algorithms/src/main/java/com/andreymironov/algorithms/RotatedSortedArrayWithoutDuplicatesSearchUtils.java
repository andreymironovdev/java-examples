package com.andreymironov.algorithms;

public class RotatedSortedArrayWithoutDuplicatesSearchUtils {
    /**
     * @param nums   - rotated sorted array without duplicates, i.e. like [4, 5, 6, 7, 1, 2, 3]
     * @param target - item to search
     * @return index of target or -1 if array doesn't contain target
     */
    public static int search(int[] nums, int target) {
        int pivotIndex = searchPivotIndex(nums);

        if (pivotIndex == -1) {
            return BinarySearchUtils.search(nums, target);
        } else {
            int firstIndex = BinarySearchUtils.search(nums, target, 0, pivotIndex);
            if (firstIndex == -1) {
                return BinarySearchUtils.search(nums, target, pivotIndex + 1, nums.length - 1);
            } else {
                return firstIndex;
            }
        }
    }

    /**
     * @param nums   - rotated sorted array without duplicates, i.e. like [4, 5, 6, 7, 1, 2, 3]
     * @return pivot index, i.e. such i that nums[i] > nums[i+1]
     */
    public static int searchPivotIndex(int[] nums) {
        int start = 0, end = nums.length - 1;

        while (start <= end) {
            int current = start + (end - start) / 2;

            if (current < nums.length - 1 && nums[current] > nums[current + 1]) {
                return current;
            } else if (nums[current] > nums[end]) {
                start = current;
            } else if (nums[current] < nums[start]) {
                end = current;
            } else {
                return -1;
            }
        }

        return -1;
    }
}
