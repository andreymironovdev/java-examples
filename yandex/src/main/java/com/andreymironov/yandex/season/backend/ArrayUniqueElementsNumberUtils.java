package com.andreymironov.yandex.season.backend;

import java.io.*;
import java.util.*;

/**
 * Задан массив a размера n. Необходимо посчитать количество уникальных элементов в данном массиве.
 * Элемент называется уникальным, если встречается в массиве ровно один раз.
 */
public class ArrayUniqueElementsNumberUtils {
    public static void main(String[] args) throws IOException {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int n = Integer.parseInt(reader.readLine());
            int[] array = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            writer.write(String.valueOf(getUniqueElementsNumber(array)));
        }
    }

    public static long getUniqueElementsNumber(int[] array) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (int current : array) {
            frequencyMap.put(current, frequencyMap.getOrDefault(current, 0) + 1);
        }

        return frequencyMap.entrySet().stream()
                .filter(e-> e.getValue().equals(1))
                .map(Map.Entry::getKey)
                .count();
    }

}
