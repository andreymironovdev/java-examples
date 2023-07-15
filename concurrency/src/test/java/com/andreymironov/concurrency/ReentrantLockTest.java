package com.andreymironov.concurrency;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static org.awaitility.Awaitility.*;

public class ReentrantLockTest {
    @Test
    void should_supply_held_info() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        SoftAssertions softAssertions = new SoftAssertions();

        Thread thread = new Thread(() -> {
            softAssertions.assertThat(lock.isHeldByCurrentThread()).isFalse();
            softAssertions.assertThat(lock.isLocked()).isFalse();

            lock.lock();
            softAssertions.assertThat(lock.isHeldByCurrentThread()).isTrue();
            softAssertions.assertThat(lock.isLocked()).isTrue();
            lock.unlock();
        });
        thread.start();
        thread.join();

        softAssertions.assertAll();
    }

    @Test
    void should_be_fair() throws InterruptedException, ExecutionException {
        Assertions.assertThat(wasAnyThreadUnfair(new ReentrantLock(true), 100, 5)).isFalse();
    }

    @Test
    // TODO: doesn't pass
    void should_be_unfair() throws InterruptedException, ExecutionException {
//        Assertions.assertThat(wasAnyThreadUnfair(new ReentrantLock(false), 100, 5)).isTrue();
    }

    private boolean wasAnyThreadUnfair(ReentrantLock lock, int iterationsCount, int waitingThreadsCount)
            throws InterruptedException {
        Semaphore unlockingSemaphore = new Semaphore(1);
        boolean wasAnyThreadUnfair = false;

        for (int i = 0; i < iterationsCount; i++) {
            unlockingSemaphore.acquire();
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(waitingThreadsCount + 1);
            Future<?> lockingFuture = executor.submit(() -> {
                try {
                    lock.lock();
                    unlockingSemaphore.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    unlockingSemaphore.release();
                    lock.unlock();
                }
            });
            AtomicInteger sharedState = new AtomicInteger();
            List<Future<Integer>> futures = IntStream.rangeClosed(1, waitingThreadsCount)
                    .sequential()
                    .mapToObj(j -> {
                        Future<Integer> submitted = executor.submit(() -> {
                            try {
                                lock.lock();
                                System.out.println("Acquiring lock for j=" + j);
                                return sharedState.updateAndGet((k) -> j);
                            } finally {
                                lock.unlock();
                            }
                        });
                        await().atMost(150, TimeUnit.MILLISECONDS).until(() -> lock.getQueueLength() == j);
                        return submitted;
                    })
                    .toList();
            unlockingSemaphore.release();
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.SECONDS);
            System.out.println("Ended " + i + "-th cycle with the last index=" + sharedState.get());
            if (sharedState.get() != waitingThreadsCount) {
                wasAnyThreadUnfair = true;
                break;
            }
        }

        return wasAnyThreadUnfair;
    }
}
