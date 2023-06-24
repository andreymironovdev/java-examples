package com.andreymironov.yandex.season.backend;

import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * У Васи есть квадрат, вершины которого расположены в точках с координатами
 * (0,0),(1,0),(0,1),(1,1). В этом квадрате расположены
 * N фишек, i-я фишка имеет координаты (x_i,y_i).
 * Фишки имеют пренебрежительно малые размеры, будем считать их материальными точками.
 * Вася играет в игру. Он выбирает случайную точку с равномерным распределением в своем квадрате.
 * Потом Вася подсчитывает количество фишек, расстояние от которых до выбранной точки не превышает
 * R, и получает соответствующее количество очков.
 * Помогите Васе узнать математическое ожидание количества очков, которые он наберёт в описанной выше игре.
 * Формат ввода
 * В первой строке входных данных содержатся два числа N и R (1≤10001≤N≤1000,0.001≤R≤2).
 * Каждая из следующих N строк содержит разделенные пробелом координаты очередной фишки
 */
public class SquareAndCircleUtils {
    public static void main(String[] args) throws IOException {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String[] numbers = reader.readLine().split(" ");
            int N = Integer.parseInt(numbers[0]);
            double R = Double.parseDouble(numbers[1]);
            double[][] coords = new double[N][2];
            for (int i = 0; i < N; i++) {
                coords[i] = Arrays.stream(reader.readLine().split(" "))
                        .mapToDouble(Double::parseDouble)
                        .toArray();
            }

            writer.write(String.valueOf(getExpectedValue(coords, R)));
        }
    }

    public static BigDecimal getExpectedValue(double[][] coords, double r) {
        BigDecimal expectedValue = BigDecimal.ZERO;
        for (int pointsCount = 1; pointsCount <= coords.length; pointsCount++) {
            BigDecimal expectedValueTerm = BigDecimal.valueOf(pointsCount)
                    .multiply(getProbability(pointsCount, coords, r));
            expectedValue = expectedValue.add(expectedValueTerm);
        }
        return expectedValue;
    }

    public static BigDecimal getProbability(int pointsCount, double[][] coords, double r) {
        BigDecimal probability = BigDecimal.ZERO;

        int[] pointsIndexes = IntStream.range(0, pointsCount).toArray();
        while (true) {
            // calculate intersection of circles of points from pointsIndexes
            // calculate intersection with square
            // subtract from it circles of all other points
            // calculate area and add it to probability
            break;
            //find next (lexigrographical) array of indexes
//            if (pointsIndexes[0] == coords.length - pointsCount) {
//                break;
//            } else if (pointsIndexes[pointsCount - 1] < coords.length - 1) {
//                pointsIndexes[pointsCount - 1]++;
//            } else {
//                int i = pointsCount - 1;
//                while (pointsIndexes[i] == coords.length - 1)
//            }
        }

        return probability;
    }
}
