package com.andreymironov.concurrency.synchronization;

import java.util.concurrent.atomic.AtomicInteger;

public class IntStateAtomic implements IntState {
    private final AtomicInteger current = new AtomicInteger();

    @Override
    public int getNext() {
        return current.incrementAndGet();
    }

    @Override
    public int getCurrent() {
        return current.get();
    }
}
