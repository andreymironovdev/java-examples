package com.andreymironov.algorithms;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class IntervalUtils {
    /**
     * @param intervals array of possibly overlapping intervals
     * @return array of merged intervals
     */
    public static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        LinkedList<int[]> result = new LinkedList<>();

        for (int i = 0; i < intervals.length; i++) {
            int[] current = intervals[i];

            if (result.isEmpty() || result.getLast()[1] < current[0]) {
                result.add(current);
            } else if (result.getLast()[1] < current[1]) {
                result.getLast()[1] = current[1];
            }
        }

        return result.toArray(new int[][]{});
    }
}
