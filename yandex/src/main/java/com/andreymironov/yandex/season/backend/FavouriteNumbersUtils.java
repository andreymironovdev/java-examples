package com.andreymironov.yandex.season.backend;

import java.io.*;
import java.util.Arrays;

/**
 * У Саши есть три любимых числа: 5,6,10.
 * Кроме них, у Саши есть число N, не содержащее нулей.
 * Он хочет сделать так, чтобы оно делилось хотя бы на одно из его любимых чисел. Для этого он
 * K раз выполняет следующее: случайно выбирает две цифры, стоящие на разных позициях, и меняет их местами.
 * Помогите Саше найти вероятность того, что итоговое число будет делиться хотя бы на одно из любимых чисел.
 */
public class FavouriteNumbersUtils {
    public static void main(String[] args) throws IOException {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            int n = Integer.parseInt(reader.readLine());
            int k = Integer.parseInt(reader.readLine());
            writer.write(String.valueOf(getProbability(n, k)));
        }
    }

    public static double getProbability(int number, int operations) {
        if (operations == 0) {
            return number % 6 == 0 || number % 5 == 0 ? 1.0 : 0.0;
        }

        // number after operations can divide 5, 6 only
        // number after operations can divide 5 if and only if the number contains 5 cipher
        // number after operations can divide 6 if and only if sum of ciphers divides 3 and number contains even cipher

        int[] ciphers = String.valueOf(number).chars().map(i -> i - '0').toArray();
        int numLength = ciphers.length;

        long countOf5 = Arrays.stream(ciphers).filter(i -> i == 5).count();
        boolean divide3 = Arrays.stream(ciphers).sum() % 3 == 0;
        long countOf2 = Arrays.stream(ciphers).filter(i -> i % 2 == 0).count();
        boolean canDivide5 = countOf5 > 0;
        boolean canDivide2 = countOf2 > 0;
        boolean canDivide6 = canDivide2 && divide3;

        if (!canDivide5 && !canDivide6) {
            return 0.0;
        }

        int[][] transpositions = new int[numLength * (numLength - 1) / 2][2];
        int start = 0, end = 1;
        for (int i = 0; i < transpositions.length; i++) {
            transpositions[i] = new int[]{start, end};
            if (end < numLength - 1) {
                end++;
            } else {
                start++;
                end = start + 1;
            }
        }

        long successCasesCount = 0;
        long allCasesCount = (long) Math.pow(transpositions.length, operations);

        if (canDivide5 && !canDivide6) {
            //find probability that the number after operations ends with 5
            int[] transpositionIndexes = new int[operations];
            while (transpositionIndexes != null) {
                if (getNumberAfterTranspositions(ciphers, transpositionIndexes, transpositions) % 5 == 0) {
                    successCasesCount++;
                }
                transpositionIndexes = increment(transpositionIndexes, transpositions.length - 1);
            }
        } else if (!canDivide5) {
            //find probability that the number after operations ends with even cipher
            int[] transpositionIndexes = new int[operations];
            while (transpositionIndexes != null) {
                if (getNumberAfterTranspositions(ciphers, transpositionIndexes, transpositions) % 2 == 0) {
                    successCasesCount++;
                }
                transpositionIndexes = increment(transpositionIndexes, transpositions.length- 1);
            }
        } else {
            //find probability that the number after operations ends with even cipher or 5
            int[] transpositionIndexes = new int[operations];
            while (transpositionIndexes != null) {
                int numberAfterTranspositions = getNumberAfterTranspositions(ciphers, transpositionIndexes,
                        transpositions);
                if (numberAfterTranspositions % 2 == 0 || numberAfterTranspositions % 5 == 0) {
                    successCasesCount++;
                }
                transpositionIndexes = increment(transpositionIndexes, transpositions.length - 1);
            }
        }

        return (double) successCasesCount / allCasesCount;
    }

    private static int[] increment(int[] transpositionIndexes, int maxValue) {
        int index = transpositionIndexes.length - 1;
        while (index >= 0) {
            if (transpositionIndexes[index] == maxValue) {
                transpositionIndexes[index] = 0;
                index--;
            } else {
                transpositionIndexes[index] = transpositionIndexes[index] + 1;
                break;
            }
        }

        if (index < 0) {
            return null;
        } else {
            return transpositionIndexes;
        }
    }

    private static int getNumberAfterTranspositions(int[] ciphers, int[] transpositionIndexes, int[][] transpositions) {
        int[] ciphersCopy = Arrays.copyOf(ciphers, ciphers.length);
        for (int index : transpositionIndexes) {
            int[] transposition = transpositions[index];
            int k = ciphersCopy[transposition[0]];
            ciphersCopy[transposition[0]] = ciphersCopy[transposition[1]];
            ciphersCopy[transposition[1]] = k;
        }
        int result = 0;
        for (int i = 0; i < ciphersCopy.length; i++) {
            result += Math.pow(10, ciphersCopy.length - i - 1) * ciphersCopy[i];
        }
        return result;
    }
}
