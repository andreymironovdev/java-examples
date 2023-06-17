package com.andreymironov.leetcode;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class ValidateRequestUtils {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String countRow = scanner.nextLine();
        int count = Integer.parseInt(countRow);

        for (int i = 0; i < count; i++) {
            String request = scanner.nextLine();
            System.out.println(isValid(request) ? "valid" : "invalid");
        }
    }

    private static boolean isValid(String request) {
        Queue<Character> stack = new ArrayDeque<>();

        boolean insideTag = false;
        boolean insideGroup = false;
        boolean insidePredicate = false;

        for (int i = 0; i < request.length(); i++) {
            char c = request.charAt(i);

            if (c == '{') {
                stack.offer(c);
                insideTag = true;
            } else if (c == '[') {
                stack.offer(c);
                insidePredicate = true;
            } else if (c == '(') {
                stack.offer(c);
                insideGroup = true;
            } else if (c == '}') {
                if (!insideTag) {
                    return false;
                }
                if (!stack.poll().equals('t')) {
                    return false;
                }
                if (!stack.poll().equals('{')) {
                    return false;
                }
            } else if (c == ']') {
                if (!stack.peek().equals('{')) {
                    return false;
                }
            } else if (c == ')') {
                if (!stack.peek().equals('{')) {
                    return false;
                }
            } else {
            }
        }

        return true;
    }
}
