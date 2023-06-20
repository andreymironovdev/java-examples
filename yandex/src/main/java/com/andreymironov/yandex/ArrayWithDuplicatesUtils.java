package com.andreymironov.yandex;

import java.util.Scanner;

public class ArrayWithDuplicatesUtils {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        if (n == 0) {
            return;
        }
        int current = s.nextInt();
        StringBuilder res = new StringBuilder();
        res.append(current);
        res.append("\n");
        int previous = current;

        for (int i = 1; i < n; i++) {
            current = s.nextInt();
            if (current != previous) {
                res.append(current);
                res.append("\n");
            }
            previous = current;
        }

        System.out.println(res);
    }
}