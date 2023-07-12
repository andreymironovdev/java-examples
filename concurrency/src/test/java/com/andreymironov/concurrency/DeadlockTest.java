package com.andreymironov.concurrency;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Semaphore;

import static org.awaitility.Awaitility.*;

public class DeadlockTest {
    @Test
    void should_be_in_deadlock() throws InterruptedException {
        Object object1 = new Object();
        Object object2 = new Object();
        Semaphore semaphore = new Semaphore(1);

        Thread thread1 = new Thread(() -> {
            try {
                synchronized (object1) {
                    System.out.println("Acquired lock 1 in thread 1...");
                    semaphore.acquire();
                    System.out.println("Acquired semaphore in thread 1...");
                    synchronized (object2) {
                        System.out.println("Acquired lock 2 in thread 1...");
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (object2) {
                System.out.println("Acquired lock 2 in thread 2...");
                synchronized (object1) {
                    System.out.println("Acquired lock 1 in thread 2...");
                }
            }
        });

        semaphore.acquire();
        thread1.start();
        await().atMost(Duration.of(2, ChronoUnit.SECONDS)).until(() -> thread1.getState() == Thread.State.WAITING);

        thread2.start();
        await().atMost(Duration.of(2, ChronoUnit.SECONDS)).until(() -> thread2.getState() == Thread.State.BLOCKED);
        semaphore.release();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(thread1.getState()).isEqualTo(Thread.State.BLOCKED);
            softAssertions.assertThat(thread2.getState()).isEqualTo(Thread.State.BLOCKED);
        });
    }
}
