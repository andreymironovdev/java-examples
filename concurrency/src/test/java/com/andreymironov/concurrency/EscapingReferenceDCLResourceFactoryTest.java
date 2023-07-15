package com.andreymironov.concurrency;

import com.andreymironov.concurrency.doublecheckedlocking.EscapingReferenceDCLResourceFactory;
import com.andreymironov.concurrency.doublecheckedlocking.EscapingReferenceResource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class EscapingReferenceDCLResourceFactoryTest {
    @Test
    void should_see_partial_created() throws InterruptedException {
        int iterationsCount = 100;
        int threadsCount = 5;
        AtomicBoolean partiallyCreatedVisibleDetected = new AtomicBoolean();

        for (int i = 0; i < iterationsCount; i++) {
            EscapingReferenceDCLResourceFactory factory = new EscapingReferenceDCLResourceFactory();

            Runnable runnable = () -> {
                EscapingReferenceResource escapingReferenceResource = factory.getResource();
                if (escapingReferenceResource.getName() == null || escapingReferenceResource.getValue() == 0) {
                    partiallyCreatedVisibleDetected.set(true);
                }
            };

            List<Thread> threads = IntStream.range(0, threadsCount)
                    .mapToObj(j -> {
                        Thread thread = new Thread(runnable);
                        thread.start();
                        return thread;
                    })
                    .toList();

            for (Thread thread : threads) {
                thread.join();
            }

            if (partiallyCreatedVisibleDetected.get()) {
                break;
            }
        }

        Assertions.assertThat(partiallyCreatedVisibleDetected.get()).isTrue();
    }
}
