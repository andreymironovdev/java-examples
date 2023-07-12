package com.andreymironov.concurrency;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.awaitility.Awaitility.*;

public class DeadlockTest {
    @Test
    void should_simple_threads_be_in_deadlock() {
        Object lock1 = new Object();
        Object lock2 = new Object();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread thread1 = new Thread(() -> {
            try {
                System.out.println("Trying to acquire lock 1 in thread 1...");
                synchronized (lock1) {
                    System.out.println("Acquired lock 1 in thread 1...");
                    System.out.println(" Waiting for countdown in thread 1...");
                    countDownLatch.await();
                    System.out.println(" Countdown occured, thread 1 is unblocked...");
                    System.out.println("Trying to acquire lock 2 in thread 1...");
                    synchronized (lock2) {
                        System.out.println("Acquired lock 2 in thread 1...");
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("Trying to acquire lock 2 in thread 2...");
            synchronized (lock2) {
                System.out.println("Acquired lock 2 in thread 2...");
                System.out.println("Trying to acquire lock 1 in thread 2...");
                synchronized (lock1) {
                    System.out.println("Acquired lock 1 in thread 2...");
                }
            }
        });

        thread1.start();
        await().atMost(Duration.of(2, ChronoUnit.SECONDS)).until(() -> thread1.getState() == Thread.State.WAITING);

        thread2.start();
        await().atMost(Duration.of(2, ChronoUnit.SECONDS)).until(() -> thread2.getState() == Thread.State.BLOCKED);
        countDownLatch.countDown();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(thread1.getState()).isEqualTo(Thread.State.BLOCKED);
            softAssertions.assertThat(thread2.getState()).isEqualTo(Thread.State.BLOCKED);
        });
    }

    @Test
    void should_pool_tasks_be_in_deadlock() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Semaphore calculatingSemaphore = new Semaphore(1);
        Semaphore savingSemaphore = new Semaphore(1);
        CountDownLatch latch = new CountDownLatch(1);

        Runnable calculatingAndSavingRunnable = () -> {
            try {
                calculatingSemaphore.acquire();
                latch.await();
                savingSemaphore.acquire();
                System.out.println("Calculating and saving results...");
                calculatingSemaphore.release();
                savingSemaphore.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable savingAndCalculatingRunnable = () -> {
            try {
                savingSemaphore.acquire();
                calculatingSemaphore.acquire();
                System.out.println("Saving and calculating results...");
                savingSemaphore.release();
                calculatingSemaphore.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        executorService.submit(calculatingAndSavingRunnable);
        executorService.submit(savingAndCalculatingRunnable);
        await().atMost(1, TimeUnit.SECONDS).until(()-> calculatingSemaphore.availablePermits() == 0);
        await().atMost(1, TimeUnit.SECONDS).until(()-> savingSemaphore.availablePermits() == 0);
        latch.countDown();
        executorService.shutdown();

        Assertions.assertThat(executorService.awaitTermination(3, TimeUnit.SECONDS)).isFalse();
    }

    @Test
    void should_be_dynamic_deadlock() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        BiFunction<Object, Object, Runnable> runnableBiFunction = (lock1, lock2) -> () -> {
            try {
                synchronized (lock1) {
                    latch.await();
                    synchronized (lock2) {
                        System.out.println("Do some work");
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Object lock1 = new Object();
        Object lock2 = new Object();

        executorService.submit(runnableBiFunction.apply(lock1, lock2));
        executorService.submit(runnableBiFunction.apply(lock2, lock1));
        latch.countDown();
        executorService.shutdown();
        Assertions.assertThat(executorService.awaitTermination(3, TimeUnit.SECONDS)).isFalse();
    }
}
