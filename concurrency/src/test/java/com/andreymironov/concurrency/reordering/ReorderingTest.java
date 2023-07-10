package com.andreymironov.concurrency.reordering;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ReorderingTest {
    static class State {
        int number;
        boolean flag;
    }

    @Test
    void should_reorder() throws InterruptedException {
        int newNumber = 1;

        boolean reordered = false;

        for (int i = 0; i < 1000000; i++) {
            State state = new State();
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            CountDownLatch countDownLatch = new CountDownLatch(2);
            AtomicInteger numberAfterUpdate = new AtomicInteger();

            executorService.submit(() -> {
                while (!state.flag) {
                    Thread.yield();
                }
                numberAfterUpdate.set(state.number);
                countDownLatch.countDown();
            });

            executorService.submit(() -> {
                state.number = newNumber;
                state.flag = true;
                countDownLatch.countDown();
            });

            executorService.shutdown();
            countDownLatch.await();

            if (numberAfterUpdate.get() != newNumber) {
                reordered = true;
                break;
            }
        }

        Assertions.assertThat(reordered).isTrue();
    }
}
