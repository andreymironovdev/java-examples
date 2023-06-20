package com.andreymironov.yandex;

import java.util.Scanner;


public class BinaryArrayUtils {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int result = 0;
        int currentResult = 0;
        for (int i = 0; i < n; i++) {
            int current = scanner.nextInt();

            if (current == 1) {
                currentResult++;
                result = Math.max(result, currentResult);
            } else {
                currentResult = 0;
            }
        }

        System.out.println(result);
    }
}
