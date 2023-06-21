package com.andreymironov.yandex;

import java.io.*;
import java.util.Arrays;

/**
 * В каждой клетке прямоугольной таблицы N×M записано некоторое число.
 * Изначально игрок находится в левой верхней клетке.
 * За один ход ему разрешается перемещаться в соседнюю клетку либо вправо, либо вниз (влево и вверх перемещаться запрещено).
 * При проходе через клетку с игрока берут столько килограммов еды, какое число записано в этой клетке
 * (еду берут также за первую и последнюю клетки его пути).
 * Требуется найти минимальный вес еды в килограммах, отдав которую игрок может попасть в правый нижний угол.
 */
public class CheapestWayUtils {
    public static void main(String[] args) throws IOException {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String[] sizes = reader.readLine().split(" ");
            int n = Integer.parseInt(sizes[0]);
            int m = Integer.parseInt(sizes[1]);
            int[][] table = new int[n][m];
            for (int i = 0; i < n; i++) {
                table[i] = Arrays.stream(reader.readLine().split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();
            }

            writer.write(String.valueOf(getMinPrice(table)));
        }
    }

    public static int getMinPrice(int[][] table) {
        int n = table.length;
        int m = table[0].length;
        int[][] minPrices = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int addition = i == 0 && j == 0
                        ? 0
                        : i == 0
                        ? minPrices[0][j - 1]
                        : j == 0
                        ? minPrices[i - 1][0]
                        : Math.min(minPrices[i - 1][j], minPrices[i][j - 1]);
                minPrices[i][j] = table[i][j] + addition;
            }
        }

        return minPrices[n - 1][m - 1];
    }
}
