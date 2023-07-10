package com.andreymironov.concurrency.synchronization;

public class IntStateVolatile implements IntState {
    private volatile int current;

    @Override
    public int getNext() {
        return ++current;
    }

    @Override
    public int getCurrent() {
        return current;
    }
}
