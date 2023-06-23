package com.andreymironov.yandex.season.backend;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.stream.IntStream;

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
            BigInteger number = new BigInteger(reader.readLine());
            int operations = Integer.parseInt(reader.readLine());
            writer.write(String.valueOf(getProbability(number, operations)));
        }
    }

    public static BigDecimal getProbability(BigInteger number, int operations) {
        if (operations == 0) {
            return number.remainder(BigInteger.valueOf(6)).equals(BigInteger.ZERO) || number.remainder(
                    BigInteger.valueOf(5)).equals(BigInteger.ZERO)
                    ? BigDecimal.ONE
                    : BigDecimal.ZERO;
        }

        // number after operations can divide 5, 6 only
        // number after operations can divide 5 if and only if the number contains 5 cipher
        // number after operations can divide 6 if and only if sum of ciphers divides 3 and number contains even cipher

        int[] ciphers = String.valueOf(number).chars().map(i -> i - '0').toArray();
        int numLength = ciphers.length;

        int[] indexesOf5 = IntStream.range(0, ciphers.length).filter(i -> ciphers[i] == 5).toArray();
        boolean divide3 = Arrays.stream(ciphers).sum() % 3 == 0;
        int[] indexesOfEven = IntStream.range(0, ciphers.length).filter(i -> ciphers[i] % 2 == 0).toArray();
        boolean canDivide5 = indexesOf5.length > 0;
        boolean canDivide2 = indexesOfEven.length > 0;
        boolean canDivide6 = canDivide2 && divide3;

        if (!canDivide5 && !canDivide6) {
            return BigDecimal.ZERO;
        }

        BigInteger successWaysCount = BigInteger.ZERO;
        BigInteger allWaysCount = BigInteger.valueOf((long) numLength * (numLength - 1) / 2).pow(operations);

        // waysCount[op][i][j] = number of ways to move the cipher from the i-th position to the j-th position in op operations
        BigInteger[][][] waysCount = new BigInteger[operations + 1][numLength][numLength];
        for (int op = 0; op < operations + 1; op++) {
            for (int i = 0; i < numLength; i++) {
                for (int j = 0; j < numLength; j++) {
                    if (op == 0) {
                        waysCount[op][i][j] = i == j ? BigInteger.ONE : BigInteger.ZERO;
                    } else {
                        int finalJ = j;
                        int finalOp = op;
                        int finalI = i;
                        waysCount[op][i][j] = IntStream.range(0, numLength)
                                .filter(ind -> ind != finalJ)
                                .mapToObj(ind -> waysCount[finalOp - 1][finalI][ind])
                                .reduce(BigInteger.ZERO, BigInteger::add);
                        waysCount[op][i][j] = waysCount[op][i][j].add(
                                waysCount[op - 1][i][j].multiply(BigInteger.valueOf(
                                        (long) (numLength - 1) * (numLength - 2) / 2)));
                    }
                }
            }
        }

        if (canDivide5) {
            // find all ways for which the number after operations ends with 5
            for (int i = 0; i < indexesOf5.length; i++) {
                int indexOf5 = indexesOf5[i];
                successWaysCount = successWaysCount.add(waysCount[operations][indexOf5][numLength - 1]);
            }
        }

        if (canDivide6) {
            // find all ways for which the number after operations ends with even cipher
            for (int i = 0; i < indexesOfEven.length; i++) {
                int indexOfEven = indexesOfEven[i];
                successWaysCount = successWaysCount.add(waysCount[operations][indexOfEven][numLength - 1]);
            }
        }

        return new BigDecimal(successWaysCount).divide(new BigDecimal(allWaysCount), 11, RoundingMode.FLOOR);
    }
}