package com.andreymironov.algorithms;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class ParenthesisUtils {
    /**
     * @param s string consists of symbols (,),{,},[,]
     * @return true if s is a valid parenthesis sequence
     */
    public static boolean isValid(String s) {
        Map<Character, Character> closingToOpeningMap = Map.of(')', '(', '}', '{', ']', '[');
        Deque<Character> stack = new ArrayDeque<>();

        for (int i =0; i < s.length(); i++) {
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
            } else {
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }
}
