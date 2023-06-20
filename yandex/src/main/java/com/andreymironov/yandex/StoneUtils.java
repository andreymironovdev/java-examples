package com.andreymironov.yandex;

import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;


public class StoneUtils {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String brilliants = scanner.nextLine();
        Set<Character> brilliantsSet = brilliants.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
        String stones = scanner.nextLine();
        int result = 0;
        for (int i = 0; i < stones.length(); i++) {
            if (brilliantsSet.contains(stones.charAt(i))) {
                result++;
            }
        }

        System.out.println(result);
    }
}
