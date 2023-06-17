package com.andreymironov.leetcode;

public class WaterContainerUtils {
    /**
     * @param heights array of heights of columns with the bottom at (i, 0), where i is an index of column
     * @return
     */
    public static int findMaxWaterArea(int[] heights) {
        int n = heights.length;

        if (n == 0) {
            return 0;
        }

        int start = 0, end = n - 1, maxArea = 0;

        while (start <= end) {
            int minHeight = Math.min(heights[start], heights[end]);
            maxArea = Math.max(maxArea, (end - start) * minHeight);

            if (minHeight == heights[start]) {
                start++;
            } else {
                end--;
            }
        }

        return maxArea;
    }
}
