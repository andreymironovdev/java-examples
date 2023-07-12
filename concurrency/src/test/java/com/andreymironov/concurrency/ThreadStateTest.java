package com.andreymironov.concurrency;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.awaitility.Awaitility.*;

public class ThreadStateTest {
    @Test
    void should_be_interrupted() {
        Thread threadToBeInterrupted = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ex) {
                System.out.println("Was interrupted...");
            }
        });
        threadToBeInterrupted.start();
        threadToBeInterrupted.interrupt();
        Assertions.assertThat(threadToBeInterrupted.isInterrupted()).isTrue();
    }

    @Test
    void should_interrupt_itself() throws InterruptedException {
        Thread threadToBeInterrupted = new Thread(() -> {
            System.out.println("Do some work...");
            Thread.currentThread().interrupt();
        });
        threadToBeInterrupted.start();
        threadToBeInterrupted.join();
        Assertions.assertThat(threadToBeInterrupted.isInterrupted()).isTrue();
    }

    @Test
    void should_be_new() {
        Thread newThread = new Thread(() -> {
            System.out.println("Do some work...");
        });
        Assertions.assertThat(newThread.getState()).isEqualTo(Thread.State.NEW);
    }

    @Test
    void should_be_runnable() {
        Thread newThread = new Thread(() -> {
            while (true) {
                // Do some work...
            }
        });
        newThread.start();
        Assertions.assertThat(newThread.getState()).isEqualTo(Thread.State.RUNNABLE);
    }

    @Test
    void should_be_blocked() {
        Runnable runnable = () -> {
            synchronized (this) {
                while (true) {
                    // Do some work
                }
            }
        };
        Thread threadToBlock = new Thread(runnable);
        Thread threadToBeBlocked = new Thread(runnable);

        threadToBlock.start();
        threadToBeBlocked.start();

        await().atMost(Duration.of(3, ChronoUnit.SECONDS))
                .until(() -> threadToBeBlocked.getState() == Thread.State.BLOCKED);

        Assertions.assertThat(threadToBeBlocked.getState()).isEqualTo(Thread.State.BLOCKED);
    }

    @Test
    void should_be_waiting() {
        Thread threadToWaitFor = new Thread(() -> {
            while (true) {
                // Do some work
            }
        });
        Thread threadToBeWaiting = new Thread(() -> {
            try {
                threadToWaitFor.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        threadToWaitFor.start();
        threadToBeWaiting.start();

        Assertions.assertThat(threadToBeWaiting.getState()).isEqualTo(Thread.State.WAITING);
    }

    @Test
    void should_be_timed_waiting() {
        Thread threadToBeTimedWaiting = new Thread(() -> {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        threadToBeTimedWaiting.start();

        await().atMost(Duration.of(3, ChronoUnit.SECONDS))
                .until(() -> threadToBeTimedWaiting.getState() == Thread.State.TIMED_WAITING);
        Assertions.assertThat(threadToBeTimedWaiting.getState()).isEqualTo(Thread.State.TIMED_WAITING);
    }

    @Test
    void should_be_terminated() {
        Thread threadToBeTerminated = new Thread(() -> {
            System.out.println("Doing some work...");
        });

        threadToBeTerminated.start();

        await().atMost(Duration.of(3, ChronoUnit.SECONDS))
                .until(() -> threadToBeTerminated.getState() == Thread.State.TERMINATED);
        Assertions.assertThat(threadToBeTerminated.getState()).isEqualTo(Thread.State.TERMINATED);
    }
}
