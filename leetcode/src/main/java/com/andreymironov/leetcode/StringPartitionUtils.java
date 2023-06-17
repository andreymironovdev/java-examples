package com.andreymironov.leetcode;

import java.util.*;

public class StringPartitionUtils {
    /**
     * @param s string of lowercase english letters, which needs to be splitted into parts
     *          , such that each letter is presented in at most one part
     * @return list of parts sizes
     */
    public static List<Integer> splitStringWithSymbolsUniqueness(String s) {
        if (s == null || s.length() == 0) {
            return Collections.emptyList();
        }

        Map<Character, int[]> firstAndLastPositions = new HashMap();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int[] positions = firstAndLastPositions.getOrDefault(c, new int[]{i, i});
            positions[1] = i;
            firstAndLastPositions.put(c, positions);
        }

        List<int[]> intervals = firstAndLastPositions.values().stream()
                .sorted(Comparator.comparingInt(a -> a[0]))
                .toList();

        LinkedList<int[]> mergedIntervals = new LinkedList<>();

        intervals.forEach(interval -> {
            if (mergedIntervals.isEmpty()) {
                mergedIntervals.add(interval);
            } else {
                int[] last = mergedIntervals.getLast();
                if (last[1] >= interval[0] && last[1] < interval[1]) {
                    last[1] = interval[1];
                } else if (last[1] < interval[0]) {
                    mergedIntervals.add(interval);
                }
            }
        });

        return mergedIntervals.stream().map(a -> a[1] - a[0] + 1).toList();
    }
}
