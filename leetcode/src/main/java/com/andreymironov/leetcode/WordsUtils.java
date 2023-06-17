package com.andreymironov.leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class WordsUtils {
    /**
     * @param words array of words
     * @param k     parameter for top
     * @return top k frequent words from array
     */
    public static List<String> topKFrequentUsingPriorityQueue(String[] words, int k) {
        Map<String, Integer> frequency = new HashMap<>();

        for (int i = 0; i < words.length; i++) {
            String current = words[i];
            frequency.put(current, frequency.getOrDefault(current, 0) + 1);
        }

        Comparator<Map.Entry<String, Integer>> comparator = (e1, e2) -> e1.getValue().equals(e2.getValue())
                ? -e1.getKey().compareTo(e2.getKey())
                : e1.getValue().compareTo(e2.getValue());

        PriorityQueue<Map.Entry<String, Integer>> priorityQueue = new PriorityQueue<>(comparator);

        frequency.entrySet().forEach(e -> {
            priorityQueue.offer(e);
            if (priorityQueue.size() > k) {
                priorityQueue.poll();
            }
        });

        return priorityQueue.stream()
                .sorted(comparator.reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> frequency = new HashMap<>();

        for (int i = 0; i < words.length; i++) {
            String current = words[i];
            if (frequency.containsKey(current)) {
                frequency.put(current, frequency.get(current) + 1);
            } else {
                frequency.put(current, 1);
            }
        }

        return frequency.entrySet().stream()
                .sorted((e1, e2) -> e1.getValue().equals(e2.getValue())
                        ? e1.getKey().compareTo(e2.getKey())
                        : -e1.getValue().compareTo(e2.getValue())
                )
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
