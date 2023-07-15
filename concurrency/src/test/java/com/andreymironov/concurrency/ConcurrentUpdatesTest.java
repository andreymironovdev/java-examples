package com.andreymironov.concurrency;

import com.andreymironov.concurrency.synchronization.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class ConcurrentUpdatesTest {

    public static final int UPDATES_COUNT = 1000;

    @Test
    void should_simple_not_be_thread_safe() throws InterruptedException {
        IntState intState = new IntStateSimple();
        joinConcurrentUpdates(intState);
        Assertions.assertThat(intState.getCurrent()).isLessThan(UPDATES_COUNT);
    }


    @Test
    void should_volatile_not_be_thread_safe() throws InterruptedException {
        IntState intState = new IntStateVolatile();
        joinConcurrentUpdates(intState);
        Assertions.assertThat(intState.getCurrent()).isLessThan(UPDATES_COUNT);
    }

    @Test
    void should_volatile_cas_be_thread_safe() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        IntState intState = new IntStateVolatileUnsafeCAS();
        joinConcurrentUpdates(intState);
        Assertions.assertThat(intState.getCurrent()).isEqualTo(UPDATES_COUNT);
    }

    @Test
    void should_synchronized_be_thread_safe() throws InterruptedException {
        IntState intState = new IntStateSynchronized();
        joinConcurrentUpdates(intState);
        Assertions.assertThat(intState.getCurrent()).isEqualTo(UPDATES_COUNT);
    }

    @Test
    void should_explicit_lock_be_thread_safe() throws InterruptedException {
        IntState intState = new IntStateExplicitLock();
        joinConcurrentUpdates(intState);
        Assertions.assertThat(intState.getCurrent()).isEqualTo(UPDATES_COUNT);
    }

    @Test
    void should_atomic_be_thread_safe() throws InterruptedException {
        IntState intState = new IntStateAtomic();
        joinConcurrentUpdates(intState);
        Assertions.assertThat(intState.getCurrent()).isEqualTo(UPDATES_COUNT);
    }

    private void joinConcurrentUpdates(IntState intState) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(UPDATES_COUNT);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        IntStream
                .range(0, UPDATES_COUNT)
                .forEach(i -> executorService.submit(() -> {
                    intState.getNext();
                    countDownLatch.countDown();
                }));

        countDownLatch.await();
        executorService.shutdown();
    }
}
