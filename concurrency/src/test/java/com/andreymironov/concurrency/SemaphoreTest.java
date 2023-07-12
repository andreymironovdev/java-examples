package com.andreymironov.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.*;

public class SemaphoreTest {
    @Test
    void should_acquire() {
        Semaphore semaphore = new Semaphore(1);

        Runnable runnable = () -> {
            try {
                semaphore.acquire();
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Do some work in " + Thread.currentThread().getName());
                    Thread.sleep(500L);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                semaphore.release();
            }
        };

        Thread thread1 = new Thread(runnable, "first thread");
        Thread thread2 = new Thread(runnable, "second thread");

        thread1.start();
        await().atMost(1, TimeUnit.SECONDS).until(() -> semaphore.availablePermits() == 0);

        thread2.start();
        await().atMost(1, TimeUnit.SECONDS).until(() -> thread2.getState() == Thread.State.WAITING);

        thread1.interrupt();
        await().atMost(1, TimeUnit.SECONDS).until(() -> semaphore.availablePermits() == 0);

        thread2.interrupt();
        await().atMost(1, TimeUnit.SECONDS).until(() -> semaphore.availablePermits() == 1);
    }
}
