package com.andreymironov.yandex;

import java.util.*;
//TODO
public class BracketSequenceGenerationUtils {
    public static void main(String[] args) {
    }

    private static Set<String> getbracketSequences(int n) {
        if (n == 0) {
            return Collections.emptySet();
        } else if (n == 1) {
            return Set.of("()");
        } else {
            Set<String> result = new TreeSet<>();

            Map<Integer, Set<String>> seqsMap = new HashMap<>();

            for (int i = 0; i < n; i++) {
                Set<String> seqStart = seqsMap.getOrDefault(n - i - 1, getbracketSequences(n - i - 1));
                seqsMap.put(n - i - 1, seqStart);
                Set<String> seqEnd = seqsMap.getOrDefault(i, getbracketSequences(i));
                seqsMap.put(i, seqEnd);
                if (seqStart.isEmpty()) {
                    seqEnd.forEach(s2 -> result.add("(" + ")" + s2));
                } else if (seqEnd.isEmpty()) {
                    seqStart.forEach(s1 -> result.add("(" + s1 + ")"));
                } else {
                    seqStart.forEach(s1 -> seqEnd.forEach(s2 -> result.add("(" + s1 + ")" + s2)));
                }
            }

            return result;
        }
    }
}
