package com.andreymironov.algorithms;

import java.util.*;

public class ParenthesisUtils {
    /**
     * @param s string consists of symbols (,),{,},[,]
     * @return true if s is a valid parenthesis sequence
     */
    public static boolean isValid(String s) {
        Map<Character, Character> closingToOpeningMap = Map.of(')', '(', '}', '{', ']', '[');
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (closingToOpeningMap.containsKey(c)) {
                if (stack.isEmpty()) {
                    return false;
                }

                Character openingChar = closingToOpeningMap.get(c);
                Character last = stack.pop();
                if (!last.equals(openingChar)) {
                    return false;
                }
            } else if (closingToOpeningMap.containsValue(c)) {
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }

    /**
     * @param s contains (,) and letters
     * @return list of all substrings, which are valid parenthesis structures
     */
    public static List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        int foundLength = 0;
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(s);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (visited.contains(current) || current.length() < foundLength) {
                continue;
            }

            visited.add(current);

            if (isValid(current)) {
                result.add(current);
                foundLength = current.length();
                continue;
            }

            for (int i = 0; i < current.length(); i++) {
                char c = current.charAt(i);
                if (c == '(' || c == ')') {
                    String candidate = current.substring(0, i) + current.substring(i + 1);
                    queue.offer(candidate);
                }
            }
        }
        return result;
    }
}
