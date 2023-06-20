package com.andreymironov.yandex;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Yandex task
 * input has the following rows:
 * the first row contains two numbers divided by space (n k),
 * where n is the rate (maximum number of requests per 1 second allowed for user),
 * and k is the number of log rows
 * the rest k rows are the logs, and each log consists of 2 values divided by space - unix timestamp millis and user token
 * We need to output all logs of prohibited requests
 */
public class RateUtils {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String countsRow = scanner.nextLine();
        String[] counts = countsRow.split(" ");

        int rate = Integer.parseInt(counts[0]);
        int logsCount = Integer.parseInt(counts[1]);
        Map<String, ArrayDeque<Long>> lastUserTimestamps = new HashMap<>();

        for (int i = 0; i < logsCount; i++) {
            String log = scanner.nextLine();

            String[] split = log.split(" ");
            long timestamp = Long.parseLong(split[0]);
            String token = split[1];

            if (!lastUserTimestamps.containsKey(token)) {
                lastUserTimestamps.put(token, new ArrayDeque<>());
            }

            ArrayDeque<Long> deque = lastUserTimestamps.get(token);
            deque.offerFirst(timestamp);

            while (deque.peekFirst() - deque.peekLast() >= 1000) {
                deque.pollLast();
            }

            if (deque.size() <= rate) {
                System.out.println(log);
            } else {
                deque.pollFirst();
            }
        }
    }
}
