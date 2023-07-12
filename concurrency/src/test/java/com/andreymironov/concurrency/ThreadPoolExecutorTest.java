package com.andreymironov.concurrency;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.awaitility.Awaitility.*;

public class ThreadPoolExecutorTest {
    @Test
    void should_cached_thread_pool_reject_task() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS, new SynchronousQueue<>());

        CountDownLatch latch = new CountDownLatch(1);

        executor.submit(() -> {
            try {
                latch.await();
                System.out.println("Do some work");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Assertions.assertThatCode(() -> executor.submit(() -> {
        })).isInstanceOf(RejectedExecutionException.class);

        latch.countDown();
        await().atMost(1, TimeUnit.SECONDS).until(() -> executor.getActiveCount() == 0);

        Assertions.assertThatCode(() -> executor.submit(() -> {
        })).doesNotThrowAnyException();
    }
}
