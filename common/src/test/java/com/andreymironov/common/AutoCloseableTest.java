package com.andreymironov.common;

import com.andreymironov.common.autocloseable.AutoCloseableResourceWithCleaner;
import org.junit.jupiter.api.Test;

public class AutoCloseableTest {
    @Test
    void should_invoke_cleanable_without_invoking_close() {
        AutoCloseableResourceWithCleaner resource = new AutoCloseableResourceWithCleaner();

        resource = null;

        System.gc();
    }

    @Test
    void should_invoke_cleanable_with_invoking_close() {
        try (AutoCloseableResourceWithCleaner resource = new AutoCloseableResourceWithCleaner()) {
            System.out.println(resource);
        }
    }
}
