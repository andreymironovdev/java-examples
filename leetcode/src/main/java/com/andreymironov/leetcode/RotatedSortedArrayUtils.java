package com.andreymironov.leetcode;

public class RotatedSortedArrayUtils {
    /**
     * @param nums   - rotated sorted array with duplicates possible, i.e. like [4, 5, 6, 7, 1, 3, 3, 4]
     * @param target - item to search
     * @return index of target or -1 if array doesn't contain target
     */
    public static boolean searchWithDuplicates(int[] nums, int target) {
        int n = nums.length;

        if (n == 0) {
            return false;
        }

        int start = 0, end = n - 1;

        while (start <= end) {
            int middle = start + (end - start) / 2;

            if (nums[middle] == target) {
                return true;
            }

            if (nums[start] == nums[middle]) {
                start++;
                continue;
            }

            boolean middleBeforePivot = nums[start] < nums[middle];
            boolean targetBeforePivot = nums[start] <= target;

            if (middleBeforePivot && targetBeforePivot || !middleBeforePivot && !targetBeforePivot) {
                // middle and target are in the same sorted array
                if (nums[middle] < target) {
                    start = middle + 1;
                } else {
                    end = middle - 1;
                }
            } else {
                // middle and target are in the different sorted arrays
                if (middleBeforePivot) {
                    start = middle + 1;
                } else {
                    end = middle - 1;
                }
            }
        }

        return false;
    }

    /**
     * @param nums   - rotated sorted array without duplicates, i.e. like [4, 5, 6, 7, 1, 2, 3]
     * @param target - item to search
     * @return index of target or -1 if array doesn't contain target
     */
    public static int searchWithoutDuplicates(int[] nums, int target) {
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
     * @param nums - rotated sorted array without duplicates, i.e. like [4, 5, 6, 7, 1, 2, 3]
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
