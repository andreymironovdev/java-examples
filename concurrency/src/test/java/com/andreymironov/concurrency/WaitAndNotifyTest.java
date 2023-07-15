package com.andreymironov.concurrency;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.awaitility.Awaitility.*;

public class WaitAndNotifyTest {
    @Test
    void should_wait() {
        Object object = new Object();

        Thread waitingThread = new Thread(() -> {
            try {
                System.out.println("Started waiting thread...");
                synchronized (object) {
                    System.out.println("Acquired lock in waiting thread...");
                    object.wait();
                    System.out.println("Finished waiting...");
                }
                System.out.println("Exited synchronized block in waiting thread...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        waitingThread.start();

        await().atMost(1, TimeUnit.SECONDS).until(() -> waitingThread.getState() == Thread.State.WAITING);

        Thread notifyingThread = new Thread(() -> {
            System.out.println("Started notifying thread...");
            synchronized (object) {
                System.out.println("Acquired lock in notifying thread...");
                object.notify();
                System.out.println("Finished notifying...");
            }
            System.out.println("Exited synchronized block in notifying thread...");
        });
        notifyingThread.start();

        await().atMost(1, TimeUnit.SECONDS).until(() -> notifyingThread.getState() == Thread.State.TERMINATED);
        await().atMost(1, TimeUnit.SECONDS).until(() -> waitingThread.getState() == Thread.State.TERMINATED);
    }

    @Test
    void should_throw_on_wait() throws InterruptedException {
        Object object = new Object();
        AtomicReference<Throwable> exceptionContainer = new AtomicReference<>();

        Thread waitingThread = new Thread(() -> {
            try {
                object.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        waitingThread.setUncaughtExceptionHandler((t, e) -> exceptionContainer.set(e));
        waitingThread.start();
        waitingThread.join();

        Assertions.assertThat(exceptionContainer.get())
                .isInstanceOf(IllegalMonitorStateException.class).hasMessageContaining("current thread is not owner");
    }
}
