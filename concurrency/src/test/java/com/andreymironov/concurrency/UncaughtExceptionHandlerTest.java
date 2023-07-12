package com.andreymironov.concurrency;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

public class UncaughtExceptionHandlerTest {
    @Test
    void should_handle() {
        String expectedThreadName = "throwing thread";
        RuntimeException expectedEx = new RuntimeException("Oops!!!");

        Thread thread = new Thread(() -> {
            throw expectedEx;
        }, expectedThreadName);

        AtomicReference<Throwable> actualEx = new AtomicReference<>();
        AtomicReference<String> actualName = new AtomicReference<>();

        thread.setUncaughtExceptionHandler((t, e) -> {
            actualEx.set(e);
            actualName.set(t.getName());
        });

        thread.start();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(actualEx.get()).isEqualTo(expectedEx);
            softAssertions.assertThat(actualName.get()).isEqualTo(expectedThreadName);
        });
    }
}
