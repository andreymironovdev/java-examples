package com.andreymironov.concurrency.list;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

public class ListTest {
    @Test
    void should_fail_on_simple_list_concurrent_modification() {
        List<Integer> simpleList = new java.util.ArrayList<>(IntStream.range(0, 100)
                .boxed()
                .toList());

        new Thread(() -> {
            while (true) {
                simpleList.add(0);
            }
        }).start();

        Assertions.assertThatCode(() -> {
            for (int i : simpleList) {
                System.out.println(i);
            }
        }).isInstanceOf(ConcurrentModificationException.class);
    }

    @Test
    void should_not_fail_on_copy_write_array_list_concurrent_modification() {
        List<Integer> threadSafeList = new CopyOnWriteArrayList<>(IntStream.range(0, 100)
                .boxed()
                .toList());

        new Thread(() -> {
            while (true) {
                threadSafeList.add(0);
            }
        }).start();

        Assertions.assertThatCode(() -> {
            for (int i : threadSafeList) {
                System.out.println(i);
            }
        }).doesNotThrowAnyException();
    }
}
