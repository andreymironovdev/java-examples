package com.andreymironov.concurrency;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class InstructionsReorderingTest {
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
            ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
            CountDownLatch countDownLatch = new CountDownLatch(2);
            CountDownLatch startingLatch = new CountDownLatch(1);
            AtomicInteger numberAfterUpdate = new AtomicInteger();

            executorService.submit(() -> {
                while (!state.flag) {
                    startingLatch.countDown();
                    Thread.yield();
                }
                numberAfterUpdate.set(state.number);
                countDownLatch.countDown();
            });

            startingLatch.await();

            executorService.submit(() -> {
                state.number = newNumber;
                state.flag = true;
                countDownLatch.countDown();
            });

            executorService.shutdown();
            countDownLatch.await();

            if (numberAfterUpdate.get() != newNumber) {
                System.out.println(
                        "On iteration " + i + " expected = " + newNumber + ", actual = " + numberAfterUpdate.get());
                reordered = true;
                break;
            }
        }

        Assertions.assertThat(reordered).isTrue();
    }
}
