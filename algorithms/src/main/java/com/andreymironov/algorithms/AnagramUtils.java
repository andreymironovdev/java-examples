package com.andreymironov.algorithms;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AnagramUtils {
    /**
     * @param strs array of strings, each of them consists of lowercase english characters
     * @return groups of anagram strings from the input array
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<Integer, List<String>> map = Arrays.stream(strs)
                .collect(Collectors.groupingBy(AnagramUtils::hashCodeWithCollisionsOnAnagrams));

        return map.values().stream().toList();
    }

    private static int hashCodeWithCollisionsOnAnagrams(String row) {
        if (row == null) {
            return 0;
        }
        return row.chars()
                .sorted()
                .reduce(0, ((left, right) -> 31 * left + right));
    }

    /**
     * @param s - string consists of lowercase english characters
     * @param t - string consists of lowercase english characters
     * @return true, if t is anagram of s
     */
    public static boolean isAnagram(String s, String t) {
        int[] sFrequency = new int[26];
        int[] tFrequency = new int[26];
        s.chars().forEach(ch -> sFrequency[ch - 'a']++);
        t.chars().forEach(ch -> tFrequency[ch - 'a']++);
        return IntStream.range(0, 26).noneMatch(i -> sFrequency[i] != tFrequency[i]);
    }
}
