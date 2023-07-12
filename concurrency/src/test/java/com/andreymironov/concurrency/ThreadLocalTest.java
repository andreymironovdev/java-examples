package com.andreymironov.concurrency;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ThreadLocalTest {
    @Test
    void should_use_thread_locals() {
        int tasksCount = 100;
        int threadsCount = 10;

        AtomicInteger atomicInteger = new AtomicInteger();
        ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(atomicInteger::incrementAndGet);

        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);

        List<Future<Integer>> futures = IntStream.range(0, tasksCount)
                .mapToObj(i -> executorService.submit(threadLocal::get))
                .toList();

        List<Integer> threadLocalValues = futures.stream().map(f -> {
                    try {
                        return f.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .distinct()
                .toList();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(threadLocalValues)
                    .containsExactlyInAnyOrder(IntStream.rangeClosed(1, threadsCount).boxed().toArray(Integer[]::new));
            softAssertions.assertThat(threadLocal.get()).isEqualTo(threadsCount + 1);
        });
    }
}
