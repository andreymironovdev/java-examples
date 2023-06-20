package com.andreymironov.yandex;


import java.io.*;
import java.util.Arrays;

/**
 * В дощечке в один ряд вбиты гвоздики. Любые два гвоздика можно соединить ниточкой.
 * Требуется соединить некоторые пары гвоздиков ниточками так,
 * чтобы к каждому гвоздику была привязана хотя бы одна ниточка, а суммарная длина всех ниточек была минимальна.
 */
public class PinsUtils {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        int[] coordinates = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        reader.close();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.valueOf(getMinLength(coordinates)));
        writer.close();
    }

    public static int getMinLength(int[] coordinates) {
        int n = coordinates.length;

        if (n == 0) {
            return 0;
        }

        Arrays.sort(coordinates);

        // minLengths[i] = min length for array coordinates[0], ... ,coordinates[i-1]
        int[] minLengths = new int[n];

        minLengths[1] = coordinates[1] - coordinates[0];

        if (n < 3) {
            return minLengths[n - 1];
        }

        minLengths[2] = coordinates[2] - coordinates[0];

        for (int i = 3; i < n; i++) {
            // if we add i-th pin, then we must connect i with i-1,
            // and we have 2 possibilities - i-1 is connected with i-2, or not,
            // so min length of 0,...,i-1 is a minimum of these cases
            minLengths[i] = coordinates[i] - coordinates[i - 1] + Math.min(minLengths[i - 1], minLengths[i - 2]);
        }

        return minLengths[n - 1];
    }
}
