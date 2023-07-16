package com.andreymironov.common.autocloseable;

import java.lang.ref.Cleaner;

public class AutoCloseableResourceWithCleaner implements AutoCloseable {
    private final Cleaner cleaner = Cleaner.create();
    private final Cleaner.Cleanable cleanable;

    public AutoCloseableResourceWithCleaner() {
        Runnable cleanRunnable = () -> {
            System.out.println("Cleaning the state in thread " + Thread.currentThread().getName());
        };
        cleanable = cleaner.register(this, cleanRunnable);
    }

    @Override
    public void close() {
        cleanable.clean();
    }
}
